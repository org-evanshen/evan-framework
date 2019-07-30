package org.evanframework.utils;

import org.junit.Test;

public class ImageUtilsTest {
    private static final String SRC_IMG1 = "d:/test/123.jpg";
    private static final String SRC_WATERMARK = "d:/waterMark.png";
    private static final String SRC_IMG2 = "d:/123.JPG";

    @Test
    public void testScaleDefault() {
        ImageUtils.toFile("d:/test/123-2.jpg", ImageUtils.scale(SRC_IMG1, 0.1));
    }

    @Test
    public void testScaleHasQuality() {
        ImageUtils.toFile("d:/test/123-3.jpg", ImageUtils.scale(SRC_IMG1, 0.1,1f));
    }

    @Test
    public void testWaterMark() {
         ImageUtils.waterMark(SRC_IMG1,"d:/test/123-waterMark.jpg", SRC_WATERMARK);
    }

    @Test
    public void testScaleAndWaterMark() {
        ImageUtils.scaleAndWaterMark(SRC_IMG1, "d:/test/123-waterMark.jpg", 0.2f, SRC_WATERMARK);
    }
}
