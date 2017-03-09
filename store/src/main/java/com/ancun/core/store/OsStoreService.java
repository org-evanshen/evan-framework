package com.ancun.core.store;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * create at 2016/5/27
 *
 * @author <a href="mailto:taofei@ancun.com">Paul Yao</a>
 * @version %I%, %G%
 */
public interface OsStoreService {


    /**
     * 保存至云服务器
     *
     * @param key
     * @param content
     * @throws IOException
     */
    String saveToCloud(String key, InputStream content) throws IOException;


    /**
     * 保存至云服务器
     *
     * @param key
     * @param localFilePath
     * @throws IOException
     */
    String saveToCloud(String key, String localFilePath) throws IOException;

    /**
     * 保存至云服务器
     *
     * @param key
     * @param localFile
     * @throws IOException
     */
    String saveToCloud(String key, File localFile) throws IOException;

    /**
     * 从云服务器下载文件流
     *
     * @param key
     */
    InputStream getFromCloud(String key);

    /**
     * 从云服务器下载到本地文件
     *
     * @param key
     * @return
     */
    void getFromCloud(String key, File localFile);

    /**
     * 从云服务器下载到本地文件
     *
     * @param key
     * @return
     */
    void getFromCloud(String key, String localFilePath);

    /**
     * 从云服务器下载文件流
     *
     * @param key
     */
    InputStream getFromCloudWithBuket(String buket, String key);

    /**
     * 从云服务器下载到本地文件
     *
     * @param key
     * @return
     */
    void getFromCloudWithBuket(String buket, String key, File localFile);

    /**
     * 从云服务器下载到本地文件
     *
     * @param key
     * @return
     */
    void getFromCloudWithBuket(String buket, String key, String localFilePath);

    /**
     * 分片断点续传下载
     */
    void downloadFile(String key, String localFilePath) throws Throwable;

    /**
     * 删除云服务器文件
     *
     * @param key
     */
    void deleteFromCloud(String key);

    boolean multipartUpload(String filePath) throws IOException;

}
