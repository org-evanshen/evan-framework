package org.evanframework.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Test;

public class ImageIoTest {
	private static final String SRC_IMG1 = "d:/IMG_0750.JPG";
	private static final String SRC_IMG2 = "d:/pic6.png";

	@Test
	public void test() throws IOException {
		File file = new File(SRC_IMG1);

		BufferedImage im = ImageIO.read(file);

		String formatName = "jpg";
		ImageIO.write(im, formatName, new File("d:/1.JPG"));
		
		file = new File(SRC_IMG2);
		
		formatName = "png";
		ImageIO.write(im, formatName, new File("d:/2.PNG"));
	}

}
