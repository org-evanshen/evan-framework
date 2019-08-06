package com.yourong;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by J.W on 2017/3/13.
 */
public class OSSUtilTest {

    //@Test
    public void testOSS() throws IOException {
        OSSUtil ossUtil = new OSSUtil("LfpHr00gcIxuhxfe", "ARyibWNAslko0IvRZJpcr353r3XMZ2");
        InputStream content = new FileInputStream("F:\\testText.txt");
        String url = ossUtil.uploadAttachmentToOSS("testText", content, null, "yrcontract-support");
        System.out.println(url);
    }
}
