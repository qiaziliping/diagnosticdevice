package com.launch.diagdevice.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.launch.diagdevice.common.constant.AppCodeConstant;
import com.launch.diagdevice.common.constant.AppConstant;
import com.launch.diagdevice.common.constant.Constants;
import com.launch.diagdevice.common.exception.ParameterIllegalErrorException;
import com.launch.diagdevice.common.result.AppResult;
import com.launch.diagdevice.common.util.JsonHelper;
import com.launch.diagdevice.common.util.MD5Util;
import com.launch.diagdevice.common.util.VerifyUtil;
import com.launch.diagdevice.common.utils.RedisUtil;
import com.launch.diagdevice.entity.User;
import com.launch.diagdevice.entity.vo.SignVo;
import com.launch.diagdevice.service.UserService;
import com.launch.diagdevice.system.model.SysProduct;
import com.launch.diagdevice.system.service.KeyValueExpandManager;
import com.launch.diagdevice.system.service.SysProductManager;
import com.launch.diagdevice.system.vo.UserLoginInfo;


/**
 * 
 * 用户控制类
 * @author LIPING
 * @version 0.0.1
 * @since 2018年8月10日
 */
@RestController
@RequestMapping("/app/user")
public class UserController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(UserController.class);

	
	@Reference(interfaceClass=KeyValueExpandManager.class)
	private KeyValueExpandManager keyValueExpandManager;
	
	/**
	 * reference 引用接口
	 */
	@Reference(interfaceClass = UserService.class)
	private UserService userService;
	
	@Reference(interfaceClass = SysProductManager.class)
	private SysProductManager sysProductManager;

	@Autowired
	private RedisUtil redisUtil;

	public static void main(String[] args) {
		
		String pwd = MD5Util.MD5("123#");
		System.out.println("第一次>:" + pwd);
		String date = "2018-07-06 11:11:11";
		pwd = MD5Util.MD5(pwd + date);
		System.out.println("第2次>:" + pwd);
		
	}
	
	/**
	 * APP用户注册
	 * LIPING
	 */
	@ResponseBody
	@RequestMapping(value = "/register/{email}/{password}/{username}/{uuid}/{verCode}", method = { RequestMethod.POST,RequestMethod.GET  })
	public String register(@PathVariable String email,@PathVariable String password,@PathVariable String username,
			@PathVariable String uuid,@PathVariable String verCode, HttpServletRequest request) {
		AppResult appResult = new AppResult();
		JsonHelper helper =  new JsonHelper();
		logger.info("------request param>:email={},password={},username={},uuid={},verCode={},",
				email,password,username,uuid,verCode);
		try {
			// 1、 先验证验证码是否为空
			if (StringUtils.isNotBlank(verCode) || StringUtils.isNotBlank(uuid)) {
				String rdsVerCode = redisUtil.get(AppConstant.DIAGDEVICE_VERIFY_CODE+":"+uuid);
				rdsVerCode = (rdsVerCode == null) ? "" : rdsVerCode;
				// 2、 先验证验证码是否正确
				if (!verCode.toLowerCase().equals(rdsVerCode)) {
					appResult.setCode(AppCodeConstant.VER_CODE_ERROR);
		            appResult.setMessage(AppCodeConstant.checkCodeOld(AppCodeConstant.VER_CODE_ERROR));
		            return helper.toJsonStr(appResult);
				}
				// 3、验证用户名和密码是否符合规则
				if (!VerifyUtil.isAcount(username)) {
					appResult.setCode(AppCodeConstant.USERNAME_FORMAT_ERROR);
		            appResult.setMessage(AppCodeConstant.checkCodeOld(AppCodeConstant.USERNAME_FORMAT_ERROR));
		            return helper.toJsonStr(appResult);
				}
				if (!VerifyUtil.isPasswordValid(password)) {
					appResult.setCode(AppCodeConstant.PASSWORD_FORMAT_ERROR);
					appResult.setMessage(AppCodeConstant.checkCodeOld(AppCodeConstant.PASSWORD_FORMAT_ERROR));
					return helper.toJsonStr(appResult);
				}
				
				User user = new User();
				user.setEmail(email);
				user.setPassword(password);
				user.setUsername(username);
				// 4、验证码正确再注册
				userRegister(user,appResult,uuid);
			} else {
				// 缺少参数
				setResultOld(appResult, AppCodeConstant.PARAM_LACK);
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("--------register.ado.errerInfo>:{}", e);
			appResult.setCode(AppCodeConstant.ERROR);
			appResult.setMessage(AppCodeConstant.checkCode(AppCodeConstant.ERROR));
		}

		return helper.toJsonStr(appResult);
	}
	
	public void userRegister(User user,AppResult appResult,String uuid) throws Exception{
		
		User tUser = new User();
		tUser.setUsername(user.getUsername());
		tUser = userService.selectOne(tUser);
		if (tUser == null) {
			user.setRemark(user.getPassword()); // 上线之后去掉
			user.setPassword(MD5Util.MD5(user.getPassword()));
			int count = userService.save(user);
			logger.info("---count>:" + count);
			// 注册成功清理redis验证码
			if (count > 0) redisUtil.remove(AppConstant.DIAGDEVICE_VERIFY_CODE+":"+uuid); 
			
		} else {
			// 该用户名已存在
			appResult.setCode(AppCodeConstant.USER_EXIST);
			appResult.setMessage(AppCodeConstant.checkCodeOld(AppCodeConstant.USER_EXIST));
		}
		
	}


	/**
	 * 用户登录
	 * LIPING
	 */
	@ResponseBody
	@RequestMapping(value = "/login/{dateTime}/{password}/{serialNo}/{username}", method = { RequestMethod.POST,RequestMethod.GET  })
	public String login(@PathVariable String dateTime,@PathVariable String password,@PathVariable String serialNo,
			@PathVariable String username) throws Exception {
		AppResult appResult = new AppResult();
		JsonHelper helper =  new JsonHelper();
		logger.info("------login request param>:[dateTime={},password={},serialNo={},username={}]",dateTime,password,serialNo,username);
		try {
			
			
			if (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)
					&& StringUtils.isNotBlank(dateTime) && StringUtils.isNotBlank(serialNo)) {

				// 验证序列号是否非法
				if (serialNo.length() != 12 || !StringUtils.isNumeric(serialNo)) {
					setResultOld(appResult, AppCodeConstant.SERIAL_NO_ILLEGAL);
					return helper.toJsonStr(appResult);
				}
				// 验证序列号是否存在
				SysProduct prodect = sysProductManager.getProcuctBySerialNo(serialNo);
				if (prodect == null) {
					setResultOld(appResult, AppCodeConstant.SERIAL_NO_NOT_EXIST);
					return helper.toJsonStr(appResult);
				}
				
				// 返回结果的对象
				Map<String,Object> rstMap = new HashMap<String,Object>();
				
				// 获取设备用户信息
				UserLoginInfo deviceUserInfo = sysProductManager.getProductUserLoginInfoBySerialNo(serialNo);
				
				// 只有PADIII产品才能操作
				//if(isValid(deviceUserInfo.getPdtTypeId())){
				if(isValid(deviceUserInfo.getSoftConfId())){
					
					// --------------------------登录用户信息----------------------------
					User user = new User();
					user.setUsername(username);
					user.setPassword(password);

					int flag = userService.login(user,dateTime);
					if (flag == AppCodeConstant.SUCCESS) {
						//User tempUser = userService.selectByUniqueFlag(username);
						User tempUser = userService.selectOne(user);
						
						String token = AppConstant.getToken();
						String userId = tempUser.getId();
						String key = AppConstant.DIAGDEVICE_APPUSER_TOKEN + ":" + userId;
						String snKey = AppConstant.DIAGDEVICE_APPUSER_SERIAL_NO + ":" + userId;
						// 过期时间2个小时
						redisUtil.set(key, token, 60 * 60 * 2L);
						redisUtil.set(snKey, serialNo, 60 * 60 * 2L);
						
						SignVo signvo = new SignVo();
						signvo.setLoginId(Long.parseLong(userId));
						signvo.setToken(token);
						rstMap.put("loginUser", signvo); // 登录用户
						rstMap.put("deviceUser", deviceUserInfo); // 设备用户
						
						// 用户登录成功并且要返回，远程诊断xmpp地址
						Map<String,Object> xmpp = new HashMap<String,Object>();
						xmpp.put("domain", Constants.YC_DIAG_XMPP_DOMAIN);
						xmpp.put("ip", Constants.YC_DIAG_XMPP_IP);
						xmpp.put("port", Constants.YC_DIAG_XMPP_PORT);
						rstMap.put("xmpp", xmpp);
						
						appResult.setData(rstMap);
					} else {
						setResultOld(appResult, flag);
					}
				} else {
					//序列号前5位是98569才是X431PADIII，XPDIII  
					setResultOld(appResult, AppCodeConstant.SERIAL_NO_ERROR);
				}
			} else {
				// 缺少参数
				setResultOld(appResult, AppCodeConstant.PARAM_LACK);
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("--------login.ado>:{}", e);
			appResult.setCode(AppCodeConstant.ERROR);
			appResult.setMessage(AppCodeConstant.checkCode(AppCodeConstant.ERROR));
		}
		return helper.toJsonStr(appResult);
	}
	
	/**
	 * 验证是否为PAD3产品，只有pad3才能允许操作
	 * LIPING
	 */
	private boolean isValid(Integer pdtTypeId) {
		boolean flag = false;
		
		String value = keyValueExpandManager.queryValueByKey(Constants.DIAGDEVICE_ALLOW_CONFIG_ID);
		logger.info("UserController isValid produect type config ids={}",value);
		if (StringUtils.isNotBlank(value)) {
			String [] arr = value.split(",");
			
			for (int i = 0;i<arr.length;i++) {
				String arrValue = arr[i];
				int ptId = Integer.parseInt(arrValue);
				
				if (pdtTypeId.intValue() == ptId) {
					flag = true;
					continue;
				}
			}
			
		}
		// 判断是否为PAD3产品
		return flag;
	}
	

}
