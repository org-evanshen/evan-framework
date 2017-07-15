package org.evanframework.utils;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 压缩工具类
 * 
 * <p>
 * create at 2015年5月27日 下午3:26:52
 * @author shen.wei
 * @version %I%, %G%
 *
 */
public class ZipUtil {

	private static Log log = LogFactory.getLog(ZipUtil.class);
	
	/**
	 * 压缩。
	 * 
	 * @param src
	 *            源文件或者目录
	 * @param dest
	 *            压缩文件路径
	 * @throws IOException
	 */
	public static void zip(String src, String dest) throws IOException {
		ZipOutputStream out = null;
		try {
			File outFile = new File(dest);
			OutputStream os = new FileOutputStream(outFile);
			out = new ZipOutputStream(os);
			File fileOrDirectory = new File(src);

			if (fileOrDirectory.isFile()) {
				zipFileOrDirectory(out, fileOrDirectory, "");
			} else {
				File[] entries = fileOrDirectory.listFiles();
				if(null !=entries && entries.length>0){
					for (int i = 0; i < entries.length; i++) {
						// 递归压缩，更新curPaths
						zipFileOrDirectory(out, entries[i], "");
					}
				}	
			}
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException ex) {
					log.error(ex);
				}
			}
		}
	}

	/**
	 * 递归压缩文件或目录
	 * 
	 * @param out
	 *            压缩输出流对象
	 * @param fileOrDirectory
	 *            要压缩的文件或目录对象
	 * @param curPath
	 *            当前压缩条目的路径，用于指定条目名称的前缀
	 * @throws IOException
	 */
	private static void zipFileOrDirectory(ZipOutputStream out, File fileOrDirectory, String curPath)
			throws IOException {
		FileInputStream in = null;
		try {
			if (!fileOrDirectory.isDirectory()) {
				// 压缩文件
				byte[] buffer = new byte[4096];
				int bytes_read;
				in = new FileInputStream(fileOrDirectory);

				ZipEntry entry = new ZipEntry(curPath + fileOrDirectory.getName());
				out.putNextEntry(entry);

				while ((bytes_read = in.read(buffer)) != -1) {
					out.write(buffer, 0, bytes_read);
				}
				out.closeEntry();
			} else {
				// 压缩目录
				File[] entries = fileOrDirectory.listFiles();
				for (int i = 0; i < entries.length; i++) {
					// 递归压缩，更新curPaths
					zipFileOrDirectory(out, entries[i], curPath + fileOrDirectory.getName() + "/");
				}
			}
		} catch (IOException ex) {
			log.error(ex);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException ex) {
					log.error(ex);
				}
			}
		}
	}

	/**
	 * 解压缩。
	 * 
	 * @param zipFileName
	 *            源文件
	 * @param outputDirectory
	 *            解压缩后文件存放的目录
	 * @throws IOException
	 */
	public static void unzip(String zipFileName, String outputDirectory) throws IOException {

		ZipFile zipFile = null;

		try {
			zipFile = new ZipFile(zipFileName);
			Enumeration e = zipFile.entries();

			ZipEntry zipEntry = null;

			File dest = new File(outputDirectory);
			dest.mkdirs();

			while (e.hasMoreElements()) {
				zipEntry = (ZipEntry) e.nextElement();

				String entryName = zipEntry.getName();

				InputStream in = null;
				FileOutputStream out = null;

				try {
					if (zipEntry.isDirectory()) {
						String name = zipEntry.getName();
						name = name.substring(0, name.length() - 1);

						File f = new File(outputDirectory + File.separator + name);
						f.mkdirs();
					} else {
						int index = entryName.lastIndexOf("\\");
						if (index != -1) {
							File df = new File(outputDirectory + File.separator + entryName.substring(0, index));
							df.mkdirs();
						}
						index = entryName.lastIndexOf("/");
						if (index != -1) {
							File df = new File(outputDirectory + File.separator + entryName.substring(0, index));
							df.mkdirs();
						}

						File f = new File(outputDirectory + File.separator + zipEntry.getName());
						// f.createNewFile();
						in = zipFile.getInputStream(zipEntry);
						out = new FileOutputStream(f);

						int c;
						byte[] by = new byte[1024];

						while ((c = in.read(by)) != -1) {
							out.write(by, 0, c);
						}
						out.flush();
					}
				} catch (IOException ex) {
					log.error("解压失败：" + ex.toString());
				} finally {
					if (in != null) {
						try {
							in.close();
						} catch (IOException ex) {
							log.error(ex);
						}
					}
					if (out != null) {
						try {
							out.close();
						} catch (IOException ex) {
							log.error(ex);
						}
					}
				}
			}

		} catch (IOException ex) {
			log.error("解压失败：" + ex.toString());
		} finally {
			if (zipFile != null) {
				try {
					zipFile.close();
				} catch (IOException ex) {
					log.error(ex);
				}
			}
		}

	}
}