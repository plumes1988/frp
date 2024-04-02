package com.figure.core.query.spectrum;

import com.figure.core.model.spectrum.SpectrumServiceInfo;
import com.figure.core.util.copycat.annotaion.Eq;
import com.figure.core.util.copycat.query.AbstractQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SpectrumServiceInfoQuery extends AbstractQuery<SpectrumServiceInfo> {

    @Eq
    @ApiModelProperty("主键")
    private Integer id;

    @Eq
    @ApiModelProperty("频谱服务编号")
    private String serviceCode;

    @Eq
    @ApiModelProperty("频谱服务名称")
    private String serviceName;

    @Eq
    @ApiModelProperty("状态：0:未删除、1:已删除、2:停用")
    private Integer isDelete;

}