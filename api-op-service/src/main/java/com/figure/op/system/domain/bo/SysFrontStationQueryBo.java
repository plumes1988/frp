package com.figure.op.system.domain.bo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.figure.op.common.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author fsn
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("front_station_info")
public class SysFrontStationQueryBo extends BaseEntity {

    /**
     * 用户ID
     */
    @TableId(value = "frontId")
    private Integer frontId;

    /**
     * 姓名
     */
    @TableField("frontName")
    private String frontName;

    /**
     * 登录名
     */
    @TableField("supFrontId")
    private Integer supFrontId;

    /**
     * 工号
     */
    @TableField("frontType")
    private String frontType;

}
