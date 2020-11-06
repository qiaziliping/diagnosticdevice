package com.launch.diagdevice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubboConfig;

/**
 * @MapperScan 扫描映射文件包目录
 * 
 * 
 * @EnableTransactionManagement 使用事务管理（测试之后可以不加，service类配置就行了，最好加上）
 */
@MapperScan("com.launch.diagdevice.dao.*")
@SpringBootApplication
@EnableDubboConfig
@EnableTransactionManagement 
public class DiagServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiagServiceApplication.class, args);
		
		// web(WebApplicationType.NONE).run(args) 表示不运行web端口号
		//new SpringApplicationBuilder(DiagServiceApplication.class).web(WebApplicationType.NONE).run(args);
	}

}
