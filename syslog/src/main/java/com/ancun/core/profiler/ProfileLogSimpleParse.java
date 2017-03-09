package com.ancun.core.profiler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfileLogSimpleParse {

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        if (args == null || args.length == 0) {
            printHelp();
            return;
        }
        String logFileName = args[0];
        int top = 0;
        if (args.length > 1) {
            top = Integer.parseInt(args[1]);
        }
        String outputFileName = null;
        if (args.length > 2) {
            outputFileName = args[2];
        }

        File logFile = new File(logFileName);
        if (!logFile.exists()) {
            printHelp();
            return;
        }

        List<OnceTime> all = parse(logFile);
        Collections.sort(all);

        if (top > 0) {
            if (top > all.size()) {
                top = all.size();
            }
            all = all.subList(0, top);
        }

        printResult(all, outputFileName);
    }

    private static List<OnceTime> parse(File logFile) throws Exception {
        Map<String, OnceTime> back = new HashMap<String, OnceTime>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(logFile));
            String line;
            int lineNum = 0;
            while ((line = br.readLine()) != null) {
                lineNum++;
                line = line.trim();
                if (line.startsWith(TimeProfiler.SummaryHeader)) {
                    String left = line.substring(TimeProfiler.SummaryHeader
                            .length());
                    int timeEnd = left
                            .indexOf(TimeProfiler.SummaryHeaderTimeEnd);
                    if (timeEnd == -1) {
                        System.err.println("format error in line:" + lineNum
                                + " {" + line + "}");
                        continue;
                    }
                    long time = Long.parseLong(left.substring(0, timeEnd));
                    String runName = null;
                    left = left.substring(
                            left.indexOf(TimeProfiler.In)
                                    + TimeProfiler.In.length()).trim();
                    int spaceIndex = left.indexOf(' ');
                    if (spaceIndex == -1) {
                        runName = left;
                    } else {
                        runName = left.substring(0, spaceIndex);
                    }
                    OnceTime get = back.get(runName);
                    if (get == null) {
                        OnceTime one = new OnceTime();
                        one.name = runName;
                        one.count = 1;
                        one.costSum = time;
                        back.put(runName, one);
                    } else {
                        get.count++;
                        get.costSum += time;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                br.close();
            }
        }
        return new ArrayList<OnceTime>(back.values());
    }

    private static void printResult(List<OnceTime> result, String outputFileName)
            throws Exception {
        Writer out = null;
        if (outputFileName == null) {
            out = new OutputStreamWriter(System.out);
        } else {
            out = new FileWriter(outputFileName);
        }

        for (OnceTime one : result) {
            out.append(one.toString()).append("\r\n");
        }

        out.close();
    }

    private static void printHelp() {
        System.out
                .println("parameters: logfileURL [topN] [parseOutputFileURL]");

    }

    private static class OnceTime implements Comparable<OnceTime> {
        private String name;// ����
        private long count;// �ܵ�ִ�д���
        private long costSum;// �ܵ����ʱ��

        private long getAverageCost() {
            if (count == 0 || costSum == 0) {
                return 0;
            }
            return costSum / count;
        }

        public int compareTo(OnceTime o) {
            long thisTime = this.getAverageCost();
            long thatTime = o.getAverageCost();
            if (thisTime < thatTime) {
                return 1;
            }
            if (thisTime > thatTime) {
                return -1;
            }
            return 0;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("url:[").append(name).append("] ƽ����ʱ:[").append(
                    getAverageCost()).append("] ִ�д���:[").append(count).append(
                    ']');
            return sb.toString();
        }

    }

}
