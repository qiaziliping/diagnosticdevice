package com.launch.diagdevice.system.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.dubbo.config.annotation.Reference;
import com.launch.diagdevice.MyCarSerApp;
import com.launch.diagdevice.system.service.SysProductManager;
import com.launch.diagdevice.system.vo.UserLoginInfo;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyCarSerApp.class)
// @SpringBootTest
public class ProductServiceTest {

	@Reference(interfaceClass = SysProductManager.class)
	private SysProductManager productManager;

//	@Ignore
	@Test
	public void testSysProductManager() throws Exception {
		// SysProduct sysProduct= productManager.getProductBySerialNoCC("963890001063",
		// "18926");
		UserLoginInfo sysProduct = productManager.getProductUserLoginInfoBySerialNo("963890001063");
		System.out.println("-------------test sysproduct dubbo service------------");
		System.out.println(sysProduct.toString());
		System.out.println("-------------test sysproduct dubbo service------------");
	}

}
