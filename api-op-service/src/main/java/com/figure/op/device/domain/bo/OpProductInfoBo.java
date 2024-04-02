package com.figure.op.device.domain.bo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.figure.op.common.domain.BaseEntity;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

/**
 * 品牌实体对象
 * @author fsn
 */
@Data
public class OpProductInfoBo {

    private Integer opProductId;

    private String opProductName;

    private Integer deviceTypeId;

    private Integer brandId;

    private Integer modelId;

    private Integer inAllAmount;

    private Integer stock;

    private String unit;

    private Integer isDelete;

}
