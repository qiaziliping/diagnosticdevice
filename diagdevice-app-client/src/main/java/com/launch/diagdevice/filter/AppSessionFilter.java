package com.launch.diagdevice.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.launch.diagdevice.common.constant.AppCodeConstant;
import com.launch.diagdevice.common.constant.AppConstant;
import com.launch.diagdevice.common.result.Result;
import com.launch.diagdevice.common.utils.RedisUtil;
import com.launch.diagdevice.entity.request.RequestHead;

import net.sf.json.JSONObject;

//@WebFilter(urlPatterns = { "*.ado" }, filterName = "appSessionFilter")
//@Order(value = 2)
public class AppSessionFilter implements Filter {

	private Logger logger = LoggerFactory.getLogger(AppSessionFilter.class);

	/** 请求头 */
	private static final String REQUEST_HEAD = "requestHead";
	
	/** 不需要登录校验session的接口 */
	private List<String> excludeLogin  = new ArrayList<String>();

	@Autowired
	private RedisUtil redisUtil;

	@Override
	public void destroy() {
		if (logger.isDebugEnabled()) {
			logger.debug("---------------------AppSessioinFilter.destroy-->:");
		}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		logger.debug("---------------------AppSessioinFilter.doFilter-->:");

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
			logger.info("AppSessioinFilter请求接口：{}，request params: {}", URI, JSONObject.fromObject(paramMap));

			// ---------------判断session是否过期-------------------------

			// 判断是否需要验证登录session
			int isCheck = 1;
			for (String filter : excludeLogin) {
				if (URI.contains(filter)) {
					isCheck = 0;
					break;
				}
			}
			
			if (isCheck == 1) {
				boolean isMustParam = true; //必填参数
				String requestHead = paramMap.get(REQUEST_HEAD);
				
				String sessionId = null;
				String loginId = null;
				if (!StringUtils.isBlank(requestHead)) {
					JSONObject jsonHead = JSONObject.fromObject(requestHead);
					RequestHead head = (RequestHead) JSONObject.toBean(jsonHead, RequestHead.class);
					
//					sessionId = head.getSessionId();
					sessionId = "";
					loginId = String.valueOf(head.getLoginId());
					if (StringUtils.isBlank(loginId) || StringUtils.isBlank(sessionId)) {
						isMustParam = false;//必填为空
					}
				} else {
					isMustParam = false;//必填为空
				}
				
				// 必填参数不能为空
				if (!isMustParam) {
					mustParameterIsnotnull(result);
					jsonWriter(JSONObject.fromObject(result).toString(), response);
					return;
				}
				
				

				String key = AppConstant.DIAGDEVICE_APPUSER_TOKEN+":"+loginId;
				String redisSessionId = redisUtil.get(key);
				
				// 判断session是否过期
				if (!sessionId.equals(redisSessionId)) {
					result.setCode(AppCodeConstant.FORBIDDEN);
					result.setMessage(AppCodeConstant.checkCode(AppCodeConstant.FORBIDDEN));
					jsonWriter(JSONObject.fromObject(result).toString(), response);
				} else {
					chain.doFilter(request, response);// 调用接口
				}
				
				// ---------------判断session是否过期-------------------------
			} else {
				chain.doFilter(request, response);// 调用接口
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("------doFilter.exception>:" + e.getMessage());
		} 

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
		excludeLogin.add("/login.ado");
		excludeLogin.add("/register.ado");
		excludeLogin.add("/verifyAuthimage.ado");
	}

	/**
	 * 必填参数不能为空
	 * LIPING
	 */
	private void mustParameterIsnotnull(Result result) {
		result.setCode(AppCodeConstant.PARAM_LACK);
		result.setMessage(AppCodeConstant.checkCodeOld(AppCodeConstant.PARAM_LACK));
	}

}
