package com.figure.op.system.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.figure.op.common.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author fsn
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("know_info")
public class KnowInfo extends BaseEntity {

    @TableId("knowId")
    private Integer knowId;

    @TableField("topic")
    private String topic;

    @TableField("knowKeywords")
    private String knowKeywords;

    @TableField("deviceId")
    private Integer deviceId;

    @TableField("device")
    private String device;

    @TableField("content")
    private String content;

    @TableLogic
    private Integer isDelete;

}
