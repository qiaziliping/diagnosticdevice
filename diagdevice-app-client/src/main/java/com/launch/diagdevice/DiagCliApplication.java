package com.launch.diagdevice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan //控制的过滤器和监听器,暂时没有用到过滤器和监听器(起作用)
//@EnableAutoConfiguration(exclude={org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class}) //此注解取消springsecurity
public class DiagCliApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiagCliApplication.class, args);
	}

}
