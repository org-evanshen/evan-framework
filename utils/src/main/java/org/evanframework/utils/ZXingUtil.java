package org.evanframework.utils;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * 生产二维码的工具类
 *
 */
public class ZXingUtil {

	
	private static Log log = LogFactory.getLog(ZXingUtil.class);

	/**
	 * 生成二维码
	 * 
	 * @param content
	 *            二维码内容
	 * @param charset
	 *            编码二维码内容时采用的字符集(传null时默认采用UTF-8编码)
	 * @param imagePath
	 *            二维码图片存放路径(含文件名)
	 * @param width
	 *            生成的二维码图片宽度
	 * @param height
	 *            生成的二维码图片高度
	 * @param logoPath
	 *            logo头像存放路径(含文件名,若不加logo则传null即可)
	 *  生成二维码结果(true or false)
	 */

	@SuppressWarnings("deprecation")
	public static boolean encodeQRCodeImage(String content, String charset,
			String imagePath, int width, int height, String logoPath) {
		Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
		// 指定编码格式
		// hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		// 指定纠错级别(L--7%,M--15%,Q--25%,H--30%)
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		// 编码内容,编码类型(这里指定为二维码),生成图片宽度,生成图片高度,设置参数
		BitMatrix bitMatrix = null;
		try {
			bitMatrix = new MultiFormatWriter().encode(
					new String(content.getBytes(charset == null ? "UTF-8"
							: charset), "ISO-8859-1"), BarcodeFormat.QR_CODE,
					width, height, hints);
		} catch (Exception e) {
			log.error("编码待生成二维码图片的文本时发生异常,堆栈轨迹如下", e);
			return false;
		}
		// 生成的二维码图片默认背景为白色,前景为黑色,但是在加入logo图像后会导致logo也变为黑白色,至于是什么原因还没有仔细去读它的源码
		// 所以这里对其第一个参数黑色将ZXing默认的前景色0xFF000000稍微改了一下0xFF000001,最终效果也是白色背景黑色前景的二维码,且logo颜色保持原有不变
		MatrixToImageConfig config = new MatrixToImageConfig(0xFF000001,
				0xFFFFFFFF);
		// 这里要显式指定MatrixToImageConfig,否则还会按照默认处理将logo图像也变为黑白色(如果打算加logo的话,反之则不须传MatrixToImageConfig参数)

		try {
			MatrixToImageWriter.writeToFile(bitMatrix,
					imagePath.substring(imagePath.lastIndexOf(".") + 1),
					new File(imagePath), config);
		} catch (IOException e) {
			log.error(String.format("生成二维码图片[%s]时遇到异常,堆栈轨迹如下", imagePath), e);
			return false;
		}

		// 此时二维码图片已经生成了,只不过没有logo头像,所以接下来根据传入的logoPath参数来决定是否加logo头像
		if (null == logoPath) {
			return true;
		} else {
			// 如果此时最终生成的二维码不是我们想要的,那么可以扩展MatrixToImageConfig类(反正ZXing提供了源码)
			// 扩展时可以重写其writeToFile方法,令其返回toBufferedImage()方法所生成的BufferedImage对象(尽管这种做法未必能解决为题,故需根据实际情景测试)
			// 然后替换这里overlapImage()里面的第一行BufferedImage image = ImageIO.read(new
			// File(imagePath));
			// 即private static void overlapImage(BufferedImage image, String
			// imagePath, String logoPath)
			try {
				// 这里不需要判断logoPath是否指向了一个具体的文件,因为这种情景下overlapImage会抛IO异常
				overlapImage(imagePath, logoPath);
				return true;
			} catch (IOException e) {
				log.error(String.format("为二维码图片[%s]添加logo头像[%s]时遇到异常,堆栈轨迹如下",
						imagePath, logoPath), e);
				return false;
			}
		}

	}

	/**
	 * 为二维码图片增加logo头像
	 * 
	 *  其原理类似于图片加水印
	 * @param imagePath
	 *            二维码图片存放路径(含文件名)
	 * @param logoPath
	 *            logo头像存放路径(含文件名)
	 */

	private static void overlapImage(String imagePath, String logoPath)
			throws IOException {

		BufferedImage image = ImageIO.read(new File(imagePath));
		int logoWidth = image.getWidth() / 5; // 设置logo图片宽度为二维码图片的五分之一
		int logoHeight = image.getHeight() / 5; // 设置logo图片高度为二维码图片的五分之一
		int logoX = (image.getWidth() - logoWidth) / 2; // 设置logo图片的位置,这里令其居中
		int logoY = (image.getHeight() - logoHeight) / 2; // 设置logo图片的位置,这里令其居中
		Graphics2D graphics = image.createGraphics();
		graphics.drawImage(ImageIO.read(new File(logoPath)), logoX, logoY,
				logoWidth, logoHeight, null);
		graphics.dispose();
		ImageIO.write(image,
				imagePath.substring(imagePath.lastIndexOf(".") + 1), new File(
						imagePath));

	}

	/**
	 * 解析二维码
	 * 
	 * @param imagePath
	 *            二维码图片存放路径(含文件名)
	 * @param charset
	 *            解码二维码内容时采用的字符集(传null时默认采用UTF-8编码)
	 *  解析成功后返回二维码文本,否则返回空字符串
	 */

	public static String decodeQRCodeImage(String imagePath, String charset) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(imagePath));
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
		if (null == image) {
			log.info("Could not decode QRCodeImage");
			return "";
		}
		BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(
				new BufferedImageLuminanceSource(image)));
		Map<DecodeHintType, String> hints = new HashMap<DecodeHintType, String>();
		hints.put(DecodeHintType.CHARACTER_SET, charset == null ? "UTF-8"
				: charset);
		Result result = null;
		try {
			result = new MultiFormatReader().decode(bitmap, hints);
			return result.getText();
		} catch (NotFoundException e) {
			log.info(String.format("二维码图片[%s]解析失败,堆栈轨迹如下", imagePath), e);
			return "";

		}

	}

	public static void main(String[] args) {

		encodeQRCodeImage("账号：123444 &nbsp;&nbsp;金额：30<br/>", null,
				"C:/cg987.png", 300, 300, "D:/test1234.jpg");

		// System.out.println(decodeQRCodeImage("C:/Users/Jadyer/Desktop/myQRCodeImage.png",
		// null));

	}

	 }
