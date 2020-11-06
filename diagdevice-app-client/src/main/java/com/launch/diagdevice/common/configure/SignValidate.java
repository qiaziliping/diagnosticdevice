package com.launch.diagdevice.common.configure;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) // 注解会在class中存在，运行时可通过反射获取
@Target(ElementType.METHOD) // 目标是方法
@Documented // 文档生成时，该注解将被包含在javadoc中，可去掉
public @interface SignValidate {
	// 第三方服务器和launch服务器之间调用校验
	public static String OS2LSVal = "oS2LSValidate";
	// launch app和launch server之间校验
	public static String LA2LSVal = "lA2LSValidate";
	// 获取license校验
	public static String ObtainLicenseVal = "obtainLicenseVal";
	// 下载诊断软件license校验
	public static String DownloadDiagVal = "downloadDiagVal";

	/**
	 * 模块名字
	 */
	String modelName() default "";

	/**
	 * 操作类型
	 */
	String option() default "";

}