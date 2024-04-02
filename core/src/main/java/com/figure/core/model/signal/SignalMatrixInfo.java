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
 * 矩阵信息列表
 * </p>
 *
 * @author jobob
 * @since 2022-12-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("signal_matrix_info")
public class SignalMatrixInfo implements Serializable {

    /**
     * 矩阵ID
     */
    @TableId(value = "matrixId", type = IdType.AUTO)
    private Integer matrixId;

    /**
     * 矩阵名称
     */
    @TableField("matrixName")
    private String matrixName;

    /**
     * 关联设备ID
     */
    @TableField("deviceId")
    private Integer deviceId;

    /**
     * 关联逻辑频道编号
     */
    @TableField("logicChannelCode")
    private String logicChannelCode;

    /**
     * 输入接口数量
     */
    @TableField("inputCount")
    private Integer inputCount;

    /**
     * 输出接口数量
     */
    @TableField("outputCount")
    private Integer outputCount;


   @TableField(exist = false)
   private String ins;

    @TableField(exist = false)
    private String outs;
}
