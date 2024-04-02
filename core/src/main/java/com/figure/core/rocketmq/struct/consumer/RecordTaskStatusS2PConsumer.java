package com.figure.core.rocketmq.struct.consumer;

import com.figure.core.rocketmq.struct.MessageBase;
import com.figure.core.rocketmq.struct.info.RecordAlarmInfo;
import com.figure.core.rocketmq.struct.info.RecordTaskStatusInfo;
import lombok.Data;

import java.util.List;

@Data
public class RecordTaskStatusS2PConsumer extends MessageBase {

    private String serviceCode;

    /**
     * 服务运行状态 0正常 1警告
     */
    private String serviceStatus;

    private Integer bitRate;

    private List<RecordAlarmInfo> alarmInfoArray;

    private List<RecordTaskStatusInfo> taskInfoArray;

}
