package org.evanframework.utils;

import java.awt.image.BufferedImage;

import org.junit.Test;

public class ImageUtilsTest {
	private static final String SRC_IMG1 = "d:/123.JPG";
	private static final String SRC_IMG2 = "d:/123.JPG";

	@Test
	public void test() {
		ImageUtils.scale(SRC_IMG1, "d:/123-1.JPG", 500);
		ImageUtils.scale(SRC_IMG1, "d:/123-2.JPG", 500);

		BufferedImage image = ImageUtils.getBufferedImage(SRC_IMG1);

		ImageUtils.cut(0, 0, image.getWidth(), image.getWidth(), SRC_IMG1, "d:/123-cut.JPG");
		ImageUtils.scale("d:/123-cut.JPG", "d:/123-scale.JPG", 120);

		//ImageUtils.scaleAndWaterMark(SRC_IMG2, "d:/3/3.JPG", 700, "",null,null,"d:\\home7.jpg");
	}
}
