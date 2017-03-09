package com.ancun.core.web.model;

import java.io.Serializable;

/**
 * 
 * <p>
 * 
 * @author <a href="mailto:shenwei@ancun.com">ShenWei</a>
 * @version 2011-3-7 下午10:24:53
 */
public class ImageUploador implements Serializable {
	private static final long serialVersionUID = -6380033335136553695L;

	private String uploadedSubDir;
	private String originalFileUploadedName;
	private String scaleFileUploadedPath;
	//private String originalFileUploadedFullPath;//原始图片全路径
	private boolean delTag;
	private int no = 0;

	/**
	 * 原始文件上传之后的文件名
	 */
	public String getOriginalFileUploadedName() {
		return originalFileUploadedName;
	}

	/**
	 * 原始文件上传之后的文件名
	 */
	public void setOriginalFileUploadedName(String originalFileUploadedName) {
		this.originalFileUploadedName = originalFileUploadedName;
	}

	/**
	 * 上传子目录
	 */
	public String getUploadedSubDir() {
		return uploadedSubDir;
	}

	/**
	 * 上传子目录
	 */
	public void setUploadedSubDir(String uploadedSubDir) {
		this.uploadedSubDir = uploadedSubDir;
	}

	public String getScaleFileUploadedPath() {
		return scaleFileUploadedPath;
	}

	public void setScaleFileUploadedPath(String scaleFileUploadedPath) {
		this.scaleFileUploadedPath = scaleFileUploadedPath;
	}

	public boolean isDelTag() {
		return delTag;
	}

	public void setDelTag(boolean delTag) {
		this.delTag = delTag;
	}

	//	/**原始图片全路径*/
	//	public String getOriginalFileUploadedFullPath() {
	//		return originalFileUploadedFullPath;
	//	}

	//	public void setOriginalFileUploadedFullPath(String originalFileUploadedFullPath) {
	//		this.originalFileUploadedFullPath = originalFileUploadedFullPath;
	//	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}
}
