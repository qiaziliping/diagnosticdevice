package com.launch.diagdevice.config;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.launch.diagdevice.common.constant.WebConstant;
import com.launch.diagdevice.common.utils.RedisUtil;

/**
 *  自定义的用户名密码验证，调用了loadUserByUsername方法
 * @author LIPING
 * @version 0.0.1
 * @since 2018年10月24日
 */
@Component
public class MyAuthenticationProvider implements AuthenticationProvider{

	 private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	 @Autowired
	 private CustomUserDetailsService customUserDetailsService;
	 
	 @Autowired
     private PasswordEncoder passwordEncoder;
	 
	 @Autowired
	 private RedisUtil redisUtil;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		// 从request获取验证码，验证验证码是否正确
		/*ServletRequestAttributes attribute =  (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attribute.getRequest();
		String verCode = request.getParameter("verCode");
		
		if (!StringUtils.isEmpty(verCode)) {
			verCode = verCode.toLowerCase();
			String uuid = redisUtil.get(AppConstant.DIAGDEVICE_VERIFY_CODE_ADMIN+":"+verCode);
			if (StringUtils.isEmpty(uuid)) throw new BadCredentialsException(""+WebConstant.VERIFY_CODE_ERROR);
		} else {
			// 验证码不能为空
			throw new BadCredentialsException(""+WebConstant.VERIFY_CODE_IS_EMPTY);
		}*/
		
		
		String username = authentication.getName();
        // entryPwd页面输入的密码
		String entryPwd = (String) authentication.getCredentials();
        UserDetails user = customUserDetailsService.loadUserByUsername(username);
        if(user == null) {
        	throw new UsernameNotFoundException(""+WebConstant.USERNAME_NOT_FOUND);
        }
        // password 是数据库已经加密的密码
        String password = user.getPassword();
//        password = passwordEncoder.encode(password); // 这行代码到时候要注销
        
        // 加密过程在这里体现
        //if (!entryPwd.equals(password)) {
        if (!passwordEncoder.matches(entryPwd, password)) {
            logger.info(entryPwd+"--password error>:"+password);
            throw new BadCredentialsException(""+WebConstant.PASSWORD_ERROR);
        }

        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        return new UsernamePasswordAuthenticationToken(user, entryPwd, authorities);
	}

	/**
	 * 默认返回false，改为true
	 */
	@Override
	public boolean supports(Class<?> arg0) {
		return true;
	}

}
