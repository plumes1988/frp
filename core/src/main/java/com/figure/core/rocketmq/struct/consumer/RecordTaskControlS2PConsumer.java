package com.figure.core.rocketmq.struct.consumer;

import com.figure.core.rocketmq.struct.MessageBase;
import lombok.Data;

import java.util.List;

@Data
public class RecordTaskControlS2PConsumer extends MessageBase {

    /**
     * 服务编号
     */
    private String serviceCode;

    /**
     * 是否全部有效，0不是全部有效，1对全部任务有效
     */
    private Integer isAll;

    /**
     * 需要处理任务ID数组
     */
    private List<Integer> taskIDArray;

    /**
     * 执行状态 0正常执行，1执行故障
     */
    private Integer actionFlag;

    /**
     * 故障原因
     */
    private String alarmContent;

}
