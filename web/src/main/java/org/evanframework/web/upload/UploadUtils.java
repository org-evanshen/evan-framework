package org.evanframework.web.upload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import org.evanframework.utils.DateUtils;
import org.evanframework.web.WebCoreConstants;

/**
 * 文件上传工具
 */
public class UploadUtils {
    private static Logger logger = LoggerFactory.getLogger(UploadUtils.class);

    /**
     * 上传内部实现类
     * <p/>
     * author: shen.wei<br>
     * version: 2010-12-27 下午03:35:06 <br>
     *
     * @param mfile
     * @param uploadRootDir     上传的根目录
     * @param uploadFirstSubDir 上传的第一层子目录
     */
    private UploadedFile uploadFile(MultipartFile mfile, String uploadRootDir, String uploadFirstSubDir) {
        String uploadSubDir = getUploadFullPath(uploadFirstSubDir);// 上传全子目录
        createUploadFullDir(uploadRootDir, uploadSubDir);// 创建上传的全目录
        UploadedFile retrunO = null;
        if (mfile != null && mfile.getSize() > 0) {
            String fileOriginalName = mfile.getOriginalFilename();// 原始文件名
            String fileUploadedName = getUploadedName(fileOriginalName);// 上传之后的文件名

            // 上传之后返回上传的信息
            retrunO = new UploadedFile();

            retrunO.setUploadRootDir(uploadRootDir);// 上传根目录
            retrunO.setFileUploadedName(fileUploadedName);// 上传之后的文件名
            retrunO.setFileOriginalName(fileOriginalName);//
            retrunO.setUploadSubDir(uploadSubDir);// 上传的子目录
            retrunO.setFileSize(mfile.getSize() / 1000);
            retrunO.setReadUrl(uploadSubDir + fileUploadedName);

            if (logger.isDebugEnabled()) {
                logger.debug("upload file [" + retrunO.getUploadFullPath() + "]");
            }

            FileOutputStream fos = null;
            File file = new File(uploadRootDir + uploadSubDir + fileUploadedName);

            try {
                fos = new FileOutputStream(file);
                fos.write(mfile.getBytes());
                fos.flush();
            } catch (Exception e) {
                if (logger.isErrorEnabled()) {
                    logger.error(e.getMessage(), e);
                }
                throw new IllegalStateException(e);
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                        fos = null;
                    } catch (IOException e) {
                        if (logger.isErrorEnabled()) {
                            logger.error(e.getMessage(), e);
                        }
                    }
                }
            }
        }

        return retrunO;

    }

    /**
     * 上传到正式目录
     * <p/>
     * author: shen.wei<br>
     * version: 2011-3-8 下午11:20:24 <br>
     *
     * @param uploadFirstSubDir 上传的第一层子目录
     * @param mfile
     */
    public UploadedFile uploadFileToUploadDir(String uploadDir, String uploadFirstSubDir, MultipartFile mfile) {
        return uploadFile(mfile, uploadDir, uploadFirstSubDir);
    }

    /**
     * 上传到临时目录
     * <p/>
     * author: shen.wei<br>
     * version: 2011-3-6 下午12:00:19 <br>
     *
     * @param mfile
     */
    public UploadedFile uploadFileTempDir(String uploadDir, MultipartFile mfile) {
        return uploadFile(mfile, uploadDir + WebCoreConstants.UPLAOD_TEMP, "");
    }

    /**
     * 上传之后的名称
     * <p/>
     * author: shen.wei<br>
     * version: 2011-3-6 上午11:44:33 <br>
     *
     * @param fileOriginalName
     */
    private String getUploadedName(String fileOriginalName) {
        UUID uuid = UUID.randomUUID();
        String fileUploadedName = uuid.toString()
                + StringUtils.substring(fileOriginalName, StringUtils.lastIndexOf(fileOriginalName, '.'));

        return fileUploadedName;
    }

    /**
     * <p/>
     * author: shen.wei<br>
     * version: 2011-3-6 下午01:31:08 <br>
     *
     * @param uploadRootDir
     * @param uploadFullPath
     */
    private void createUploadFullDir(String uploadRootDir, String uploadFullPath) {
        File dir = new File(uploadRootDir + uploadFullPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    /**
     * 创建上传的全目录(在根目录下建立子目录),并范围子目录路径
     * <p/>
     * author: shen.wei<br>
     * version: 2011-3-3 下午12:50:03 <br>
     */
    private String getUploadFullPath(String firstDir) {
        Date today = new Date();
        if (StringUtils.isNotBlank(firstDir)) {
            firstDir = "/" + firstDir;
        }
        String s = firstDir + "/" + DateUtils.format(today, "yyyyMM") + "/" + DateUtils.format(today, "dd")
                + "/" + DateUtils.format(today, "HH") + "/";
        return s;
    }
}
