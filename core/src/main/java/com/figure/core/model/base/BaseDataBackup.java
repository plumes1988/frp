package com.figure.core.model.base;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;
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
@TableName("base_data_backup")
public class BaseDataBackup implements Serializable {


    /**
     * 主键
     */
    @TableId(value = "backupId", type = IdType.AUTO)
    private Integer id;

    /**
     * 数据备份对象，逗号分隔表名
     */
    @TableField("backupObject")
    private String backupObject;

    /**
     * 归档时间
     */
    @TableField("backupTime")
    private Date backupTime;

    /**
     * 归档文件名称
     */
    @TableField("backupFileName")
    private String backupFileName;

    /**
     * 备份模式：0 手动 ，1 自动
     */
    @TableField("backupMode")
    private Integer backupMode;

    /**
     * 执行人
     */
    @TableField("excuteUserId")
    private Integer excuteUserId;


    @TableField(exist = false)
    private String excuteUserName;


    /**
     * 执行时间
     */
    @TableField("excuteTime")
    private Date excuteTime;

    /**
     * 完成时间
     */
    @TableField("finishTime")
    private Date finishTime;

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
     * 执行状态：0未执行，1成功，2失败
     */
    @TableField("excuteStatus")
    private Integer excuteStatus;

    /**
     * 备份数据时间区间:开始时间
     */
    @TableField("startTime")
    private String startTime;

    /**
     * 备份数据时间区间:开始时间
     */
    @TableField("endTime")
    private String endTime;

}
