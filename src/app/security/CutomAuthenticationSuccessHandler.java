package app.security;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class CutomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		String role = authentication.getAuthorities().toString();

		System.out.println(role);

		if (role.contains("BOT") || role.contains("THIEF") || role.contains("FORGOT")) {
			redirectStrategy.sendRedirect(request, response, "/loginpage");
		} else {

			Cookie cookie = new Cookie("username", authentication.getName());
			cookie.setMaxAge(164 * 60 * 60);
			response.addCookie(cookie);

			redirectStrategy.sendRedirect(request, response, "/");
		}
	}
}