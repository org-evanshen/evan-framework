package com.ancun.core.store.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.CompleteMultipartUploadRequest;
import com.aliyun.oss.model.CompleteMultipartUploadResult;
import com.aliyun.oss.model.DownloadFileRequest;
import com.aliyun.oss.model.DownloadFileResult;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.InitiateMultipartUploadRequest;
import com.aliyun.oss.model.InitiateMultipartUploadResult;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.PartETag;
import com.aliyun.oss.model.PutObjectResult;
import com.aliyun.oss.model.UploadPartRequest;
import com.aliyun.oss.model.UploadPartResult;
import com.ancun.core.store.OsStoreService;

/**
 * create at 2016/5/27
 *
 * @author <a href="mailto:taofei@ancun.com">Paul Yao</a>
 * @version %I%, %G%
 */
public class OssStoreHelper extends BaseStoreHelper implements OsStoreService {
    private static final Logger logger = LoggerFactory.getLogger(OssStoreHelper.class);

    private OSSClient ossClient;

    public void init() {
        bucketName = sysConfig.get("oss.bucketName");
        super.init();
        logger.info("OSSClient inited success,endpoint[{}],bucketName[{}]", ossClient.getEndpoint(), bucketName);
    }

    @Override
    public String saveToCloud(String key, InputStream content) throws IOException {
        // 创建上传Object的Metadata
//        ObjectMetadata meta = new ObjectMetadata();
        // 必须设置ContentLength
//        meta.setContentLength(content.available());
        PutObjectResult result = ossClient.putObject(bucketName, key, content);
        return result.getETag();
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
        PutObjectResult result = ossClient.putObject(bucketName, key, localFile);
        return result.getETag();
    }

    @Override
    public InputStream getFromCloud(String key) {
        InputStream is = null;
        OSSObject o = ossClient.getObject(bucketName, key);
        if (o != null && o.getObjectContent() != null) {
            is = o.getObjectContent();
        }
        return is;
    }

    /**
     * 从云服务器下载到本地文件
     *
     * @param key
     * @param localFile
     * @return
     */
    @Override
    public void getFromCloud(String key, File localFile) {
        ossClient.getObject(new GetObjectRequest(bucketName, key), localFile);
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
        InputStream is = null;
        OSSObject o = ossClient.getObject(buket, key);
        if (o != null && o.getObjectContent() != null) {
            is = o.getObjectContent();
        }
        return is;
    }

    @Override
    public void getFromCloudWithBuket(String buket, String key, File localFile) {
        ossClient.getObject(new GetObjectRequest(buket, key), localFile);
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
        // 下载请求，10个任务并发下载，启动断点续传
        DownloadFileRequest downloadFileRequest = new DownloadFileRequest(bucketName, key);
        downloadFileRequest.setDownloadFile(localFilePath);
        downloadFileRequest.setTaskNum(10);
        downloadFileRequest.setEnableCheckpoint(true);
        // 下载文件
        DownloadFileResult downloadRes = ossClient.downloadFile(downloadFileRequest);
        // 下载成功时，会返回文件的元信息
        downloadRes.getObjectMetadata();
    }

    @Override
    public void deleteFromCloud(String key) {
        ossClient.deleteObject(bucketName, key);
    }

    @Override
    public boolean multipartUpload(String filePath) throws IOException {
        boolean result = true;
        if (sysConfig.isWriteToOss()) {

            String fileFullPath = null;
            String ossKey = null;
            if (filePath.startsWith("/")) {
                fileFullPath = storeRootDir + filePath;
                ossKey = filePath.substring(1);
            } else {
                fileFullPath = storeRootDir + "/" + filePath;
                ossKey = filePath;
            }
            InitiateMultipartUploadRequest initiateMultipartUploadRequest = new InitiateMultipartUploadRequest(
                    bucketName, ossKey);
            InitiateMultipartUploadResult initiateMultipartUploadResult = ossClient
                    .initiateMultipartUpload(initiateMultipartUploadRequest);
            // 设置每块为 5M
            final int partSize = 1024 * 1024 * 5;

            File partFile = new File(fileFullPath);
            // 计算分块数目
            int partCount = (int) (partFile.length() / partSize);
            if (partFile.length() % partSize != 0) {
                partCount++;
            }

            // 新建一个List保存每个分块上传后的ETag和PartNumber
            List<PartETag> partETags = new ArrayList<PartETag>();
            for (int i = 0; i < partCount; i++) {
                // 获取文件流
                FileInputStream fis = new FileInputStream(partFile);
                // 跳到每个分块的开头
                long skipBytes = partSize * i;
                fis.skip(skipBytes);
                // 计算每个分块的大小
                long size = partSize < partFile.length() - skipBytes ? partSize : partFile.length() - skipBytes;
                // 创建UploadPartRequest，上传分块
                UploadPartRequest uploadPartRequest = new UploadPartRequest();
                uploadPartRequest.setBucketName(bucketName);
                uploadPartRequest.setKey(ossKey);
                uploadPartRequest.setUploadId(initiateMultipartUploadResult.getUploadId());
                uploadPartRequest.setInputStream(fis);
                uploadPartRequest.setPartSize(size);
                uploadPartRequest.setPartNumber(i + 1);
                UploadPartResult uploadPartResult = ossClient.uploadPart(uploadPartRequest);
                // 将返回的PartETag保存到List中。
                partETags.add(uploadPartResult.getPartETag());
                // 关闭文件
                fis.close();
            }

            CompleteMultipartUploadRequest completeMultipartUploadRequest = new CompleteMultipartUploadRequest(
                    bucketName, ossKey, initiateMultipartUploadResult.getUploadId(), partETags);
            // 完成分块上传
            CompleteMultipartUploadResult completeMultipartUploadResult = ossClient
                    .completeMultipartUpload(completeMultipartUploadRequest);
            if (StringUtils.isBlank(completeMultipartUploadResult.getETag())) {
                result = false;
            }
            logger.info("===========etag========" + completeMultipartUploadResult.getETag());
        }
        return result;
    }

    public void setOssClient(OSSClient ossClient) {
        this.ossClient = ossClient;
    }

    @Override
    public String toString() {
        return  "OssStoreHelper[endpoint:"+ossClient.getEndpoint()+",bucketName:"+bucketName+"]";
    }

}




