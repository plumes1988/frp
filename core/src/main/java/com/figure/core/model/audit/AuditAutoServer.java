package com.figure.core.model.audit;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.figure.core.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 自动技审服务
 * </p>
 *
 * @author jobob
 * @since 2021-04-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("audit_auto_server")
public class AuditAutoServer extends BaseModel implements Serializable {

    /**
     * 服务编号
     */
    @TableId(value="auditServerCode",type= IdType.ASSIGN_UUID)
    private String id;

    /**
     * 服务名称
     */
    @TableField("auditServerName")
    private String auditServerName;

    /**
     * 角色ID
     */
    @TableField("auditServerIP")
    private String auditServerIP;

    /**
     * 最大任务数
     */
    @TableField("maxTaskNum")
    private Integer maxTaskNum;

    /**
     * 启用状态：0停用、1启用
     */
    @TableField("isEnable")
    private Integer isEnable;

    /**
     * 本地文件存储路径，从素材存储位置拷贝回本地后再进行分析
     */
    @TableField("localPath")
    private String localPath;

}
