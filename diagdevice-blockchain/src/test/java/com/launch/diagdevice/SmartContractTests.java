package com.launch.diagdevice;

import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.gson.Gson;
import com.launch.diagdevice.blockchain.client.service.SmartContractClientService;
import com.launch.diagdevice.blockchain.client.vo.AllocateOrderRequest;
import com.launch.diagdevice.blockchain.client.vo.AllocateOrderResult;
import com.launch.diagdevice.blockchain.client.vo.AllocationDetail;
import com.launch.diagdevice.blockchain.client.vo.CreateAccountRequest;
import com.launch.diagdevice.blockchain.client.vo.CreateAccountResult;
import com.launch.diagdevice.blockchain.client.vo.CreateAllocationRequest;
import com.launch.diagdevice.blockchain.client.vo.CreateAllocationResult;
import com.launch.diagdevice.blockchain.client.vo.CreateDAORequest;
import com.launch.diagdevice.blockchain.client.vo.CreateDAOResult;
import com.launch.diagdevice.blockchain.client.vo.Result;
import com.launch.diagdevice.blockchain.client.vo.UpdateAllocationRequest;
import com.launch.diagdevice.blockchain.dao.ZnhyAccountDao;
import com.launch.diagdevice.blockchain.dao.ZnhyAllocationDao;
import com.launch.diagdevice.blockchain.dao.ZnhyOrderAllocationDao;
import com.launch.diagdevice.blockchain.model.UserOrder;
import com.launch.diagdevice.blockchain.model.ZnhyAllocation;
import com.launch.diagdevice.blockchain.service.ZnhyService;
import com.launch.diagdevice.common.util.DateUtils;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DiagdeviceBlockchainApplication.class)
public class SmartContractTests
{
	private static Logger log=LoggerFactory.getLogger(SmartContractTests.class);
    @Autowired
    private SmartContractClientService smartContractClientService;
    @Autowired
    private ZnhyAccountDao znhyAccountDao;
    @Autowired
    private ZnhyAllocationDao znhyAllocationDao;
    @Autowired
    private ZnhyService znhyService;
    @Autowired
    private ZnhyOrderAllocationDao znhyOrderAllocationDao;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    @Ignore
    public void createAccountTest()
    {
//        ZnhyAccount za = znhyAccountDao.getZnhyAccount(54321);
//        this.rabbitTemplate.convertAndSend("znhyAccountCreate", za);
//        ZnhyAccount zb = znhyAccountDao.getZnhyAccount(12345);
//        this.rabbitTemplate.convertAndSend("znhyAccountCreate", zb);
         CreateAccountRequest request = new CreateAccountRequest();
//         request.setName("张三");
         request.setTelephone("13987654321");
         CreateAccountResult result =
         smartContractClientService.createAccount(request);
         log.debug("result:" + result);
    }

    /**
     * 暂时不使用，不进行测试
     */
    @Test
    @Ignore
    public void createDAOTest()
    {
        CreateDAORequest request = new CreateDAORequest();
        CreateDAOResult result = smartContractClientService.createDAO(request);
        log.debug("assetOfTest result:" + new Gson().toJson(result));
    }

     @Ignore
    @Test
    public void createAllocationTest()
    {

        ZnhyAllocation znhyAllocation = new ZnhyAllocation();
        znhyAllocation.setId(3);
        znhyAllocation = znhyAllocationDao.queryZnhyAllocation(znhyAllocation);
         this.rabbitTemplate.convertAndSend("znhyAllocationCreate",
         znhyAllocation);
    }

    @Ignore
    @Test
    public void createAllocation2Test()
    {
        CreateAllocationRequest request = new CreateAllocationRequest();
        request.setCreator("0x53c5ff33a13c06ac214701b5c587ba506ca1da6b");

        List<AllocationDetail> allocation_list = new ArrayList<AllocationDetail>();

        AllocationDetail chushi = new AllocationDetail();
        AllocationDetail.Account chushiaccount = chushi.new Account();
        // chushiaccount.setAlipay("18765431234");
        chushiaccount.setTelephone("18765431234");
        chushiaccount.setName("张三");
        chushi.setJob("厨师");
        chushi.setRadios(0.8);
        chushi.setAccount(chushiaccount);

        AllocationDetail fuwuyuan = new AllocationDetail();
        AllocationDetail.Account fuwuyuanaccount = fuwuyuan.new Account();
        // fuwuyuanaccount.setAlipay("13145628795");
        fuwuyuanaccount.setTelephone("13145628795");
        fuwuyuanaccount.setName("王五");
        fuwuyuan.setJob("服务员");
        fuwuyuan.setRadios(0.2);
        fuwuyuan.setAccount(fuwuyuanaccount);

        allocation_list.add(chushi);
        allocation_list.add(fuwuyuan);
        request.setAssigners(allocation_list);

        CreateAllocationResult result = smartContractClientService.createAllocation(request);
        log.debug("appendRecordTest result:" + new Gson().toJson(result));

    }

    /**
     * 暂时不使用，不进行测试
     */
    @Test
    @Ignore
    public void updateAllocationTest()
    {
        ZnhyAllocation znhyAllocation=new ZnhyAllocation();
        znhyAllocation.setId(4);	
		znhyAllocation=znhyService.queryZnhyAllocation( znhyAllocation);
		znhyAllocation.setCreatorId("0xd78062de5ca25f7fe1b6a48454529118c8bf1a74");
		znhyAllocation.setJobGroupId(1);
		znhyAllocation.setCreator("toutou");
        UpdateAllocationRequest query4UpdateZnhyAllocation = znhyAllocationDao
				.query4UpdateZnhyAllocation(znhyAllocation);
		Result result = smartContractClientService.updateAllocation(query4UpdateZnhyAllocation);
		log.debug("result:--------------" + result.toString());
        log.debug("assetRecordOf result:" + new Gson().toJson(result));
    }

    @Test
    @Ignore
    public void allocateOrderTest()
    {
//        AllocateOrderRequest request = new AllocateOrderRequest();
//        request.setThirdTradeNo("4200000167201809085254464971");
//        request.setOrderNo("FXO20180908165506631");
//        request.setSubNumber("6f7215a773156880c4274a6b2f425062889cf03d315fe0a4caab4f1a1f0c88f3");
//        request.setOrderTime("2018-09-08 16:55:06");
//        request.setCompany("元征科技");
//        request.setBranchShop("汽诊设备");
//        request.setPayWay("支付宝");
//        request.setSumPrice(70.0);
//        request.setPrice(70.0);
//        AllocateOrderResult result = smartContractClientService.allocateOrder(request);
//        log.debug("assetRecordOf result:" + new Gson().toJson(result));
        
        UserOrder userOrder = znhyOrderAllocationDao.queryUserOrderByOrderId(Integer.valueOf(87));
		ZnhyAllocation znhyAllocation = new ZnhyAllocation();
		znhyAllocation.setName(userOrder.getSerialNo());
		znhyAllocation = znhyAllocationDao.queryZnhyAllocation(znhyAllocation);

		AllocateOrderRequest request = new AllocateOrderRequest();
		request.setThirdTradeNo(userOrder.getThirdTradeNo());
		request.setOrderNo(userOrder.getOrderNo());
		request.setSubNumber(znhyAllocation.getAllocationId());
		request.setOrderTime(DateUtils.convertDateToString(userOrder.getPayTime()));
		request.setCompany("元征科技");
		request.setBranchShop("汽诊设备");
		if (userOrder.getPayFrom() == 1) {
			request.setPayWay("支付宝");
		} else if (userOrder.getPayFrom() == 2) {
			request.setPayWay("微信");
		}
		request.setSumPrice(userOrder.getPrice());
		request.setPrice(userOrder.getPrice());
		log.debug("znhyOrderAllocate request:--------------" + request.toString());
		AllocateOrderResult result = smartContractClientService.allocateOrder(request);
		log.debug("znhyOrderAllocate result:" + new Gson().toJson(result));
    }

}
