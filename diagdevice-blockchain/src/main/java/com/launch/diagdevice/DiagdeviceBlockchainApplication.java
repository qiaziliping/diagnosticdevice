package com.launch.diagdevice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@MapperScan("com.launch.diagdevice.blockchain.dao.*")
@SpringBootApplication
public class DiagdeviceBlockchainApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(DiagdeviceBlockchainApplication.class).web(WebApplicationType.NONE).run(args);
	}
}
