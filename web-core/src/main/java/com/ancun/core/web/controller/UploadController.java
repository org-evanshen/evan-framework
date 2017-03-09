package com.ancun.core.web.controller;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ancun.core.store.StoreConstants;
import com.ancun.core.sysconfig.SysConfig;
import com.ancun.core.utils.FileUtils;
import com.ancun.core.utils.ImageUtils;
import com.ancun.core.utils.JsonUtils;
import com.ancun.core.web.WebCoreConstants;
import com.ancun.core.web.model.ImageUploadToFrame;
import com.ancun.core.web.model.UploadedFile;
import com.ancun.core.web.utils.UploadUtils;

/**
 * 上传公用Action
 * <p>
 * 
 * @author <a href="mailto:shenwei@ancun.com">ShenWei</a>
 * @version 2010-12-21 下午07:35:26
 */
@Controller
public class UploadController {
	// private static final Logger log =
	// LoggerFactory.getLogger(UploadController.class);

	private static final String SCALE = "-scale";

	@Autowired
	private UploadUtils uploadUtils;

	@Autowired
	private SysConfig sysConfig;

	/**
	 * kindeditor中的图片上传
	 */
	@RequestMapping("kindeditor-upload")
	@ResponseBody
	public String kindeditorUpload(@RequestParam("imgFile") MultipartFile file,
			@RequestParam(value = "dir", required = false) String dir) {
		String returnV = null;

		if (StringUtils.equals(dir, "file")) {
			returnV = kindeditorAccessoryUpload(file);
		} else if (StringUtils.equals(dir, "image")) {
			returnV = kindeditorImageUpload(file);
		} else {

		}

		return returnV;
	}

	/**
	 * kindeditor中的图片上传
	 */
	@RequestMapping("kindeditor-image-upload")
	@ResponseBody
	public String kindeditorImageUpload(@RequestParam("imgFile") MultipartFile file) {
		// if (request instanceof MultipartHttpServletRequest) {
		// MultipartHttpServletRequest multipartRequest =
		// (MultipartHttpServletRequest) request;
		// MultipartFile mfile = multipartRequest.getFile("imgFile"); // 取得上传的文件

		UploadedFile uploadedFile = null;
		String uploadFirstSubDir = null;
		String url = null;// 上传之后的读取url
		uploadedFile = uploadUtils.uploadFileTempDir(file);// 上传到临时目录

		String uploadDir = sysConfig.get(StoreConstants.STORE_DIR);
		uploadFirstSubDir = "/kindeditor-image";
		String webresourcesDir = sysConfig.get(StoreConstants.STORE_DIR);

		ImageUtils.scaleAndWaterMark(uploadedFile.getUploadFullPath(),
				uploadDir + uploadFirstSubDir + "/" + uploadedFile.getUploadSubPath(), 600,
				WebCoreConstants.WATET_MARK_TEXT, WebCoreConstants.WATET_MARK_COLOR, WebCoreConstants.WATET_MARK_FONT,
				webresourcesDir + "/warter.png");

		String uploadUrl = sysConfig.get(StoreConstants.STORE_SERVER_URL);
		url = uploadUrl + uploadFirstSubDir + uploadedFile.getReadUrl();

		return kindeditorOutput(uploadedFile, url);
	}

	private String kindeditorOutput(UploadedFile uploadedFile, String url) {
		Map<String, Object> map = new HashMap<String, Object>(4);

		map.put("filename", uploadedFile.getFileUploadedName());// 上传之后的文件名
		map.put("fileOriginalName", uploadedFile.getFileOriginalName());// 原始文件名
		map.put("url", url);
		map.put("error", 0);

		return JsonUtils.beanToJSON(map);
	}

	/**
	 * kindeditor中的附件上传
	 */
	@RequestMapping("kindeditor-accessory-upload")
	@ResponseBody
	public String kindeditorAccessoryUpload(@RequestParam("imgFile") MultipartFile file) {
		UploadedFile uploadedFile = null;
		String uploadFirstSubDir = null;
		String url = null;// 上传之后的读取url

		uploadFirstSubDir = "kindeditor-accessory";
		uploadedFile = uploadUtils.uploadFileToUploadDir(uploadFirstSubDir, file);
		String uploadUrl = sysConfig.get(StoreConstants.STORE_SERVER_URL);
		url = uploadUrl + uploadedFile.getReadUrl();

		return kindeditorOutput(uploadedFile, url);
	}

	/**
	 * 图片上传器的上传
	 * <p>
	 * author: <a href="mailto:shenwei@ancun.com">ShenWei</a><br>
	 * version: 2011-3-6 下午11:40:44 <br>
	 */
	@RequestMapping("imageUploador-upload")
	@ResponseBody
	public String imageUploadorUpload(@RequestParam("Filedata") MultipartFile file,
			@RequestParam(value = "width", required = false) int width) {
		UploadedFile uploadedFile = uploadUtils.uploadFileTempDir(file);// 上传到临时目录

		String uploadDir = sysConfig.get(StoreConstants.STORE_DIR);
		String uploadUrl = sysConfig.get(StoreConstants.STORE_SERVER_URL);

		// 缩率图
		ImageUtils.scale(uploadedFile.getUploadFullPath(),
				uploadDir + WebCoreConstants.UPLAOD_TEMP + uploadedFile.getUploadSubDir()
						+ FileUtils.fileNameAddSuffix(uploadedFile.getFileUploadedName(), SCALE),
				width);

		Map<String, Object> map = new HashMap<String, Object>(8);

		map.put("uploadedSubDir", uploadedFile.getUploadSubDir());// 子目录
		map.put("originalFileUploadedName", uploadedFile.getFileUploadedName());// 原始文件上传之后的文件名
		map.put("originalFileUploadedFullPath", uploadedFile.getUploadFullPath());// 原始文件上传之后的文件名
		map.put("scalePictureUrl", uploadUrl + WebCoreConstants.UPLAOD_TEMP + uploadedFile.getUploadSubDir()
				+ FileUtils.fileNameAddSuffix(uploadedFile.getFileUploadedName(), SCALE));// 缩率图读取url

		map.put("error", 0);

		return JsonUtils.beanToJSON(map);
	}

	/**
	 * 上传图片，向iframe提交
	 */
	@RequestMapping("upload-to-iframe")
	@ResponseBody
	public String imageUploadToIframe(@ModelAttribute("imageUpload") ImageUploadToFrame imageUpload) {
		UploadedFile uploadedFile = uploadUtils.uploadFileTempDir(imageUpload.getFile());// 上传到临时目录

		String uploadDir = sysConfig.get(StoreConstants.STORE_DIR);
		String uploadUrl = sysConfig.get(StoreConstants.STORE_SERVER_URL);

		String filePath = null;
		if (imageUpload.getMaxSize() > 0) {
			filePath = uploadedFile.getUploadSubDir()
					+ FileUtils.fileNameAddSuffix(uploadedFile.getFileUploadedName(), SCALE);
			ImageUtils.scale(uploadedFile.getUploadFullPath(), uploadDir + filePath, imageUpload.getMaxSize());
		} else {
			filePath = uploadedFile.getUploadSubPath();
		}

		filePath = filePath.replace(File.separator, "/");
		String fileUrl = uploadUrl + filePath;

		return "<script type=\"text/javascript\">parent.uploadSuccess('" + fileUrl + "', '" + filePath + "');</script>";
	}
}
