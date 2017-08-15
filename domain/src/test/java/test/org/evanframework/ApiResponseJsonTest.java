package test.org.evanframework;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Before;
import org.junit.Test;
import test.org.evanframework.support.DemoListDTO;

import java.io.IOException;

/**
 * Created on 2017/8/15.
 *
 * @author evan.shen
 */
public class ApiResponseJsonTest {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void init(){
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        //super.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }


    @Test
    public void test() throws IOException{
        DemoListDTO dto =  objectMapper.readValue(json,DemoListDTO.class);

        System.out.println(dto);
    }

    private String json = "{\n" +
            "  \"code\": \"SUCCESS\",\n" +
            "  \"msg\": \"成功\",\n" +
            "  \"data\": [\n" +
            "    {\n" +
            "      \"id\": 533,\n" +
            "      \"fieldProvince\": \"330000\",\n" +
            "      \"fieldProvinceName\": \"浙江\",\n" +
            "      \"gmtCreate\": \"2013-07-08T07:44:05+0800\",\n" +
            "      \"fieldRadio\": \"2\",\n" +
            "      \"fieldRadioText\": \"女\",\n" +
            "      \"fieldRadioEnum\": \"WOMAN\",\n" +
            "      \"fieldText\": \"张三6\",\n" +
            "      \"status\": 1,\n" +
            "      \"statusText\": \"未发布\",\n" +
            "      \"statusColor\": \"#ff6600\",\n" +
            "      \"statusEnum\": \"NO_PUBLISH\",\n" +
            "      \"demoChild1s\": []\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 280,\n" +
            "      \"fieldProvince\": \"330000\",\n" +
            "      \"fieldProvinceName\": \"浙江\",\n" +
            "      \"gmtCreate\": \"2014-03-29T00:57:05+0800\",\n" +
            "      \"fieldRadio\": \"2\",\n" +
            "      \"fieldRadioText\": \"女\",\n" +
            "      \"fieldRadioEnum\": \"WOMAN\",\n" +
            "      \"fieldText\": \"DEMO831\",\n" +
            "      \"status\": 1,\n" +
            "      \"statusText\": \"未发布\",\n" +
            "      \"statusColor\": \"#ff6600\",\n" +
            "      \"statusEnum\": \"NO_PUBLISH\",\n" +
            "      \"demoChild1s\": []\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 281,\n" +
            "      \"fieldProvince\": \"330000\",\n" +
            "      \"fieldProvinceName\": \"浙江\",\n" +
            "      \"gmtCreate\": \"2014-03-27T23:56:05+0800\",\n" +
            "      \"fieldRadio\": \"2\",\n" +
            "      \"fieldRadioText\": \"女\",\n" +
            "      \"fieldRadioEnum\": \"WOMAN\",\n" +
            "      \"fieldText\": \"DEMO7345\",\n" +
            "      \"status\": 1,\n" +
            "      \"statusText\": \"未发布\",\n" +
            "      \"statusColor\": \"#ff6600\",\n" +
            "      \"statusEnum\": \"NO_PUBLISH\",\n" +
            "      \"demoChild1s\": []\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 537,\n" +
            "      \"fieldProvince\": \"330000\",\n" +
            "      \"fieldProvinceName\": \"浙江\",\n" +
            "      \"gmtCreate\": \"2013-07-04T03:40:05+0800\",\n" +
            "      \"fieldRadio\": \"2\",\n" +
            "      \"fieldRadioText\": \"女\",\n" +
            "      \"fieldRadioEnum\": \"WOMAN\",\n" +
            "      \"fieldText\": \"DEMO32651\",\n" +
            "      \"status\": 1,\n" +
            "      \"statusText\": \"未发布\",\n" +
            "      \"statusColor\": \"#ff6600\",\n" +
            "      \"statusEnum\": \"NO_PUBLISH\",\n" +
            "      \"demoChild1s\": []\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 282,\n" +
            "      \"fieldProvince\": \"330000\",\n" +
            "      \"fieldProvinceName\": \"浙江\",\n" +
            "      \"gmtCreate\": \"2014-03-26T22:55:05+0800\",\n" +
            "      \"fieldRadio\": \"2\",\n" +
            "      \"fieldRadioText\": \"女\",\n" +
            "      \"fieldRadioEnum\": \"WOMAN\",\n" +
            "      \"fieldText\": \"张三2\",\n" +
            "      \"status\": 1,\n" +
            "      \"statusText\": \"未发布\",\n" +
            "      \"statusColor\": \"#ff6600\",\n" +
            "      \"statusEnum\": \"NO_PUBLISH\",\n" +
            "      \"demoChild1s\": []\n" +
            "    }       \n" +
            "  ],\n" +
            "  \"page\": {\n" +
            "    \"pageSize\": 15,\n" +
            "    \"pageNo\": 1,\n" +
            "    \"recordCount\": 269,\n" +
            "    \"pageCount\": 18,\n" +
            "    \"first\": 1,\n" +
            "    \"hasPre\": false,\n" +
            "    \"hasNext\": true,\n" +
            "    \"nextPage\": 2,\n" +
            "    \"prePage\": 1\n" +
            "  },\n" +
            "  \"success\": true\n" +
            "}";
}
