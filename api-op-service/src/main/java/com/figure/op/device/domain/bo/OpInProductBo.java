package com.figure.op.device.domain.bo;

import com.figure.op.common.validate.AddGroup;
import com.figure.op.common.validate.EditGroup;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * 入库实体对象
 * @author fsn
 */
@Data
public class OpInProductBo {

    private Integer inId;

    private Integer opProductId;

    private String opProductName;

    private Integer deviceTypeId;

    private Integer brandId;

    private Integer modelId;

    @NotNull(message = "入库数量不能为空", groups = { AddGroup.class, EditGroup.class })
    @Range(min = 1, max = 999999, message = "入库数量范围为1-999999", groups = { AddGroup.class, EditGroup.class })
    private Integer amount;

    private String unit;

    private String inType;

    private Integer operatorId;

    private Integer isDelete;

}
