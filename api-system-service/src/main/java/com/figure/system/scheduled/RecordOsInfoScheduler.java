package com.figure.system.scheduled;

import com.figure.core.helper.SystemStatus;
import com.figure.core.model.sys.SysOsStatusRecord;
import com.figure.core.service.sys.ISysOsStatusRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import oshi.hardware.NetworkIF;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Component
public class RecordOsInfoScheduler {

    private static final Logger log = LoggerFactory.getLogger(RecordOsInfoScheduler.class);

    @Resource
    private ISysOsStatusRecordService sysOsStatusRecordService;

    Long prev_BytesRecv = null;
    Long prev_BytesSend = null;

    Long prev_time = null;

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

    @Scheduled(fixedRate = 1000*60,initialDelay = 10000)
    public void recorde() {

        List<SysOsStatusRecord> records = new ArrayList<>();
        Date now = new Date();

        float cpuUsed = SystemStatus.getCpuUsed();

        SysOsStatusRecord record = new SysOsStatusRecord();
        record.setTime(now);
        record.setType(1);
        record.setValue(String.valueOf(cpuUsed));
        records.add(record);

        float[] jvmInfo = SystemStatus.getJVMInfo();

        record = new SysOsStatusRecord();
        record.setTime(now);
        record.setType(2);
        record.setValue(String.valueOf( (jvmInfo[0]-jvmInfo[1])*100/jvmInfo[0]));
        records.add(record);


        List<NetworkIF> networkIFs = SystemStatus.si.getHardware().getNetworkIFs();

        NetworkIF networkIF = null;

        for (int i = 0; i <networkIFs.size() ; i++) {
            if(networkIFs.get(i).getIPv4addr().length>0 && networkIFs.get(i).getIPv4addr()[0].startsWith("192.168.100.")){
                networkIF = networkIFs.get(i);
            }
        }

        if(networkIF!=null){

            long now_time = new Date().getTime();

            Long bytesSent = networkIF.getBytesSent();
            Long bytesReceived = networkIF.getBytesRecv();

            if(prev_time!=null){

                long diff_seconds = (now_time-prev_time)/1000;

                float speed_Recv = ((bytesReceived - prev_BytesRecv)/1024)/diff_seconds;

                record = new SysOsStatusRecord();
                record.setTime(now);
                record.setType(3);
                record.setValue(String.valueOf(speed_Recv));
                records.add(record);

                float speed_Send = ((bytesSent - prev_BytesSend)/1024)/diff_seconds;

                record = new SysOsStatusRecord();
                record.setTime(now);
                record.setType(4);
                record.setValue(String.valueOf(speed_Send));
                records.add(record);

                System.out.println("发送: " + formatBytes(bytesSent));
                System.out.println("接收: " + formatBytes(bytesReceived));

                if(prev_BytesSend!=null){
                    long speed_Recv_ = (bytesReceived - prev_BytesRecv)/diff_seconds;
                    long speed_Sent_ = (bytesSent - prev_BytesSend)/diff_seconds;
                    System.out.println("接收速度: " + formatBytes(speed_Recv_)+" /秒");
                    System.out.println("发送速度: " + formatBytes(speed_Sent_)+" /秒");
                }
                try {
                    Thread.sleep(5*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

            prev_BytesSend = bytesSent;

            prev_BytesRecv = bytesReceived;

            prev_time = now_time;
        }

        sysOsStatusRecordService.saveBatch(records);

    }

}




