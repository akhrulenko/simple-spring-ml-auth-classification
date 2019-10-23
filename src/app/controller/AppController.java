package app.controller;

import java.util.Date;
import java.util.Random;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import app.model.ExtendedUser;
import app.model.IPHistory;
import app.service.ExtendedUserManager;
import app.service.IPHistoryManager;
import app.service.LoginQueryManager;

@Controller
public class AppController {

	@Autowired
	private ExtendedUserManager userManager;

	@Autowired
	private IPHistoryManager ipHistoryManager;

	@Autowired
	private LoginQueryManager loginQueryManager;

	@GetMapping("/")
	public ModelAndView sayHello(SecurityContextHolderAwareRequestWrapper requestWrapper) {

		ExtendedUser user = userManager.getByName(requestWrapper.getUserPrincipal().getName());

		if (requestWrapper.isUserInRole("ROLE_ADMIN")) {
			ModelAndView model = new ModelAndView("adminpage");
			model.addObject("queryList", loginQueryManager.getLoginQueries());
			return model;
		}

		ModelAndView model = new ModelAndView("welcome");
		model.addObject("userName", user.getUsername());
		model.addObject("userIP", user.getUserIP());
		model.addObject("userPhone", user.getUserPhone());
		model.addObject("userEmail", user.getUserEmail());
		return model;
	}

	@GetMapping("/loginpage")
	public ModelAndView loginpage(HttpServletRequest request, HttpServletResponse response,
			SecurityContextHolderAwareRequestWrapper requestWrapper) {

		Cookie cookie = new Cookie("bonus", "4#5645*%675s877@%%");
		cookie.setMaxAge(164 * 60 * 60);
		response.addCookie(cookie);

		setReqPerMin(request.getRemoteAddr());

		ModelAndView modelAndView = new ModelAndView("loginpage");
		if (requestWrapper.isUserInRole("ROLE_PRE_AUTH_BOT")) {
			modelAndView.addObject("captcha", true);
			modelAndView.addObject("code", false);
			return modelAndView;
		}
		if (requestWrapper.isUserInRole("ROLE_PRE_AUTH_THIEF")) {
			modelAndView.addObject("code", true);
			modelAndView.addObject("captcha", false);
			return modelAndView;
		}
		if (requestWrapper.isUserInRole("ROLE_PRE_AUTH_FORGOT")) {
			modelAndView.addObject("forgot", true);
			modelAndView.addObject("code", false);
			modelAndView.addObject("captcha", false);
			return modelAndView;
		}

		int jscode = (new Random()).nextInt();
		modelAndView.addObject("jscode", jscode);
		modelAndView.addObject("code", false);
		modelAndView.addObject("captcha", false);

		return modelAndView;
	}

	@GetMapping("/logout")
	public String logout() {
		SecurityContextHolder.clearContext();
		return "redirect:/loginpage";
	}

	@PostMapping("/loginpage")
	public String login() {
		return "loginpage";
	}

	public void setReqPerMin(String address) {
		IPHistory ipHistory = ipHistoryManager.getIPByAddr(address);
		if (ipHistory == null) {
			ipHistoryManager
					.addIP(new IPHistory(address, 0, 1, new Date().getTime() / 1000, new Date().getTime() / 1000));

		} else {
			long endTime = new Date().getTime() / 1000;
			int period = (int) ((endTime - ipHistory.getStartDate()) / 60);

			if (period >= 1) {
				ipHistoryManager.updateDates(address, new Date().getTime() / 1000, new Date().getTime() / 1000);
				ipHistoryManager.setReqPerMin(address, ipHistory.getReqPerMin() / period + 1);
			} else {
				ipHistoryManager.setReqPerMin(address, ipHistory.getReqPerMin() + 1);
				ipHistoryManager.updateDates(address, ipHistory.getStartDate(), endTime);
			}
		}
	}
}