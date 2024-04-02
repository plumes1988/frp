package com.figure.core.model.audit;

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
 * 自动技审任务
 * </p>
 *
 * @author jobob
 * @since 2021-04-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("audit_auto_task")
public class AuditAutoTask implements Serializable {

    /**
     * 任务ID
     */
    @TableId(value="taskId",type= IdType.AUTO)
    private Integer id;

    /**
     * 分析服务编号
     */
    @TableField("auditserverCode")
    private String auditserverCode;

    /**
     * 素材ID
     */
    @TableField("clipId")
    private Integer clipId;

    /**
     * 处理进度 0%~100%
     */
    @TableField("progress")
    private Integer progress;

    /**
     * 优先级：0普通、1较高、2最高
     */
    @TableField("priority")
    private String priority;

    /**
     * 创建时间
     */
    @TableField("createTime")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField("completionTime")
    private Date completionTime;

    /**
     * 任务状态：0:未处理、1处理中、2处理出错、3待重新执行、4处理完成、5取消处理
     */
    @TableField("taskStatus")
    private Integer taskStatus;

    @TableField(exist = false)
    private String auditServerName;

    @TableField(exist = false)
    private String auditServerIP;

    @TableField(exist = false)
    private String localPath;

    @TableField(exist = false)
    private String clipName;

    @TableField(exist = false)
    private String clipExtension;


}
