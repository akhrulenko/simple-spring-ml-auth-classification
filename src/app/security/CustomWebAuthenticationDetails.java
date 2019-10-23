package app.security;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

@SuppressWarnings("serial")
public class CustomWebAuthenticationDetails extends WebAuthenticationDetails {

	private String verificationCode;
	private String captchaResponse;

	private String ip;
	private boolean validHeaders;
	private boolean validCookie;
	private boolean js;
	private boolean validUserAgent;

	public CustomWebAuthenticationDetails(HttpServletRequest request) {

		super(request);

		this.verificationCode = request.getParameter("code");
		this.captchaResponse = request.getParameter("g-recaptcha-response");

		this.ip = request.getRemoteAddr();

		if (request.getHeader("X-Forwarded-For") != null || request.getHeader("Upgrade-Insecure-Requests") == null
				|| request.getHeader("DNT") == null || request.getHeader("Accept-Language") == null) {
			validHeaders = false;
		} else {
			validHeaders = true;
		}

		if (request.getCookies().toString().contains("bonus")) {
			validCookie = true;
		} else {
			validCookie = false;
		}

		if (request.getParameter("jscode") == null) {
			js = false;
		} else {
			js = true;
		}

		Pattern pattern = Pattern.compile(
				"(bot|crawl|archiver|transcoder|spider|uptime|validator|fetcher|cron|checker|reader|extractor|monitoring|analyzer)");
		Matcher matcher = pattern.matcher(request.getHeader("user-agent"));
		if (matcher.find()) {
			validUserAgent = false;
		} else {
			validUserAgent = true;
		}

	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public String getCaptchaResponse() {
		return captchaResponse;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public boolean isValidHeaders() {
		return validHeaders;
	}

	public void setValidHeaders(boolean validHeaders) {
		this.validHeaders = validHeaders;
	}

	public boolean isValidCookie() {
		return validCookie;
	}

	public void setValidCookie(boolean validCookie) {
		this.validCookie = validCookie;
	}

	public boolean isJs() {
		return js;
	}

	public void setJs(boolean js) {
		this.js = js;
	}

	public boolean isValidUserAgent() {
		return validUserAgent;
	}

	public void setValidUserAgent(boolean validUserAgent) {
		this.validUserAgent = validUserAgent;
	}
}