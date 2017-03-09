package com.ancun.core.store;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baidubce.services.bos.BosClient;
import com.baidubce.services.bos.model.CompleteMultipartUploadRequest;
import com.baidubce.services.bos.model.CompleteMultipartUploadResponse;
import com.baidubce.services.bos.model.InitiateMultipartUploadRequest;
import com.baidubce.services.bos.model.InitiateMultipartUploadResponse;
import com.baidubce.services.bos.model.PartETag;
import com.baidubce.services.bos.model.PutObjectResponse;
import com.baidubce.services.bos.model.UploadPartRequest;
import com.baidubce.services.bos.model.UploadPartResponse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:sysconfig-bean.xml","classpath*:store-bean.xml" })
public class BOSClientTest {

	@Autowired
	private BosClient bosClient;

	@Test
	public void testPutObject() throws FileNotFoundException {
		  // 获取指定文件
	    File file = new File("D:\\software\\SWFTools.zip");
	    // 以文件形式上传Object
	    PutObjectResponse putObjectFromFileResponse = bosClient.putObject("ancun-platform-img", "aosp/SWFTools2.zip", file);
	    // 以数据流形式上传Object
	    //PutObjectResponse putObjectResponseFromInputStream = client.putObject(bucketName, objectKey, inputStream);
	    // 以二进制串上传Object
	  //  PutObjectResponse putObjectResponseFromByte = client.putObject(bucketName, objectKey, byte1);
	    // 以字符串上传Object
	  //  PutObjectResponse putObjectResponseFromString = client.putObject(bucketName, objectKey, string1);

	    // 打印ETag
	    System.out.println(putObjectFromFileResponse.getETag());
	}
	
	@Test
	public void testMultipartUpload() throws IOException{
		String bucketName = "ancun-platform-img";

		String fileFullPath = "D:\\software\\SWFTools.zip";
		InputStream input = new FileInputStream(fileFullPath);
		String fileMd5 = DigestUtils.md5Hex(input);
		System.out.println("fileMd5=" + fileMd5);
		String objectKey = "aosp/SWFTools.zip";

		// 开始Multipart Upload
		InitiateMultipartUploadRequest initiateMultipartUploadRequest = new InitiateMultipartUploadRequest(bucketName,objectKey);
		InitiateMultipartUploadResponse initiateMultipartUploadResponse = bosClient.initiateMultipartUpload(initiateMultipartUploadRequest);
		// 打印UploadId
		System.out.println("UploadId: " + initiateMultipartUploadResponse.getUploadId());
		
		// 设置每块为 5MB
		final long partSize = 1024 * 1024 * 5L;

		File partFile = new File(fileFullPath);

		// 计算分块数目
		int partCount = (int) (partFile.length() / partSize);
		if (partFile.length() % partSize != 0){
		    partCount++;
		}

		// 新建一个List保存每个分块上传后的ETag和PartNumber
		List<PartETag> partETags = new ArrayList<PartETag>();

		for(int i = 0; i < partCount; i++){
		    // 获取文件流
		    FileInputStream fis = new FileInputStream(partFile);

		    // 跳到每个分块的开头
		    long skipBytes = partSize * i;
		    fis.skip(skipBytes);

		    // 计算每个分块的大小
		    long size = partSize < partFile.length() - skipBytes ?
		            partSize : partFile.length() - skipBytes;

		    // 创建UploadPartRequest，上传分块
		    UploadPartRequest uploadPartRequest = new UploadPartRequest();
		    uploadPartRequest.setBucketName(bucketName);
		    uploadPartRequest.setKey(objectKey);
		    uploadPartRequest.setUploadId(initiateMultipartUploadResponse.getUploadId());
		    uploadPartRequest.setInputStream(fis);
		    uploadPartRequest.setPartSize(size);
		    uploadPartRequest.setPartNumber(i + 1);
		    UploadPartResponse uploadPartResponse = bosClient.uploadPart(uploadPartRequest);

		    // 将返回的PartETag保存到List中。
		    partETags.add(uploadPartResponse.getPartETag());

		    // 关闭文件
		    fis.close();
		}
		
		CompleteMultipartUploadRequest completeMultipartUploadRequest =      new CompleteMultipartUploadRequest(bucketName, objectKey, initiateMultipartUploadResponse.getUploadId(), partETags);

		// 完成分块上传
		CompleteMultipartUploadResponse completeMultipartUploadResponse = bosClient.completeMultipartUpload(completeMultipartUploadRequest);

		// 打印Object的ETag
		System.out.println(completeMultipartUploadResponse.getETag());
 
	}
}
