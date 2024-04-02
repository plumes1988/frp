package com.figure.op.system.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.figure.op.common.domain.BaseEntity;
import lombok.Data;

/**
 *
 * @author fsn
 */
@Data
public class TechInfo extends BaseEntity {

    @TableId("techInfoId")
    private Integer techInfoId;

    @TableField("techName")
    private String techName;

    @TableField("techType")
    private String techType;

    @TableField("techDes")
    private String techDes;

    @TableLogic
    private Integer isDelete;

}
