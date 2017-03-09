package com.ancun.core.profiler.report;

/**
 * @author fish
 */
public class JDBCConnetionHolder {
    private Thread holder;

    private long startTime;

    public JDBCConnetionHolder(Thread t) {
        super();
        this.holder = t;
        this.startTime = System.currentTimeMillis();
    }

    public long getHoldingTimeMillis() {
        return System.currentTimeMillis() - this.startTime;
    }

    public long getHoldingTimeSeconds() {
        return getHoldingTimeMillis() / 1000;
    }

    public StackTraceElement[] getThreadStackTrace() {
        return this.holder.getStackTrace();
    }

    public Thread getHolder() {
        return holder;
    }

    public void setHolder(Thread holder) {
        this.holder = holder;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }
}
