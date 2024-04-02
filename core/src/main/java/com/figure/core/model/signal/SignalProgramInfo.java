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

import java.util.Date;

/**
 * <p>
 * 节目信息
 * </p>
 *
 * @author feather
 * @date 2022-08-16 11:23:19
 */
@Data
@Accessors(chain = true)
@TableName("signal_program_info")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SignalProgramInfo", description = "节目信息")
public class SignalProgramInfo extends BaseModel {

    /**
     * 节目ID
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("节目ID")
    private Integer id;
    /**
     * 栏目ID
     */
    @ApiModelProperty("栏目ID")
    private String columnId;
    /**
     * 逻辑频道ID
     */
    @ApiModelProperty("逻辑频道ID")
    private String logicChannelId;
    /**
     * 节目单ID
     */
    @ApiModelProperty("节目单ID")
    private String epgId;
    /**
     * 节目名称
     */
    @ApiModelProperty("节目名称")
    private String programName;
    /**
     * 开始日期
     */
    @ApiModelProperty("开始日期")
    private Date startDate;
    /**
     * 节目时长 单位秒
     */
    @ApiModelProperty("节目时长 单位秒")
    private Integer programLen;
    /**
     * 0:删除、1:未删除，已删除数据只有系统管理员可以查看和编辑
     */
    @ApiModelProperty("0:删除、1:未删除，已删除数据只有系统管理员可以查看和编辑")
    private Integer isDelete;

}