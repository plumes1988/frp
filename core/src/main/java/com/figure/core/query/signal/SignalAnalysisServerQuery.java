package com.figure.core.query.signal;

import com.figure.core.model.signal.SignalAnalysisServer;
import com.figure.core.util.copycat.annotaion.Eq;
import com.figure.core.util.copycat.query.AbstractQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SignalAnalysisServerQuery extends AbstractQuery<SignalAnalysisServer> {

    @Eq
    @ApiModelProperty("自增主键")
    private Integer id;

    @Eq
    @ApiModelProperty("服务编号")
    private String serverNum;

    @Eq
    @ApiModelProperty("关联设备Id")
    private Integer deviceId;

    @Eq
    @ApiModelProperty("服务IP")
    private String serverIP;

    @Eq
    @ApiModelProperty("最大分析节目数")
    private Integer analysisMaximum;

    @Eq
    @ApiModelProperty("是否删除：0未删除、1已删除，已删除数据只有系统管理员可以查看和编辑")
    private Integer isDelete;

}