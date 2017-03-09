package com.ancun.core.web.model;

/**
 * 
 * <p>
 * 
 * @author <a href="mailto:shenwei@ancun.com">ShenWei</a>
 * @version 2011-8-13 下午03:27:19
 */
public class ImageCut {
	private int x1;
	private int y1;
	private int x2;
	private int y2;
	private int width;
	private int height;
	private String srcImagePath;

	public int getX1() {
		return x1;
	}

	public void setX1(int x1) {
		this.x1 = x1;
	}

	public int getY1() {
		return y1;
	}

	public void setY1(int y1) {
		this.y1 = y1;
	}

	public int getX2() {
		return x2;
	}

	public void setX2(int x2) {
		this.x2 = x2;
	}

	public int getY2() {
		return y2;
	}

	public void setY2(int y2) {
		this.y2 = y2;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getSrcImagePath() {
		return srcImagePath;
	}

	public void setSrcImagePath(String srcImagePath) {
		this.srcImagePath = srcImagePath;
	}
}
