package com.launch.diagdevice.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.dubbo.config.annotation.Reference;
import com.launch.diagdevice.DiagServiceApplication;
import com.launch.diagdevice.common.constant.PayConstants;
import com.launch.diagdevice.entity.SysAuthority;
import com.launch.diagdevice.entity.UserOrder;
import com.launch.diagdevice.entity.vo.SysAuthorityVo;
import com.launch.diagdevice.service.SysAuthorityService;
import com.launch.diagdevice.service.UserOrderService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=DiagServiceApplication.class)
public class UserOrderTest {

	
	@Reference(interfaceClass=UserOrderService.class)
	private UserOrderService userOrderService;
	
	@Reference(interfaceClass=SysAuthorityService.class)
	private SysAuthorityService sysAuthorityService;
	
	@Test
	@Ignore
	public void menuTest() {
		
		SysAuthority authority = new SysAuthority();
		List<SysAuthorityVo> listvo = new ArrayList<SysAuthorityVo>();
		List<SysAuthorityVo> result =  sysAuthorityService.selectMenuList(authority,1);
		System.out.println(result);
		
	}
	
	
	
	
	
	@Test
	public void test() {
		
		UserOrder uOrder = new UserOrder();
		uOrder.setOrderNo("1010101010");
		uOrder.setSerialNo("1111111111");
		uOrder.setUserId(4L);
		uOrder.setPrice(new BigDecimal(1.1));
		uOrder.setPayFrom(PayConstants.PAY_FROM_ALIPAY);
		uOrder.setPayTime(new Date());
		uOrder.setThirdTradeNo("20180912210010043205002693103");
		
		long dspId = 6l;
		Integer result = userOrderService.saveOrderAndDetail(uOrder,dspId);
		
		System.out.print(  "---save userOrder result >:" + result);
		
	}

}
