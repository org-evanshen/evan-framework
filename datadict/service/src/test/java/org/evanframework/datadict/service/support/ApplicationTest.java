package org.evanframework.datadict.service.support;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ApplicationTest {
    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(ApplicationTest.class);
        builder.web(true);
        ApplicationContext ctx = builder.run(args);

    }
}
