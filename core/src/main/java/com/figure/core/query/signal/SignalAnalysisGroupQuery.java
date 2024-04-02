package com.figure.core.query.signal;

import com.figure.core.model.signal.SignalAnalysisGroup;
import com.figure.core.util.copycat.annotaion.Eq;
import com.figure.core.util.copycat.query.AbstractQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SignalAnalysisGroupQuery extends AbstractQuery<SignalAnalysisGroup> {

    @Eq
    @ApiModelProperty("自增主键")
    private Integer id;

    @Eq
    @ApiModelProperty("分析组名称")
    private String analysisName;

    @Eq
    @ApiModelProperty("关联分析服务器Id")
    private Integer serverId;

    @Eq
    @ApiModelProperty("所属标准频道Id")
    private String channelCode;

    @Eq
    @ApiModelProperty("音频1 视频2")
    private Integer streamType;

    @Eq
    @ApiModelProperty("报警模板规则Id")
    private Integer modelId;

    @Eq
    @ApiModelProperty("报警模式选择 0逻辑中心判定 1直接判定")
    private Integer alarmMode;

    @Eq
    @ApiModelProperty("参考源类型 0实时 1计审结果文件")
    private Integer refSourceType;

    @Eq
    @ApiModelProperty("参考源频道Id")
    private String refSourceChannelId;

    @Eq
    @ApiModelProperty("比对分析源 频道Id逗号隔开")
    private String comparisonChannelIds;

    @Eq
    @ApiModelProperty("是否删除：0未删除、1已删除，已删除数据只有系统管理员可以查看和编辑")
    private Integer isDelete;

}