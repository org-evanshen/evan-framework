package com.ancun.core.profiler.report;

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
    private JDBCConnetionHolderReport report;

    public JDBCConnetionHolderReport getReport() {
        return report;
    }

    public void setReport(JDBCConnetionHolderReport report) {
        this.report = report;
    }

    public Object invoke(MethodInvocation invocation) throws Throwable {
        if (invocation.getMethod().getName().equals(getConnection)) {
            report.getConnectionCall(Thread.currentThread());
            Connection get = (Connection) invocation.proceed();
            return Proxy.newProxyInstance(Connection.class.getClassLoader(),
                    parameter, new ConnectionCloseHandler(get, report));
        }
        return invocation.proceed();
    }
}
