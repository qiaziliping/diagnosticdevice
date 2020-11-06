package com.launch.diagdevice.common.configure;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class ActuatorSecurity extends WebSecurityConfigurerAdapter {
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable().authorizeRequests()
				// swagger页面需要添加登录校验
				.antMatchers("/monitor/**").authenticated()
				// 普通的接口不需要校验
				.anyRequest().permitAll().and().formLogin();
	}
}

