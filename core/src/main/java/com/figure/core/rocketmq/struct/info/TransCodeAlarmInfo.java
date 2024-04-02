package com.figure.core.rocketmq.struct.info;

import lombok.Data;

@Data
public class TransCodeAlarmInfo {

    private Integer alarmType;

    private String startTime;

    private Integer duration;

    private Integer alarmFlag;

    private String alarmContent;
}
