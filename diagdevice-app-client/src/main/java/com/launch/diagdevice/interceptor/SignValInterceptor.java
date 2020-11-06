package com.launch.diagdevice.interceptor;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerMapping;

import com.launch.diagdevice.common.configure.SignValidate;
import com.launch.diagdevice.common.constant.AppConstant;
import com.launch.diagdevice.common.exception.ParameterIllegalErrorException;
import com.launch.diagdevice.common.exception.ParameterNullErrorException;
import com.launch.diagdevice.common.exception.SignErrorException;
import com.launch.diagdevice.common.util.JsonHelper;
import com.launch.diagdevice.common.util.Md5SumUtil;
import com.launch.diagdevice.common.util.VerifyUtil;
import com.launch.diagdevice.common.utils.RedisUtil;

@Aspect
@Component
public class SignValInterceptor {
	private static final Logger logger = LoggerFactory.getLogger(SignValInterceptor.class);

	private static final String SIGN = "sign";
	/** 公共参数 */
	private static final String COMMON_PARAM = "commonParam";

	@Autowired
	private RedisUtil redisUtil;

	// @Around("@annotation(signValidate)")
	@Before("@annotation(signValidate)") // 拦截被SingValidate注解的方法；如果你需要拦截指定package指定规则名称的方法，可以使用表达式execution(...)
	public void beforeProcess(JoinPoint point, SignValidate signValidate) throws Throwable {
		logger.info("beforeProcess:" + signValidate.modelName() + "." + signValidate.option());

		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();// 获取request

		String URI = request.getRequestURI();
		// 获取请求参数
		Map<String, String> map = getReqMap(request);
		JsonHelper jsonUtil = new JsonHelper();
		logger.info(URI + "--自定义拦截器请求接口：{}，request params: {}", URI, jsonUtil.toJsonStr(map));

		// 根据注解中的标识进入不同的校验方法
		if (signValidate.option().equals(SignValidate.LA2LSVal)) {
			// 公司客户端对服务器校验
			lA2LSValidate(map);
		}

	}

	/**
	 * launch api请求launch服务器的校验
	 * 
	 * @param map
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void lA2LSValidate(Map map) throws Exception {

		String preSignstr = appendSignParam(map);

		// 获取公共参数
		// CommonParam cp = getCommonParam(map);

		String sign = (String) map.get(SIGN);
		Long loginId = Long.parseLong(String.valueOf(map.get("userId")));

		// 验证LoginId值是否为空
		validateParameterFormat("loginId", String.valueOf(loginId));

		// 获取本地token
		String key = AppConstant.DIAGDEVICE_APPUSER_TOKEN + ":" + loginId;
		String token = redisUtil.get(key);

		String LALSToken = token != null ? token : "";

		// 签名校验
		if (StringUtils.isBlank(LALSToken) || !sign.equals(Md5SumUtil.md5(preSignstr + LALSToken))) {
			logger.info("lA2LSValidate preSignStr={}, and token={},sign={}" ,preSignstr , LALSToken, map.get("sign"));
			logger.info("lA2LSValidate server md5:" + Md5SumUtil.md5(preSignstr + LALSToken));
			throw new SignErrorException();
		} else {
			String snKey = AppConstant.DIAGDEVICE_APPUSER_SERIAL_NO + ":" + loginId;
			// 只要再操作，重新设置过期时间2个小时
			redisUtil.set(key, token, 60 * 60 * 2L);
			redisUtil.setExpire(snKey, 60 * 60 * 2L);
		}
	}

	public static void main(String[] args) throws Exception {

		String preSignstr = "[1,2]1";
		String LALSToken = "2NK8M5M61VL8HNBDO04OU1657A94A617";
		String sign = Md5SumUtil.md5(preSignstr + LALSToken);
		System.out.println("--------sign>:" + sign);
	}

	@SuppressWarnings("rawtypes")
	private String appendSignParam(Map<String, Object> paramMap) {
		Map<String, Object> map = new TreeMap<String, Object>(paramMap);

		StringBuilder sb = new StringBuilder();
		// 遍历参数
		for (Iterator iterator = map.keySet().iterator(); iterator.hasNext();) {
			String paraName = (String) iterator.next();
			String value = (String) map.get(paraName);
			// 参数基本校验
			validateParameterFormat(paraName, value);
			if (!(paraName.equals("sign") || paraName.equals("app") || paraName.equals(COMMON_PARAM))) {
				// 拼接需要校验的参数
				sb.append(value);
			}
			logger.info(paraName + ":" + value);

		}
		return sb.toString();
	}

	/**
	 * 参数统一基本格式校验，包括空校验和基本格式校验
	 * 
	 * @param parame
	 * @param value
	 */
	private void validateParameterFormat(String parame, String value) {
		// 对参数进行空校验
		if (StringUtils.isBlank(value))
			throw new ParameterNullErrorException(parame);
		// 对参数格式进行基本校验
		switch (parame) {
		case "serialNo":
			if (!StringUtils.isNumeric(value)) {
				throw new ParameterIllegalErrorException(parame);
			}
			break;
		case "registeredSerialNo":
			if (!StringUtils.isNumeric(value)) {
				throw new ParameterIllegalErrorException(parame);
			}
			break;
		case "oldSerialNo":
			if (!StringUtils.isNumeric(value)) {
				throw new ParameterIllegalErrorException(parame);
			}
			break;
		case "userId":
			if (!StringUtils.isNumeric(value)) {
				throw new ParameterIllegalErrorException(parame);
			}
			break;
		case "pdtTypeId":
			if (!StringUtils.isNumeric(value)) {
				throw new ParameterIllegalErrorException(parame);
			}
			break;
		case "softId":
			if (!StringUtils.isNumeric(value)) {
				throw new ParameterIllegalErrorException(parame);
			}
			break;

		// case "clientType":
		// if (!Constants.clientTypeSet.contains(value)) {
		// throw new ParameterIllegalErrorException(parame);
		// }
		// break;
		// case "transferType":
		// if (!Constants.transferTypeSet.contains(Integer.valueOf(value))) {
		// throw new ParameterIllegalErrorException(parame);
		// }
		// break;
		case "version":
			if (!StringUtils.containsOnly(value, new char[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, '.' }))
				throw new ParameterIllegalErrorException(parame);
			break;
		case "versionId":
			if (!StringUtils.isNumeric(value)) {
				throw new ParameterIllegalErrorException(parame);
			}
			break;
		case "languageId":
			if (!StringUtils.isNumeric(value)) {
				throw new ParameterNullErrorException(parame);
			}
			break;
		case "defaultLanId":
			if (!StringUtils.isNumeric(value)) {
				throw new ParameterNullErrorException(parame);
			}
			break;
		case "loginId":
			if (!StringUtils.isNumeric(value)) {
				throw new ParameterNullErrorException(parame);
			}
			break;
		case "pageNum":
			if (!StringUtils.isNumeric(value)) {
				throw new ParameterNullErrorException(parame);
			}
			break;
		case "pageSize":
			if (!StringUtils.isNumeric(value)) {
				throw new ParameterNullErrorException(parame);
			}
			break;
		case "price":
			if (!VerifyUtil.isBigDecimal(value)) {
				throw new ParameterNullErrorException(parame);
			}
			break;
		// case "app":
		// if (!Constants.appSet.contains(value)) {
		// throw new ParameterIllegalErrorException(parame);
		// }
		// break;
		}
	}

	@SuppressWarnings("rawtypes")
	private Map<String, String> getReqMap(ServletRequest request) {

		
		Map pathVariables = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

		Map<String, String> rstMap = new HashMap<String, String>();
		Set set = pathVariables.entrySet();
		for (Iterator itr = set.iterator(); itr.hasNext();) {
			Map.Entry me = (Map.Entry) itr.next();
			String key = me.getKey().toString();
			Object value = me.getValue();
			// 排除sign 文件上传的参数校验签名
			if (key.equals("upfile") || key.equals("watchData")) {
				continue;
			}
			rstMap.put(key, String.valueOf(value));
		}

		return rstMap;
	}

}