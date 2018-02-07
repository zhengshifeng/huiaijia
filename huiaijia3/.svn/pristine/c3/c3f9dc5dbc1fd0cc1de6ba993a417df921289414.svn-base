package com.flf.util;

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

public class FileUpload {

	private static final Logger log = Logger.getLogger(FileUpload.class);

	private static String FILE_BASE_PATH = File.separator + "opt" + File.separator + "haj" + File.separator;

	/**
	 * 图片文件上传
	 *
	 * @param subPath 分类保存，不同类型的文件放在不同类型目录下
	 * @return 返回该文件的网络地址
	 */
	public static String uploadFile(MultipartFile file, String subPath, HttpServletRequest request) throws IOException {
		String fileName = file.getOriginalFilename();
		log.info("图片上传文件名: " + fileName);
		int dotIndex = fileName.lastIndexOf(".");
		String fileNameSuffix = fileName.substring(dotIndex, fileName.length());
		fileName = System.currentTimeMillis() + fileNameSuffix;

		Calendar calendar = Calendar.getInstance();
		subPath += calendar.get(Calendar.YEAR) + File.separator;
		subPath += calendar.get(Calendar.MONTH) + File.separator;

		String filePath = FILE_BASE_PATH + subPath;
		log.info("图片上传后的目录: " + filePath);
		File tempFile = new File(filePath, fileName);
		if (!tempFile.exists()) {
			tempFile.mkdirs();
		}
		file.transferTo(tempFile);
		return request.getScheme() + "://" + request.getServerName() + request.getContextPath() + File.separator
				+ subPath + tempFile.getName();
	}

	/**
	 * excel文件上传
	 *
	 * @param file
	 * @param subPath 分类保存，不同类型的文件放在不同类型目录下
	 * @return 返回该文件的磁盘路径
	 * @throws IOException
	 * @Author SevenWong
	 */
	public static String uploadExcel(MultipartFile file, String subPath)
			throws IOException {
		String fileName = file.getOriginalFilename();

		Calendar calendar = Calendar.getInstance();
		subPath += calendar.get(Calendar.YEAR) + File.separator;
		subPath += calendar.get(Calendar.MONTH) + File.separator;

		String filePath = FILE_BASE_PATH + subPath;
		log.info("上传后的文件地址: " + filePath + fileName);
		File tempFile = new File(filePath, fileName);
		if (!tempFile.exists()) {
			tempFile.mkdirs();
		}
		file.transferTo(tempFile);
		filePath = filePath + tempFile.getName();
		return filePath;
	}
}
