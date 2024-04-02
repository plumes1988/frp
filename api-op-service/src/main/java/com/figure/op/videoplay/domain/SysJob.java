package com.figure.op.videoplay.domain;

import lombok.Data;

/**
 * @author lizhijie
 * @version 1.0
 * @date 2023/9/14 9:19
 */
@Data
public class SysJob {

    /**
     * 任务ID
     */
    private Integer jobId;
    /**
     * bean名称
     */
    private String beanName;
    /**
     * 方法名称
     */
    private String methodName;
    /**
     * 方法参数
     */
    private String methodParams;
    /**
     * cron表达式
     */
    private String cronExpression;
    /**
     * 状态（1正常 0暂停）
     */
    private Integer jobStatus;
    /**
     * 备注
     */
    private String remark;

}

