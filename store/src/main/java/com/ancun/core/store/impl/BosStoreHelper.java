package com.ancun.core.store.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ancun.core.store.OsStoreService;
import com.baidubce.auth.DefaultBceCredentials;
import com.baidubce.services.bos.BosClient;
import com.baidubce.services.bos.BosClientConfiguration;
import com.baidubce.services.bos.model.BosObject;
import com.baidubce.services.bos.model.GetObjectRequest;
import com.baidubce.services.bos.model.PutObjectResponse;

/**
 * create at 2016/5/31
 *
 * @author <a href="mailto:taofei@ancun.com">Paul Yao</a>
 * @version %I%, %G%
 */
public class BosStoreHelper extends BaseStoreHelper implements OsStoreService {

    private static final Logger logger = LoggerFactory.getLogger(OssStoreHelper.class);

    private BosClient bosClient;

    @Override
    public void init() {
        String accessKeyId = sysConfig.get("baidubce.accessKeyId");
        String secretKey = sysConfig.get("baidubce.secretKey");
        String endpoint = sysConfig.get("bos.endpoint");

        if (StringUtils.isNotEmpty(accessKeyId) && StringUtils.isNotEmpty(secretKey)) {
            DefaultBceCredentials defaultBceCredentials = new DefaultBceCredentials(accessKeyId, secretKey);
            BosClientConfiguration bosClientConfiguration = new BosClientConfiguration();
            bosClientConfiguration.setEndpoint(endpoint);

            bosClient = new BosClient(bosClientConfiguration);

            bucketName = sysConfig.get("bos.bucketName");
            super.init();
            logger.info("BosClient inited success,endpoint[{}],bucketName[{}]", bosClient.getEndpoint(), bucketName);
        }
    }

    /**
     * 保存至云服务器
     *
     * @param key
     * @param content
     * @throws IOException
     */
    @Override
    public String saveToCloud(String key, InputStream content) throws IOException {
        PutObjectResponse putObjectResponseFromInputStream = bosClient.putObject(bucketName, key, content);
        return putObjectResponseFromInputStream.getETag();
    }

    /**
     * 保存至云服务器
     *
     * @param key
     * @param localFilePath
     * @throws IOException
     */
    @Override
    public String saveToCloud(String key, String localFilePath) throws IOException {
        return saveToCloud(key, new File(localFilePath));
    }

    /**
     * 保存至云服务器
     *
     * @param key
     * @param localFile
     * @throws IOException
     */
    @Override
    public String saveToCloud(String key, File localFile) throws IOException {
        PutObjectResponse putObjectResponseFromFile = bosClient.putObject(bucketName, key, localFile);
        return putObjectResponseFromFile.getETag();
    }

    /**
     * 从云服务器下载
     *
     * @param key
     */
    @Override
    public InputStream getFromCloud(String key) {
        // 获取Object，返回结果为BosObject对象
        BosObject object = bosClient.getObject(bucketName, key);
        InputStream objectContent = object.getObjectContent();
        return objectContent;
    }

    /**
     * 从云服务器下载到本地文件
     * <p>new File("/path/to/file","w+")</p>
     *
     * @param key
     * @param localFile
     * @return
     */
    @Override
    public void getFromCloud(String key, File localFile) {
        GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, key);
        bosClient.getObject(getObjectRequest, localFile);
    }

    /**
     * 从云服务器下载到本地文件
     *
     * @param key
     * @param localFilePath
     * @return
     */
    @Override
    public void getFromCloud(String key, String localFilePath) {
        getFromCloud(key, new File(localFilePath));
    }

    @Override
    public InputStream getFromCloudWithBuket(String buket, String key) {
        // 获取Object，返回结果为BosObject对象
        BosObject object = bosClient.getObject(buket, key);
        InputStream objectContent = object.getObjectContent();
        return objectContent;
    }

    @Override
    public void getFromCloudWithBuket(String buket, String key, File localFile) {
        GetObjectRequest getObjectRequest = new GetObjectRequest(buket, key);
        bosClient.getObject(getObjectRequest, localFile);
    }

    @Override
    public void getFromCloudWithBuket(String buket, String key, String localFilePath) {
        getFromCloudWithBuket(buket, key, new File(localFilePath));
    }

    /**
     * 分片断点续传下载
     *
     * @param key
     * @param localFilePath
     */
    @Override
    public void downloadFile(String key, String localFilePath) throws Throwable {
        // throw new BceClientException("bos do not support this method.");
        GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, key);
        bosClient.getObject(getObjectRequest, new File(localFilePath, "w+"));
    }

    /**
     * 删除云服务器文件
     *
     * @param key
     */
    @Override
    public void deleteFromCloud(String key) {
        bosClient.deleteObject(bucketName, key);
    }

    @Override
    public boolean multipartUpload(String filePath) throws IOException {
        throw new UnsupportedOperationException("百度云上暂时没时间大文件分块上传");
        //return false;
    }

    public void setBosClient(BosClient bosClient) {
        this.bosClient = bosClient;
    }

    @Override
    public String toString() {
        return "BosStoreHelper[endpoint:" + bosClient.getEndpoint() + ",bucketName:" + bucketName + "]";
    }

}
