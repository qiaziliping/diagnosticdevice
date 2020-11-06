package com.launch.diagdevice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * springsecurity核心配置类
 *
 */
@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true) //开启security注解
//@EnableGlobalMethodSecurity(securedEnabled = true) //开启security注解
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//	private Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 拦截器
	 */
	@Autowired
    private MyFilterSecurityInterceptor myFilterSecurityInterceptor;
	
	@Autowired
    private MyAuthenticationProvider authenticationProvider;
	
	@Autowired
    private AuthenticationSuccessHandler myAuthenticationSuccessHandler;
    @Autowired
    private AuthenticationFailureHandler myAuthenticationFailHander;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		/**
		 *   formLogin 定义当需要用户登录时候，转到的登录页面。
		 *   authorizeRequests 定义哪些URL需要被保护、哪些不需要被保护
		 *   anyRequest 任何请求,登录后可以访问
		 *   permitAll 允许所有访问
		 *   loginProcessingUrl 的值必须和登录页面请求的url一样
		 */
		/*http
		 // 在正确的位置添加我们自定义的过滤器
		//.addFilterBefore(mySecurityFilter, FilterSecurityInterceptor.class)
		 // 关闭csrf防护
		.csrf().disable()
		.authorizeRequests()
		 //访问： 无需登录认证权限
		.antMatchers("/","/tl/login","403").permitAll()
		 // 其他地址访问均需要验证权限,登录后访问
		.anyRequest().authenticated()
		.and()
        	.formLogin()
        	 //指定登录页是"/login"
        	.loginPage("/tl/login")
//        	.loginProcessingUrl("/web/account/login/form")
        	.loginProcessingUrl("/tl/hello/ym")
        	 //登录成功后默认跳转到"/index"
//        	.defaultSuccessUrl("/tl/index")
//        	.failureUrl("/tl/login-error").permitAll()
        	.successHandler(myAuthenticationSuccessHandler)
        	.failureHandler(myAuthenticationFailHander)
        	.permitAll()
        .and()
            .logout()
             // 退出登录后的默认url是"/home"
//            .logoutSuccessUrl("/").permitAll()
        .and()
             // 登录后记住用户，下次自动登录,数据库中必须存在名为persistent_logins的表
            .rememberMe()
            .tokenValiditySeconds(60*60*2);*/
		/**
		 * 1、loginPage("/account/login") | 自定义一个url，打开from表单
		 * 2、loginProcessingUrl("/account/login/process") | 设置一个登陆成功的跳转页面
		 * 3、 successHandler 登陆成功处理类
		 * 4、failureHandler 登陆失败处理类
		 * 5、
		 */
		http
        .formLogin().loginPage("/account/login").loginProcessingUrl("/account/login/process")
        .successHandler(myAuthenticationSuccessHandler)
        .failureHandler(myAuthenticationFailHander)
        .permitAll()  //表单登录，permitAll()表示这个不需要验证 登录页面，登录失败页面
        .and()
        	.authorizeRequests()
        	 // 获取验证码和校验验证码接口不需要拦截
        	.antMatchers("/account/authimage","/account/verifyAuthimage/*").permitAll()
        .and()
            //开启cookie保存用户数据
            .rememberMe()
            //设置cookie有效期
            .tokenValiditySeconds(60*60*1)
        .and()
            .authorizeRequests().anyRequest().authenticated()                  
        .and()
        	.headers().frameOptions().disable()
        .and()
        	.logout().invalidateHttpSession(true).logoutUrl("/account/logout") //.logoutSuccessHandler(logoutSuccessHandler)
        	// 退出登录后的默认url是"/home"
        	.logoutSuccessUrl("/account/login").permitAll()
        .and()
            .csrf().disable(); 
		
		http.addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor.class);   
            
	}

	
	@Override 
	protected void configure(AuthenticationManagerBuilder auth) throws Exception  { 
		// 暂时使用基于内存的AuthenticationProvider
		//auth.inMemoryAuthentication().withUser("username").password("password").roles("USER");
		
		auth.authenticationProvider(authenticationProvider);
		//auth.userDetailsService(getUserDetailsService()).passwordEncoder(passwordEncoder());
	}
	
	@Override 
	public void configure(WebSecurity web)  throws Exception {
		//web.ignoring().antMatchers("/resource/**"); 
	    // 可以仿照上面一句忽略静态资源
		// web.ignoring().antMatchers("/static/**"); 
//	    web.ignoring().antMatchers("/css/**", "/js/**","/images/**","/layui/**"); 
	    web.ignoring().antMatchers("/css/**"); 
	    web.ignoring().antMatchers("/js/**"); 
	    web.ignoring().antMatchers("/images/**"); 
	    web.ignoring().antMatchers("/layui/**"); 
		

	}
	
	
	/**
     * 设置用户密码的加密方式为MD5加密
     * @return
     */
	/*@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}*/
	
	/**
	 * 自定义UserDetailsService，从数据库中读取用户信息
	 * TODO
	 * LIPING
	 */
	@Bean
	public CustomUserDetailsService getUserDetailsService() {
		return new CustomUserDetailsService();
	}
	
	
	 

}