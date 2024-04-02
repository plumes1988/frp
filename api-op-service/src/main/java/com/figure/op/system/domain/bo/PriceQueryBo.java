package com.figure.op.system.domain.bo;

import lombok.Data;

import java.util.Date;

@Data
public class PriceQueryBo {

    private String timeStr;

    private Date start;

    private Date end;

}
