package com.figure.core.model.alarm;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.figure.core.base.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * <p>
 * 播出事件信息
 * </p>
 *
 * @author feather
 * @date 2023-03-22 10:53:57
 */
@Data
@Accessors(chain = true)
@TableName("alarm_event_info")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "AlarmEventInfo", description = "播出事件信息")
public class AlarmEventInfo extends BaseModel {

    /**
     * 播出事件自增主键id
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("播出事件自增主键id")
    private Integer eventId;
    /**
     * 前端Id
     */
    @ApiModelProperty("前端Id")
    private Integer frontId;
    /**
     * 前端名称
     */
    @ApiModelProperty("前端名称")
    private String frontName;
    /**
     * 播出系统编号
     */
    @ApiModelProperty("播出系统编号")
    private String systemCode;
    /**
     * 播出系统名称
     */
    @ApiModelProperty("播出系统名称")
    private String systemName;
    /**
     * 开始时间
     */
    @ApiModelProperty("开始时间")
    private Date startTime;
    /**
     * 结束时间
     */
    @ApiModelProperty("结束时间")
    private Date endTime;
    /**
     * 持续时间
     */
    @ApiModelProperty("持续时间")
    private Integer duration;
    /**
     * 事件状态 0恢复 1报警中
     */
    @ApiModelProperty("事件状态 0恢复 1报警中")
    private Integer eventFlag;
    /**
     * 事件类型 0未判定 1停播 2劣播
     */
    @ApiModelProperty("事件类型 0未判定 1停播 2劣播")
    private Integer eventType;
    /**
     * 事件等级 0误报 1普通事故 2重大事故
     */
    @ApiModelProperty("事件等级 0误报 1普通事故 2重大事故")
    private Integer eventLevel;
    /**
     * 状态：0:正常、1:删除、2:停用
     */
    @ApiModelProperty("状态：0:正常、1:删除、2:停用")
    private Integer isDelete;

    /**
     * 关联报警数量
     */
    @TableField(exist = false)
    private int alarmCount = 1;

    /**
     * 最后更新时间
     */
    private Date lastUpdateTime;
}