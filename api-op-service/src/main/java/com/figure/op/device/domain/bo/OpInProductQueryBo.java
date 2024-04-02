package com.figure.op.device.domain.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 入库查询对象
 * @author fsn
 */
@Data
public class OpInProductQueryBo {

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
