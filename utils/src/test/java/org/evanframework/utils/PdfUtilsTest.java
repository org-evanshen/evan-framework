package org.evanframework.utils;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static org.junit.Assert.fail;


public class PdfUtilsTest {

    private static final Executor executor = Executors.newFixedThreadPool(100);

    @Test
    public void testAddWaterSignStringStringString() throws PdfConvertException {
        PdfUtils.addWaterSign("d:/1428045733597.pdf", "d:/logo.png", "d:/2.pdf");
    }

    @Test
    public void testAddWaterSignStringStringStringDoubleDouble() {
        fail("Not yet implemented");
    }

    @Test
    public void testAddWaterSignStringStringStringStringString() {
        fail("Not yet implemented");
    }

    @Test
    public void testConcatPDF() throws PdfConvertException {
        PdfUtils.concat("d:/" + new Date().getTime() + ".pdf", "d:/1428045733597.pdf",
                "d:/H1W19X1T16XFCLCZ9SPZ_show.pdf");
    }

    @Test
    public void testPdfToSwf() throws PdfConvertException, IOException {
        //PdfUtils.pdfToSwf("d:/1.pdf", "d:/3.swf");

    }

    @Test
    public void testTemplateToPdf() throws PdfConvertException {
        long begin = new Date().getTime();

        Map<String, Object> map = new HashMap<String, Object>();

        map.put("date",new Date());
        map.put("number",0.00001);

        String font = "/System/Library/Fonts/STHeiti Light.ttc";

        PdfUtils.templateToPdf(font, "/Users/evan/Downloads/" + begin + ".pdf", map, "pdfTemplate.vm");

        long end = new Date().getTime();

        System.out.println(end - begin);

//        File dir = new File("d:/111");
//        if (dir.exists()) {
//            dir.delete();
//
//        }
//        dir.mkdir();
//
//        for (int i = 0; i < 1; i++) {
//            // executor.execute(new Thread1(i));
//            new Thread1(i).run();
//        }

    }

    @Test
    public void testPdfToImage() throws FileNotFoundException, PdfConvertException {
        List<String> list = PdfUtils.pdfToImage("d:/H1W19X1T16XFCLCZ9SPZ_show.pdf");
        System.out.println(list);
    }

    @Test
    public void testPdfToImage2() throws FileNotFoundException, PdfConvertException {
        FileInputStream fis = new FileInputStream("d:/1446284251352.pdf");

        List<String> list = PdfUtils.pdfToImage(fis, "1446284251352.pdf", "d:/");
        System.out.println(list);
    }

//    @Test
//    public void testHtmlToPdf() throws PdfConvertException {
//        long begin = new Date().getTime();
//        PdfUtils.htmlToPdf("","d:/QFE8ZF1TPQYYE0B00Q91_htmlCert.html", "d:/" + begin + ".pdf");
//        long end = new Date().getTime();
//        System.out.println(end - begin);
//    }

    class Thread1 implements Runnable {
        private int no;

        public Thread1(int no) {
            this.no = no;
        }

        public void run() {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("webresourcesUrl", "http://127.0.0.1:9001/store/aosp");
            long begin = new Date().getTime();
            try {
                PdfUtils.templateToPdf("", begin + "_" + no + ".pdf", map, "pdfTemplate.vm",
                        "/Users/evan/Downloads/");
            } catch (PdfConvertException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            long end = new Date().getTime();

            System.out.println(end - begin);
        }

        ;
    }


}
