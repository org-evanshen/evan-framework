package com.ancun.core.store.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.CompleteMultipartUploadRequest;
import com.aliyun.oss.model.CompleteMultipartUploadResult;
import com.aliyun.oss.model.InitiateMultipartUploadRequest;
import com.aliyun.oss.model.InitiateMultipartUploadResult;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PartETag;
import com.aliyun.oss.model.PutObjectResult;
import com.aliyun.oss.model.UploadPartRequest;
import com.aliyun.oss.model.UploadPartResult;
import com.ancun.core.store.StoreConstants;
import com.ancun.core.store.StoreService;
import com.ancun.core.sysconfig.SysConfig;

/**
 * 阿里云文件存储
 * <p>
 * create at 2015年6月11日 下午1:54:52
 * 
 * @author <a href="mailto:caozhenfei@ancun.com">Dim.Cao</a>
 * @version %I%, %G%
 *  OssStoreHelper
 */
@Deprecated
public class OssStoreServiceImpl extends AbstractStoreService implements StoreService {
	private static Logger logger = LoggerFactory.getLogger(OssStoreServiceImpl.class);

	private static final String DEFAULT_BUCKETNAME = "ancun-platform";

	/** 本地存储根目录 */
	private String storeRootDir;

	/** Oss存储的bucketname */
	private String bucketName;
	
	private SysConfig sysConfig;
	
	private OSSClient ossClient;

	public void init() {
		super.init(sysConfig);
		storeRootDir = sysConfig.get(StoreConstants.STORE_DIR);
		bucketName = sysConfig.get("oss.bucketName");
		if (StringUtils.isBlank(bucketName)) {
			bucketName = DEFAULT_BUCKETNAME;
		}				
	}

	@Override
	public boolean multipartUpload(String filePath) throws Exception {
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

	protected String saveToCloud(String key, long length, InputStream content) {
		// 创建上传Object的Metadata
		ObjectMetadata meta = new ObjectMetadata();
		// 必须设置ContentLength
		meta.setContentLength(length);
		logger.info("Start upload oss,key:[{}]",key);
		PutObjectResult result = ossClient.putObject(bucketName, key, content, meta);
		logger.info("Upload oss end," + result.getETag());
		return result.getETag();
	}

	protected InputStream getFromCloud(String key) {
		InputStream is = null;
		logger.info("Get object[{}] from oss ",key);
		OSSObject o = null;
		try {
			o = ossClient.getObject(bucketName, key);
		} catch (OSSException ex) {
			if ("NoSuchKey".equals(ex.getErrorCode())) {
				logger.info("Get object[" + key + "] from oss fail,The specified key does not exist.");
			} else {
				logger.error("Get object[" + key + "] from oss fail", ex);
			}
		}
		if (o != null && o.getObjectContent() != null) {
			logger.info("Get object[{}] from oss success ", key);
			is = o.getObjectContent();
		}
		return is;
	}

	public void setSysConfig(SysConfig sysConfig) {
		this.sysConfig = sysConfig;
	}

	public void setOssClient(OSSClient ossClient) {
		this.ossClient = ossClient;
	}

	// public boolean saveObject2Oss(String filePath) {
	// boolean flag = true;
	// if (sysConfig.isWriteToOss()) {
	// String fileFullPath, ossKey;
	// if (filePath.startsWith("/")) {
	// fileFullPath = storeRootDir + filePath;
	// ossKey = filePath.substring(1);
	// } else {
	// fileFullPath = storeRootDir + "/" + filePath;
	// ossKey = filePath;
	// }
	// File file = new File(fileFullPath);
	//
	// InputStream content = null;
	// try {
	// content = new FileInputStream(file);
	// } catch (FileNotFoundException e) {
	// if (logger.isDebugEnabled()) {
	// logger.debug(e.getMessage(), e);
	// }
	// }
	//
	// if (content != null) {
	// // 创建上传Object的Metadata
	// ObjectMetadata meta = new ObjectMetadata();
	// // 必须设置ContentLength
	// meta.setContentLength(file.length());
	// PutObjectResult result = ossClient.putObject(bucketName, ossKey, content,
	// meta);
	// if (StringUtils.isBlank(result.getETag())) {
	// flag = false;
	// }
	// }
	// }
	// return flag;
	// }

}
