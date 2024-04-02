package com.figure.core.model.logic;

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
 * 监控单元和接口信息关联
 * </p>
 *
 * @author feather
 * @date 2023-02-21 10:41:10
 */
@Data
@Accessors(chain = true)
@TableName("logic_monitorunit_interface_rel")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "LogicMonitorunitInterfaceRel", description = "监控单元和接口信息关联")
public class LogicMonitorunitInterfaceRel extends BaseModel {

    /**
     * 自增主键
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("自增主键")
    private Integer id;
    /**
     * 监控单元Id
     */
    @ApiModelProperty("监控单元Id")
    private Integer unitId;
    /**
     * 切换器接口Id,唯一
     */
    @ApiModelProperty("切换器接口Id,唯一")
    private String interfaceId;
    /**
     * 状态：0:未删除、1:已删除、2:停用
     */
    @ApiModelProperty("状态：0:未删除、1:已删除、2:停用")
    private Integer isDelete;

}