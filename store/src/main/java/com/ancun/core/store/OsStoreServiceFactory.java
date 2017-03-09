package com.ancun.core.store;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * OsStoreServiceFactory
 *
 * @author <a href="mailto:shenwei@ancun.com">Evan.shen</a>
 * @date 16/9/16
 */
public class OsStoreServiceFactory implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    public OsStoreService getInstance(EnumCloudType cloudType) {
        OsStoreService osStoreService = (OsStoreService) applicationContext.getBean(cloudType.getName() + "StoreHelper");
        return osStoreService;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
