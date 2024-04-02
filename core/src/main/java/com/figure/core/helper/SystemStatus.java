package com.figure.core.helper;

import com.figure.core.util.StringUtils;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.NetworkIF;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;

import java.util.*;
import java.util.function.Consumer;

public class SystemStatus {

    public static final SystemInfo si = new SystemInfo();
    private static final HardwareAbstractionLayer hal = si.getHardware();
    private static final CentralProcessor cpu = hal.getProcessor();

    private static long[] oldTicks  =  new long[CentralProcessor.TickType.values().length];

    private long[][] oldProcTicks = new long[cpu.getLogicalProcessorCount()][CentralProcessor.TickType.values().length];

    private static float[] floatArrayPercent(double d) {
        float[] f = new float[1];
        f[0] = (float) (100d * d);
        return f;
    }

    private static double cpuData(CentralProcessor proc) {
        double d = proc.getSystemCpuLoadBetweenTicks(oldTicks);
        oldTicks = proc.getSystemCpuLoadTicks();
        return d;
    }

    private double[] procData(CentralProcessor proc) {
        double[] p = proc.getProcessorCpuLoadBetweenTicks(oldProcTicks);
        oldProcTicks = proc.getProcessorCpuLoadTicks();
        return p;
    }


    public static float byte2GB(float size_bytes){
        String cnt_size;

        float size_kb = size_bytes /1024;
        float size_mb = size_kb / 1024;
        float size_gb = size_mb / 1024 ;

        if (size_gb > 0){
            cnt_size = size_gb + " GB";
        }else if(size_mb > 0){
            cnt_size = size_mb + " MB";
        }else{
            cnt_size = size_kb + " KB";
        }
        return size_gb;
    }

    public static float getCpuUsed(){
        float[] f =  floatArrayPercent(cpuData(cpu));
        return f[0];
    }

    public static float[] getDiskInfo(){
        FileSystem fs = si.getOperatingSystem().getFileSystem();

        List<OSFileStore> fileStores = fs.getFileStores();

        float all_total = 0;
        float all_usable = 0;
        Set<Float> set = new HashSet<>();
        for (OSFileStore store : fileStores) {
            String storeName = store.getName();
            float usable = store.getUsableSpace();
            float total = store.getTotalSpace();
            if(set.contains(total)){
                continue;
            }
            set.add(total);
            all_usable += usable;
            all_total += total;
            System.out.println("storeName:"+storeName+";"+"total:"+byte2GB(total)+";"+"usable:"+byte2GB(usable));
        }
        return new float[]{all_total, all_usable};
    }

    public static float[] getJVMInfo(){
        Runtime runtime = Runtime.getRuntime();
        long total = runtime.totalMemory();
        long free = runtime.freeMemory();
        long memory = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("Total memory is megabytes: "
                + byte2GB(total));
        System.out.println("free memory is megabytes: "
                + byte2GB(free));
        System.out.println("Used memory is megabytes: "
                + byte2GB(memory));
        return new float[]{total, free};
    }

    public static float[] getMemoryInfo(){
        GlobalMemory memory = si.getHardware().getMemory();

        long total = memory.getTotal();

        long available = memory.getAvailable();

        System.out.println("Total memory is megabytes: "
                + byte2GB(total));
        System.out.println("free memory is megabytes: "
                + byte2GB(available));

        return new float[]{total, available};
    }

    public static float[] getNetInfo(){
        List<NetworkIF> networkIFs = si.getHardware().getNetworkIFs();

        Optional<NetworkIF> networkOptional = networkIFs.stream().filter(n -> StringUtils.isNotEmpty(n.getIPv4addr())).findAny();

        float cur_BytesRecv = 0;

        float cur_BytesSend = 0;

        if (networkOptional.isPresent()) {
            NetworkIF net = networkOptional.get();
            cur_BytesRecv = net.getBytesRecv();
            cur_BytesSend = net.getBytesSent();
        }

        return new float[]{cur_BytesRecv, cur_BytesSend};
    }

    public static String formatBytes(long bytes) {
        if (bytes < 1024) {
            return bytes + " B";
        } else if (bytes < 1024 * 1024) {
            double kb = bytes / 1024.0;
            return String.format("%.2f KB", kb);
        } else if (bytes < 1024 * 1024 * 1024) {
            double mb = bytes / (1024.0 * 1024);
            return String.format("%.2f MB", mb);
        } else {
            double gb = bytes / (1024.0 * 1024 * 1024);
            return String.format("%.2f GB", gb);
        }
    }

    public static void main(String[] args) throws InterruptedException {

        SystemInfo systemInfo = new SystemInfo();

        Long prev_bytesSent = null;

        Long prev_bytesReceived =  null;

        while (true){


            List<NetworkIF> networkIFs = systemInfo.getHardware().getNetworkIFs();

            NetworkIF networkIF = null;

            for (int i = 0; i <networkIFs.size() ; i++) {
                 if(networkIFs.get(i).getIPv4addr().length>0 && networkIFs.get(i).getIPv4addr()[0].startsWith("192.168.1.")){
                     networkIF = networkIFs.get(i);
                 }
            }

            if(networkIF!=null){
                Long bytesSent = networkIF.getBytesSent();
                Long bytesReceived = networkIF.getBytesRecv();



                System.out.println("发送: " + formatBytes(bytesSent));
                System.out.println("接收: " + formatBytes(bytesReceived));

                if(prev_bytesSent!=null){
                    long speed_Recv = (bytesReceived - prev_bytesReceived)/(5);
                    long speed_Sent = (bytesSent - prev_bytesSent)/(5);
                    System.out.println("接收速度: " + formatBytes(speed_Recv)+" /秒");
                    System.out.println("发送速度: " + formatBytes(speed_Sent)+" /秒");
                }
                Thread.sleep(5*1000);

                prev_bytesSent = bytesSent;

                prev_bytesReceived = bytesReceived;
            }

        }



    }
}
