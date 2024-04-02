package com.figure.core.rocketmq.struct.info;

import lombok.Data;

@Data
public class TranscodeTaskAlarmInfo {

    private Integer taskID;

    private Integer state;

    private Integer messageID;

    private String startTime;

    private Integer duration;

    private String alarmContent;
}
