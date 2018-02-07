package com.flf.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.flf.util.FileUpload;

/**
 * Created by shhao. Date: 14-9-1 Time:下午4:32
 */
@Controller
public class FileUploadController {

	Logger logger = LoggerFactory.getLogger(FileUploadController.class);

	@RequestMapping("upload")
	public void upload(@RequestParam("studentPhoto") MultipartFile file, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String filePath = FileUpload.uploadFile(file, "", request);
		logger.info("filePath:" + filePath);
		response.setContentType("text/html;charset=utf8");
		response.getWriter().write("<img src='" + filePath + "'/>");
	}

	@RequestMapping("toUpload")
	public String toUpload() {
		return "upload";
	}

}