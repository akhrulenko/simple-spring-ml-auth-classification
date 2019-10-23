package app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import app.security.CustomAuthenticationProvider;
import app.security.CustomWebAuthenticationDetailsSource;
import app.security.CutomAuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	CustomAuthenticationProvider provider;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(provider);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin().loginPage("/loginpage").permitAll()
				.authenticationDetailsSource(new CustomWebAuthenticationDetailsSource())
				.successHandler(new CutomAuthenticationSuccessHandler()).and().logout().invalidateHttpSession(true)
				.logoutSuccessUrl("/loginpage").and().authorizeRequests().antMatchers("/loginpage")
				.hasAnyRole("PRE_AUTH_BOT", "PRE_AUTH_THIEF", "PRE_AUTH_FORGOT").antMatchers("/**")
				.hasAnyRole("ADMIN", "USER", "PRE_AUTH_SUPERVALID").and().httpBasic();
	}
}