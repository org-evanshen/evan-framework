package com.ancun.core.profiler.report;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.Connection;

/**
 * @author fish
 */
public class ConnectionCloseHandler implements InvocationHandler {

    private JDBCConnetionHolderReport report;

    private Connection target;

    public ConnectionCloseHandler(Connection target, JDBCConnetionHolderReport report) {
        this.target = target;
        this.report = report;
    }

    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {
        String methodName = method.getName();
        if (methodName.equals("close")) {
            report.connectionCloseCall(Thread.currentThread());
        }
        return method.invoke(target, args);
    }
}
