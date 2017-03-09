package com.ancun.core.log4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Layout;
import org.apache.log4j.RollingFileAppender;
import org.apache.log4j.helpers.QuietWriter;

/**
 * log4j appender, 既能设置buffer大小,也能定时刷新
 *
 * @author shenwei
 * @since 1.0.14
 * v1.0.16,20160619,改为继承RollingFileAppender
 */
public class TimedBufferedRollingFileAppender extends RollingFileAppender {
    private static final int CHECK_INTERVAL = 3 * 1000;//默认且最小刷新时间
    private static final Object appendersLock = new Object();
    private static final List<TimedBufferedRollingFileAppender> appenders = new ArrayList<TimedBufferedRollingFileAppender>();
    private static final int DEFAULT_BUFFER_SIZE = 1024; // 默认1K的buffer

    static {
        Thread logFlushThread = new Thread(new Runnable() {
            public void run() {
                while (true) {
                    synchronized (appendersLock) {
                        for (TimedBufferedRollingFileAppender appender : appenders) {
                            appender.flush();
                        }
                    }
                    try {
                        Thread.sleep(CHECK_INTERVAL);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }

                }
            }
        }, "TimedBufferedRollingFileAppender-timed-flush");

        logFlushThread.setDaemon(true);
        logFlushThread.start();
    }

    private int flushInterval = CHECK_INTERVAL; // 默认的定时刷新间隔(秒)
    private Date flushTime = new Date(); // 下一次刷新的时间点


    public TimedBufferedRollingFileAppender() {
        super();
        init();
    }

    public TimedBufferedRollingFileAppender(Layout layout, String filename) throws IOException {
        super(layout, filename);
        init();
    }

    public TimedBufferedRollingFileAppender(Layout layout, String filename, boolean append) throws IOException {
        super(layout, filename, append);
        init();
    }

    private void init() {
        this.setBufferedIO(true);
        this.setBufferSize(DEFAULT_BUFFER_SIZE);
        this.setImmediateFlush(false);
        synchronized (appendersLock) {
            appenders.add(this);
            Runtime.getRuntime().addShutdownHook(new Log4jHockThread(this));
        }
    }

    private void flush() {
        if (!(new Date()).after(flushTime)) {
            return;
        }
        if (!checkEntryConditions()) {
            return;
        }
        qw.flush();
        this.flushTime = new Date(System.currentTimeMillis() + this.flushInterval);
    }

    public void setFlushInterval(int flushInterval) {
        flushInterval = flushInterval * 1000;//转换成秒
        if (flushInterval < CHECK_INTERVAL) {// 至少CHECK_INTERVAL秒
            flushInterval = CHECK_INTERVAL;
        }
        this.flushInterval = flushInterval;
    }

    // 本appender必须是bufferedIO, 否则没意义
    @Override
    public boolean getBufferedIO() {
        return true;
    }

    @Override
    public void setBufferedIO(boolean bufferedIO) {
        super.setBufferedIO(true);
    }

    @Override
    public boolean getImmediateFlush() {
        return false;
    }

    @Override
    public void setImmediateFlush(boolean value) {
        super.setImmediateFlush(false);
    }

    /**
     * 程序关闭前执行
     */
    private class Log4jHockThread extends Thread {
        private TimedBufferedRollingFileAppender appender;

        public Log4jHockThread(TimedBufferedRollingFileAppender appender) {
            this.appender = appender;
        }

        @Override
        public void run() {
            QuietWriter qw = this.appender.qw;
            if (qw != null) {
                qw.flush();
            }
        }
    }

}
