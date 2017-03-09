package com.ancun.core.store.impl;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import com.ancun.core.store.OsStoreService;

/**
 * create at 2016/5/31
 *
 * @author <a href="mailto:taofei@ancun.com">Paul Yao</a>
 * @version %I%, %G%
 *
 */
public class OsStoreAdapter {

    public static final Integer AGENT_OSS = 1;
    public static final Integer AGENT_BOS = 2;

    private OsStoreService ossStoreHelper; // 阿里云储
    private OsStoreService bosStoreHelper; // 百度云储

    /**
     * 保存至云服务器
     *
     * @param key
     * @param content
     *
     * @throws IOException
     */
    public String saveToCloud(String key, InputStream content, Integer storeAgent) throws IOException {
        return this.adaptAgent(storeAgent).saveToCloud(key, content);
    }

    /**
     * 保存至云服务器
     *
     * @param key
     * @param localFile
     *
     * @throws IOException
     */
    public String saveToCloud(String key, File localFile, Integer storeAgent) throws IOException {
        return this.adaptAgent(storeAgent).saveToCloud(key, localFile);
    }

    /**
     * 保存至云服务器
     *
     * @param key
     * @param localFilePath
     *
     * @throws IOException
     */
    public String saveToCloud(String key, String localFilePath, Integer storeAgent) throws IOException {
        return this.adaptAgent(storeAgent).saveToCloud(key, localFilePath);
    }

    /**
     * 从云服务器下载
     *
     * @param key
     *
     */
    public InputStream getFromCloud(String key, Integer storeAgent) {
        return this.adaptAgent(storeAgent).getFromCloud(key);
    }

    /**
     * 从云服务器下载到本地文件
     * @param key
     * @return
     */
    public void getFromCloud(String key, File localFile, Integer storeAgent) {
        this.adaptAgent(storeAgent).getFromCloud(key, localFile);
    }

    /**
     * 从云服务器下载到本地文件
     * @param key
     * @return
     */
    public void getFromCloud(String key, String localFilePath, Integer storeAgent) {
        this.adaptAgent(storeAgent).getFromCloud(key, localFilePath);
    }

    /**
     * 指定buket从云服务器下载
     * @param key
     *
     */
    public InputStream getFromCloudWithBuket(String buket, String key, Integer storeAgent) {
        return this.adaptAgent(storeAgent).getFromCloudWithBuket(buket, key);
    }

    /**
     * 指定buket从云服务器下载
     * @param key
     *
     */
    public void getFromCloudWithBuket(String buket, String key, File localFile, Integer storeAgent) {
        this.adaptAgent(storeAgent).getFromCloudWithBuket(buket, key, localFile);
    }

    /**
     * 指定buket从云服务器下载
     * @param key
     *
     */
    public void getFromCloudWithBuket(String buket, String key, String localFilePath, Integer storeAgent) {
        this.adaptAgent(storeAgent).getFromCloudWithBuket(buket, key, localFilePath);
    }



    /**
     * 分片断点续传下载
     */
    public void downloadFile(String key, String localFilePath, Integer storeAgent) throws Throwable {
        this.adaptAgent(storeAgent).downloadFile(key, localFilePath);
    }

    /**
     * 删除云服务器文件
     *
     * @param key
     */
    public void deleteFromCloud(String key, Integer storeAgent) {
        this.adaptAgent(storeAgent).deleteFromCloud(key);
    }

    private OsStoreService adaptAgent(Integer storeAgent) {
        switch (storeAgent) {
            case 2: return bosStoreHelper;
            case 1:
            default: return ossStoreHelper;
        }
    }

    public void setOssStoreHelper(OsStoreService ossStoreHelper) {
        this.ossStoreHelper = ossStoreHelper;
    }

    public void setBosStoreHelper(OsStoreService bosStoreHelper) {
        this.bosStoreHelper = bosStoreHelper;
    }


}
