package com.figure.core.model.spectrum;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import com.figure.core.base.BaseModel;


/**
 * <p>
 * 频谱数据详细
 * </p>
 *
 *@author feather
 *@date 2024-03-01 14:10:45
 */
@Data
@Accessors(chain = true)
@TableName("spectrum_record_tracedata")
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "SpectrumRecordTracedata", description = "频谱数据详细")
public class SpectrumRecordTracedata extends BaseModel {

    /** 
     *主键 
     */
    @ApiModelProperty("主键")
    private Long id;
    /**
     * 频谱服务编号
     */
    @ApiModelProperty("频谱服务编号")
    private String serviceCode;
    /**
     * 频谱设备编号
     */
    @ApiModelProperty("频谱设备编号")
    private String spectrumCode;
    /** 
     *实时频谱数据 
     */
    @ApiModelProperty("实时频谱数据")
    private String traceData;
    /** 
     *平均频谱数据 
     */
    @ApiModelProperty("平均频谱数据")
    private String traceDataAverage;

    /**
     * 状态：0:正常、1:删除、2:停用
     */
    @ApiModelProperty("状态：0:正常、1:删除、2:停用")
    private Integer isDelete;

    public SpectrumRecordTracedata(SpectrumRecordMessage recordMessage) {

        this.id = recordMessage.getId();
        this.traceData = recordMessage.getTraceData();
        this.traceDataAverage = recordMessage.getTraceDataAverage();
        this.spectrumCode = recordMessage.getSpectrumCode();
        this.serviceCode = recordMessage.getServiceCode();
    }

}