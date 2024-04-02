package com.figure.op.system.domain.vo;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.figure.op.common.domain.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author fsn
 */
@Data
public class OutPriceInfoListVo extends BaseEntity {

    private Integer outPriceId;

    private String useInfo;

    private String source;

    private BigDecimal price;

    private String status;

    private String reason;

    private Integer reviewId;

    private String reviewer;

    private Date reviewTime;

    private String path;

    private String applier;

    private Integer applierId;

    private Date applyTime;

    @TableLogic
    private Integer isDelete;

    private String organ;

    private String purchase;

}
