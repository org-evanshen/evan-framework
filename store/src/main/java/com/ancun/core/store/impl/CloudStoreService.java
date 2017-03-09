package com.ancun.core.store.impl;

import java.io.InputStream;

/**
 * 云端上传组件
 *
 * @author <a href="mailto:shenwei@ancun.com">Evan.shen</a>
 * @date 16/9/16
 */
public interface CloudStoreService {
    /**
     * 存储
     *
     * @param filePath
     *  成功范围hash值，失败返回null
     *            <p>
     *            author: <a href="mailto:shenw@hundsun.com">shenw</a><br>
     *            create at: 2014年7月2日下午3:19:48
     */
    String saveObject(String filePath);

    /**
     * 存储
     *
     * @param inputStream
     * @param extensName
     * @param storePath
     *  <p>
     *         author: <a href="mailto:caozhenfei@ancun.com">Dim.Cao</a><br>
     *         create at: 2014年4月16日上午12:34:54
     */
    String saveObject(InputStream inputStream, String extensName, String storePath);

    /**
     * 获取
     *
     * @param filePath
     *  <p>
     *         author: <a href="mailto:shenw@hundsun.com">shenw</a><br>
     *         create at: 2014年7月2日下午3:20:03
     */
    InputStream getObject(String filePath);



    /**
     * 大文件上传云端
     *
     * @param filePath
     *
     */
    public boolean multipartUpload(String filePath) throws Exception;
}
