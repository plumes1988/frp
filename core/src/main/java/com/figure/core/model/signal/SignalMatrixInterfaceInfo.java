package com.figure.core.model.signal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 矩阵接口表
 * </p>
 *
 * @author jobob
 * @since 2022-12-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("signal_matrix_interface_info")
public class SignalMatrixInterfaceInfo implements Serializable {


    /**
     * 接口ID
     */
    @TableId(value = "interfaceId", type = IdType.AUTO)
    private Integer interfaceId;

    /**
     * 矩阵ID
     */
    @TableField("matrixId")
    private Integer matrixId;

    /**
     * 接口类型 0:输入，1：输出
     */
    @TableField("interfaceType")
    private Integer interfaceType;

    /**
     * 接口序号
     */
    @TableField("interfaceNumber")
    private Integer interfaceNumber;

    /**
     * 采集点ID
     */
    @TableField("positionId")
    private Integer positionId;

    /**
     * 关联播出设备ID
     */
    @TableField("boardcastDevId")
    private Integer boardcastDevId;

    /**
     * 关联输入接口序号，当前为输出接口时有效
     */
    @TableField("useInputNumber")
    private Integer useInputNumber;

    /**
     * 0：停用、1：启用
     */
    @TableField("isEnable")
    private Integer isEnable;

    /**
     * 0:删除、1:未删除，已删除数据只有系统管理员可以查看和编辑
     */
    @TableField("isDelete")
    private Integer isDelete;


}
