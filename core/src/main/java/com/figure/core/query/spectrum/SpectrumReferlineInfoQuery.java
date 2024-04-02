package com.figure.core.query.spectrum;

import com.figure.core.model.spectrum.SpectrumReferlineInfo;
import com.figure.core.util.copycat.annotaion.Eq;
import com.figure.core.util.copycat.query.AbstractQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class SpectrumReferlineInfoQuery extends AbstractQuery<SpectrumReferlineInfo> {

    @Eq
    @ApiModelProperty("主键")
    private Integer id;

    @Eq
    @ApiModelProperty("时段频谱名称")
    private String referName;

    @Eq
    @ApiModelProperty("频谱仪deviceCode")
    private String spectrumCode;

    @Eq
    @ApiModelProperty("电平")
    private Integer level;

    @Eq
    @ApiModelProperty("频谱数据:存放场强、电平的json结构")
    private String spectrumData;

    @Eq
    @ApiModelProperty("开始日期")
    private Date dateStart;

    @Eq
    @ApiModelProperty("结束日期")
    private Date dateEnd;

    @Eq
    @ApiModelProperty("开始时间")
    private Date timeStart;

    @Eq
    @ApiModelProperty("结束时间")
    private Date timeEnd;

    @Eq
    @ApiModelProperty("状态：0:未删除、1:已删除、2:停用")
    private Integer isDelete;

}