package com.figure.op.system.domain.bo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 预算管理查询对象
 * @author fsn
 */
@Data
public class BudgetInfoQueryBo {

    private String isPlan;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date entryTime;

    private String source;

    /**
     * 入账机构
     */
    private String organ;

    /**
     * 登记人
     */
    private Integer createUserId;

    /**
     * 审核人
     */
    private Integer reviewId;

    /**
     * 审核状态
     */
    private String status;

}
