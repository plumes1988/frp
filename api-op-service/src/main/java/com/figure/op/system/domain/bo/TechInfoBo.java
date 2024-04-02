package com.figure.op.system.domain.bo;

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
public class TechInfoBo extends BaseEntity {

    private Integer techInfoId;

    private String techName;

    private String techType;

    private String techDes;

    private Integer isDelete;

}
