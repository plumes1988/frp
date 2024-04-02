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
 * 特征比对分析组
 * </p>
 *
 * @author feather
 * @date 2022-11-28 09:58:04
 */
@Data
@Accessors(chain = true)
@TableName("signal_analysis_group")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SignalAnalysisGroup", description = "特征比对分析组")
public class SignalAnalysisGroup extends BaseModel {

    /**
     * 自增主键
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("自增主键")
    private Integer id;
    /**
     * 分析组名称
     */
    @ApiModelProperty("分析组名称")
    private String analysisName;
    /**
     * 关联分析服务器Id
     */
    @ApiModelProperty("关联分析服务器Id")
    private Integer serverId;
    /**
     * 所属标准频道Id
     */
    @ApiModelProperty("所属标准频道Id")
    private String channelCode;
    /**
     * 音频1 视频2
     */
    @ApiModelProperty("音频1 视频2")
    private Integer streamType;
    /**
     * 报警模板规则Id
     */
    @ApiModelProperty("报警模板规则Id")
    private Integer modelId;
    /**
     * 报警模板规则Id
     */
    @ApiModelProperty("报警模式选择 0逻辑中心判定 1直接判定")
    private Integer alarmMode;
    /**
     * 参考源类型 0实时 1计审结果文件
     */
    @ApiModelProperty("参考源类型 0实时 1计审结果文件")
    private Integer refSourceType;
    /**
     * 参考源频道Id
     */
    @ApiModelProperty("参考源频道Id")
    private String refSourceChannelId;
    /**
     * 比对分析源 频道Id逗号隔开
     */
    @ApiModelProperty("比对分析源 频道Id逗号隔开")
    private String comparisonChannelIds;
    /**
     * 是否删除：0未删除、1已删除，已删除数据只有系统管理员可以查看和编辑
     */
    @ApiModelProperty("是否删除：0未删除、1已删除，已删除数据只有系统管理员可以查看和编辑")
    private Integer isDelete;

}