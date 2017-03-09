package com.ancun.core.store;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * StoreServiceImpl Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>九月 16, 2016</pre>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:sysconfig-bean.xml", "classpath*:store-bean.xml"})
public class StoreServiceTest {

    @Autowired
    private StoreService storeService;


    /**
     * Method: saveObject(String filePath)
     */
    @Test
    public void testSaveObjectFilePath() throws Exception {
        String result = storeService.saveObject("1.txt");
        System.out.println("\r\n" + result);
    }

    /**
     * Method: saveObject(InputStream inputStream, String extensName, String storePath)
     */
    @Test
    public void testSaveObjectForInputStreamExtensNameStorePath() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: saveObject(String filePath, byte[] data)
     */
    @Test
    public void testSaveObjectForFilePathData() throws Exception {

    }

    /**
     * Method: getObject(String filePath)
     */
    @Test
    public void testGetObject() throws Exception {

    }

    /**
     * Method: isExistOnDisk(String filePath)
     */
    @Test
    public void testIsExistOnDisk() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: deleteLocalFile(String filePath)
     */
    @Test
    public void testDeleteLocalFile() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: getUploadFullPath(String firstDir)
     */
    @Test
    public void testGetUploadFullPath() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: multipartUpload(String filePath)
     */
    @Test
    public void testMultipartUpload() throws Exception {
//TODO: Test goes here... 
    }


    /**
     * Method: getObjectFromDisk(String filePath)
     */
    @Test
    public void testGetObjectFromDisk() throws Exception {
//TODO: Test goes here... 

    }

} 
