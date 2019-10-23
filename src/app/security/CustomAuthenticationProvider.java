package app.security;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import app.Classificator;
import app.model.CaptchaResponseDTO;
import app.model.ExtendedUser;
import app.model.IPHistory;
import app.model.LoginQuery;
import app.service.ExtendedUserManager;
import app.service.IPHistoryManager;
import app.service.LoginQueryManager;
import app.service.MailSender;
import app.service.SMSSender;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

	@Value("${recaptcha.site}")
	private String site;

	@Autowired
	private ExtendedUserManager manager;

	@Autowired
	private IPHistoryManager ipHistoryManager;

	@Autowired
	private LoginQueryManager loginQueryManager;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private MailSender emailSender;

	@Autowired
	private SMSSender smsSender;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		CustomWebAuthenticationDetails customDetails = (CustomWebAuthenticationDetails) authentication.getDetails();

		String username = authentication.getName();
		String password = authentication.getCredentials().toString();

		String verificationCode = customDetails.getVerificationCode();
		String captchaResponse = customDetails.getCaptchaResponse();

		Collection<GrantedAuthority> authorities = new HashSet<>();

		if (username.equals("") && SecurityContextHolder.getContext().getAuthentication() != null) {
			username = SecurityContextHolder.getContext().getAuthentication().getName();
		}

		ExtendedUser user = manager.getByName(username);
		IPHistory ipHistory = ipHistoryManager.getIPByAddr(customDetails.getIp());

		LoginQuery query = new LoginQuery();
		query.setUsername(username);
		query.setIp(customDetails.getIp());

		if (SecurityContextHolder.getContext().getAuthentication() != null) {
			String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();

			if (role.contains("ROLE_PRE_AUTH")) {
				if (role.contains("ROLE_PRE_AUTH_BOT")) {// captcha
					if (!verifyCaptcha(captchaResponse)) {
						throw new BadCredentialsException(messages.getMessage(
								"AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
					}
				}
				if (role.contains("ROLE_PRE_AUTH_THIEF")) {// code
					if (user.getUserPhone() != null) {//
						if (!verificationCode.equals(user.getPhone2faCode())) {
							throw new BadCredentialsException(messages.getMessage(
									"AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
						}
					} else {
						if (!verificationCode.equals(user.getEmail2faCode())) {
							throw new BadCredentialsException(messages.getMessage(
									"AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
						}
					}
				}
				if (role.contains("ROLE_PRE_AUTH_FORGOT")) {//
					if (!password.equals(user.getPassword())) {
						throw new BadCredentialsException(messages.getMessage(
								"AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
					}
				}

				authorities.addAll(user.getAuthorities());

				query.setQueryClass(authorities.toString());

				loginQueryManager.addQuery(query);

			}
		} else {
			if (user == null) {
				throw new BadCredentialsException(messages
						.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
			}

			int userClass = classify(user, customDetails, ipHistory);
			System.out.println(userClass);

			if (!password.equals(user.getPassword())) {

				ipHistoryManager.setTryNumber(customDetails.getIp(), ipHistory.getTryNumber() + 1);

				if (user.getUserSuccPeriod() != 0) {
					manager.setPeriod(username, 0);
				}

				if (userClass == 2) {
					authorities.add(new SimpleGrantedAuthority("ROLE_PRE_AUTH_FORGOT"));
				}
				if (userClass == 3) {

				}
				if (userClass != 3 && userClass != 2) {
					throw new BadCredentialsException(messages
							.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
				}
			}

			switch (userClass) {
			case 0:
				authorities.add(new SimpleGrantedAuthority("ROLE_PRE_AUTH_BOT"));
				break;
			case 1:
				authorities.add(new SimpleGrantedAuthority("ROLE_PRE_AUTH_THIEF"));
				break;
			case 2:
				authorities.add(new SimpleGrantedAuthority("ROLE_PRE_AUTH_FORGOT"));
				break;
			case 3:// supervalid
				authorities.addAll(user.getAuthorities());
				break;
			case 4:
				authorities.addAll(user.getAuthorities());
				break;
			}

			query.setQueryClass(authorities.toString());

			if (userClass == 1) {
				String code = Integer.toString(1000 + (new Random()).nextInt(8999));
				if (user.getUserPhone() == null) {// email
					emailSender.send(user.getUserEmail(), "Verification Code",
							"We detected an attempt to login from your account.\nPlease enter the code to make sure that it is you.\nYour code: "
									+ code);
					manager.setEmail2faCode(user.getUsername(), code);
				} else {// phone
					smsSender.sendSMS("+" + user.getUserPhone(), "Your verification code: " + code);
					manager.setPhone2faCode(user.getUsername(), code);
				}
			}

			loginQueryManager.addQuery(query);
		}

		manager.setPeriod(username, user.getUserSuccPeriod() + 1);
		int entryTimes[] = user.getUserEnterTime();
		int curTime = (new Date()).getHours();
		for (int i : entryTimes) {
			if (!(i == curTime || i == curTime - 1 || i == curTime + 1)) {
				entryTimes = Arrays.copyOf(entryTimes, entryTimes.length + 1);
				entryTimes[entryTimes.length - 1] = curTime;
				break;
			}
		}
		manager.setEntryTimes(username, entryTimes);

		return new UsernamePasswordAuthenticationToken(user, user.getPassword(), authorities);
	}

	public boolean verifyCaptcha(String captchaResponse) {
		String url = String.format("https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s", site,
				captchaResponse);

		CaptchaResponseDTO googleResponse = null;
		try {
			googleResponse = (new ObjectMapper()).readValue(restTemplate.postForObject(url, "", String.class),
					CaptchaResponseDTO.class);
		} catch (RestClientException | IOException e) {
			e.printStackTrace();
		}
		return googleResponse.isSuccess();
	}

	public int classify(ExtendedUser user, CustomWebAuthenticationDetails customDetails, IPHistory ipHistory) {
		int ip = user.getUserIP().equals(customDetails.getIp()) ? 1 : 0;
		int headers = customDetails.isValidHeaders() ? 1 : 0;
		int cookie = customDetails.isValidCookie() ? 1 : 0;
		int tryNum = ipHistory.getTryNumber();
		int js = customDetails.isJs() ? 1 : 0;
		int userAgent = customDetails.isValidUserAgent() ? 1 : 0;
		int reqPerMin = ipHistory.getReqPerMin();
		int period = user.getUserSuccPeriod();

		int entryTimes[] = user.getUserEnterTime();
		int entryTime = 0;
		int curTime = (new Date()).getHours();
		for (int i : entryTimes) {
			if (i == curTime) {
				entryTime = 1;
			}
		}

		System.out.println(1 + ", " + ip + ", " + headers + ", " + cookie + ", " + tryNum + ", " + js + ", " + userAgent
				+ ", " + reqPerMin + ", " + period + ", " + entryTime);

		return new Classificator().classify(1, ip, headers, cookie, tryNum, js, userAgent, reqPerMin, period,
				entryTime);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}
