package com.ancun.core.utils;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

public class ImageHelper {
	
	/**
	 * ���ˮӡͼƬ
	 * 		Ĭ�ϲ�͸����ˮӡ�߾�Ϊ10����,λ��Ϊ9�����½�
	 * @param targetImg		�����ˮӡ��ͼƬ
	 * @param watermarkImg		ˮӡ��ȥ��ͼƬ	
	 * @throws IOException
	 */
	public static void pWatermarkImage(String targetImg, String watermarkImg) throws IOException {
		pWatermarkImage(targetImg,watermarkImg,9,1f,10);
	} 
	
	/**
	 * ���ˮӡͼƬ
	 * 		Ĭ�ϲ�͸����ˮӡ�߾�Ϊ10����
	 * @param targetImg		�����ˮӡ��ͼƬ
	 * @param watermarkImg		ˮӡ��ȥ��ͼƬ	
	 * @param position		�Ź���λ�ò���,[1-9]
	 * @throws IOException
	 */
	public static void pWatermarkImage(String targetImg, String watermarkImg,int position) throws IOException {
		pWatermarkImage(targetImg,watermarkImg,position,1f,10);
	} 
	
	/**
	 * ���ˮӡͼƬ
	 * 		Ĭ��ˮӡ�߾�Ϊ10����
	 * @param targetImg		�����ˮӡ��ͼƬ
	 * @param watermarkImg		ˮӡ��ȥ��ͼƬ	
	 * @param position		�Ź���λ�ò���,[1-9]
	 * @param alpha		ˮӡ͸���ȣ�0-1��Χȡֵ
	 * @throws IOException
	 */
	public static void pWatermarkImage(String targetImg, String watermarkImg,int position, float alpha) throws IOException {
		pWatermarkImage(targetImg,watermarkImg,position,alpha,10);
	} 
	
	/**
	 * ���ˮӡͼƬ
	 * @param targetImg		�����ˮӡ��ͼƬ
	 * @param watermarkImg		ˮӡ��ȥ��ͼƬ	
	 * @param position		�Ź���λ�ò���,[1-9]
	 * @param alpha		ˮӡ͸���ȣ�0-1��Χȡֵ
	 * @param margin	ˮӡ�߾�
	 * @throws IOException
	 */
	public static void pWatermarkImage(String targetImg, String watermarkImg,int position, float alpha, int margin) throws IOException {
		if (targetImg.indexOf(".") == -1) {
			return;
		}
		String distImgType = targetImg
				.substring(targetImg.lastIndexOf(".") + 1);
		File tarImgFile = new File(targetImg);
		Image src = ImageIO.read(tarImgFile);
		File wmFile = new File(watermarkImg);
		Image wmSrc = ImageIO.read(wmFile);
		int[] xy = getXY(position,src, wmSrc,margin);
		BufferedImage image = watermarkImage(src,wmSrc,xy[0],xy[1],alpha,distImgType);
		ImageIO.write(image, distImgType, new File(targetImg));
	}


	private static int[] getXY(int position, Image src, int wmWidth, int wmHeight, int margin){
		int[] xy = new int[2];
		int width = src.getWidth(null);
		int height = src.getHeight(null);
		if(position==1){
			xy[0] = margin;
			xy[1] = margin;
		}else if(position==2){
			xy[0] = (width-wmWidth)/2;
			xy[1] = margin;
		}else if(position==3){
			xy[0] = width-wmWidth-margin;
			xy[1] = margin;		
		}else if(position==4){
			xy[0] = margin;
			xy[1] = (height-wmHeight)/2;
		}else if(position==5){
			xy[0] = (width-wmWidth)/2;
			xy[1] =  (height-wmHeight)/2;
		}else if(position==6){
			xy[0] = width-wmWidth-margin;
			xy[1] = (height - wmHeight)/2; 
		}else if(position==7){
			xy[0] =margin;
			xy[1] = height - wmHeight - margin;
		}else if(position==8){
			xy[0] =  (width-wmWidth)/2;
			xy[1] = height - wmHeight - margin;
		}else{
			xy[0] = width-wmWidth-margin;
			xy[1] = height - wmHeight - margin;
		}
		return xy;
	}
	
	private static int[] getXY(int position, Image src, Image wmSrc, int margin) {
		int wmWidth = wmSrc.getWidth(null);
		int wmHeight = wmSrc.getHeight(null);
		return getXY(position,src,wmWidth,wmHeight,margin);
	}


	private static BufferedImage watermarkImage(Image targetImg, Image watermarkImg,
			int x, int y, float alpha, String distImgType) throws IOException {
		int width = targetImg.getWidth(null);
		int height = targetImg.getHeight(null);
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
//		�����pngͼƬʹ��͸������
		Graphics2D g = image.createGraphics();
		if("png".equalsIgnoreCase(distImgType)){
			image = g.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT); 
			g.dispose(); 
			g = image.createGraphics(); 
		}
		g.drawImage(targetImg, 0, 0, width, height, null);
		//��ȡˮӡ�ļ�
		int wmWideth = watermarkImg.getWidth(null);
		int wmHeight = watermarkImg.getHeight(null);
		//���ˮӡ
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
				alpha));
		g.drawImage(watermarkImg, x, y, wmWideth, wmHeight, null);
		g.dispose();
		return image;
	}
	
	
	private static int[] getTextXY(int position, Image src, String text, int fontsize, int margin){
		int wmWidth;
//		�ж��Ƿ��Ǵ�Ӣ��ˮӡ
		if(text.getBytes().length==text.length()){
			wmWidth = text.length()*fontsize / 2 +20;//Ӣ��ˮӡ���ȳ���2�ټ�20���ص�����
		}else{
			wmWidth = text.length()*fontsize;
		}
		int wmHeight = fontsize;
		return getXY(position,src,wmWidth,wmHeight,margin);
	}
	
	/**
	 * ͼƬ���ͼƬˮӡ
	 * 		��֧��Gif��̬ͼƬ
	 * @param targetImg				�����ˮӡ��ͼƬ
	 * @param watermarkImg		ˮӡ��ȥ��ͼƬ	
	 * @param x		��ͼƬ���Ͻ�Ϊԭ���X��ƫ����
	 * @param y		��ͼƬ���Ͻ�Ϊԭ���Y��ƫ����
	 * @param alpha		ˮӡͼƬ͸���ȣ�ȡֵ��Χ0-1
	 * @throws IOException 
	 */
	public static void watermarkImage(String targetImg, String watermarkImg,
			int x, int y, float alpha) throws IOException {
		if (targetImg.indexOf(".") == -1) {
			return;
		}
		String distImgType = targetImg
				.substring(targetImg.lastIndexOf(".") + 1);
		//��ȡĿ���ļ�
		File tarImgFile = new File(targetImg);
		Image src = ImageIO.read(tarImgFile);
		int width = src.getWidth(null);
		int height = src.getHeight(null);
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g = image.createGraphics();
//		�����pngͼƬʹ��͸������
		if("png".equalsIgnoreCase(distImgType)){
			image = g.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT); 
			g.dispose(); 
			g = image.createGraphics(); 
		}
		
		g.drawImage(src, 0, 0, width, height, null);
		File wmFile = new File(watermarkImg);
		//��ȡˮӡ�ļ�
		Image wmSrc = ImageIO.read(wmFile);
		int wmWideth = wmSrc.getWidth(null);
		int wmHeight = wmSrc.getHeight(null);
		//			���ˮӡ
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
				alpha));
		g.drawImage(wmSrc, x, y, wmWideth, wmHeight, null);
		g.dispose();
		//			����д���ļ�
		ImageIO.write(image, distImgType, new File(targetImg));
	}

	/**
	 * ͼƬ�������ˮӡ
	 * 		��֧��Gif��̬ͼƬ
	 * @param watermarkText  ˮӡ����
	 * @param targetImg  Ŀ��ͼƬ
	 * @param fontName  ������
	 * @param fontStyle  ������ʽ
	 * @param color  ������ɫ
	 * @param fontSize  �����С
	 * @param x   ��ͼƬ���Ͻ�Ϊԭ���X��ƫ����
	 * @param y   ��ͼƬ���Ͻ�Ϊԭ���Y��ƫ����
	 * @param alpha		ˮӡ����͸���ȣ�ȡֵ��Χ0-1
	 * @throws IOException 
	 */
	public static void watermarkText(String watermarkText, String targetImg,
			String fontName, int fontStyle, Color color, int fontSize, int x,
			int y, float alpha) throws IOException {
		if (targetImg.indexOf(".") == -1) {
			return;
		}
		String distImgType = targetImg
				.substring(targetImg.lastIndexOf(".") + 1);
		File tarImgFile = new File(targetImg);
		Image tarImg = ImageIO.read(tarImgFile);
		int width = tarImg.getWidth(null);
		int height = tarImg.getHeight(null);
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g = image.createGraphics();
//		�����pngͼƬʹ��͸������
		if("png".equalsIgnoreCase(distImgType)){
			image = g.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT); 
			g.dispose(); 
			g = image.createGraphics(); 
		}
		
		g.drawImage(tarImg, 0, 0, width, height, null);
		g.setColor(color);
		g.setFont(new Font(fontName, fontStyle, fontSize));
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
				alpha));
		g.drawString(watermarkText, x, y + fontSize);
		g.dispose();
		//			����д���ļ�
		ImageIO.write(image, distImgType, new File(targetImg));
	}

	public static void watermarkText(String watermarkText, String targetImg,int position,int margin,
			String fontName, int fontStyle, Color color, int fontSize, float alpha,String distImgType) throws IOException {
		File tarImgFile = new File(targetImg);
		Image tarImg = ImageIO.read(tarImgFile);
		int width = tarImg.getWidth(null);
		int height = tarImg.getHeight(null);
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g = image.createGraphics();
		if("png".equalsIgnoreCase(distImgType)){
			image = g.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT); 
			g.dispose(); 
			g = image.createGraphics(); 
		}
		int[] xy = getTextXY(position, tarImg, watermarkText, fontSize, margin);
		g.drawImage(tarImg, 0, 0, width, height, null);
		g.setColor(color);
		g.setFont(new Font(fontName, fontStyle, fontSize));
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
		g.drawString(watermarkText, xy[0], xy[1] + fontSize);
		g.dispose();
	}
	
	/**
	 * ͼƬ�������ˮӡ
	 * 		��֧��Gif��̬ͼƬ
	 * @param watermarkText  ˮӡ����
	 * @param targetImg  Ŀ��ͼƬ
	 * @param color  ������ɫ
	 * @param fontSize  �����С
	 * @param x   ��ͼƬ���Ͻ�Ϊԭ���X��ƫ����
	 * @param y   ��ͼƬ���Ͻ�Ϊԭ���Y��ƫ����
	 * @param alpha		ˮӡ����͸���ȣ�ȡֵ��Χ0-1
	 */
	public static void watermarkText(String watermarkText, String targetImg,
			Color color, int fontSize, int x, int y, float alpha)
			throws IOException {
		watermarkText(watermarkText, targetImg, "����", Font.BOLD, color,
				fontSize, x, y, alpha);
	}
	
	/**
	 * ͼƬ�������ˮӡ
	 * 		��֧��Gif��̬ͼƬ
	 * @param watermarkText		ˮӡ����
	 * @param targetImg		Ŀ��ͼƬ
	 * @param position 	�Ź���λ�ò���,[1-9]
	 * @param margin 		�߾�
	 * @param fontStyle 	��ʽ
	 * @param fontName 	����
	 * @param color		��ɫ
	 * @param fontSize		�����С
	 * @param alpha		͸���ȣ�ȡֵ��Χ0-1
	 * @throws IOException 
	 */
	public static void pWatermarkText(String watermarkText, String targetImg,int position,int margin, Color color, int fontSize, float alpha, int fontStyle, String fontName) throws IOException{
		if (targetImg.indexOf(".") == -1) {
			return;
		}
		String distImgType = targetImg
				.substring(targetImg.lastIndexOf(".") + 1);
		File tarImgFile = new File(targetImg);
		Image tarImg = ImageIO.read(tarImgFile);
		int width = tarImg.getWidth(null);
		int height = tarImg.getHeight(null);
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g = image.createGraphics();
//		�����pngͼƬʹ��͸������
		if("png".equalsIgnoreCase(distImgType)){
			image = g.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT); 
			g.dispose(); 
			g = image.createGraphics(); 
		}
		int[] xy = getTextXY(position, tarImg, watermarkText, fontSize, margin);
		g.drawImage(tarImg, 0, 0, width, height, null);
		g.setColor(color);
		g.setFont(new Font(fontName, fontStyle, fontSize));
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
				alpha));
		g.drawString(watermarkText, xy[0], xy[1] + fontSize);
		g.dispose();
		//			����д���ļ�
		ImageIO.write(image, distImgType, new File(targetImg));
	}
	
	/**
	 * ͼƬ�������ˮӡ
	 * 		��֧��Gif��̬ͼƬ,
	 * 		Ĭ������λ��Ϊ7�����½ǣ�����ɫ����ɫ�����壺���塢���壬�����С��20px���߾ࣺ10px��͸���ȣ�1
	 * @param watermarkText		ˮӡ����
	 * @param targetImg			Ŀ��ͼƬ
	 * @throws IOException
	 */
	public static void pWatermarkText(String watermarkText, String targetImg) throws IOException{
		pWatermarkText(watermarkText,targetImg,7,10,Color.BLACK,20,1f,Font.BOLD,"����");
	}

	/**
	 * ͼƬ�������ˮӡ
	 * 		��֧��Gif��̬ͼƬ,Ĭ��������ɫ����ɫ�����壺���塢���壬�����С��20px���߾ࣺ10px��͸���ȣ�1
	 * @param watermarkText		ˮӡ����
	 * @param targetImg			Ŀ��ͼƬ
	 * @param position			�Ź���λ�ò���,[1-9]
	 * @throws IOException
	 */
	public static void pWatermarkText(String watermarkText, String targetImg,int position) throws IOException{
		pWatermarkText(watermarkText,targetImg,position,10,Color.BLACK,20,1f,Font.BOLD,"����");
	}
	
	/**
	 * ͼƬ�������ˮӡ
	 * 		��֧��Gif��̬ͼƬ,Ĭ���������壺���塢���壬�����С��20px���߾ࣺ10px��͸���ȣ�1
	 * @param watermarkText		ˮӡ����
	 * @param targetImg			Ŀ��ͼƬ
	 * @param position			�Ź���λ�ò���,[1-9]
	 * @param color		��ɫ
	 * @throws IOException
	 */
	public static void pWatermarkText(String watermarkText, String targetImg,int position,Color color) throws IOException{
		pWatermarkText(watermarkText,targetImg,position,10,color,20,1f,Font.BOLD,"����");
	}
	
	/**
	 * ͼƬ�������ˮӡ
	 * 		��֧��Gif��̬ͼƬ,Ĭ���������壺���塢���壬�߾ࣺ10px��͸���ȣ�1
	 * @param watermarkText		ˮӡ����
	 * @param targetImg			Ŀ��ͼƬ
	 * @param position			�Ź���λ�ò���,[1-9]
	 * @param color		��ɫ
	 * @param fontSize		�����С
	 * @throws IOException
	 */
	public static void pWatermarkText(String watermarkText, String targetImg,int position,Color color, int fontSize) throws IOException{
		pWatermarkText(watermarkText,targetImg,position,10,color,fontSize,1f,Font.BOLD,"����");
	}
	
	/**
	 * ͼƬ�������ˮӡ
	 * 		��֧��Gif��̬ͼƬ
	 * @param watermarkText		ˮӡ����
	 * @param targetImg			Ŀ��ͼƬ
	 * @param position			�Ź���λ�ò���,[1-9]
	 * @param color		��ɫ
	 * @param fontSize		�����С
	 * @param alpha		͸���ȣ�ȡֵ��Χ0-1
	 * @throws IOException 
	 */
	public static void pWatermarkText(String watermarkText, String targetImg,int position,Color color, int fontSize, float alpha) throws IOException{
		pWatermarkText(watermarkText,targetImg,position,10,color,fontSize,alpha,Font.BOLD,"����");
	}
	
	/** 
	 * �⻬����ͼƬ
	 * 		��֧�ֶ�̬GifͼƬ(GifͼƬ��ʽ�ܵ���Ȩ����)
	 * 		��ɵ�ͼƬ�����ȽϺ� ���ٶ���
	 * @param srcImg  		ԭͼƬ�ļ�·�� 
	 * @param distImg  	��ɵ�ͼƬ�ļ�·�� 
	 * @param distWidth  ���ͼƬ�Ŀ�� 
	 * @param distHeight  ���ͼƬ�ĸ߶� 
	 * @throws IOException 
	 */
	public static void zoomImgSmooth(String srcImg, String distImg,
			int distWidth, int distHeight) throws IOException {
		if (distImg.indexOf(".") == -1) {
			return;
		}
		String distImgType = distImg.substring(distImg.lastIndexOf(".") + 1);
		File srcfile = new File(srcImg);
		Image src = ImageIO.read(srcfile);
		BufferedImage image = new BufferedImage((int) distWidth,
				(int) distHeight, BufferedImage.TYPE_INT_RGB);
		if("png".equalsIgnoreCase(distImgType)){
			Graphics2D g = image.createGraphics();
			image = g.getDeviceConfiguration().createCompatibleImage(distWidth, distHeight, Transparency.TRANSLUCENT); 
			g.dispose(); 
			g = image.createGraphics(); 
			g.drawImage(src.getScaledInstance(distWidth, distHeight,
							Image.SCALE_SMOOTH), 0, 0, null);
		}else{
			image.getGraphics().drawImage(src.getScaledInstance(distWidth, distHeight,
							Image.SCALE_SMOOTH), 0, 0, null);
		}
		ImageIO.write(image, distImgType, new File(distImg));
	}
	
	/**
	 * ������⻬����ͼƬ
	 * @param srcImg  		ԭͼƬ�ļ�·�� 
	 * @param distImg  	��ɵ�ͼƬ�ļ�·�� 
	 * @param proportion	���ű���
	 * @throws IOException
	 */
	public static void proportionalZoomImgSmooth(String srcImg, String distImg, float proportion) throws IOException{
		if (distImg.indexOf(".") == -1) {
			return;
		}
		String distImgType = distImg.substring(distImg.lastIndexOf(".") + 1);
		File srcfile = new File(srcImg);
		Image src = ImageIO.read(srcfile);
		int distWidth = Math.round(src.getWidth(null) * proportion);
		int distHeight = Math.round(src.getHeight(null) * proportion);
		zoomImgSmooth(src, distImg,distWidth, distHeight, distImgType);
	}
	
	private static void zoomImgSmooth(Image srcImg, String distImg,
			int distWidth, int distHeight, String distImgType) throws IOException {
		BufferedImage image = new BufferedImage((int) distWidth,
				(int) distHeight, BufferedImage.TYPE_INT_RGB);
		if("png".equalsIgnoreCase(distImgType)){
			Graphics2D g = image.createGraphics();
			image = g.getDeviceConfiguration().createCompatibleImage(distWidth, distHeight, Transparency.TRANSLUCENT); 
			g.dispose(); 
			g = image.createGraphics(); 
			g.drawImage(srcImg.getScaledInstance(distWidth, distHeight,
							Image.SCALE_SMOOTH), 0, 0, null);
		}else{
			image.getGraphics().drawImage(srcImg.getScaledInstance(distWidth, distHeight,
							Image.SCALE_SMOOTH), 0, 0, null);
		}
		ImageIO.write(image, distImgType, new File(distImg));
	}
	
	/** 
	 * ����ͼƬ
	 * 		��֧�ֶ�̬GifͼƬ(GifͼƬ��ʽ�ܵ���Ȩ����)
	 * @param srcImg  		ԭͼƬ�ļ�·�� 
	 * @param distImg  	��ɵ�ͼƬ�ļ�·�� 
	 * @param distWidth  ���ͼƬ�Ŀ�� 
	 * @param distHeight  ���ͼƬ�ĸ߶� 
	 * @throws IOException 
	 */
	public static void zoomImg(String srcImg, String distImg,
			int distWidth, int distHeight) throws IOException {
		if (distImg.indexOf(".") == -1) {
			return;
		}
		String distImgType = distImg.substring(distImg.lastIndexOf(".") + 1);
		File srcfile = new File(srcImg);
		Image src = ImageIO.read(srcfile);
		BufferedImage image = new BufferedImage((int) distWidth,
				(int) distHeight, BufferedImage.TYPE_INT_RGB);
		if("png".equalsIgnoreCase(distImgType)){
			Graphics2D g = image.createGraphics();
			image = g.getDeviceConfiguration().createCompatibleImage(distWidth, distHeight, Transparency.TRANSLUCENT); 
			g.dispose(); 
			g = image.createGraphics(); 
			g.drawImage(src.getScaledInstance(distWidth, distHeight,
							Image.SCALE_DEFAULT), 0, 0, null);
		}else{
			image.getGraphics().drawImage(src.getScaledInstance(distWidth, distHeight,
							Image.SCALE_DEFAULT), 0, 0, null);
		}
		ImageIO.write(image, distImgType, new File(distImg));
	}

	
	/**
	 * ����������ͼƬ
	 * @param srcImg  		ԭͼƬ�ļ�·�� 
	 * @param distImg  	��ɵ�ͼƬ�ļ�·�� 
	 * @param proportion	���ű���
	 * @throws IOException
	 */
	public static void proportionalZoomImg(String srcImg, String distImg, float proportion) throws IOException{
		if (distImg.indexOf(".") == -1) {
			return;
		}
		String distImgType = distImg.substring(distImg.lastIndexOf(".") + 1);
		File srcfile = new File(srcImg);
		Image src = ImageIO.read(srcfile);
		int distWidth = Math.round(src.getWidth(null) * proportion);
		int distHeight = Math.round(src.getHeight(null) * proportion);
		zoomImg(src, distImg,distWidth, distHeight, distImgType);
	}
	
	
	private static void zoomImg(Image srcImg, String distImg,
			int distWidth, int distHeight, String distImgType) throws IOException {
		BufferedImage image = new BufferedImage((int) distWidth,
				(int) distHeight, BufferedImage.TYPE_INT_RGB);
		if("png".equalsIgnoreCase(distImgType)){
			Graphics2D g = image.createGraphics();
			image = g.getDeviceConfiguration().createCompatibleImage(distWidth, distHeight, Transparency.TRANSLUCENT); 
			g.dispose(); 
			g = image.createGraphics(); 
			g.drawImage(srcImg.getScaledInstance(distWidth, distHeight,
							Image.SCALE_DEFAULT), 0, 0, null);
		}else{
			image.getGraphics().drawImage(srcImg.getScaledInstance(distWidth, distHeight,
							Image.SCALE_DEFAULT), 0, 0, null);
		}
		ImageIO.write(image, distImgType, new File(distImg));
	}
	
	/**
	 * ����pngͼƬ
	 * @param srcImg		ԭͼƬ�ļ�
	 * @param distImg		���ͼƬ�ļ�
	 * @param distWidth	���ͼƬ�ļ����
	 * @param distHeight	���ͼƬ�ļ��߶�
	 * @throws IOException
	 */
	public static void zoomPngImg(String srcImg, String distImg,
			double distWidth, double distHeight) throws IOException {
		if (distImg.indexOf(".png") == -1) {
			return;
		}
		File resource = new File(srcImg);
		BufferedImage sourceImage = ImageIO.read(resource);
		int width = sourceImage.getWidth(null);
		int height = sourceImage.getHeight(null);
		double widthRatio = distWidth / width;
		double heightRatio = distHeight / height;
		BufferedImage dstImage = null;
		AffineTransform transform = AffineTransform.getScaleInstance(
				widthRatio, heightRatio);
		AffineTransformOp op = new AffineTransformOp(transform,
				AffineTransformOp.TYPE_BILINEAR);
		dstImage = op.filter(sourceImage, null);
		ImageIO.write(dstImage, "png", new File(distImg));
	}
	
	 /**
     * �ü�ͼƬ
     *
     * @param srcImgPath  ԭ�ļ�ȫ·��
     * @param  targetImgPath �ü�����ļ�����ȫ·��
     * @param  startX ��ʼ�ü���x���
     * @param  startY ��ʼ�ü���y���
     * @param  targetWidth �ü��Ŀ��
     * @param  targetHeight �ü��ĸ߶�
     * @throws Exception 
     */
	public static void cutOutImg(String srcImgPath, String targetImgPath, int startX,
                                              int startY, int targetWidth, int targetHeight)
                                                                                            throws Exception {
    	String fileType = srcImgPath.substring(srcImgPath.lastIndexOf(".")+1);
        // ȡ��ͼƬ������
        Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName(fileType);
        ImageReader reader = (ImageReader) readers.next();
        // ȡ��ͼƬ������
        InputStream source = null;
        ImageInputStream iis = null;
        try {
            source = new FileInputStream(srcImgPath);
            iis = ImageIO.createImageInputStream(source);
            reader.setInput(iis, true);
            // ����ͼƬ���� Rectangle(���϶���x���, y���, ���ο��, ���θ߶�)
            ImageReadParam param = reader.getDefaultReadParam();
            Rectangle rect = new Rectangle(startX, startY, targetWidth, targetHeight);
            param.setSourceRegion(rect);
            BufferedImage bi = reader.read(0, param);
            ImageIO.write(bi, targetImgPath.substring(targetImgPath.lastIndexOf(".")+1), new File(targetImgPath));
        } finally {
            try {
                if (source != null) {
                    source.close();
                }
            } catch (Exception e) {
                throw new Exception("ImgUtils:cutOutImg()source  �رմ���");
            }
            try {
                if (iis != null) {
                    iis.close();
                }
            } catch (Exception e) {
                throw new Exception("ImgUtils:cutOutImg()iis �رմ���");
            }
        }
    }

    /**
     * �ü�ͼƬ�������Ͻ�(0,0)��ʼ
     *
     * @param srcImgPath  ԭ�ļ�ȫ·��
     * @param  targetImgPath �ü�����ļ�����ȫ·��
     * @param  endX �ü���Ŀ��
     * @param  endY �ü���ĸ߶�
     * @throws Exception 
     */
    public static void cutOutImg(String srcImgPath, String targetImgPath, int endX,
                                              int endY) throws Exception {
        cutOutImg(srcImgPath, targetImgPath, 0, 0, endX, endY);
    }

    /**
     * ����������ͼƬ
     *		�Ŵ������Сʱ���հ������ð�ɫ�������
     * @param srcImgPath  ԭ�ļ�ȫ·��
     * @param targetImgPath ѹ������ļ�����ȫ·��
     * @param targetWidth ѹ����Ŀ��
     * @param targetHeight ѹ����ĸ߶�
     * @throws IOException 
     */
    public static void noneExtrudeZoomImg(String srcImgPath, String targetImgPath,
                                              int targetWidth, int targetHeight) throws IOException  {
        File srcfile = new File(srcImgPath);
        Image src = ImageIO.read(srcfile);
        int width = src.getWidth(null);
        int height = src.getHeight(null);

        BufferedImage tag = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        Graphics gra = tag.getGraphics();
        // ���ñ���ɫ
        gra.setColor(Color.white);
        gra.fillRect(0, 0, targetWidth, targetHeight);
        if (width <= targetWidth && height <= targetHeight) {
            gra.drawImage(src.getScaledInstance(width, height, Image.SCALE_SMOOTH),
                (targetWidth - width) / 2, (targetHeight - height) / 2, null);
        } else {
            float wh = (float) width / (float) height;
            if (wh > 1) {
                float tmp_heigth = (float) targetWidth / wh;
                float y = ((float) targetHeight - tmp_heigth) / 2;
                gra.drawImage(src.getScaledInstance(targetWidth, (int) tmp_heigth,
                    Image.SCALE_SMOOTH), 0, (int) y, null);
            } else {
                float tmp_width = (float) targetHeight * wh;
                float x = ((float) targetWidth - tmp_width) / 2;
                gra.drawImage(src.getScaledInstance((int) tmp_width, targetHeight,
                    Image.SCALE_SMOOTH), (int) x, 0, null);
            }
        }
        ImageIO.write(tag, targetImgPath.substring(targetImgPath.lastIndexOf(".")+1), new File(targetImgPath));
    }
 
}
