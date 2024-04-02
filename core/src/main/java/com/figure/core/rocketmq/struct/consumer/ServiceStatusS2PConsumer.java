package com.figure.core.rocketmq.struct.consumer;

import com.figure.core.rocketmq.struct.MessageBase;
import com.figure.core.rocketmq.struct.info.SpectrumServiceAlarmInfo;
import com.figure.core.rocketmq.struct.info.SpectrumServiceTaskInfo;
import lombok.Data;

import java.util.List;

@Data
public class ServiceStatusS2PConsumer extends MessageBase {

    /**
     * 服务编号
     */
    private String serviceCode;

    /**
     * 服务IP
     */
    private String IP;

    /**
     * 服务状态0正常 1报警  服务模块不上报消息就表示离线
     */
    private Integer serviceState;

    /**
     * 报警数量
     */
    private Integer alarmCount;

    /**
     * 报警信息
     */
    private List<SpectrumServiceAlarmInfo> alarmInfoArray;

    /**
     * 任务数量
     */
    private Integer taskCount;

    /**
     * 任务状态信息
     */
    private List<SpectrumServiceTaskInfo> taskInfoArray;
}
