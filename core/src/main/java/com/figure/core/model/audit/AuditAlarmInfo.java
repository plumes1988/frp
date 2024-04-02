package com.figure.core.model.audit;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

import com.figure.core.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 技审异态管理
 * </p>
 *
 * @author jobob
 * @since 2021-04-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("audit_alarm_info")
public class AuditAlarmInfo extends BaseModel implements Serializable {

    public AuditAlarmInfo(){
        super();
    }

    public AuditAlarmInfo(Integer id, String name, Integer parentId) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
    }

    /**
     * id
     */
    @TableId(value="id",type= IdType.AUTO)
    private Integer id;

    /**
     * 报警编号
     */
    @TableField("alarmId")
    private Integer alarmId;

    /**
     * 报警名称
     */
    @TableField("alarmName")
    private String alarmName;

    /**
     * 异态来源：0其他、1视频、2音频、3编码
     */
    @TableField("alarmFrom")
    private Integer alarmFrom;

    /**
     * 阈值
     */
    @TableField("alarmThreshold")
    private Integer alarmThreshold;

    /**
     * 比较模式：0等于、1大于、2小时
     */
    @TableField("compareMode")
    private Integer compareMode;

    /**
     * 判定延时
     */
    @TableField("alarmDelay")
    private Integer alarmDelay;

    /**
     * 启用状态：0停用、1启用
     */
    @TableField("isEnable")
    private Integer isEnable;


    /**
     * 是否删除：0未删除、1已删除
     */
    @TableField("isDelete")
    private Integer isDelete;


    @TableField(exist = false)
    private String name;

    @TableField(exist = false)
    private Integer parentId;


}
