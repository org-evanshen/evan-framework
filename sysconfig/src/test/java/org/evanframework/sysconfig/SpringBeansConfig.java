package org.evanframework.sysconfig;

import org.evanframework.sysconfig.impl.SysConfigImpl;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * Created by evan.shen on 2017/4/25.
 */
@Configuration
@EnableAutoConfiguration
public class SpringBeansConfig {
    @Bean(initMethod = "init")
    public SysConfig sysConfig(Environment environment) {
        SysConfigImpl sysConfig = new SysConfigImpl();
        sysConfig.setEnvironment(environment);
        return sysConfig;
    }
}
