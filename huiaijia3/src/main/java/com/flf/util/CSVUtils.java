package com.flf.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * CSV操作(导出和导入)
 */
public class CSVUtils {

	/**
	 * 导出
	 *
	 * @param file     csv文件(路径+文件名)，csv文件不存在会自动创建
	 * @param dataList 数据
	 * @return
	 */
	public static boolean exportCsv(File file, List<String> dataList) {
		boolean isSuccess;

		FileOutputStream out = null;
		OutputStreamWriter osw = null;
		BufferedWriter bw = null;
		try {
			out = new FileOutputStream(file);

			//加上UTF-8文件的标识字符
			out.write(new byte[]{(byte) 0xEF, (byte) 0xBB, (byte) 0xBF});
			osw = new OutputStreamWriter(out, "UTF-8");
			bw = new BufferedWriter(osw);
			if (dataList != null && !dataList.isEmpty()) {
				for (String data : dataList) {
					bw.append(data).append("\n");
				}
			}
			isSuccess = true;
		} catch (Exception e) {
			isSuccess = false;
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (osw != null) {
				try {
					osw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return isSuccess;
	}

	/**
	 * 导入
	 *
	 * @param file csv文件(路径+文件)
	 * @return
	 */
	public static List<String> importCsv(File file) {
		List<String> dataList = new ArrayList<>();

		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
			String line;
			while ((line = br.readLine()) != null) {
				dataList.add(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return dataList;
	}
}