package com.launch.diagdevice.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtil {

	
//	private static Logger logger = LoggerFactory.getLogger(FileUploadUtil.class);
	
	/**
	 * file是文件
	 * path 上传之后的文件路径
	 * updateFileName 修改之后的文件名，不包括后缀,为修改可传null
	 * LIPING
	 */
	public static String upload(MultipartFile file, String path, String updateFileName) {
		String dirPath = "";
		try {
			String fileName = file.getOriginalFilename();

			// 得到文件后缀
			int pos = fileName.lastIndexOf(".");
			String extName = fileName.substring(pos + 1);

			// 判断上传文件必须是zip或者是rar否则不允许上传
			/*
			 * if (!extName.equals("zip") && !extName.equals("rar")) { return
			 * "-1"; }
			 */

			// 一个用户一个文件夹
//			path = path + File.separator + userId;
//			path = path + File.separator ;

			File descFile = new File(path);
			if (!descFile.exists()) {
				descFile.mkdirs();
			}

			// 是否保留原来的文件名
			if (!StringUtils.isEmpty(updateFileName)) {
				fileName = updateFileName + "." + extName;
			}
			// 根据服务器的文件保存地址和原文件名创建目录文件全路径
			dirPath = path + File.separator + fileName;

			File pushFile = new File(dirPath);
			// 传输文件
			file.transferTo(pushFile);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dirPath;

	}

	public static boolean isImage(String fileName) {
		boolean isFlag = fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".bmp")
				|| fileName.endsWith(".gif") || fileName.endsWith(".png");

		return isFlag;
	}
	

}
