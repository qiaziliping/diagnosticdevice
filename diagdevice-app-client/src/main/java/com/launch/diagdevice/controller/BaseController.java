package com.launch.diagdevice.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.launch.diagdevice.common.constant.AppCodeConstant;
import com.launch.diagdevice.common.result.Result;
import com.launch.diagdevice.common.util.JsonHelper;
import com.launch.diagdevice.entity.request.RequestHead;

/**Controller基类
 * @author LIPING
 *
 */
public class BaseController {

	/**
	 * 默认的字符编码
	 */
	public String DEFAULT_LANCODE = "zh-cn";
	
	
	public void setResultOld(Result result,Integer code) {
		result.setCode(code);
		result.setMessage(AppCodeConstant.checkCodeOld(code));
	}
	
	public void setResult(Result result,Integer code) {
		result.setCode(code);
		result.setMessage(AppCodeConstant.checkCode(code));
	}
	
	public void jsonWrite(String json, HttpServletResponse reponse, HttpServletRequest request) {
		PrintWriter out = null;
		try {
			out = reponse.getWriter();
			reponse.setCharacterEncoding("utf-8");
			reponse.setContentType("application/json charset=utf8");
			out.write(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public RequestHead requestHeadToBean(String requestHead) {
		
		JsonHelper jsonhp = new JsonHelper();
		RequestHead vo = jsonhp.fromObj(requestHead, RequestHead.class);
		return vo;
	}

	public void jsonWriter(String json, HttpServletResponse response) {
		PrintWriter out = null;
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取post字符串
	 * @return
	 * @throws IOException 
	 */
	public String getPostJson(HttpServletRequest request) throws IOException {
		byte buffer[] = getRequestPostBytes(request);
		String charEncoding = request.getCharacterEncoding();
		if (charEncoding == null) {
			charEncoding = "UTF-8";
		}
		String param = new String(buffer, charEncoding);
		System.out.println("传的参数为：" + param);
		return param;
	}

	public byte[] getRequestPostBytes(HttpServletRequest request) throws IOException {
		int contentLength = request.getContentLength();
		if (contentLength < 0) {
			return null;
		}
		byte buffer[] = new byte[contentLength];
		for (int i = 0; i < contentLength;) {

			int readlen = request.getInputStream().read(buffer, i, contentLength - i);
			if (readlen == -1) {
				break;
			}
			i += readlen;
		}
		return buffer;
	}
}
