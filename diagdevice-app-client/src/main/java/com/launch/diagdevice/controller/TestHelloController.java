package com.launch.diagdevice.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.launch.diagdevice.common.configure.SignValidate;
import com.launch.diagdevice.common.result.AppResult;
import com.launch.diagdevice.common.util.JsonHelper;
import com.launch.diagdevice.common.utils.RedisUtil;
import com.launch.diagdevice.entity.request.RequestHead;
import com.launch.diagdevice.entity.vo.UserVo;
import com.launch.diagdevice.pay.service.TestPayService;
import com.launch.diagdevice.service.DiagSoftService;
import com.launch.diagdevice.service.TestHelloService;
import com.launch.diagdevice.system.service.DiagSoftInfoManager;

@RestController
@RequestMapping("/testHello")
public class TestHelloController {

	private Logger logger = LoggerFactory.getLogger(TestHelloController.class);
	
	/**
	 * reference 引用接口
	 */
	@Reference(interfaceClass = TestHelloService.class)
	private TestHelloService testHelloService;
	@Reference(interfaceClass = TestPayService.class)
	private TestPayService testPayService;
	
	@Reference(interfaceClass = DiagSoftInfoManager.class)
	private DiagSoftInfoManager diagSoftInfoManager;
	
	@Reference(interfaceClass = DiagSoftService.class)
	private DiagSoftService diagSoftService;
	
	@Autowired
	private RedisUtil redisUtil;

	
	@SignValidate(option=SignValidate.DownloadDiagVal,modelName="testInterceptor")
	@ResponseBody
	@RequestMapping(value = "/testInterceptor", method = RequestMethod.POST)
	public String testInterceptor(HttpServletRequest request,@RequestParam String name,RequestHead rHead) throws Exception {
		
		logger.info("------会进入拦截器吗？---"+name);
		logger.info("------会进入拦截器吗？rHead---"+rHead);
		logger.info("------会进入拦截器吗？---"+name);
		
		
		return "测试成功";
	}
	
	
	public static void main(String[] args) {
		UserVo uservo = new UserVo();
		uservo.setId(1L+22);
		uservo.setPassword("password-"+22);
		uservo.setUsername("username"+22);
		uservo.setEmail("59772179@qq.com"+22);
		uservo.setNickName("昵称-"+22);
		
		String filterName = "userVoFilter";
		JsonHelper json = new JsonHelper(JsonInclude.Include.ALWAYS);
		/*json.setFieldExclude(filterName, new String [] {"nickName","username"});
		String yy =  json.toJsonStr(uservo);
		System.out.println("----yy>:"+yy);
		
		String lstr = json.toJsonStrExclude(uservo,filterName, new String [] {"nickName","username"});
		System.out.println("----lstr>:"+lstr);
		
		String lstr2 = json.toJsonStrInclude(uservo,filterName, new String [] {"nickName","username"});
		System.out.println("----lstr2>:"+lstr2);*/
		
		List<UserVo> luser = new ArrayList<UserVo>();
		
		for (int i = 0;i<5;i++) {
			UserVo user = new UserVo();
			user.setId(1L+i);
			user.setPassword("password-"+i);
			user.setUsername("username"+i);
			user.setEmail("59772179@qq.com"+i);
			user.setNickName("昵称-"+i);
			luser.add(user);
		}
		
		json.filter(UserVo.class, null, "email,id,xx");
		String liststr = json.toJsonStr(luser);
		System.out.println("liststr>:"+liststr);
		
		json = new JsonHelper(JsonInclude.Include.ALWAYS);
		json.filter(UserVo.class, "email,id",null );
		liststr = json.toJsonStr(luser);
		System.out.println("Include liststr>:"+liststr);
		
//		String xx = json.toJsonStrExclude(luser, filterName, new String [] {"nickName","password"});
//		System.out.println(xx);
//		
//		String xx2 = json.toJsonStrInclude(luser, filterName, new String [] {"nickName","username","id"});
//		System.out.println(xx2);
		
		
	}
	
	
	@RequestMapping(value = "/testJkson", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public String testJkson(HttpServletRequest request) throws Exception {
		
		AppResult result = new AppResult();
		
		
		
		UserVo uservo = new UserVo();
		uservo.setId(1L+22);
		uservo.setPassword("password-"+22);
		uservo.setUsername("username"+22);
		uservo.setEmail("59772179@qq.com"+22);
		uservo.setNickName("昵称-"+22);
		
		
		JsonHelper json = new JsonHelper();
		result.setData(uservo);
		return json.toJsonStr(result);
	}
	
	@RequestMapping(value = "/payTest", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public String payTest(HttpServletRequest request,@RequestParam String name) throws Exception {
		
		logger.info("---name>:"+name);
		
		String result = testPayService.getAmount(name);
		return result;
	}
}
