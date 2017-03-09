package com.ancun.core.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 文件工具
 * 
 * @author <a href="mailto:shenwei@ancun.com">ShenWei</a>
 * @version Date: 2010-10-16 上午11:24:50
 * @since
 */
public class FileUtils extends org.apache.commons.io.FileUtils {
	private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

	/**
	 * 删除文件
	 * 
	 * @param f
	 */
	public static boolean deleteFile(File f) {
		if (f.isFile()) {
			f.delete();
		}
		return true;
	}

	/**
	 * 删除文件
	 * <p>
	 * author: <a href="mailto:shenwei@ancun.com">ShenWei</a><br>
	 * version: 2011-3-13 下午03:19:23 <br>
	 * 
	 * @param filePath
	 *
	 */
	public static boolean deleteFile(String filePath) {
		return deleteFile(new File(filePath));
	}

	/**
	 * 删除文件夹
	 * 
	 * @param dir
	 */
	public static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			File[] files = dir.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					deleteDir(files[i]);
				}
			}
		}
		dir.delete();
		return true;
	}

	/**
	 * 删除文件夹
	 * <p>
	 * author: <a href="mailto:shenwei@ancun.com">ShenWei</a><br>
	 * version: 2011-3-13 下午03:20:04 <br>
	 * 
	 * @param dir
	 *
	 */
	public static boolean deleteDir(String dir) {
		return deleteDir(new File(dir));
	}

	// /**
	// * 得到指定文件夹下，指定文件夹名前缀的所有文件夹,除了excludeDir
	// *
	// * @param dir
	// * @param namePrefix
	// * @param excludeDir
	// *
	// */
	// public static List getDirByNameprefix(File dir, String namePrefix,
	// String excludeDir) {
	// List dirList = new ArrayList();
	// if (dir.isDirectory()) {
	// File[] files = dir.listFiles();
	// for (int i = 0; i < files.length; i++) {
	// if (!files[i].isDirectory()
	// || files[i].getName().equals(excludeDir))
	// continue;
	// if (files[i].getName().startsWith(namePrefix))
	// dirList.add(files[i]);
	// }
	// }
	// return dirList;
	// }
	//
	/**
	 * 文件重命名
	 * 
	 * @param oldFile
	 * @param newFileName
	 *
	 */
	public static boolean renameFile(File oldFile, String newFileName) {
		if (oldFile.isFile()) {
			oldFile.renameTo(new File(oldFile.getAbsolutePath().substring(0,
					oldFile.getAbsolutePath().lastIndexOf(File.separator) + 1)
					+ newFileName));
		}
		return true;
	}

	/**
	 * 
	 * <p>
	 * author: <a href="mailto:shenwei@ancun.com">ShenWei</a><br>
	 * version: 2011-3-13 下午03:33:33 <br>
	 * 
	 * @param oldFile
	 * @param newFileName
	 *
	 */
	public static boolean renameFile(String oldFile, String newFileName) {
		return renameFile(new File(oldFile), newFileName);
	}

	//
	// /**
	// * 文件夹重命名
	// *
	// * @param oldDir
	// * @param newDirName
	// *
	// */
	// public static boolean renameDir(File oldDir, String newDirName) {
	// if (oldDir.isDirectory()) {
	// oldDir.renameTo(new File(oldDir.getAbsolutePath().substring(0,
	// oldDir.getAbsolutePath().lastIndexOf(File.separator) + 1)
	// + newDirName));
	// }
	// return true;
	// }
	//
	/**
	 * 将源文件复制一份到目标文件
	 * 
	 * @param srcFile
	 *            源文件全路径
	 * @param targetFile
	 *            目标文件全路径
	 * @throws IOException
	 */
	public static void copyFile(String srcFile, String targetFile) throws IOException {
		copyFile(new File(srcFile), new File(targetFile));
	}

	/**
	 * 
	 * <p>
	 * author: <a href="mailto:shenwei@ancun.com">ShenWei</a><br>
	 * version: 2011-3-6 下午09:51:18 <br>
	 * 
	 * @param outFilePath
	 */
	public static void mkdirs(String outFilePath) {
		File file = new File(outFilePath);
		File dir = file.getParentFile();
		if (!dir.exists()) {
			dir.mkdirs();
		}
	}

	/**
	 * 文件名加后缀
	 * <p>
	 * author: <a href="mailto:shenwei@ancun.com">ShenWei</a><br>
	 * version: 2011-3-8 下午05:41:57 <br>
	 * 
	 * @param fileName
	 * @param suffix
	 *
	 */
	public static String fileNameAddSuffix(String fileName, String suffix) {
		int p = fileName.lastIndexOf(".");
		String fileName1 = fileName.substring(0, p);
		String extName = fileName.substring(p);
		return fileName1 + suffix + extName;
	}

	/**
	 * 将内容写入文件，如果文件不存在，则创建一个新文件
	 * 
	 * @param file
	 *            写入的文件物理路径
	 * @param data
	 *            写入的文件内容
	 * @throws IOException
	 */
	public static void writeStringToFile(String file, String data, String webencoding) throws IOException {
		writeStringToFile(new File(file), data, webencoding);
		// File file = new File(targetPath);
		// PrintWriter out = null;
		// try {
		// if (!file.exists()) {
		// File parent = file.getParentFile();
		// if (parent.exists() || parent.mkdirs()) {
		// file.createNewFile();
		// }
		// }
		// out = new PrintWriter(file, webencoding);
		// out.print(fileContent);
		// } catch (IOException e) {
		// throw new UnsupportedOperationException(e);
		// } finally {
		// if (out != null) {
		// out.close();
		// }
		// }
	}

	/**
	 * 
	 * @param file
	 */
	public static String readFile(String file) {
		FileInputStream fis = null;
		
		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
		}

		StringBuffer str = null;
		if (fis != null) {
			FileChannel fc = fis.getChannel();
			ByteBuffer readBuffer = ByteBuffer.allocate(4096);
			try {
				str = new StringBuffer((int) fc.size());
				while (fc.read(readBuffer) != -1) {
					readBuffer.flip();
					str.append(Charset.forName("UTF-8").decode(readBuffer));
					//while (readBuffer.hasRemaining()) {
						//str.append((char) readBuffer.getChar());
					//}					
					readBuffer.clear();
				}
			} catch (IOException ex) {
				logger.error(ex.getMessage(), ex);
			} finally {
				try {
					fis.close();
					fc.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}

			}
		}

		return str == null ? null : str.toString();

	}

	/**
	 * 创建文件夹 defaultPath/bqbh/userId %100/
	 * 
	 * @param defaultPath
	 *            指定存放位置
	 * @param dataId
	 *            用户Id
		*
	 *
	 */
	public static String createFolder(String defaultPath, Integer dataId) throws Exception {
		final String separator = System.getProperty("file.separator");
		StringBuffer sb = new StringBuffer(defaultPath);
		sb.append(dataId % 100);
		sb.append(separator);
		sb.append(dataId);
		sb.append(separator);
		sb.append(DateUtils.getNow("yyMMdd"));
		sb.append(separator);

		try {
			File myFilePath = new File(sb.toString());
			if (!myFilePath.exists()) {
				myFilePath.mkdirs();
			}
			return sb.toString().replace("\\", "/");
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * 生成uuid
	 * 
	 *  <p>
	 *         author: <a href="mailto:caozhenfei@ancun.com">Dim.Cao</a><br>
	 *         create at: 2014年4月16日上午12:34:54
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	/**
	 * 
	 * @param inputStream
	 * @param target
	 *            <p>
	 *            author: ShenWei<br>
	 *            create at 2015年5月23日 下午3:39:52
	 * @throws IOException
	 */
	public static void writeFile(InputStream inputStream, String target) throws IOException {
		//PushbackInputStream pif = new PushbackInputStream(inputStream);

		File fileTarget = new File(target);

		File dir = fileTarget.getParentFile();
		if (!dir.exists()) {
			dir.mkdirs();
		}

		FileOutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(fileTarget);
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
		}
		if (outputStream == null) {
			logger.error("Write file " + target + "fail!");
			return;
		}
		int bytesRead = 0;
		byte[] buffer = new byte[8192];
		while ((bytesRead = inputStream.read(buffer, 0, 8192)) != -1) {
			outputStream.write(buffer, 0, bytesRead);
		}
		outputStream.close();
		//pif.close();

		//
		// FileChannel channel = outputStream.getChannel();
		// ByteBuffer buffer = ByteBuffer.allocate(1024);
		//
		// int count = 0;
		//
		// while(pif.available())
		//
		//
		//
		// channel.write(buffer);
		// channel.close();
		// outputStream.close();
	}

	/**
	 * 判断文件夹是否为空
	 * 
	 * @param path
	 *  <p>
	 *         author: xyy create at 2015年5月25日 下午12:45:20
	 */
	public static boolean isEmptyDir(String path) {
		File d = new File(path);
		File[] list = d.listFiles();
		if (list != null && list.length > 0) {
			return false;
		} else {
			return true;
		}
	}
}
