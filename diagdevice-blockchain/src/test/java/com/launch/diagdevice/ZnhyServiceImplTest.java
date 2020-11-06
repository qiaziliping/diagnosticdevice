package com.launch.diagdevice;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.dubbo.config.annotation.Reference;
import com.launch.diagdevice.blockchain.dao.ZnhyOrderAllocationDao;
import com.launch.diagdevice.blockchain.model.UserOrder;
import com.launch.diagdevice.blockchain.model.ZnhyAccount;
import com.launch.diagdevice.blockchain.model.ZnhyAllocation;
import com.launch.diagdevice.blockchain.service.ZnhyService;
import com.launch.diagdevice.blockchain.vo.ZnhyAccountVo;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DiagdeviceBlockchainApplication.class)
public class ZnhyServiceImplTest {
	private static Logger log=LoggerFactory.getLogger(ZnhyServiceImplTest.class);
	@Reference(interfaceClass = ZnhyService.class)
	private ZnhyService znhyService;
	@Autowired
	private ZnhyOrderAllocationDao znhyOrderAllocationDao;
	@Ignore
	@Test
	public void createAccount() {
//		ZnhyAccount za=new ZnhyAccount();
//		za.setName("梁东甲");
//		za.setAlipay("13332974343");
//		za.setTelephone("13332974343");
//		za.setCreator("xiangrui.ou");
//		znhyService.createAccount(za);
		
		ZnhyAccount za=new ZnhyAccount();
		za.setName("罗丹");
		za.setAlipay("13049363091");
		za.setTelephone("13049363091");
		za.setCreator("xiangrui.ou");
		znhyService.createAccount(za);
		
//		ZnhyAccount za=new ZnhyAccount();
//		za.setName("欧祥瑞");
//		za.setAlipay("18617086698");
//		za.setTelephone("18617086698");
//		za.setCreator("xiangrui.ou");
//		znhyService.createAccount(za);
	}
	
	@Ignore
	@Test
	public void updateAccount() {
		ZnhyAccount za=znhyService.getZnhyAccount(54329);
		
		ZnhyAccountVo vo = new ZnhyAccountVo();
		BeanUtils.copyProperties(za, vo);
		
		vo.setName("欧祥瑞");
		vo.setAlipay("ouxiangrui@yahoo.com.cn");
//		za.setTelephone("18617086698");
		vo.setUpdator("xiangrui.ou");
		znhyService.updateAccount(vo);
	}
	@Ignore
	@Test
	public void createAllocation() {
		ZnhyAllocation allocation=new ZnhyAllocation();
		allocation.setJobGroupId(14);
		allocation.setName("985691007800");
		allocation.setCreatorId("0xd78062de5ca25f7fe1b6a48454529118c8bf1a74");
		allocation.setCreator("xiangrui.ou");
		allocation.setAssetsType(0);
		znhyService.createAllocation(allocation);
	}
	@Ignore
	@Test
	public void updateAllocation() {
		ZnhyAllocation znhyAllocation =new ZnhyAllocation();
		znhyAllocation.setId(7);	
		znhyAllocation=znhyService.queryZnhyAllocation( znhyAllocation);
//		znhyAllocation.setCreatorId("0x42913b13d6d470e99bd512fb98224ec7b051bb0c");
		znhyAllocation.setJobGroupId(1);
		znhyAllocation.setCreator("xiangrui.ou");
		znhyService.updateAllocation(znhyAllocation);
		
	}
	@Ignore
	@Test
	public void allocateOrder() {
		znhyService.allocateOrder("87");
		UserOrder uo=znhyOrderAllocationDao.queryUserOrderByOrderId(87);
		log.debug(uo.toString());
	}
}
