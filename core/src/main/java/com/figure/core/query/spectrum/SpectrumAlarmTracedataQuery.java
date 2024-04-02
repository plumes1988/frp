package com.figure.core.query.spectrum;

import com.figure.core.model.spectrum.SpectrumAlarmTracedata;
import com.figure.core.util.copycat.annotaion.Eq;
import com.figure.core.util.copycat.query.AbstractQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SpectrumAlarmTracedataQuery extends AbstractQuery<SpectrumAlarmTracedata> {

    @Eq
    @ApiModelProperty("主键")
    private Long alarmId;

    @Eq
    @ApiModelProperty("实时频谱数据")
    private String traceData;

    @Eq
    @ApiModelProperty("平均频谱数据")
    private String traceDataAverage;

}