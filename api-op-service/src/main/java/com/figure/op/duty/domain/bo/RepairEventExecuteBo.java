package com.figure.op.duty.domain.bo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.figure.op.common.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 品牌实体对象
 * @author fsn
 */
@Data
public class RepairEventExecuteBo extends BaseEntity {

    private Integer executeId;

    private Integer repairEventId;

    private String executeStatus;

    private Integer workerId;

    private String executeDes;

    private String repairDes;

    private String repairRestore;

    private String tryResult;

    private String repairImgs;

}
