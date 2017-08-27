package org.evanframework.web.utils;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

/**
 * 验证工具类
 * <p>
 * <p>
 *
 * @version %I%, %G%
 */
public class ValidateCodeImageRender {

    /**
     * 渲染校验图片
     */
    public static void render(HttpServletResponse response, String validateCode,
                              int imageHeight) throws IOException {
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "No-cache");
        response.setDateHeader("Expires", 0);
        // 指定生成的响应是图片
        response.setContentType("image/jpeg");

        createImage(validateCode, imageHeight, response.getOutputStream());
        response.flushBuffer();
    }

    /**
     * @param validateCode
     * @param imageHeight
     * @param os
     * @throws IOException
     */
    private static void createImage(String validateCode, int imageHeight, OutputStream os) throws IOException {

        int fontSize = imageHeight - 10;

        int width = validateCode.length() * (int) (fontSize * 0.95);
        int height = imageHeight;
        BufferedImage image = new BufferedImage(width, imageHeight, BufferedImage.TYPE_INT_RGB); // 创建BufferedImage类的对象
        Graphics gPanel = image.getGraphics(); // 创建Graphics类的对象
        // Graphics gFont = image.getGraphics(); // 创建Graphics类的对象
        // Graphics2D g2d = (Graphics2D) g; // 通过Graphics类的对象创建一个Graphics2D类的对象
        Random random = new Random(); // 实例化一个Random对象
        Font mFont = new Font("微软雅黑", Font.PLAIN, fontSize); // 通过Font构造字体
        Color panelColor = getRandColor(200, 250);
        gPanel.setColor(panelColor); // 改变图形的当前颜色为随机生成的颜色
        gPanel.fillRect(0, 0, width, height); // 绘制一个填色矩形
        gPanel.setFont(mFont);

        // 画一条折线
        // BasicStroke bs = new BasicStroke(2f, BasicStroke.CAP_BUTT,
        // BasicStroke.JOIN_BEVEL); // 创建一个供画笔选择线条粗细的对象
        // g2d.setStroke(bs); // 改变线条的粗细
        gPanel.setColor(Color.DARK_GRAY); // 设置当前颜色为预定义颜色中的深灰色
        int[] xPoints = new int[3];
        int[] yPoints = new int[3];
        for (int j = 0; j < 3; j++) {
            xPoints[j] = random.nextInt(width - 1);
            yPoints[j] = random.nextInt(height - 1);
        }
        gPanel.drawPolyline(xPoints, yPoints, 3);

        char[] chars = validateCode.toCharArray();
        int i = 0;
        for (char c : chars) {
            Color color = new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110));
            gPanel.setColor(color);
            // 随机缩放文字并将文字旋转指定角度
            // 将文字旋转指定角度
            // Graphics2D g2d_word = (Graphics2D) gPanel;
            // AffineTransform trans = new AffineTransform();
            // trans.rotate(random.nextInt(20) * 3.14 / 180, 15 * i + 10, 6);
            // // 缩放文字
            // float scaleSize = random.nextFloat() + 0.9f;
            // if (scaleSize > 1.1f) {
            // scaleSize = 1f;
            // }
            // trans.scale(scaleSize, scaleSize);
            // g2d_word.setTransform(trans);

            gPanel.drawString(String.valueOf(c), (int) Math.round(fontSize * i * 0.8) + 12,
                    (int) Math.round(height * 0.7) + 3);

            i++;
        }

        gPanel.dispose();

        JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(image);
        if (jep != null) {
            jep.setQuality(0.999f, true);
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(os, jep);
            encoder.encode(image);
        } else {
            ImageIO.write(image, "JPEG", os);
        }
    }

    /**
     * 给定范围获得随机颜色
     */
    private static Color getRandColor(int fc, int bc) {
        Random random = new Random();
        if (fc > 255)
            fc = 255;
        if (bc > 255)
            bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }
}
