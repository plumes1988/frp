package com.figure.core.model.notice;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 通知模板
 * </p>
 *
 * @author jobob
 * @since 2022-09-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("notice_message_templete")
public class NoticeMessageTemplete implements Serializable {


    @TableId(value = "templeteId", type = IdType.AUTO)
    private Integer templeteId;

    @TableField("name")
    private String name;

    @TableField("config")
    private String config;


}
