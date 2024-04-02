package com.figure.core.query.logic;

import com.figure.core.model.logic.LogicMonitorunitInterfaceInfo;
import com.figure.core.util.copycat.annotaion.Eq;
import com.figure.core.util.copycat.annotaion.In;
import com.figure.core.util.copycat.query.AbstractQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class LogicMonitorunitInterfaceInfoQuery extends AbstractQuery<LogicMonitorunitInterfaceInfo> {

    @Eq
    @ApiModelProperty("切换器接口Id,唯一")
    private String interfaceId;


    @In(alias = "interfaceId")
    @ApiModelProperty("切换器接口Id,唯一")
    private List<String> interfaceIds;

    @Eq
    @ApiModelProperty("切换器编号")
    private String switchCode;

    @Eq
    @ApiModelProperty("切换器接口名称")
    private String interfaceName;

    @Eq
    @ApiModelProperty("接口类型 1输入 2输出")
    private Integer interfaceType;

    @Eq
    @ApiModelProperty("接口序号")
    private Integer interfaceSerialNo;

    @Eq
    @ApiModelProperty("接口优先级")
    private Integer interfaceRank;

    @Eq
    @ApiModelProperty("接口关联采集点")
    private Integer logicPosition;

    @Eq
    @ApiModelProperty("自动倒换模式 0关闭 1仅录播 2仅直播 3全启用")
    private Integer autoEmergency;

    @Eq
    @ApiModelProperty("自动回切模式 0关闭 1仅录播 2仅直播 3全启用")
    private Integer autoSwitchBack;

    @Eq
    @ApiModelProperty("异态判定模式")
    private Integer conditionMode;

    @Eq
    @ApiModelProperty("权重阈值")
    private Integer conditionGrade;

    @Eq
    @ApiModelProperty("状态：0:未删除、1:已删除、2:停用")
    private Integer isDelete;

}