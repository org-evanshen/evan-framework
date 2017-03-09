package com.ancun.core.httpclient;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * HttpClientUtilsTest
 *
 * @author <a href="mailto:shenwei@ancun.com">Evan.shen</a>
 * @date 16/9/14
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:httpclient-bean.xml", "classpath:test-bean.xml"})
public class HttpClientUtilsTest {

    @Autowired
    private HttpClient httpClient;

    @Test
    public void testHttpGet() {
        HttpGet httpGet = new HttpGet("http://www.baidu.com");

        String responseBody = HttpClientUtils.execute(httpClient, httpGet, new HttpClientSuccessHanlder<String>() {
            @Override
            public String hanlder(int statusCode, String responseBody, HttpResponse httpResponse) {
                return responseBody;
            }
        });

        System.out.println(responseBody);

    }
}
