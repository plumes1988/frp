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
 * 逻辑中心服务器
 * </p>
 *
 * @author feather
 * @date 2022-08-09 16:55:05
 */
@Data
@Accessors(chain = true)
@TableName("logic_server_info")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "LogicServerInfo", description = "逻辑中心服务器")
public class LogicServerInfo extends BaseModel {

    /**
     * 逻辑中心服务器自增Id
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("逻辑中心服务器自增Id")
    private Integer id;
    /**
     * 逻辑中心服务编号
     */
    @ApiModelProperty("逻辑中心服务编号")
    private String deviceCode;
    /**
     * 逻辑中心服务名称
     */
    @ApiModelProperty("逻辑中心服务名称")
    private String deviceName;
    /**
     * 设备Id
     */
    @ApiModelProperty("设备Id")
    private Integer deviceId;
    /**
     * 状态：0:正常、1:删除、2:停用
     */
    @ApiModelProperty("状态：0:正常、1:删除、2:停用")
    private Integer isDelete;

}