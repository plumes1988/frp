package com.figure.op.system.domain.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.figure.op.common.domain.BaseEntity;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 *
 * @author fsn
 */
@Data
public class TechInfoQueryBo {

    private Integer techInfoId;

    private String techName;

    private String techType;

    private String techDes;

    private Integer createUserId;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date startCreateTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date endCreateTime;

}
