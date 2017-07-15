package org.evanframework.httpclient;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;

//import org.springframework.beans.factory.annotation.Autowired;

/**
 * HttpClientUtilsTest
 *
 * @author <a href="mailto:shenwei@">Evan.shen</a>
 * @date 16/9/14
 */
//@RunWith(SpringJUnit4ClassRunner.class)
public class HttpClientUtilsTest {

    //@Autowired
   // private HttpClient httpClient;

    @Test
    public void testHttpGet() {

        HttpClient  httpClient = HttpClients.createDefault();

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
