package com.figure.core.rocketmq.struct.consumer;

import com.figure.core.rocketmq.struct.MessageBase;
import lombok.Data;

import java.util.List;

@Data
public class SignalAlarmCheckS2PConsumer extends MessageBase {

    /**
     * 请求报警服务编号
     */
    private String serviceCode;

    /**
     * 是否是全部前端 0不是全部前端 1全部前端
     */
    private Integer isAllFront;

    /**
     * 前端Id数组
     */
    private List<Integer> frontIDArray;

    /**
     * 是否是全部播出系统 0不是全部播出系统 1全部播出系统
     */
    private Integer isAllSystem;

    /**
     * 播出系统编号数组
     */
    private List<String> systemCodeArray;

}
