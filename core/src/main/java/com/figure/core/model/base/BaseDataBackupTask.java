package com.figure.core.model.base;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.figure.core.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author jobob
 * @since 2021-05-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("base_data_backup_task")
public class BaseDataBackupTask extends BaseModel implements Serializable  {

    /**
     * 任务ID
     */
    @TableId(value = "taskId", type = IdType.AUTO)
    private Integer id;

    /**
     * 任务名称
     */
    @TableField("taskName")
    private String taskName;

    /**
     * 数据备份对象，逗号分隔表名
     */
    @TableField("taskObject")
    private String taskObject;

    /**
     * 模式 0，单次；3每月；4每年
     */
    @TableField("taskMode")
    private Integer taskMode;

    /**
     * 数据保留天数
     */
    @TableField("dataRetentionTime")
    private Integer dataRetentionTime;

    /**
     * 备份保留天数
     */
    @TableField("backRetentionTime")
    private Integer backRetentionTime;

    /**
     * 每月第几日执行
     */
    @TableField("dayOfMonth")
    private Integer dayOfMonth;

    /**
     * 每年重复执行日期
     */
    @TableField("dateOfYear")
    private String dateOfYear;

    /**
     * 执行时间
     */
    @TableField("excuteTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date excuteTime;



    @TableField(exist = false)
    private String dateOfYear_day;

    @TableField(exist = false)
    private String dateOfYear_month;

}
