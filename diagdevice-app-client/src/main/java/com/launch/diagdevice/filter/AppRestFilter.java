package com.launch.diagdevice.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.launch.diagdevice.common.constant.AppCodeConstant;
import com.launch.diagdevice.common.result.Result;
import com.launch.diagdevice.common.util.MD5Util;

import net.sf.json.JSONObject;

//@WebFilter(urlPatterns = { "*.ado" }, filterName = "appRestFilter")
public class AppRestFilter implements Filter {

	private Logger logger = LoggerFactory.getLogger(AppRestFilter.class);

	private static final String SIGN = "sign";
	/** 签名key */
	private final static String SIGN_KEY = "DIAG_DEVICE_BC_LP";


	@Override
	public void destroy() {
		if (logger.isDebugEnabled()) {
			logger.debug("---------------------AppRestFilter.destroy-->:");
		}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		logger.debug("---------------------AppRestFilter.doFilter-->:");

		HttpServletRequest httpRequest = (HttpServletRequest) request;

		Result result = new Result();

		try {
			String URI = httpRequest.getRequestURI();
			logger.info("--过滤器--requestURI>:{}", URI);

			String contentType = request.getContentType();// 获取请求的content-type
			// 文件上传请求和form-data请求
			if (StringUtils.isNotBlank(contentType) && contentType.contains("multipart/form-data")) { // *特殊请求
				CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
				request = multipartResolver.resolveMultipart(httpRequest);
			}

			// 获取请求参数
			Map<String, String> paramMap = getReqMap(request);
			logger.info("AppRestFilter请求接口：{}，request params: {}", URI, JSONObject.fromObject(paramMap));

			// 签名校验
			String sign = paramMap.get(SIGN);
			if (StringUtils.isBlank(sign)) {
				result.setCode(AppCodeConstant.SIGN_FAIL);
				result.setMessage(AppCodeConstant.checkCodeOld(AppCodeConstant.SIGN_FAIL));
				jsonWriter(JSONObject.fromObject(result).toString(), response);
				return;
			}
			if (!checkSign(paramMap, sign)) {
				result.setCode(AppCodeConstant.SIGN_FAIL);
				result.setMessage(AppCodeConstant.checkCodeOld(AppCodeConstant.SIGN_FAIL));
				jsonWriter(JSONObject.fromObject(result).toString(), response);
				return;
			}

			chain.doFilter(request, response);// 调用接口
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("------doFilter.exception>:" + e.getMessage());
		}

	}

	/**
	 * 校验签名
	 * LIPING
	 */
	private boolean checkSign(Map<String, String> paramMap, String sign2) {
		Map<String, String> condMap = new HashMap<String, String>();

		for (String key : paramMap.keySet()) {
			if (key.equals(SIGN)) {
				continue;
			}
			condMap.put(key, paramMap.get(key));
		}

		String result = buildSign(condMap, SIGN_KEY);
		if (result.equals(sign2)) {
			return true;
		}
		return false;
	}

	public String buildSign(Map<String, String> params, String skey) {
		Map<String, String> treeMap = new TreeMap<String, String>(params);
		StringBuilder sb = new StringBuilder();
		for (String key : treeMap.keySet()) {
			sb.append(key).append("=").append(treeMap.get(key)).append("&");
		}
		String bizStr = sb.toString().substring(0, sb.toString().length() - 1);
		logger.info("---待加密的源参数串为--->:" + bizStr);
		String str = MD5Util.MD5(bizStr);
		logger.info("---第一次MD5加密--->:" + str);
		String sign = MD5Util.MD5(str + skey);
		logger.info("---加密后签名--->:" + sign);
		return sign.toString();
	}
	
	/**
	 * 相应到客户端
	 * LIPING
	 */
	private void jsonWriter(String result, ServletResponse response) {
		PrintWriter out = null;
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		try {
			if (out == null)
				out = response.getWriter();

			out.println(result);
		} catch (IOException e) {
			logger.error("---jsonWriter---{}", e.getMessage());
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.flush();
				out.close();
			}
		}
	}

	@SuppressWarnings("rawtypes")
	private Map<String, String> getReqMap(ServletRequest request) {

		Map<String, String> m = new HashMap<String, String>();
		Map map = request.getParameterMap();
		Set keSet = map.entrySet();
		for (Iterator itr = keSet.iterator(); itr.hasNext();) {
			Map.Entry me = (Map.Entry) itr.next();
			String ok = me.getKey().toString();
			Object ov = me.getValue();
			// 排除sign 文件上传的参数校验签名
			if (ok.equals("upfile") || ok.equals("watchData")) {
				continue;
			}
			String[] value = new String[0];
			if (ov instanceof String[]) {
				value = (String[]) ov;
			} else {
				value[0] = ov.toString();
			}
			m.put(ok, value[0]);
		}
		return m;
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		logger.debug("---------------------AppRestFilter.init-->:");
	}

}
