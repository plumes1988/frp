package com.figure.core.model.record;

import lombok.Data;

@Data
public class CreateRecordFile {

    private String tableName;

    private String createTable = "(\n" +
            "  `fileId` int(11) NOT NULL AUTO_INCREMENT COMMENT '文件Id,自增',\n" +
            "  `serviceCode` varchar(255) DEFAULT NULL COMMENT '收录服务编号',\n" +
            "  `serviceIP` varchar(255) DEFAULT NULL COMMENT '收录服务IP',\n" +
            "  `mediaType` tinyint(2) DEFAULT NULL COMMENT '收录类型 0音频 1视频 2码流',\n" +
            "  `signalId` char(32) DEFAULT NULL COMMENT '频道Id或者频率Id',\n" +
            "  `transcodeRuleId` int(11) DEFAULT NULL COMMENT '转码规则Id，源码录制或者码流录制时为0',\n" +
            "  `filePath` varchar(255) DEFAULT NULL COMMENT '文件路径',\n" +
            "  `startTime` datetime DEFAULT NULL COMMENT '文件开始时间',\n" +
            "  `endTime` datetime DEFAULT NULL COMMENT '文件结束时间',\n" +
            "  `fileTime` int(11) DEFAULT NULL COMMENT '文件时长',\n" +
            "  `alarmRecord` tinyint(2) DEFAULT NULL COMMENT '报警触发标识 0未关联报警 1已关联报警',\n" +
            "  `isHLS` tinyint(2) DEFAULT NULL COMMENT '是否HLS 0不是HLS 1是HLS',\n" +
            "  `saveFlag` tinyint(2) DEFAULT NULL COMMENT '另存标识 每被另存为一次，标识+1，删除另存对象标识-1',\n" +
            "  `saveTime` int(11) DEFAULT NULL COMMENT '保存时长，单位 天',\n" +
            "  `createUserId` int(11) DEFAULT NULL COMMENT '创建人员ID',\n" +
            "  `createTime` datetime DEFAULT NULL COMMENT '创建时间',\n" +
            "  `updateUserId` int(11) DEFAULT NULL COMMENT '更新人员ID',\n" +
            "  `updateTime` datetime DEFAULT NULL COMMENT '更新时间',\n" +
            "  `isDelete` int(11) DEFAULT '0' COMMENT '状态：0:未删除、1:已删除、2:停用',\n" +
            "  PRIMARY KEY (`fileId`)\n" +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";

    public CreateRecordFile(String tableName) {
        this.tableName = tableName;
    }
}
