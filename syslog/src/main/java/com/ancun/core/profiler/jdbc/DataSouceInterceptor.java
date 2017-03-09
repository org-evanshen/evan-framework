package com.ancun.core.profiler.jdbc;

import java.lang.reflect.Proxy;
import java.sql.Connection;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @author fish
 */
public class DataSouceInterceptor implements MethodInterceptor {

    private static final String getConnection = "getConnection";

    private static final Class<?>[] parameter = new Class[]{Connection.class};

    public Object invoke(MethodInvocation invocation) throws Throwable {
        if (invocation.getMethod().getName().equals(getConnection)) {
            Connection get = (Connection) invocation.proceed();
            return Proxy.newProxyInstance(Connection.class.getClassLoader(),
                    parameter, new ConnectionHandler(get));
        }
        return invocation.proceed();
    }
}
