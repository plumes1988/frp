package com.figure.op.device.domain.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.figure.op.common.domain.BaseEntity;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 出库查询对象
 * @author fsn
 */
@Data
public class OpOutProductQueryBo {

    private String opProductName;

    private Integer deviceTypeId;

    private Integer brandId;

    private Integer modelId;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date startCreateTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date endCreateTime;

}
