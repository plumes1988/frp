package com.figure.system.scheduled;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import oshi.SystemInfo;
import oshi.hardware.NetworkIF;

import java.util.List;

//@Component
public class TestScheduler {

    public String formatBytes(long bytes) {
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

    @Scheduled(fixedRate = 10000000 * 1000, initialDelay = 20 * 1000)//间隔10秒
    private void method01() {
        System.out.println("TestScheduler---method01---->");
        //RocketMQProducerExample.test();
        SystemInfo systemInfo = new SystemInfo();

        Long prev_bytesSent = null;

        Long prev_bytesReceived =  null;

        while (true){


            List<NetworkIF> networkIFs = systemInfo.getHardware().getNetworkIFs();

            NetworkIF networkIF = null;

            for (int i = 0; i <networkIFs.size() ; i++) {
                if(networkIFs.get(i).getIPv4addr().length>0 && networkIFs.get(i).getIPv4addr()[0].startsWith("192.168.100.")){
                    networkIF = networkIFs.get(i);
                }
            }

            if(networkIF!=null){

                Long bytesSent = networkIF.getBytesSent();
                Long bytesReceived = networkIF.getBytesRecv();



                System.out.println("发送: " + formatBytes(bytesSent));
                System.out.println("接收: " + formatBytes(bytesReceived));

                if(prev_bytesSent!=null){
                    long speed_Recv = (bytesReceived - prev_bytesReceived)*1000/1024/(5);
                    long speed_Sent = (bytesSent - prev_bytesSent)*1000/1024/(5);
                    System.out.println("接收速度: " + formatBytes(speed_Recv)+" /秒");
                    System.out.println("发送速度: " + formatBytes(speed_Sent)+" /秒");
                }
                try {
                    Thread.sleep(5*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                prev_bytesSent = bytesSent;

                prev_bytesReceived = bytesReceived;
            }

        }
    }
}
