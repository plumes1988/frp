package com.figure.op.duty.domain.bo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.figure.op.common.validate.AddGroup;
import com.figure.op.common.validate.EditGroup;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;

/**
 * 品牌增改对象
 * @author fsn
 */
@Data
public class DutyInfoBo implements Serializable {

    /**
     * 值班任务ID
     */
    private Integer dutyId;

    /**
     * 任务名称
     */
    private String dutyName;

    /**
     * 任务类型
     */
    private String dutyType;

    /**
     * 任务描述
     */
    private String dutyDesc;


}
