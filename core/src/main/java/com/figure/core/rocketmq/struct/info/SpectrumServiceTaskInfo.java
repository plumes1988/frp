package com.figure.core.rocketmq.struct.info;

import lombok.Data;

import java.util.List;

@Data
public class SpectrumServiceTaskInfo {

    /**
     * 任务ID 转码 收录 监制 频谱分析
     */
    private Integer taskID;

    /**
     * 对象ID 信号编号 设备编号
     */
    private String objectID;

    /**
     * 任务状态 0正常 1报警
     */
    private Integer taskState;

    /**
     * 报警数量
     */
    private Integer alarmCount;

    /**
     * 报警信息
     */
    private List<SpectrumServiceAlarmInfo> alarmInfoArray;
}
