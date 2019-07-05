package org.evanframework.utils;

import com.sun.image.codec.jpeg.ImageFormatException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageInputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.Iterator;

/**
 * 图片处理工具类
 * <p>
 * 
 * @author shen.wei
 * @version 2011-2-29 下午12:30:04
 */
// @SuppressWarnings("restriction")
public class ImageUtils {
	private static Logger log = LoggerFactory.getLogger(ImageUtils.class);

	/**
	 * 打水印
	 * <p>
	 * author: shen.wei<br>
	 * version: 2011-2-29 下午12:30:20 <br>
	 * 
	 * @param inputImagePath
	 *            原图片
	 * @param outImagePath
	 *            输出路径
	 * @param waterMarkText
	 *            文字水印，打在中间
	 * @param waterMarkImage
	 *            图片水印，打在右下角
	 */
	public static void waterMark(String inputImagePath, String outImagePath, String waterMarkText, String waterMarkImage) {
		waterMark(inputImagePath, outImagePath, waterMarkText, null, null, waterMarkImage);
	}

	/**
	 * 打水印
	 * <p>
	 * author: shen.wei<br>
	 * version: 2011-3-6 上午11:35:47 <br>
	 * 
	 * @param inputImagePath
	 *            输入图片全目录，包括盘符和文件名
	 * @param outImagePath
	 *            输出图片目录，包括盘符和文件名
	 * @param waterMarkText
	 *            水印文字
	 * @param color
	 *            水印颜色 默认Color.WHITE;
	 * @param font
	 *            水印字体 new Font("黑体", Font.BOLD, 30)
	 * 
	 * @param waterMarkImagePath
	 *            图片水印路径
	 */
	public static void waterMark(String inputImagePath, String outImagePath, String waterMarkText, Color color,
			Font font, String waterMarkImagePath) {
		BufferedImage bi = null;

		try {
			if (log.isDebugEnabled()) {
				log.debug(" Water mark file: [" + inputImagePath + "]");
			}
			bi = ImageIO.read(new File(inputImagePath));
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}

		if (bi != null) {
			waterMark(bi, outImagePath, waterMarkText, color, font, waterMarkImagePath);
		}
	}

	// /**
	// * 生成缩率图
	// * <p>
	// * author: shen.wei<br>
	// * version: 2011-8-11 下午01:27:29 <br>
	// *
	// * @param inputImagePath
	// * @param outImagePath
	// * @param scaleWidth
	// * @param scaleHeight
	// */
	// @Deprecated
	// public static void scale(String inputImagePath, String outImagePath, int
	// scaleWidth, int scaleHeight) {
	// BufferedImage bi = scale(inputImagePath, scaleWidth, scaleHeight);
	// writeFile(outImagePath, bi);
	// }

	/**
	 * 生成缩率图(等比例压缩)
	 * <p>
	 * author: shen.wei<br>
	 * version: 2011-8-11 下午01:27:23 <br>
	 * 
	 * @param inputImagePath
	 * @param outImagePath
	 * @param maxSize
	 */
	public static void scale(String inputImagePath, String outImagePath, int maxSize) {
		BufferedImage bi = scale(inputImagePath, maxSize);
		writeFile(outImagePath, bi);
	}

	// /**
	// * 生成缩率图，并打上水印
	// * <p>
	// * author: shen.wei<br>
	// * version: 2011-3-7 下午02:45:04 <br>
	// *
	// * @param inputImagePath
	// * @param outImagePath
	// * @param scaleWidth
	// * @param scaleHeight
	// * @param waterMarkText
	// * @param color
	// * 水印颜色 默认Color.WHITE;
	// * @param font
	// * 水印字体 new Font("", Font.BOLD, 26)
	// * @param waterMarkImagePath
	// * 图片水印路径
	// */
	// @Deprecated
	// public static void scaleAndWaterMark(String inputImagePath, String
	// outImagePath, int scaleWidth,
	// int scaleHeight, String waterMarkText, Color color, Font font, String
	// waterMarkImagePath) {
	//
	// BufferedImage bi = scale(inputImagePath, scaleWidth, scaleHeight);
	// waterMark(bi, outImagePath, waterMarkText, color, font,
	// waterMarkImagePath);
	// }

	// /**
	// * 生成缩率图，并打上水印
	// * <p>
	// * 水印颜色 默认Color.WHITE; 水印字体 new Font("", Font.BOLD, 26)
	// * <p>
	// * author: shen.wei<br>
	// * version: 2011-3-7 下午02:45:04 <br>
	// *
	// * @param inputImagePath
	// * @param outImagePath
	// * @param scaleWidth
	// * @param scaleHeight
	// * @param waterMarkText
	// * @param waterMarkImagePath
	// * 图片水印路径
	// *
	// */
	// @Deprecated
	// public static void scaleAndWaterMark(String inputImagePath, String
	// outImagePath, int scaleWidth,
	// int scaleHeight, String waterMarkText, String waterMarkImagePath) {
	//
	// BufferedImage bi = scale(inputImagePath, scaleWidth, scaleHeight);
	// waterMark(bi, outImagePath, waterMarkText, null, null, null);
	// }

	/**
	 * 生成缩率图(等比例压缩)，并打上水印
	 * <p>
	 * author: shen.wei<br>
	 * version: 2011-8-11 下午01:30:34 <br>
	 * 
	 * @param inputImagePath
	 * @param outImagePath
	 * @param maxSize
	 * @param waterMarkText
	 * @param waterMarkImagePath
	 */
	public static void scaleAndWaterMark(String inputImagePath, String outImagePath, int maxSize, String waterMarkText,
			String waterMarkImagePath) {

		BufferedImage bi = scale(inputImagePath, maxSize);
		waterMark(bi, outImagePath, waterMarkText, null, null, null);
	}

	/**
	 * 压缩并打上水印
	 * <p>
	 * author: shen.wei<br>
	 * version: 2011-8-11 下午01:29:39 <br>
	 * 
	 * @param inputImagePath
	 * @param outImagePath
	 * @param maxSize
	 * @param waterMarkText
	 * @param color
	 * @param font
	 * @param waterMarkImagePath
	 */
	public static void scaleAndWaterMark(String inputImagePath, String outImagePath, int maxSize, String waterMarkText,
			Color color, Font font, String waterMarkImagePath) {

		BufferedImage bi = scale(inputImagePath, maxSize);
		waterMark(bi, outImagePath, waterMarkText, color, font, waterMarkImagePath);
	}

	/**
	 * 等比例压缩
	 * <p>
	 * author: shen.wei<br>
	 * version: 2011-8-11 下午01:18:44 <br>
	 * 
	 * @param inputImagePath
	 * @param maxSize
	 *            压缩后的最大宽或高
	 */
	private static BufferedImage scale(String inputImagePath, int maxSize) {
		if (maxSize == 0) {
			throw new IllegalArgumentException("scaleWidth and scaleHeight can not set 0 all!");
		}

		Graphics2D g = null;
		try {
			BufferedImage inputImage = ImageIO.read(new File(inputImagePath)); // 读入文件
			double imgWidth = inputImage.getWidth(); // 得到源图宽
			double imgHeight = inputImage.getHeight(); // 得到源图高

			double w = 0;
			double h = 0;

			if (imgWidth > maxSize && imgHeight > maxSize) {
				if (imgWidth > imgHeight) {// 寬>高,按寬进行缩率
					w = maxSize;
					h = (imgHeight / imgWidth) * maxSize;
				} else {// 寬<高,按高进行缩率
					h = maxSize;
					w = (imgWidth / imgHeight) * maxSize;
				}
			} else {
				w = imgWidth;
				h = imgHeight;
			}

			Image image = inputImage.getScaledInstance((int) w, (int) h, Image.SCALE_SMOOTH);
			BufferedImage outputImage = new BufferedImage((int) w, (int) h, BufferedImage.TYPE_INT_RGB);

			g = (Graphics2D) outputImage.getGraphics();
			g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			// g.drawRenderedImage(inputImage,
			// AffineTransform.getScaleInstance(w, h));
			g.drawImage(image, 0, 0, null); // 绘制缩小后的图片

			return outputImage;

		} catch (Exception e) {
			throw new IllegalStateException(e);
		} finally {
			if (g != null) {
				g.dispose();
				g = null;
			}
		}
	}

	/**
	 * 图片裁切 company: hundsun
	 * 
	 * @param x
	 *            横坐标
	 * @param y
	 *            纵坐标
	 * @param width
	 *            宽度
	 * @param height
	 *            高度
	 * @param oldpath
	 *            旧图片路径
	 * @param newpath
	 *            新图片路径 void
	 */
	public static void cut(int x, int y, int width, int height, String oldpath, String newpath) {
		FileInputStream is = null;
		ImageInputStream iis = null;
		String imgType = getImageExt(oldpath);

		try {
			is = new FileInputStream(oldpath);
			Iterator<ImageReader> it = ImageIO.getImageReadersByFormatName(imgType);
			ImageReader reader = it.next();
			iis = ImageIO.createImageInputStream(is);
			reader.setInput(iis, true);
			ImageReadParam param = reader.getDefaultReadParam();
			Point p = new Point();
			p.setLocation(x, y);

			Dimension d = new Dimension();
			d.setSize(width, height);
			Rectangle rect = new Rectangle(p, d);
			param.setSourceRegion(rect);

			BufferedImage bi = reader.read(0, param);

			writeFile(newpath, bi);
		} catch (Exception e) {
			throw new IllegalStateException(e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					throw new IllegalStateException(e);
				}
			}
			if (iis != null) {
				try {
					iis.close();
				} catch (IOException e) {
					throw new IllegalStateException(e);
				}
			}
		}
	}

	/**
	 * 获取图片后缀 company: hundsun
	 * 
	 *         2011-4-20 上午10:31:19 version 1.0
	 * @param path
	 *            图片路径
	 *  String
	 */
	public static String getImageExt(String path) {
		if (path == null || path.length() == 0) {
			return "";
		} else {
			File file = new File(path);
			String fileName = file.getName();
			String ext = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
			if (isImageExt(ext)) {
				return ext;
			} else {
				return "";
			}
		}
	}

	/**
	 * 是否是图片后缀 company: hundsun
	 * 
	 * author <a href="mailto:fangqing@hundsun.com">cyan</a><br>
	 *         2011-4-20 上午10:31:25 version 1.0
	 * @param ext
	 *            图片后缀
	 *  boolean
	 */
	public static boolean isImageExt(String ext) {
		if (ext.equalsIgnoreCase("jpg") || ext.equalsIgnoreCase("bmp") || ext.equalsIgnoreCase("png")) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * 
	 * @param imgFile
	 *  <p>
	 *         author: <a href="mailto:shenw@hundsun.com">shenw</a><br>
	 *         create at: 2014年6月26日下午7:53:57
	 */
	public static BufferedImage getBufferedImage(String imgFile) {
		try {
			BufferedImage inputImage = ImageIO.read(new File(imgFile));
			return inputImage;
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	// /**
	// * 缩率图(指定宽度和高度)
	// * <p>
	// * author: shen.wei<br>
	// * version: 2011-3-6 下午01:52:31 <br>
	// *
	// * @param srcImageFile
	// * @param outImagePath
	// * @param width1
	// * @param height1
	// */
	// @Deprecated
	// private static BufferedImage scale(String inputImagePath, int scaleWidth,
	// int scaleHeight) {
	// Graphics2D g = null;
	// try {
	// if (log.isDebugEnabled()) {
	// log.debug(" scale file:[" + inputImagePath + "]");
	// }
	// BufferedImage inputImage = ImageIO.read(new File(inputImagePath)); //
	// 读入文件
	// double imgWidth = inputImage.getWidth(); // 得到源图宽
	// double imgHeight = inputImage.getHeight(); // 得到源图高
	//
	// double w = 0;
	// double h = 0;
	//
	// if (scaleWidth > 0 && scaleHeight > 0) {// 宽高都大于0
	// if (imgWidth > scaleWidth || imgHeight > scaleHeight) {
	// if (imgWidth > imgHeight) {// 寬>高,按寬进行缩率
	// w = scaleWidth;
	// h = (imgHeight / imgWidth) * scaleWidth;
	// } else {// 寬<高,按高进行缩率
	// h = scaleHeight;
	// w = (imgWidth / imgHeight) * scaleHeight;
	// }
	// } else {
	// w = imgWidth;
	// h = imgHeight;
	// }
	// } else if (scaleWidth == 0) {// 寬==0,按高进行缩率
	// if (imgHeight > scaleHeight) {
	// h = scaleHeight;
	// w = (imgWidth / imgHeight) * scaleHeight;
	// } else {
	// w = imgWidth;
	// h = imgHeight;
	// }
	// } else if (scaleHeight == 0) {// 高==0,按宽进行缩率
	// if (imgWidth > scaleWidth) {
	// w = scaleWidth;
	// h = (imgHeight / imgWidth) * scaleWidth;
	// } else {
	// w = imgWidth;
	// h = imgHeight;
	// }
	// } else {
	// throw new
	// IllegalArgumentException("scaleWidth and scaleHeight can not set 0 all!");
	// }
	//
	// Image image = inputImage.getScaledInstance((int) w, (int) h,
	// Image.SCALE_SMOOTH);
	// BufferedImage outputImage = new BufferedImage((int) w, (int) h,
	// BufferedImage.TYPE_INT_RGB);
	//
	// // AffineTransform transform = new AffineTransform();
	// // transform.setToScale(w, h);
	// // AffineTransformOp ato = new AffineTransformOp(transform, null);
	// // ato.filter(inputImage, outputImage);
	//
	// g = (Graphics2D) outputImage.getGraphics();
	// g.setRenderingHint(RenderingHints.KEY_RENDERING,
	// RenderingHints.VALUE_RENDER_QUALITY);
	// // g.drawRenderedImage(inputImage,
	// // AffineTransform.getScaleInstance(w, h));
	// g.drawImage(image, 0, 0, null); // 绘制缩小后的图片
	//
	// return outputImage;
	//
	// } catch (Exception e) {
	// throw new IllegalStateException(e);
	// } finally {
	// if (g != null) {
	// g.dispose();
	// g = null;
	// }
	// }
	// }

	/**
	 * 打水印
	 * <p>
	 * author: shen.wei<br>
	 * version: 2011-3-6 上午11:35:47 <br>
	 * 
	 * @param inputImage
	 * 
	 * @param outImagePath
	 *            输出图片目录，包括盘符和文件名
	 * @param waterMarkText
	 *            水印文字
	 * @param color
	 *            水印颜色 默认Color.WHITE;
	 * @param font
	 *            水印字体 new Font("", Font.BOLD, 26)
	 * @param waterMarkImagePath
	 *            图片水印路径
	 * @throws IOException
	 * @throws ImageFormatException
	 */
	private static void waterMark(BufferedImage inputImage, String outImagePath, String waterMarkText, Color color,
			Font font, String waterMarkImagePath) {
		if (color == null) {
			color = Color.white;
		}
		if (font == null) {
			font = new Font("黑体", Font.BOLD, 30);
		}

		Graphics2D graphics2D = inputImage.createGraphics();
		AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.4f);
		graphics2D.setComposite(ac);

		if (StringUtils.isNotEmpty(waterMarkText)) {// 打上文字水印
			graphics2D.setColor(color);
			graphics2D.setFont(font);

			int waterMarkFontSize = (int) font.getSize2D();
			// 文字水印打着图片中间
			int x = inputImage.getWidth() / 2
					- (int) (StringUtils.lengthForChinese(waterMarkText) * waterMarkFontSize / 3.7);
			int y = inputImage.getHeight() / 2;
			// int x = inputImage.getWidth() - waterMarkText.length() *
			// waterMarkFontSize;

			graphics2D.drawString(waterMarkText, x, y);
		}

		if (StringUtils.isNotEmpty(waterMarkImagePath)) {
			File waterMarkFile = new File(waterMarkImagePath);
			Image waterMarkImage = null;
			try {
				if (log.isDebugEnabled()) {
					log.debug("waterMarkImage path [" + waterMarkImagePath + "]");
				}

				waterMarkImage = ImageIO.read(waterMarkFile);
			} catch (IOException e) {
				log.warn("not find waterMarkImage file  [" + waterMarkImagePath + "]");
			}

			if (waterMarkImage != null) {
				int waterMarkImageWidth = waterMarkImage.getWidth(null);
				int waterMarkImageHeight = waterMarkImage.getHeight(null);
				graphics2D.drawImage(waterMarkImage, (inputImage.getWidth() - waterMarkImageWidth - 10),
						(inputImage.getHeight() - waterMarkImageHeight - 10), waterMarkImageWidth,
						waterMarkImageHeight, null); // 将水印放于右下角
			}
		}
		writeFile(outImagePath, inputImage);
	}

	private static void writeFile(String outFilePath, BufferedImage bufferedImage) {
		if (log.isDebugEnabled()) {
			log.debug("writeFile:[" + outFilePath + "]");
		}

		FileUtils.mkdirs(outFilePath);

		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(outFilePath);
			JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(bufferedImage);
			if (jep != null) {
				jep.setQuality(0.93f, true);
				JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(fos, jep);
				encoder.encode(bufferedImage);
			} else {
				File file = new File(outFilePath);
				ImageIO.write(bufferedImage, "JPEG", file);
			}
			// File file = new File(outFilePath);
			// ImageIO.write(bufferedImage, "JPEG", file);

		} catch (Exception e) {
			throw new IllegalStateException(e);
		} finally {
			if (fos != null) {
				try {
					fos.flush();
					fos.close();
					fos = null;
				} catch (Exception e) {
					if (log.isErrorEnabled()) {
						log.error(e.getMessage(), e);
					}
				}
			}
		}
	}

	// /**
	// * 水印位置
	// * <p>
	// *
	// * @author shen.wei
	// * @version 2011-3-29 上午09:19:42
	// */
	// public enum WaterMartLocation {
	// UP, CENTER
	// }

	// /**
	// * 打水印
	// * <p>
	// * 水印颜色默认:Color.WHITE; 字体默认:new Font("", Font.BOLD, 26)
	// * <p>
	// * author: shen.wei<br>
	// * version: 2011-3-7 下午02:48:20 <br>
	// *
	// * @param inputImagePath
	// * 输入图片全目录，包括盘符和文件名
	// * @param outImagePath
	// * 输出图片目录，包括盘符和文件名
	// * @param waterMarkText
	// * 水印文字
	// */
	// public static void waterMark(String inputImagePath, String outImagePath,
	// String waterMarkText) {
	// waterMark(inputImagePath, outImagePath, waterMarkText, null, null,null);
	// }

	/**
	 * byte数组转换成图片
	 * 
	 * @param data
	 * @param path
	 *            存放路径
	 */
	public static void byte2Image(byte[] data, String path) {
		if (data.length < 3 || StringUtils.isBlank(path)) {
			return;
		}
		try {
			FileImageOutputStream imageOutput = new FileImageOutputStream(new File(path));
			imageOutput.write(data, 0, data.length);
			imageOutput.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 *
	 * 
	 * @param targetImg
	 *            目标图片路径，如：C://myPictrue//1.jpg
	 * @param pressText
	 *            水印文字， 如：中国证券网 此处打的是32位数的MD5码
	 * @param fontName
	 *            字体名称， 如：宋体
	 * @param fontStyle
	 *            字体样式，如：粗体和斜体(Font.BOLD|Font.ITALIC)
	 * @param fontSize
	 *            字体大小，单位为像素
	 * @param alpha
	 *            透明度(0.0 -- 1.0, 0.0为完全透明，1.0为完全不透明)
	 */
	public static void waterMark2Png(String targetImg, String pressText, String fontName, int fontStyle, int fontSize,
			float alpha) {
		try {
			File file = new File(targetImg);
			ImageIcon icon = new ImageIcon(file.getPath());
			BufferedImage bufferedImage = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(),
					BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = (Graphics2D) bufferedImage.getGraphics();
			g.drawImage(icon.getImage(), 0, 0, null);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
			g.setColor(Color.DARK_GRAY);
			g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			g.setFont(new Font(fontName, fontStyle, fontSize));
			FontMetrics fontMetrics = g.getFontMetrics();
			Rectangle2D rect = fontMetrics.getStringBounds(pressText, g);
			g.drawString(pressText.substring(0, 2), icon.getIconWidth() / 2 - 20, (int) rect.getHeight() + 20);
			g.drawString(pressText.substring(2, 6), icon.getIconWidth() / 2 - 30, (int) rect.getHeight() * 2 + 15);
			g.drawString(pressText.substring(6, 12), icon.getIconWidth() / 2 - 40, (int) rect.getHeight() * 3 + 10);
			g.drawString(pressText.substring(12, 20), icon.getIconWidth() / 2 - 50, (int) rect.getHeight() * 4 + 8);
			g.drawString(pressText.substring(20, 26), icon.getIconWidth() / 2 - 40, (int) rect.getHeight() * 5 + 6);
			g.drawString(pressText.substring(26, 30), icon.getIconWidth() / 2 - 20, (int) rect.getHeight() * 6 + 4);
			g.drawString(pressText.substring(30, 32), icon.getIconWidth() / 2 - 20, (int) rect.getHeight() * 7 + 2);
			g.dispose();
			ImageIO.write(bufferedImage, "png", file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 文件复制
	 * 
	 * @param oldFilePath
	 *            原来的文件地址路径和文件名称（文件的路径都是已经存在的）
	 * @param newFilePath
	 *            新的文件地址和名称（文件的路径都是已经存在的）
	 */
	@SuppressWarnings("resource")
	public static void copyToOtherPath(String oldFilePath, String newFilePath) {
		try {
			FileChannel srcChannel = new FileInputStream(oldFilePath).getChannel();
			FileChannel dstChannel = new FileOutputStream(newFilePath).getChannel();
			dstChannel.transferFrom(srcChannel, 0, srcChannel.size());
			srcChannel.close();
			dstChannel.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
