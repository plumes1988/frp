package com.figure.core.model.notice;

import com.baomidou.mybatisplus.annotation.TableField;
import com.figure.core.model.device.DeviceInfo;
import com.figure.core.model.signal.SignalChannelInfo;
import com.figure.core.model.signal.SignalTypeInfo;
import com.figure.core.model.sys.SysUserInfo;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class NoticeStruct {

    /**
     * 报警对象类型
     */
    private String alarmObjectType;

    /**
     * 设备信息
     */
    private Map<String, DeviceInfo> deviceInfoMap;

    /**
     * 信号类型信息
     */
    private Map<Integer, SignalTypeInfo> signalTypeInfoMap;

    /**
     * 频道信息
     */
    private Map<String, SignalChannelInfo> signalChannelInfoMap;

    private List<String> deviceCodeList;

    private List<String> indicatorCodeList;

    private List<String> channelIdList;

    private List<String> alarmTypeNumberList;



    /**
     * 发送用户信息
     */
    private List<SysUserInfo> sysUserInfoList;

    /**
     * 通知媒介
     */
    private List<NoticeAgent> noticeAgentList;


    private List<NoticeAlarmTriggerRule.Setting> settings;
}
