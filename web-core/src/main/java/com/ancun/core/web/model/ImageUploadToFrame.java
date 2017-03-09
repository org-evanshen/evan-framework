package com.ancun.core.web.model;

import org.springframework.web.multipart.MultipartFile;

public class ImageUploadToFrame {
	private MultipartFile file;
	private int maxSize;

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public int getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}
}
