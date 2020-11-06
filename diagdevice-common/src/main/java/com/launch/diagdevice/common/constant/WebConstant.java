package com.launch.diagdevice.common.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 后台管理系统相关常量
 * @author LIPING
 * @version 0.0.1
 * @since 2018年9月15日
 */
@PropertySource(value={"classpath:diagdevice.properties"},encoding="UTF-8")
@Component
public class WebConstant {
	
	/**  文件上传地址  */
	public static String FILE_UPLOAD_URL;
	
	
	
	@Value(value="${file.upload.url}")
	public void setFILE_UPLOAD_URL(String FILE_UPLOAD_URL){
		WebConstant.FILE_UPLOAD_URL=FILE_UPLOAD_URL;
	}
	
	
	
	/** 2200:上传凭证非图片  */
	public static final int UPLOAD_FILE_NO_IMAGE = 2200;
	
	/** 1100:菜单子节点不为空，不能删除  */
	public static final int MENU_CHILD_IS_NOT_EMPTY = 1100;
	
	/** 1000:用户名不存在  */
	public static final int USERNAME_NOT_FOUND = 1000;
	/** 1010:密码错误  */
	public static final int PASSWORD_ERROR = 1010;
	/** 1015:验证码错误  */
	public static final int VERIFY_CODE_ERROR = 1015;
	/** 1016:验证码不能为空  */
	public static final int VERIFY_CODE_IS_EMPTY = 1016;
	
	
	public static String checkCode(int key) {
		String msg = "服务器内部错误";
		switch (key) {
			case USERNAME_NOT_FOUND:
				msg = "用户名不存在";
				break;
			case PASSWORD_ERROR:
				msg = "密码错误";
				break;
			case VERIFY_CODE_ERROR:
				msg = "验证码错误";
				break;
			case VERIFY_CODE_IS_EMPTY:
				msg = "验证码不能为空";
				break;
			case UPLOAD_FILE_NO_IMAGE:
				msg = "上传凭证非图片";
				break;
			case MENU_CHILD_IS_NOT_EMPTY:
				msg = "菜单子节点不为空，不能删除";
				break;
			default:
				break;
		}
		return msg;
	}
	

}
