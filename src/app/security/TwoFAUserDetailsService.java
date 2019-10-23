package app.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import app.service.ExtendedUserManager;

public class TwoFAUserDetailsService implements UserDetailsService {

	@Autowired
	private ExtendedUserManager manager;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return manager.getByName(username);
	}
}