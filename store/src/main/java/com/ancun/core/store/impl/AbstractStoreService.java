package com.ancun.core.store.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ancun.core.store.StoreConstants;
import com.ancun.core.store.StoreService;
import com.ancun.core.store.utils.DateUtils;
import com.ancun.core.sysconfig.SysConfig;


/**
 *
 */
@Deprecated
public abstract class AbstractStoreService implements StoreService {

    private static Logger logger = LoggerFactory.getLogger(StoreServiceImpl.class);

    /**
     * 本地存储根目录
     */
    private String storeRootDir;

    private SysConfig sysConfig;

    protected void init(SysConfig sysConfig) {
        this.sysConfig = sysConfig;
        storeRootDir = sysConfig.get(StoreConstants.STORE_DIR);
    }

    public String saveObject(String filePath) {
        String returnV = null;
        if (sysConfig.isWriteToOss()) {
            String fileFullPath;
            if (filePath.startsWith("/")) {
                fileFullPath = storeRootDir + filePath;
                filePath = filePath.substring(1);
            } else {
                fileFullPath = storeRootDir + "/" + filePath;
            }
            logger.info("Save Object,fileFullPath:[{}]", fileFullPath);
            File file = new File(fileFullPath);

            InputStream content = null;
            try {
                content = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                logger.error(String.format(" file input stream error . file is %s", fileFullPath), e);
                throw new RuntimeException(e);
            }
            if (content != null) {
                returnV = saveToCloud(filePath, file.length(), content);
                try {
                    content.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                    throw new RuntimeException(e);
                }
            }
        }
        return returnV;
    }

    public String saveObject(String filePath, byte[] data) {
        String returnV = null;

        FileOutputStream fos = null;
        String fileFullPath;

        if (filePath.startsWith("/")) {
            fileFullPath = storeRootDir + filePath;
            filePath = filePath.substring(1);
        } else {
            fileFullPath = storeRootDir + "/" + filePath;
        }
        File file = new File(fileFullPath);

        try {
            fos = new FileOutputStream(file);
            fos.write(data);
            fos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalStateException(e);
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                    fos = null;
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                    throw new RuntimeException(e);
                }
            }
        }

        if (sysConfig.isWriteToOss()) {
            InputStream content = null;
            try {
                content = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                logger.error(e.getMessage(), e);
                throw new RuntimeException(e);
            }
            if (content != null) {
                returnV = saveToCloud(filePath, file.length(), content);
            }
        }
        return returnV;
    }

    public String saveObject(InputStream inputStream, String fileName, String storePath) {
        FileOutputStream fos = null;
        String storeRootDir = sysConfig.get(StoreConstants.STORE_DIR) + storePath;
        String filePath = storePath + fileName;
        if (filePath.startsWith("/")) {
            filePath = filePath.substring(1);
        }

        File file = new File(storeRootDir);
        if (!file.exists()) {
            file.mkdirs();
        }

        file = new File(storeRootDir + fileName);
        try {
            fos = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            while (inputStream.read(buffer) > 0) {
                fos.write(buffer, 0, buffer.length);
            }
            fos.flush();
            fos.close();

        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalStateException(e);
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                    fos = null;
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }

        if (sysConfig.isWriteToOss()) {
            InputStream content = null;
            try {
                content = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                logger.error(e.getMessage(), e);
                throw new RuntimeException(e);
            }

            if (content != null) {
                try {
                    saveToCloud(filePath, file.length(), content);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                    throw new RuntimeException(e);
                }
            }
        }
        return filePath;
    }

    public void saveObjectDelLoclFile(String filePath) {
        if (sysConfig.isWriteToOss()) {
            String fileFullPath;
            if (filePath.startsWith("/")) {
                fileFullPath = storeRootDir + filePath;
                filePath = filePath.substring(1);
            } else {
                fileFullPath = storeRootDir + "/" + filePath;
            }
            logger.info("Save Object,fileFullPath:[{}]", fileFullPath);
            File file = new File(fileFullPath);

            InputStream content = null;
            try {
                content = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                logger.error(String.format(" file input stream error . file is %s", fileFullPath), e);
                throw new RuntimeException(e);
            }
            String eteg = null;
            if (content != null) {
                try {
                    eteg = saveToCloud(filePath, file.length(), content);
                    content.close();
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                    throw new RuntimeException(e);
                }
                if (StringUtils.isNotBlank(eteg)) {
                    deleteLocalFile(filePath);
                }
            }
        }
    }

    public InputStream getObject(String filePath) {
        if (StringUtils.isBlank(filePath)){
            return null;
        }

        InputStream is = null;
        if (sysConfig.isWriteToOss()) {
            if (filePath.startsWith("/")) {
                filePath = filePath.substring(1);
            }
            is = getFromCloud(filePath);
            if (is == null) {
                logger.info("get object[{}] from disk ", filePath);
                is = getObjectFromDisk(filePath);
            }
        } else {
            logger.info("get object[{}] from disk ", filePath);
            is = getObjectFromDisk(filePath);
        }

        return is;
    }

    protected abstract InputStream getFromCloud(String filePath);

    protected abstract String saveToCloud(String filePath, long length, InputStream content);

    private InputStream getObjectFromDisk(String filePath) {
        InputStream is = null;

        if (!storeRootDir.endsWith("/") && !filePath.startsWith("/")) {
            filePath = "/" + filePath;
        }

        logger.info(String.format("storeDir is %s .filePath is %s", storeRootDir, filePath));
        File file = new File(storeRootDir + filePath);
        if (file.exists()) {
            try {
                is = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                logger.error(e.getMessage(), e);
                throw new RuntimeException(e);
            }
        } else {
            logger.warn("File[{}]not find.", file.getPath());
        }

        return is;
    }

    public boolean isExistOnDisk(String filePath) {
        String storeDir = sysConfig.get(StoreConstants.STORE_DIR);

        if (!storeDir.endsWith("/") && !filePath.startsWith("/")) {
            filePath = "/" + filePath;
        }

        File file = new File(storeDir + filePath);
        return file.exists();
    }

    public boolean deleteLocalFile(String filePath) {
        boolean r = false;
        if (!filePath.startsWith("/")) {
            filePath = "/" + filePath;
        }
        if (StringUtils.isNotBlank(filePath)) {
            String storeDir = sysConfig.get(StoreConstants.STORE_DIR);
            File file = new File(storeDir + filePath);
            if (file.isFile()) {// 一定要加这个判断 ,否则把整个目录删除了
                r = FileUtils.deleteQuietly(file);
            }
        }
        return r;
    }

    /**
     * 创建上传的全目录(在根目录下建立子目录),并范围子目录路径
     * <p/>
     * author: <a href="mailto:shenwei@ancun.com">ShenWei</a><br>
     * version: 2011-3-3 下午12:50:03 <br>
     *
     * @param firstDir
     */
    public String getUploadFullPath(String firstDir) {
        Date today = new Date();

        if (StringUtils.isNotBlank(firstDir)) {
            firstDir = "/" + firstDir;
        }

        String s = firstDir + "/" + DateUtils.format(today, "yyyyMM") + "/" + DateUtils.format(today, "dd")
                + "/" + DateUtils.format(today, "HH") + "/";
        return s;
    }
}
