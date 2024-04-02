package com.figure.core.model.signal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.figure.core.base.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * <p>
 * 多源分析服务管理
 * </p>
 *
 * @author feather
 * @date 2022-11-28 09:58:04
 */
@Data
@Accessors(chain = true)
@TableName("signal_analysis_server")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SignalAnalysisServer", description = "多源分析服务管理")
public class SignalAnalysisServer extends BaseModel {

    /**
     * 自增主键
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("自增主键")
    private Integer id;
    /**
     * 服务编号
     */
    @ApiModelProperty("服务编号")
    private String serverNum;
    /**
     * 关联设备Id
     */
    @ApiModelProperty("关联设备Id")
    private Integer deviceId;
    /**
     * 服务IP
     */
    @ApiModelProperty("服务IP")
    private String serverIP;
    /**
     * 最大分析节目数
     */
    @ApiModelProperty("最大分析节目数")
    private Integer analysisMaximum;
    /**
     * 是否删除：0未删除、1已删除，已删除数据只有系统管理员可以查看和编辑
     */
    @ApiModelProperty("是否删除：0未删除、1已删除，已删除数据只有系统管理员可以查看和编辑")
    private Integer isDelete;

}