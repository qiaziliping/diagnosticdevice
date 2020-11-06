package com.launch.diagdevice.common.configure;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) // 注解会在class中存在，运行时可通过反射获取
@Target(ElementType.METHOD) // 目标是方法
@Documented // 文档生成时，该注解将被包含在javadoc中，可去掉
public @interface IPValidate {
	// 第三方服务器和launch服务器之间调用校验
	public static String OS2LSIPVal = "oS2LSIPValidate";
	// launch dlcenter server和launch server之间校验
	public static String DS2LSIPVal = "dS2LSIPValidate";

	/**
	 * 模块名字
	 */
	String modelName() default "";

	/**
	 * 操作类型
	 */
	String option() default "";

}
