package com.figure.core.model.spectrum;

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

import java.util.List;


/**
 * <p>
 * 频谱服务信息
 * </p>
 *
 * @author feather
 * @date 2023-11-24 16:44:13
 */
@Data
@Accessors(chain = true)
@TableName("spectrum_service_info")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SpectrumServiceInfo", description = "频谱服务信息")
public class SpectrumServiceInfo extends BaseModel {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("主键")
    private Integer id;
    /**
     * 频谱服务编号
     */
    @ApiModelProperty("频谱服务编号")
    private String serviceCode;

    /**
     * 频谱服务编号
     */
    @ApiModelProperty("频谱服务编号")
    @TableField(exist = false)
    private List<String> serviceCodes;
    /**
     * 频谱服务名称
     */
    @ApiModelProperty("频谱服务名称")
    private String serviceName;
    /**
     * 状态：0:未删除、1:已删除、2:停用
     */
    @ApiModelProperty("状态：0:未删除、1:已删除、2:停用")
    private Integer isDelete;

    /**
     * 开始频点
     */
    @ApiModelProperty("开始频点")
    private Integer startFrequency;

    /**
     * 结束频点
     */
    @ApiModelProperty("结束频点")
    private Integer endFrequency;

}