package com.ancun.core.store.impl;

import java.io.File;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.ancun.core.store.StoreConstants;
import com.ancun.core.store.utils.DateUtils;
import com.ancun.core.store.utils.FileUtils;
import com.ancun.core.sysconfig.SysConfig;

/**
 * 文件存储服务类
 * <p/>
 *
 * @author <a href="mailto:shenwei@ancun.com">ShenWei</a>
 * @version 2013-2-21 下午10:42:23
 */
public class FileStoreHelper {

    private SysConfig sysConfig;

    /**
     * 删除文件
     * <p/>
     * author: <a href="mailto:shenwei@ancun.com">ShenWei</a><br>
     * version: 2012-11-6 下午5:20:23 <br>
     *
     * @param fileReadPath
     */
    public boolean deleteFile(String fileReadPath) {
        boolean r = false;
        if (StringUtils.isNotBlank(fileReadPath)) {
            String storeDir = sysConfig.get(StoreConstants.STORE_DIR);
            File file = new File(storeDir + fileReadPath);
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

        String s = firstDir + "/" + DateUtils.format(today, "yyyyMM") + "/"
                + DateUtils.format(today, "dd") + "/" + DateUtils.format(today, "HH") + "/";
        return s;
    }

    public void setSysConfig(SysConfig sysConfig) {
        this.sysConfig = sysConfig;
    }
}
