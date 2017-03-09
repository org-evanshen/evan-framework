package com.ancun.core.store;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.AbortMultipartUploadRequest;
import com.aliyun.oss.model.CompleteMultipartUploadRequest;
import com.aliyun.oss.model.CompleteMultipartUploadResult;
import com.aliyun.oss.model.InitiateMultipartUploadRequest;
import com.aliyun.oss.model.InitiateMultipartUploadResult;
import com.aliyun.oss.model.ListMultipartUploadsRequest;
import com.aliyun.oss.model.ListObjectsRequest;
import com.aliyun.oss.model.MultipartUpload;
import com.aliyun.oss.model.MultipartUploadListing;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PartETag;
import com.aliyun.oss.model.PutObjectResult;
import com.aliyun.oss.model.UploadPartRequest;
import com.aliyun.oss.model.UploadPartResult;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:sysconfig-bean.xml","classpath*:store-bean.xml" })
public class OSSClientTest {

	@Autowired
	private OSSClient ossClient;

	@Test
	public void testPutObject() throws FileNotFoundException {
		String fileName = "d:/1Y1DEIVG1KKL26V16YLR_show.pdf";

		File file = new File(fileName);
		InputStream content = new FileInputStream(file);

		UUID uuid = UUID.randomUUID();
		String fileStoreName = uuid.toString()
				+ StringUtils.substring(fileName, StringUtils.lastIndexOf(fileName, '.'));

		// 创建上传Object的Metadata
		ObjectMetadata meta = new ObjectMetadata();

		// 必须设置ContentLength
		meta.setContentLength(file.length());

		PutObjectResult por = ossClient.putObject("ancun-platform-img", fileStoreName, content, meta);
		System.out.println(por);
	}

	@Test
	public void testGetObject() {
		String path = null;
		// path = "093534f2-00c4-40da-9069-b963940abb21.pdf";
		// path = "093534f2-00c4-40da-9069-b963940abb21.pdf";
		// path = "aosp/6/17/2217/66FFT1B9S19SU69M93XK_show.pdf";

		path = "aosp/1/47/2847/35GGGENA23W0FW0RRTLN_w_all.pdf";

		OSSObject ossObject = ossClient.getObject("ancun-platform-img", path);

		ObjectMetadata om = ossObject.getObjectMetadata();

		System.out.print(ossObject.getObjectContent());

	}

	@Test
	public void testListObjects() {
		ListObjectsRequest listObjectsRequest = new ListObjectsRequest("ancun-platform");
		listObjectsRequest.setPrefix("capture/0/300/141208/");
		listObjectsRequest.setMaxKeys(1000);
		ObjectListing objectListing = ossClient.listObjects(listObjectsRequest);
		List<OSSObjectSummary> objectSummaries = objectListing.getObjectSummaries();

		for (OSSObjectSummary oss : objectSummaries) {
			String string = oss.getKey() + ":" + oss.getSize() / 1000;
			System.out.println(string);
		}
	}

	@Test
	public void testMultipartUpload() throws IOException {

		String bucketName = "ancun-platform-img";
		String fileName = "d:/王牌特工特工学院.mkv";

		UUID uuid = UUID.randomUUID();
		String key = uuid.toString() + StringUtils.substring(fileName, StringUtils.lastIndexOf(fileName, '.'));

		InitiateMultipartUploadRequest initiateMultipartUploadRequest = new InitiateMultipartUploadRequest(bucketName,
				key);
		InitiateMultipartUploadResult initiateMultipartUploadResult = ossClient
				.initiateMultipartUpload(initiateMultipartUploadRequest);
		// 打印UploadId
		System.out.println("UploadId: " + initiateMultipartUploadResult.getUploadId());

		// 设置每块为 5M
		final int partSize = 1024 * 1024 * 5;
		File partFile = new File(fileName);
		// 计算分块数目
		int partCount = (int) (partFile.length() / partSize);
		if (partFile.length() % partSize != 0) {
			partCount++;
		}
		// 新建一个List保存每个分块上传后的ETag和PartNumber
		List<PartETag> partETags = new ArrayList<PartETag>();

		long beginTime = System.currentTimeMillis();

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
			uploadPartRequest.setKey(key);
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

		CompleteMultipartUploadRequest completeMultipartUploadRequest = new CompleteMultipartUploadRequest(bucketName,
				key, initiateMultipartUploadResult.getUploadId(), partETags);
		// 完成分块上传
		CompleteMultipartUploadResult completeMultipartUploadResult = ossClient
				.completeMultipartUpload(completeMultipartUploadRequest);
		// 打印Object的ETag
		System.out.println(completeMultipartUploadResult.getETag());

		long endTime = System.currentTimeMillis();

		System.out.println(endTime - beginTime);
	}

	@Test
	public void test1() throws IOException {
		String bucket = "ancun-platform";
		ListMultipartUploadsRequest listMultipartUploadsRequest = new ListMultipartUploadsRequest(bucket);

		while (true) {

			MultipartUploadListing listing = ossClient.listMultipartUploads(listMultipartUploadsRequest);
			if (listing.getMultipartUploads().isEmpty()) {
				break;
			}

			// 遍历所有上传事件
			for (MultipartUpload multipartUpload : listing.getMultipartUploads()) {
				System.out.println("Key: " + multipartUpload.getKey() + " UploadId: " + multipartUpload.getUploadId());
				// ListPartsRequest listPartsRequest = new
				// ListPartsRequest("ancun-platform", multipartUpload.getKey(),
				// multipartUpload.getUploadId());

				AbortMultipartUploadRequest abortMultipartUploadRequest = new AbortMultipartUploadRequest(bucket,
						multipartUpload.getKey(), multipartUpload.getUploadId());

				ossClient.abortMultipartUpload(abortMultipartUploadRequest);

				// // 获取上传的所有Part信息
				// PartListing partListing =
				// ossClient.listParts(listPartsRequest);
				// // 遍历所有Part
				// for (PartSummary part : partListing.getParts()) {
				// System.out.println("PartNumber: " + part.getPartNumber() +
				// " ETag: " + part.getETag());
				// ossClient.
				// }
			}
		}
	}
}
