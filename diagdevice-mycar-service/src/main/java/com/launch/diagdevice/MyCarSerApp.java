package com.launch.diagdevice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.annotation.EnableCaching;

@MapperScan("com.launch.diagdevice.system.dao.*")
@SpringBootApplication
@EnableCaching
public class MyCarSerApp {
	public static void main(String[] args) {
		// 非 Web 应用 .run(args);
		new SpringApplicationBuilder(MyCarSerApp.class).web(WebApplicationType.NONE).run(args);
	}
}

// public class MyCarSerApp implements CommandLineRunner {
// public static void main(String[] args) {
// SpringApplication.run(MyCarSerApp.class, args);
// }
//
// @Override
// public void run(String... args) throws Exception {
// Thread.currentThread().join();
// }
//
// }
