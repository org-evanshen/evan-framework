package com.yourong;

import com.aliyun.openservices.oss.OSSClient;
import com.aliyun.openservices.oss.model.ObjectMetadata;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by J.W on 2017/3/13.
 */
public class OSSUtil {

    private static int DEFAULT_EXPIRATION = 36500;

    private OSSClient ossClient;

    private String accessKey;

    private String secretKey;

    private OSSUtil() {}

    public OSSUtil(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        ossClient = new OSSClient(accessKey,secretKey);
    }

    /**
     * 将附件上传到阿里云oss
     * @param key 文件目录，可通过ossutil的getkey获取不同类型的key值
     * @param content 需要上传的流
     * @param expiration（可空） 失效的时间，如要到2099-10-01失效，则传2099-10-01的date格式日期
     * @return 返回的文件url，包含完整url
     */
    public String uploadAttachmentToOSS(String key, InputStream content, Date expiration, String bucketName) throws IOException {

            // 创建上传Object的Metadata
            ObjectMetadata meta = new ObjectMetadata();
            // 必须设置ContentLength
            meta.setContentLength((long)content.available());
            ossClient.putObject(bucketName, key, content,meta);
            // 失效时间为空，默认有效期为100年
            if(expiration==null) {
                Calendar ca = Calendar.getInstance();
                ca.setTime(new Date());
                ca.add(Calendar.DATE, DEFAULT_EXPIRATION);
                expiration = ca.getTime();
            }
            // 生成URL
            URL url = ossClient.generatePresignedUrl(bucketName, key, expiration);
            return url.toString();
    }

}
