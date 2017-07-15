package org.evanframework.web.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FileDownLoadUtill implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory
			.getLogger(FileDownLoadUtill.class);

	public static void downLoad(File file, HttpServletResponse response) {
		InputStream inputStream = null;
		OutputStream os = null;
		try {
			inputStream = new FileInputStream(file);
			String filename = file.getName();

			String fileName = URLEncoder.encode(filename, "UTF-8");

			os = response.getOutputStream();
			response.reset();
			response.setHeader(
					"Content-Disposition",
					"attachment;filename="
							+ new String(fileName
									.getBytes("utf-8"),
									"ISO8859-1"));
			byte[] b = new byte[2048];
			int len;
			while ((len = inputStream.read(b)) > 0) {
				os.write(b, 0, len);
			}
			inputStream.close();
			os.flush();
			os.close();
		} catch (Exception e) {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
				if (os != null) {
					os.flush();
					os.close();
				}
				throw new RuntimeException(e);
			} catch (IOException e1) {
			}
		}
	}

	public static void downLoad(File file, HttpServletResponse response,
			HttpServletRequest request) {
		InputStream inputStream = null;
		OutputStream os = null;

		try {
			inputStream = new FileInputStream(file);
			String filename = file.getName();
			if (request.getHeader("User-Agent").contains("MSIE")
					|| request.getHeader("User-Agent").contains(
							"Trident")) {
				filename = URLEncoder.encode(filename, "UTF-8");
			} else {
				filename = new String(filename.getBytes("UTF-8"),
						"ISO8859-1");
			}
			os = response.getOutputStream();
			response.reset();
			response.setHeader("Content-Disposition",
					"attachment;filename=" + filename);
			byte[] b = new byte[2048];
			int len;
			while ((len = inputStream.read(b)) > 0) {
				os.write(b, 0, len);
			}
			os.flush();
			os.close();
			logger.info("下载成功");
		} catch (Exception e) {
			logger.error("下载失败", e);
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
				if (os != null) {
					os.flush();
					os.close();
				}
			} catch (IOException e1) {
				logger.error("下载失败e1", e1);
			}
		}

	}

	/**
	 * 下载文件时指定下载名
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @param filePath
	 *            文件全路径
	 * @param fileName
	 *            指定客户端下载时显示的文件名
	 * @throws IOException
	 */
	public static void downloadFile(HttpServletRequest request,
	        HttpServletResponse response, String filePath, String fileName)
	        throws IOException {
	    BufferedInputStream bis = null;
	    BufferedOutputStream bos = null;
	 
	    bis = new BufferedInputStream(new FileInputStream(filePath));
	    bos = new BufferedOutputStream(response.getOutputStream());
	 
	    long fileLength = new File(filePath).length();
	 
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("multipart/form-data");
	    /*
	     * 解决各浏览器的中文乱码问题
	     */
	    String userAgent = request.getHeader("User-Agent");
	    byte[] bytes = userAgent.contains("MSIE") ? fileName.getBytes()
	            : fileName.getBytes("UTF-8"); // fileName.getBytes("UTF-8")处理safari的乱码问题
	    fileName = new String(bytes, "ISO-8859-1"); // 各浏览器基本都支持ISO编码
	    response.setHeader("Content-disposition",
	            String.format("attachment; filename=\"%s\"", fileName));
	 
	    response.setHeader("Content-Length", String.valueOf(fileLength));
	    byte[] buff = new byte[2048];
	    int bytesRead;
	    while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
	        bos.write(buff, 0, bytesRead);
	    }
	    bis.close();
	    bos.close();
	 
	}
	 
	/**
	 * 下载文件时不指定下载文件名称
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @param filePath
	 *            文件全路径
	 * @throws IOException
	 */
	public static void downloadFile(HttpServletRequest request,
	        HttpServletResponse response, String filePath) throws IOException {
	    File file = new File(filePath);
	    downloadFile(request, response, filePath, file.getName());
	}
}
