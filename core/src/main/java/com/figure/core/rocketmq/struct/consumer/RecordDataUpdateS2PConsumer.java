package com.figure.core.rocketmq.struct.consumer;

import com.figure.core.rocketmq.struct.MessageBase;
import lombok.Data;

@Data
public class RecordDataUpdateS2PConsumer extends MessageBase {

    /**
     * 录制服务编号
     */
    private String serviceCode;

    /**
     * 录制服务IP
     */
    private String serviceIP;

    /**
     * 录制类型 0:音频录制，1:视频录制，2:码流收录
     */
    private Integer mediaType;

    /**
     * 信源ID
     */
    private String signalID;

    /**
     * 转码规则ID
     */
    private Integer transcodeRuleID;

    /**
     * 是否开启HLS
     */
    private Integer isHLS;

    /**
     * 文件保存路径
     */
    private String filePath;

    /**
     * 文件URL
     */
    private String fileURL;

    /**
     * 文件开始时间
     */
    private String startTime;

    /**
     * 文件结束时间
     */
    private String endTime;

    /**
     * 文件时长 秒
     */
    private Integer fileTime;

    /**
     * 保存时间 天
     */
    private Integer saveTime;

}
