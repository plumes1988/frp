package com.figure.core.model.record;

import com.figure.core.base.BaseModel;
import lombok.Data;

import java.util.Date;

@Data
public class RecordPlayback extends BaseModel {

    /**
     * 信号Id
     */
    private String signalId;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 请求类型 0非HLS 1HLS
     */
    private Integer type = 1;

    /**
     * 拼接名称
     */
    private String mergeName;

    /**
     * 录制任务Id
     */
    private Integer recordTaskId;

    /**
     * 是否是报警录像请求
     */
    private Integer isAlarm = 0;
}
