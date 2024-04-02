package com.figure.op.system.domain.bo;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.figure.op.common.domain.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 *
 * @author fsn
 */
@Data
public class BudgetInfoBo extends BaseEntity {

    private Integer budgetId;

    private Date entryTime;

    private String source;

    private String organ;

    private Double price;

    private String status;

    private String reason;

    private Integer reviewId;

    private String reviewer;

    private Date reviewTime;

    private String creator;

    private String isPlan;

    private String info;

    @TableLogic
    private Integer isDelete;

}
