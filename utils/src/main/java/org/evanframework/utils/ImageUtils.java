package org.evanframework.utils;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * 图片
 *
 * @author mouhaining
 * @version 2019-07-08 上午9:56
 */
public class ImageUtils {
    private static Logger log = LoggerFactory.getLogger(ImageUtils.class);
    private final static float opacity = 1f;
    private final static float quality = 1f;

    /**
     * 压缩并打上水印(等比压缩)
     *
     * @param inputImagePath
     * @param outImagePath
     * @param scale
     * @param quality
     * @param position
     * @param waterMarkImagePath
     * @param opacity
     */
    public static void scaleAndWaterMark(String inputImagePath, String outImagePath, float scale, float quality, Positions position, String waterMarkImagePath, float opacity) {

        toFile(outImagePath,
                waterMark(
                        position,
                        waterMarkImagePath,
                        opacity,
                        scale(inputImagePath, scale, quality)
                )
        );
    }

    /**
     * 压缩并打上水印(按照尺寸压缩)
     *
     * @param inputImagePath
     * @param outImagePath
     * @param scaleWidth
     * @param scaleHeight
     * @param quality
     * @param position
     * @param waterMarkImagePath
     * @param opacity
     */
    public static void scaleAndWaterMark(String inputImagePath, String outImagePath, int scaleWidth, int scaleHeight, float quality, Positions position, String waterMarkImagePath, float opacity) {

        toFile(outImagePath,
                waterMark(
                        position,
                        waterMarkImagePath,
                        opacity,
                        scale(inputImagePath, scaleWidth, scaleHeight, quality)
                )
        );
    }

    /**
     * 压缩并打上水印(等比压缩)
     *
     * @param inputImagePath
     * @param outImagePath
     * @param scale
     * @param waterMarkImagePath
     */
    public static void scaleAndWaterMark(String inputImagePath, String outImagePath, float scale, String waterMarkImagePath) {

        toFile(outImagePath,
                waterMark(
                        Positions.BOTTOM_RIGHT,
                        waterMarkImagePath,
                        1f,
                        scale(inputImagePath, scale)
                )
        );
    }

    /**
     * 压缩并打上水印(按照尺寸压缩)
     *
     * @param inputImagePath
     * @param outImagePath
     * @param scaleWidth
     * @param scaleHeight
     * @param waterMarkImagePath
     */
    public static void scaleAndWaterMark(String inputImagePath, String outImagePath, int scaleWidth, int scaleHeight, String waterMarkImagePath) {

        toFile(outImagePath,
                waterMark(
                        Positions.BOTTOM_RIGHT,
                        waterMarkImagePath,
                        opacity,
                        scale(inputImagePath, scaleWidth, scaleHeight)
                )
        );
    }

    /**
     * 压缩并打上水印(等比压缩)
     *
     * @param inputImagePath
     * @param outImagePath
     * @param scale
     * @param quality
     * @param waterMarkImagePath
     * @param opacity
     */
    public static void scaleAndWaterMark(String inputImagePath, String outImagePath, float scale, float quality, String waterMarkImagePath, float opacity) {

        toFile(outImagePath,
                waterMark(
                        Positions.BOTTOM_RIGHT,
                        waterMarkImagePath,
                        opacity,
                        scale(inputImagePath, scale, quality)
                )
        );
    }

    /**
     * 压缩并打上水印(按照尺寸压缩)
     *
     * @param inputImagePath
     * @param outImagePath
     * @param scaleWidth
     * @param scaleHeight
     * @param waterMarkImagePath
     * @param opacity
     */
    public static void scaleAndWaterMark(String inputImagePath, String outImagePath, int scaleWidth, int scaleHeight, String waterMarkImagePath, float opacity) {

        toFile(outImagePath,
                waterMark(
                        Positions.BOTTOM_RIGHT,
                        waterMarkImagePath,
                        opacity,
                        scale(inputImagePath, scaleWidth, scaleHeight)
                )
        );
    }

    /**
     * 压缩
     *
     * @param inputImagePath
     * @param outImagePath
     * @param scale
     * @param quality
     */
    public static void scale(String inputImagePath, String outImagePath, double scale, float quality) {
        toFile(outImagePath, scale(inputImagePath, scale, quality));
    }

    /**
     * 压缩
     *
     * @param inputImagePath
     * @param outImagePath
     * @param scale
     */
    public static void scale(String inputImagePath, String outImagePath, double scale) {
        toFile(outImagePath, scale(inputImagePath, scale));
    }

    /**
     * 水印
     *
     * @param inputImagePath
     * @param outImagePath
     * @param waterMarkImagePath
     * @param opacity
     */
    public static void waterMark(String inputImagePath, String outImagePath, String waterMarkImagePath, float opacity) {
        toFile(outImagePath, waterMark(Positions.BOTTOM_RIGHT, waterMarkImagePath, opacity, Thumbnails.of(inputImagePath)));
    }

    /**
     * 水印
     *
     * @param inputImagePath
     * @param outImagePath
     * @param waterMarkImagePath
     */
    public static void waterMark(String inputImagePath, String outImagePath, String waterMarkImagePath) {
        toFile(outImagePath, waterMark(Positions.BOTTOM_RIGHT, waterMarkImagePath, opacity, Thumbnails.of(inputImagePath)));
    }

    /**
     * 水印
     *
     * @param inputImagePath
     * @param outImagePath
     * @param position
     * @param waterMarkImagePath
     */
    public static void waterMark(String inputImagePath, String outImagePath, Positions position, String waterMarkImagePath) {
        toFile(outImagePath, waterMark(position, waterMarkImagePath, opacity, Thumbnails.of(inputImagePath)));
    }

    /**
     * 水印
     *
     * @param inputImagePath
     * @param outImagePath
     * @param position
     * @param waterMarkImagePath
     * @param opacity
     */
    public static void waterMark(String inputImagePath, String outImagePath, Positions position, String waterMarkImagePath, float opacity) {
        toFile(outImagePath, waterMark(position, waterMarkImagePath, opacity, Thumbnails.of(inputImagePath)));
    }

    /**
     * 写文件
     *
     * @param outImagePath
     * @param builder
     */
    public static void toFile(String outImagePath, Thumbnails.Builder<File> builder) {
        try {
            builder.toFile(outImagePath);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * 按尺寸压缩
     *
     * @param inputImagePath
     * @param scaleWidth
     * @param scaleHeight
     */
    public static Thumbnails.Builder<File> scale(String inputImagePath, int scaleWidth, int scaleHeight) {

        return Thumbnails.of(inputImagePath)
                .size(scaleWidth, scaleHeight);
    }

    /**
     * 按尺寸压缩
     *
     * @param inputImagePath
     * @param scaleWidth
     * @param scaleHeight
     * @param quality
     */
    public static Thumbnails.Builder<File> scale(String inputImagePath, int scaleWidth, int scaleHeight, float quality) {

        return Thumbnails.of(inputImagePath)
                .size(scaleWidth, scaleHeight)
                .outputQuality(quality);
    }

    /**
     * 等比压缩
     *
     * @param inputImagePath 图片路径
     * @param scale          压缩大小 0 - 1
     */
    public static Thumbnails.Builder<File> scale(String inputImagePath, double scale) {

        return Thumbnails.of(inputImagePath)
                .scale(scale);
    }

    /**
     * 等比压缩
     *
     * @param inputImagePath 图片路径
     * @param scale          压缩大小 0 - 1
     * @param quality        图片质量 0 - 1
     */
    public static Thumbnails.Builder<File> scale(String inputImagePath, double scale, float quality) {

        return Thumbnails.of(inputImagePath)
                .scale(scale)
                .outputQuality(quality);
    }

    /**
     * 水印
     *
     * @param position
     * @param waterMarkImagePath
     * @param opacity
     * @param builder
     */
    public static Thumbnails.Builder<File> waterMark(Positions position, String waterMarkImagePath, float opacity, Thumbnails.Builder<File> builder) {

        try {
            return builder.watermark(position, ImageIO.read(new File(waterMarkImagePath)), opacity);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
