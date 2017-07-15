package org.evanframework.web.upload;

import java.io.Serializable;

/**
 * 上传文件返回类
 * <p>
 * 
 * @author shen.wei
 * @version 2011-3-3 下午01:09:08
 */
public class UploadedFile implements Serializable{
	private static final long serialVersionUID = 3367747020417702223L;
	private String fileOriginalName;
	private String fileUploadedName;
	private String uploadSubDir;
	private String uploadRootDir;
	private double fileSize;
	private String readUrl;

	/**
	 * 原始文件名
	 */
	public String getFileOriginalName() {
		return fileOriginalName;
	}

	/**
	 * 原始文件名
	 */
	public void setFileOriginalName(String fileOriginalName) {
		this.fileOriginalName = fileOriginalName;
	}

	/**
	 * 上传的文件的大小
	 */
	public double getFileSize() {
		return fileSize;
	}

	/**
	 * 上传的文件的大小(K)
	 */
	public void setFileSize(double fontSize) {
		this.fileSize = fontSize;
	}

	/**
	 * 上传之后的文件名(K)
	 */
	public String getFileUploadedName() {
		return fileUploadedName;
	}

	/**
	 * 上传之后的文件名
	 */
	public void setFileUploadedName(String fileUploadedName) {
		this.fileUploadedName = fileUploadedName;
	}

	/**
	 * 上传的子目录
	 */
	public String getUploadSubDir() {
		return uploadSubDir;
	}

	/**
	 * 上传的子目录
	 */
	public void setUploadSubDir(String uploadSubDir) {
		this.uploadSubDir = uploadSubDir;
	}

	public String getUploadRootDir() {
		return this.uploadRootDir;
	}
	
	/**
	 * 上传的根目录
	 */
	public void setUploadRootDir(String uploadRootDir) {
		this.uploadRootDir = uploadRootDir;
	}
	 
	/**
	 * 获取上传文件的全路径（uploadRootDir + uploadSubDir + fileUploadedName)
	 */
	public String getUploadFullPath(){
		return this.uploadRootDir + this.uploadSubDir + this.fileUploadedName;
	}
	
	/**
	 * 获取上传文件的子路径（uploadSubDir + fileUploadedName)
	 */
	public String getUploadSubPath(){
		return this.uploadSubDir + this.fileUploadedName;
	}
	
	
	/**
	 * 上传文件的读取url(不包括域名)
	 */
	public String getReadUrl(){	
		return this.readUrl;
	}

	/**
	 * 上传文件的读取url(不包括域名)
	 */
	public void setReadUrl(String readUrl) {
		this.readUrl = readUrl;
	}
}
