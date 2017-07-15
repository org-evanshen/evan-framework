package org.evanframework.syslog.jdbc;

import org.evanframework.syslog.TimeProfiler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

/**
 * @author fish
 */
public class StatementHandler implements InvocationHandler {

    private static final Set<String> traceMethods = new HashSet<String>() {
        private static final long serialVersionUID = 5833267939018472770L;

        {
            this.add("addBatch");
            this.add("execute");
            this.add("executeQuery");
            this.add("executeUpdate");
        }
    };

    private Statement target;

    public StatementHandler(Statement target) {
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
        return method.invoke(target, args);
    }

}
