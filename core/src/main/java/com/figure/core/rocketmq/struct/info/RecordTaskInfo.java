package com.figure.core.rocketmq.struct.info;

import lombok.Data;

@Data
public class RecordTaskInfo {

    /**
     * 收录类型 0音频录制 1视频录制 2码流录制
     */
    private Integer mediaType;

    /**
     * 任务模式：0全程收录、1定时收录、2报警触发录制、3节目单触发录制、4手动录制
     */
    private Integer taskMode;

    /**
     * 任务ID
     */
    private Integer taskID;

    /**
     * 信源ID
     */
    private String signalID;

    /**
     * 信源URL
     */
    private String signalURL;

    /**
     * 信号接收IP
     */
    private String receiveIP;

    /**
     * 转码规则Id
     */
    private Integer transcodeRuleID;

    /**
     * 信号码率
     */
    private Integer bitRate;

    /**
     * 是否开启HLS
     */
    private Integer isHLS;

    /**
     * 保存时间（天）
     */
    private Integer saveTime;

    /**
     * 文件时长（秒）
     */
    private Integer fileTime;

    public RecordTaskInfo(com.figure.core.model.record.RecordTaskInfo recordTaskInfo) {
        this.mediaType = recordTaskInfo.getMediaType();
        this.taskMode = recordTaskInfo.getRecordMode();
        this.taskID = recordTaskInfo.getRecordTaskId();
        this.signalID = recordTaskInfo.getSignalId();
        this.signalURL = recordTaskInfo.getSignalURL();
        this.receiveIP = recordTaskInfo.getServiceIP();
        this.transcodeRuleID = recordTaskInfo.getTranscodeRuleId();
        this.bitRate = recordTaskInfo.getBitRate();
        this.isHLS = recordTaskInfo.getIsHLS();
        this.saveTime = recordTaskInfo.getSaveTime();
    }
}
