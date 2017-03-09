package com.ancun.core.profiler;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * ���ܼ�⣬����ָ��ֵ�����?������¼�� ����TimeProfiler ��log
 * level��Ϊinfo���������Ҫ��Ϣ�������Ϊdebug���������ϸ��Ϣ
 *
 * @author fish
 */

public final class TimeProfiler {

    public final static String NewLine = "\r\n";
    public final static String SummaryHeader = "Entire running time(millis)=[";
    public final static String SummaryHeaderTimeEnd = "]";
    public final static String In = "in";
    private static final Logger logger = LoggerFactory.getLogger(TimeProfiler.class);
    private static final int Default_Threshold = 10;// default value
    private static final ThreadLocal<TimeProfiler> local = new ThreadLocal<TimeProfiler>();
    private String id;
    private int threshold;
    private long begin = 0;
    private long end = 0;
    private List<ProfilerTask> tasks = new ArrayList<ProfilerTask>();
    private ProfilerTask currentTask;

    /**
     * @param id
     * @param threshold �������ֵ�����н�����¼
     */
    private TimeProfiler(String id, int threshold) {
        this.id = id;
        this.threshold = threshold;
    }

    /**
     * ��ǰ�����Ƿ�����profile
     */
    public static boolean isProfileEnable() {
        return logger.isInfoEnabled() || logger.isDebugEnabled();
    }

    /**
     * ��ʼһ��profiler����
     *
     * @param name      �������
     * @param threshold �������ֵ�Ľ�����¼
     */
    public static TimeProfiler start(String name, int threshold) {
        // if (local.get() != null) {
        // throw new IllegalStateException(
        // "Can't start TimeProfiler: it's already running");
        // }
        TimeProfiler profiler = new TimeProfiler(name, threshold);
        local.set(profiler);
        profiler.begin = System.currentTimeMillis();
        return profiler;
    }

    /**
     * ʹ��ȱʡֵ��ʼһ��profiler����
     *
     * @param name
     */
    public static TimeProfiler start(String name) {
        return start(name, Default_Threshold);
    }

    /**
     * ��ʼһ�����������û������profile����˷���������profile����
     *
     * @param string ���������
     */
    public static void beginTask(String string) {
        TimeProfiler profiler = local.get();
        boolean iFireIt = false;
        if (profiler == null) {
            profiler = start(string);
            iFireIt = true;
        }
        ProfilerTask task = profiler.new ProfilerTask(string);
        task.fire = iFireIt;
        profiler.addTask(task);
        task.start();
    }

    /**
     * ����ǰ������
     */
    public static void endTask() {
        TimeProfiler profiler = local.get();
        if (profiler == null) {
            throw new IllegalStateException(
                    "Can't end ProfilerTask: TimeProfiler is not running");
        }
        if (profiler.currentTask == null) {
            throw new IllegalStateException(
                    "Can't end ProfilerTask: currentTask is null");
        }
        ProfilerTask current = profiler.currentTask;
        current.end();
        boolean iFireIt = current.fire;
        profiler.currentTask = profiler.currentTask.getParentTask();
        if (iFireIt) {
            profiler.release();
        }
    }

    /**
     * ����һ��������Ϣ������һ��sql ע�⣺Ŀǰ�����������޷�trace
     *
     * @param string
     */
    public static void addTrace(String string) {
        if (string == null) {
            return;
        }
        TimeProfiler profiler = local.get();
        if (profiler == null) {
            return;
        }
        profiler.taskJoin(profiler.new TraceTask(string));
    }

    /**
     * ���������
     */
    public void release() {
        release(null);
    }

    /**
     * ��������񣬲����ڴ�ȷ����������
     *
     * @param name
     */
    public void release(String name) {
        local.remove();
        this.end = System.currentTimeMillis();
        logProfiler(name);
    }

    private void logProfiler(String name) {
        if (getTotalTimeMillis() < this.threshold) {
            return;
        }
        if (logger.isDebugEnabled()) {
            logger.debug(name == null ? prettyPrint() : prettyPrint(name));
        } else {
            if (logger.isInfoEnabled()) {
                logger.info(name == null ? shortSummary() : shortSummary(name));
            }

        }
    }

    private long getTotalTimeMillis() {
        return end - begin;
    }

    private String shortSummary(String name) {
        StringBuffer sb = new StringBuffer();
        sb.append("TimeProfiler output:").append(NewLine);
        sb.append(SummaryHeader).append(getTotalTimeMillis()).append(
                SummaryHeaderTimeEnd);
        sb.append(' ').append(In).append(' ').append(name);
        return sb.toString();
    }

    private String shortSummary() {
        return this.shortSummary(this.id);
    }

    private String prettyPrint(String name) {
        StringBuffer sb = new StringBuffer(shortSummary(name));
        for (ProfilerTask task : this.tasks) {
            sb.append(NewLine).append(task.prettyPrint(this.begin));
        }
        return sb.toString();
    }

    private String prettyPrint() {
        StringBuffer sb = new StringBuffer(shortSummary());
        for (ProfilerTask task : this.tasks) {
            sb.append(NewLine).append(task.prettyPrint(this.begin));
        }
        return sb.toString();
    }

    private void addTask(ProfilerTask task) {
        taskJoin(task);
        this.currentTask = task;
    }

    private void taskJoin(ProfilerTask task) {
        if (this.currentTask == null) {// û�а����һ�������������У�Ҳ����һ����������
            this.tasks.add(task);
        } else {
            this.currentTask.addChildTask(task);
        }
    }

    private class ProfilerTask {

        protected ProfilerTask parent;

        protected List<ProfilerTask> children;

        protected String name;

        protected long start;

        protected long end;

        protected int deep = 1;

        protected boolean fire = false;// �Ƿ�����task������time Profiler

        public ProfilerTask(String n) {
            this.name = n;
        }

        public StringBuffer prettyPrint(long profileStart) {
            StringBuffer sb = new StringBuffer();
            sb.append("Task:").append(this.name);
            sb.append(" running time(millis)");
            sb.append('[').append(this.start - profileStart).append("->")
                    .append(this.end - profileStart).append(']');
            sb.append('=').append(getTotalTimeMillis());
            double rate = (double) this.getTotalTimeMillis()
                    / TimeProfiler.this.getTotalTimeMillis();
            rate = rate * 100;
            sb.append(" ").append((int) rate).append('%');
            if (this.children != null) {
                for (ProfilerTask child : this.children) {
                    sb.append(NewLine);
                    for (int i = 0; i < this.deep; i++) {
                        sb.append("--");
                    }
                    sb.append(child.prettyPrint(profileStart));
                }
            }
            return sb;
        }

        public void start() {
            start = System.currentTimeMillis();
        }

        public void addChildTask(ProfilerTask task) {
            if (children == null) {
                children = new ArrayList<ProfilerTask>();
            }
            children.add(task);
            task.parent = this;
            task.deep = this.deep + 1;
        }

        public void end() {
            end = System.currentTimeMillis();
        }

        public ProfilerTask getParentTask() {
            return parent;
        }

        public long getTotalTimeMillis() {
            return end - start;
        }
    }

    private class TraceTask extends ProfilerTask {
        public TraceTask(String n) {
            super(n);
        }

        @Override
        public StringBuffer prettyPrint(long profileStart) {
            StringBuffer sb = new StringBuffer();
            sb.append("Trace:").append(this.name);
            if (this.children != null) {
                for (ProfilerTask child : this.children) {
                    sb.append(NewLine);
                    for (int i = 0; i < this.deep; i++) {
                        sb.append("--");
                    }
                    sb.append(child.prettyPrint(profileStart));
                }
            }
            return sb;
        }
    }
}
