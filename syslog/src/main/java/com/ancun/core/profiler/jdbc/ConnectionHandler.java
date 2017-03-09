package com.ancun.core.profiler.jdbc;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import com.ancun.core.profiler.TimeProfiler;

/**
 * @author fish
 */
public final class ConnectionHandler implements InvocationHandler {

    private static final Set<String> traceMethods = new HashSet<String>() {
        private static final long serialVersionUID = -6072495675172127595L;

        {
            this.add("nativeSQL");
            this.add("prepareCall");
            this.add("prepareStatement");
        }
    };

    private static final Class<?>[] parameter = new Class[]{Statement.class};

    private static final String statementMethod = "createStatement";

    private Connection target;

    public ConnectionHandler(Connection target) {
        super();
        this.target = target;
    }

    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {
        String methodName = method.getName();
        if (traceMethods.contains(methodName)) {
            String sql = (String) args[0];
            TimeProfiler.addTrace(new StringBuilder(methodName).append(": ")
                    .append(sql.trim()).toString());
            return method.invoke(target, args);
        }

        if (methodName.equals(statementMethod)) {
            Statement statement = (Statement) method.invoke(target, args);
            return Proxy.newProxyInstance(Statement.class.getClassLoader(),
                    parameter, new StatementHandler(statement));
        }

        return method.invoke(target, args);
    }
}
