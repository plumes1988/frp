package com.figure.core.model.audit;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.figure.core.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 播出设备信息
 * </p>
 *
 * @author jobob
 * @since 2022-12-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("audit_boardcast_device_info")
public class AuditBoardcastDeviceInfo  implements Serializable {


    /**
     *
     * 所属系统
     */
    @TableField("logicChannelCode")
    private String logicChannelCode;

    /**
     * 设备ID
     */
    @TableId("devId")
    private Integer devId;

    /**
     * 设备名称
     */
    @TableField("devName")
    private String devName;

    /**
     * 设备类型
     */
    @TableField("devTypeCode")
    private Integer devTypeCode;

    /**
     * 设备归类
     */
    @TableField("devTypeName")
    private String devTypeName;

}
