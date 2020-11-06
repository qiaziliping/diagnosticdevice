package com.launch.diagdevice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 此类需要删除
 * <p>
 * TODO
 * <p>
 * @version 0.0.1
 * @since 2018年11月16日
 */
//@Component
public class MyUserDetailsService implements UserDetailsService{

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
//	@Autowired
//    private PasswordEncoder passwordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		logger.info("用户的用户名: {}", username);
//		 String password = passwordEncoder.encode("123456");
		 User user = new User(username, "password", AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
//		User user = new User(username, password, AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
		return user;
	}

}
