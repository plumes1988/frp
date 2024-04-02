package com.figure.core.rocketmq.struct.info;

import lombok.Data;

@Data
public class RecordTaskStatusInfo {

    private Integer taskID;

    private Integer taskState;

    private Integer messageID;

    private String startTime;

    private Integer duration;

    private String alarmContent;

}
