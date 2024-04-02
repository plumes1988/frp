package com.figure.core.model.signal;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * <p>
 * 特征比对分析组
 * </p>
 *
 * @author feather
 * @date 2022-11-28 09:58:04
 */
@Data
public class SignalAnalysisGroupList extends SignalAnalysisGroup {

    @ApiModelProperty("分析服务器名称")
    private String serverName;

    @ApiModelProperty("标准频道名称")
    private String channelCodeName;

    @ApiModelProperty("报警模板名称")
    private String modelName;

    @ApiModelProperty("参考源频道名称")
    private String refSourceChannelName;

    @ApiModelProperty("比对分析源频道名称")
    private String comparisonChannelName;
}