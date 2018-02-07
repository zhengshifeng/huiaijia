package com.base.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;


public class FileUploadUtil {
	/**
	 * @Title: fileUpload 
	 * @Description: 文件上传 
	 * @param fileName 文件名
	 * @param root 路径
	 * @param buffer 字节流
	 * @return Boolean IO异常返回false,成功放回true
	 */
	public static Boolean fileUpload(String fileName, String root, byte[] buffer) {
		try {
			File f = new File(root, fileName); //定义一个FILE文件，第一个参数是文件的路径，第二个是文件的名字
			OutputStream os = new FileOutputStream(f); //第一个文件的输出流
			os.write(buffer);
			os.flush();
			os.close();
		} catch(IOException e) {
			return false;
		}
		return true;
	}
}
