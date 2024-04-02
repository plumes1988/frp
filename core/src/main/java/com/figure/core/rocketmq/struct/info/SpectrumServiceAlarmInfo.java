package com.figure.core.rocketmq.struct.info;

import lombok.Data;

@Data
public class SpectrumServiceAlarmInfo {

    /**
     * 报警类型ID
     */
    private Integer alarmType;

    /**
     * 报警开始时间
     */
    private String startTime;

    /**
     * 报警时长
     */
    private Integer duration;

    /**
     * 报警状态 0恢复 1报警
     */
    private Integer alarmFlag;
    /**
     * 报警信息
     */
    private String alarmContent;
}
