package com.launch.diagdevice.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.launch.diagdevice.common.configure.SignValidate;
import com.launch.diagdevice.common.constans.AppPropConstant;
import com.launch.diagdevice.common.constant.AppCodeConstant;
import com.launch.diagdevice.common.constant.AppConstant;
import com.launch.diagdevice.common.constant.Constants;
import com.launch.diagdevice.common.result.AppResult;
import com.launch.diagdevice.common.util.JsonHelper;
import com.launch.diagdevice.common.util.StringUtil;
import com.launch.diagdevice.common.util.VerifyCodeUtil;
import com.launch.diagdevice.common.utils.RedisUtil;
import com.launch.diagdevice.entity.User;
import com.launch.diagdevice.entity.vo.DiagSoftPriceVo;
import com.launch.diagdevice.service.DiagSoftPriceService;
import com.launch.diagdevice.service.DiagSoftService;
import com.launch.diagdevice.service.UserService;
import com.launch.diagdevice.system.service.DiagSoftInfoManager;
import com.launch.diagdevice.system.service.KeyValueExpandManager;

import net.sf.json.JSONObject;

/**
 * 
 * 账户控制类
 * @author LIPING
 * @version 0.0.1
 * @since 2018年8月14日
 */
@RestController
@RequestMapping("/app/client")
public class ClientController extends BaseController{

	private Logger logger = LoggerFactory.getLogger(ClientController.class);
	
	/**
	 * reference 引用接口
	 */
	@Reference(interfaceClass = UserService.class)
	private UserService userService;
	
	@Reference(interfaceClass = DiagSoftPriceService.class)
	private DiagSoftPriceService diagSoftPriceService;
	
	@Reference(interfaceClass=KeyValueExpandManager.class)
	private KeyValueExpandManager keyValueExpandManager;
	
	@Reference(interfaceClass = DiagSoftInfoManager.class)
	private DiagSoftInfoManager diagSoftInfoManager;
	
	@Autowired
	private RedisUtil redisUtil;
	
	/** 类型：1单次，2包月，3包季，4包年 ,目前默认为1*/
	private static final Integer DEFAULT_BUY_TYPE = 1;

	/**
	 * 获取诊断软件对应的诊断价格列表
	 * @param serialNo 产品序列号
	 * @param userId 用户ID
	 * @param lanCode 如：中文zh-cn，英文en-us
	 */
	@SignValidate(option = SignValidate.LA2LSVal, modelName = "getDiagSoftList")
	@RequestMapping(value="/getDiagSoftList/{lanCode}/{serialNo}/{sign}/{userId}",method={RequestMethod.POST,RequestMethod.GET})
	public String getDiagSoftList(@PathVariable String lanCode,@PathVariable String serialNo,@PathVariable String sign,@PathVariable Long userId) {
		AppResult appResult = new AppResult();
		JsonHelper helper = new JsonHelper();
		logger.info("------getDiagSoftList request param>:[lanCode={},serialNo={},sign={},userId={}]",lanCode,serialNo,sign,userId);
		try {
			// 判断序列号是否和登录时的序列号一致
			String snKey = AppConstant.DIAGDEVICE_APPUSER_SERIAL_NO + ":" + userId;
			String cacheSN = redisUtil.get(snKey);
			if (!serialNo.equals(cacheSN)) {
				setResultOld(appResult, AppCodeConstant.SERIAL_NO_ERROR);
				return JSONObject.fromObject(appResult).toString();
			}
			
			String currency = getCurrencyBylanCode(lanCode);
			
			
			// 根据序列号查询对应的诊断软件ID集合
			List<Integer> softIDList = diagSoftInfoManager.selectSoftIDListBySerialNo(serialNo);
			
			List<DiagSoftPriceVo> dsPriceVo = new ArrayList<DiagSoftPriceVo>();
			if (softIDList.size() > 0) {
				Map<String,Object> condition = new HashMap<String,Object>();
				condition.put("currency", currency);
				condition.put("buyType", DEFAULT_BUY_TYPE);
				condition.put("list", softIDList);
				dsPriceVo = diagSoftPriceService.selectByCondition(condition);
			}
			
			appResult.setData(dsPriceVo);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("--------diagsoft getDiagSoftList error info>:{}", e);
			appResult.setCode(AppCodeConstant.ERROR);
			appResult.setMessage(AppCodeConstant.checkCode(AppCodeConstant.ERROR));
		}
		return helper.toJsonStr(appResult);
	}
	
	/**
	 * 获取诊断价格
	 * @param userId  用户主键ID
	 * LIPING
	 */
	@SignValidate(option=SignValidate.LA2LSVal,modelName="getCarPrice")
	@ResponseBody
	@RequestMapping(value = "/getCarPrice/{sign}/{userId}", method = { RequestMethod.POST,RequestMethod.GET })
	public String getCarPrice(@PathVariable String sign,@PathVariable String userId) {
		AppResult appResult = new AppResult();
		JsonHelper jsonh = new JsonHelper();
		logger.info("------getCarPrice request param>:[sign={},userId={}]",sign,userId);
		try {
			
			Long uid = Long.parseLong(userId);
			// 判断用户是否存在
			if (uid != null && uid != 0L) {
				User user = userService.selectById(uid);
				if (user != null) {
					Map<String,Object> rstMap = new HashMap<String,Object>();
					
					String value = keyValueExpandManager.queryValueByKey(Constants.DIAGDEVICE_FIXED_PRICE);
					logger.info("---clientController keyValueExpandManager fixed price={}",value);
					BigDecimal price = new BigDecimal(value);
					
//					 rstMap.put("carPrice", AppPropConstant.CAR_PRICE);
					 rstMap.put("carPrice", price);
					appResult.setData(rstMap);
				}
			} else {
				// 缺少参数
				setResultOld(appResult, AppCodeConstant.PARAM_LACK);
			}
			
		}  catch (Exception e) {
            e.printStackTrace();
            logger.error("--------getCarPrice>:{}",e);
            appResult.setCode(AppCodeConstant.ERROR);
            appResult.setMessage(AppCodeConstant.checkCode(AppCodeConstant.ERROR));
        }
		
		return jsonh.toJsonStr(appResult);
	}
	
	
	/**
	 * 获取验证码接口
	 * TODO
	 * LIPING
	 */
	@RequestMapping(value = "/authimage/{date}", method = RequestMethod.GET)
	public String AuthImage(HttpServletRequest request, HttpServletResponse response,@PathVariable String date) {
		/*response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");*/
		logger.info("-------authimage request date>:"+date);
		JsonHelper jsonh = new JsonHelper();
		AppResult appResult = new AppResult();
	
		try {
			Map<String,String> rstMap = new HashMap<String,String>();
			// 生成随机字串
			String verifyCode = VerifyCodeUtil.generateVerifyCode(4);
			
			String uuid = StringUtil.getUUID();
			// 10分钟过期,注册过程最多十分钟吧
			redisUtil.set(AppConstant.DIAGDEVICE_VERIFY_CODE+":"+uuid, verifyCode.toLowerCase(), 60*10L);
			
			rstMap.put("verCode", verifyCode);
			rstMap.put("uuid", uuid);
			appResult.setData(rstMap);
		} catch (Exception e) {
			e.printStackTrace();
            logger.error("--------authimage.ado>{}:",e);
            appResult.setCode(AppCodeConstant.ERROR);
            appResult.setMessage(AppCodeConstant.checkCode(AppCodeConstant.ERROR));
		}
		
		
//		// 存入会话session
//		HttpSession session = request.getSession(true);
//		// 删除以前的
//		session.removeAttribute("verCode");
//		session.setAttribute("verCode", verifyCode.toLowerCase());
//		
//		// 设置当前session会话过期之间20分钟，默认是30分钟
//		session.setMaxInactiveInterval(60*20);
		
		// 生成图片
		/*int w = 134, h = 50;
		try {
			VerifyCodeUtil.outputImage(w, h, response.getOutputStream(), verifyCode);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("write img verifycode catch an exception", e);
		}*/
		return jsonh.toJsonStr(appResult);
	}
	
	/**
	 * 验证验证码接口
	 * @param userId  用户主键ID
	 * LIPING
	 */
	@ResponseBody
	@RequestMapping(value = "/verifyAuthimage/{uuid}/{verCode}", method = { RequestMethod.POST,RequestMethod.GET  })
	public String verifyAuthimage(@PathVariable String uuid,@PathVariable String verCode) {
		AppResult appResult = new AppResult();
		JsonHelper helper =  new JsonHelper();
		logger.info(uuid + "------param>:"+verCode);
		try {
			if (StringUtils.isNotBlank(verCode) || StringUtils.isNotBlank(uuid)) {
				
				
				String rdsVerCode = redisUtil.get(AppConstant.DIAGDEVICE_VERIFY_CODE+":"+uuid);
				rdsVerCode = (rdsVerCode == null) ? "" : rdsVerCode;
				
				if (!verCode.toLowerCase().equals(rdsVerCode)) {
					appResult.setCode(AppCodeConstant.VER_CODE_ERROR);
		            appResult.setMessage(AppCodeConstant.checkCodeOld(AppCodeConstant.VER_CODE_ERROR));
		            return helper.toJsonStr(appResult);
				}
				// 先不删除，注册接口还需要验证
				// redisUtil.remove(AppConstant.DIAGDEVICE_VERIFY_CODE+":"+uuid);
			} else {
				// 缺少参数
				setResultOld(appResult, AppCodeConstant.PARAM_LACK);
			}
			
		} catch (Exception e) {
            e.printStackTrace();
            logger.error("--------verifyAuthimage.ado>:{}",e);
            appResult.setCode(AppCodeConstant.ERROR);
            appResult.setMessage(AppCodeConstant.checkCode(AppCodeConstant.ERROR));
        }
		return helper.toJsonStr(appResult);
	}
	
	/** 根据语言编码获取币种，code!=zh-cn的，统统返回USD */
	private String getCurrencyBylanCode(String lanCode) {
		lanCode = lanCode.toLowerCase();
		String currency = "";
		switch (lanCode) {
			case "zh-cn":
				currency = "RMB";
				break;
			case "en-us":
				currency = "USD";
				break;
			default:
				currency = "USD";
				break;
		}
		return currency;
	}

}
