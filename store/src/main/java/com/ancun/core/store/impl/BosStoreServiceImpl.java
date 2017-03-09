package com.ancun.core.store.impl;

import java.io.InputStream;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ancun.core.store.StoreService;
import com.ancun.core.sysconfig.SysConfig;
import com.baidubce.BceServiceException;
import com.baidubce.services.bos.BosClient;
import com.baidubce.services.bos.model.BosObject;
import com.baidubce.services.bos.model.ObjectMetadata;
import com.baidubce.services.bos.model.PutObjectResponse;

/**
 * 百度云文件存储 create at 2015年6月11日 下午1:53:26
 *
 * @author <a href="mailto:caozhenfei@ancun.com">Dim.Cao</a>
 * @version %I%, %G%
 *          BosStoreHelper
 */
@Deprecated
public class BosStoreServiceImpl extends AbstractStoreService implements StoreService {
    private static Logger logger = LoggerFactory.getLogger(BosStoreServiceImpl.class);

    private static final String DEFAULT_BUCKETNAME = "ancun-platform";

    /**
     * Bos存储的bucketname
     */
    private String bucketName;

    private SysConfig sysConfig;

    private BosClient bosClient;

    public void init() {
        super.init(sysConfig);
        bucketName = sysConfig.get("bucketName");
        if (StringUtils.isBlank(bucketName)) {
            bucketName = DEFAULT_BUCKETNAME;
        }
    }

    protected String saveToCloud(String key, long length, InputStream content) {
        // 创建上传Object的Metadata
        ObjectMetadata meta = new ObjectMetadata();
        // 必须设置ContentLength
        meta.setContentLength(length);
        logger.info("Start upload to bos,key [" + key + "]");
        PutObjectResponse result = bosClient.putObject(bucketName, key, content, meta);
        logger.info("Upload bos end,etag [" + result.getETag() + "]");
        return result.getETag();
    }

    protected InputStream getFromCloud(String key) {
        InputStream is = null;
        logger.info("Get object[{}] from bos ");
        BosObject o = null;
        try {
            o = bosClient.getObject(bucketName, key);
        } catch (BceServiceException ex) {
            if ("NoSuchKey".equals(ex.getErrorCode())) {
                logger.warn("Get object[" + key + "] from bos fail,The specified key does not exist.");
            } else {
                logger.error("Get object[" + key + "] from bos fail", ex);
            }
        }
        if (o != null && o.getObjectContent() != null) {
            logger.info("Get object[{}] from bos success ", key);
            is = o.getObjectContent();
        }
        return is;
    }

    @Override
    public boolean multipartUpload(String filePath) throws Exception {
        // TODO Auto-generated method stub
        return false;
    }

    public void setSysConfig(SysConfig sysConfig) {
        this.sysConfig = sysConfig;
    }

    public void setBosClient(BosClient bosClient) {
        this.bosClient = bosClient;
    }
}
