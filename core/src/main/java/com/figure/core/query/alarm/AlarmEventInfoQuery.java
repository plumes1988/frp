package com.figure.core.query.alarm;

import com.figure.core.model.alarm.AlarmEventInfo;
import com.figure.core.util.copycat.annotaion.Eq;
import com.figure.core.util.copycat.annotaion.Ge;
import com.figure.core.util.copycat.annotaion.Le;
import com.figure.core.util.copycat.query.AbstractQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class AlarmEventInfoQuery extends AbstractQuery<AlarmEventInfo> {

    @Eq
    @ApiModelProperty("播出事件自增主键id")
    private Integer eventId;

    @Eq
    @ApiModelProperty("前端Id")
    private Integer frontId;

    @Eq
    @ApiModelProperty("前端名称")
    private String frontName;

    @Eq
    @ApiModelProperty("播出系统编号")
    private String systemCode;

    @Eq
    @ApiModelProperty("播出系统名称")
    private String systemName;

    @Ge
    @ApiModelProperty("开始时间")
    private Date startTime;

    @Le
    @ApiModelProperty("结束时间")
    private Date endTime;

    @Eq
    @ApiModelProperty("持续时间")
    private Integer duration;

    @Eq
    @ApiModelProperty("事件状态 0恢复 1报警中")
    private Integer eventFlag;

    @Eq
    @ApiModelProperty("事件类型 0未判定 1停播 2劣播")
    private Integer eventType;

    @Eq
    @ApiModelProperty("事件等级 0误报 1普通事故 2重大事故")
    private Integer eventLevel;

    @Eq
    @ApiModelProperty("状态：0:正常、1:删除、2:停用")
    private Integer isDelete;

}