package com.figure.op.system.domain.bo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class StaticsQueryBo {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date staStartTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date staEndTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date sourceStartTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date sourceEndTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date useStartTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date useEndTime;
}
