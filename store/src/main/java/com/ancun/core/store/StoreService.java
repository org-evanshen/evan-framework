package com.ancun.core.store;

import java.io.InputStream;

/**
 * 存储服务接口
 * 
 * <p>
 * create at 2014年7月2日 下午3:19:23
 * 
 * @author <a href="mailto:shenwei@ancun.com">ShenWei</a>
 * @version %I%, %G%
 *
 */
public interface StoreService {

	/**
	 * 存储
	 * 
	 * @param filePath
	 *  成功范围hash值，失败返回null
	 *            <p>
	 *            author: <a href="mailto:shenw@hundsun.com">shenw</a><br>
	 *            create at: 2014年7月2日下午3:19:48
	 */
	String saveObject(String filePath);

	/**
	 * 存储
	 * 
	 * @param inputStream
	 * @param extensName
	 * @param storePath
	 *  <p>
	 *         author: <a href="mailto:caozhenfei@ancun.com">Dim.Cao</a><br>
	 *         create at: 2014年4月16日上午12:34:54
	 */
	String saveObject(InputStream inputStream, String extensName, String storePath);

	/**
	 * 获取
	 * 
	 * @param filePath
	 *  <p>
	 *         author: <a href="mailto:shenw@hundsun.com">shenw</a><br>
	 *         create at: 2014年7月2日下午3:20:03
	 */
	InputStream getObject(String filePath);

	/**
	 * 删除
	 * 
	 * @param filePath
	 *  <p>
	 *         author: <a href="mailto:shenw@hundsun.com">shenw</a><br>
	 *         create at: 2014年7月2日下午3:19:43
	 */
	boolean deleteLocalFile(String filePath);

	/**
	 * 
	 * @param firstDir
	 *  <p>
	 *         author: <a href="mailto:shenw@hundsun.com">shenw</a><br>
	 *         create at: 2014年4月16日上午12:34:54
	 */
	String getUploadFullPath(String firstDir);

	/**
	 * 大文件上传云端
	 * 
	 * @param filePath
	 *
	 */
	public boolean multipartUpload(String filePath) throws Exception;

	/**
	 * 判断文件是否在存储磁盘上
	 * 
	 * @param filePath
	 *  <p>
	 *         author: ShenWei<br>
	 *         create at 2015年5月14日 下午5:37:34
	 */
	boolean isExistOnDisk(String filePath);
	
}
