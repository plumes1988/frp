package com.figure.op.duty.domain.vo;

import lombok.Data;

@Data
public class DutyCalVo {

    private String name;

    private String dept;

    private Integer planDays = 0;

    private Double planTimes = 0d;

    private Integer actDays = 0;

    private Double actTimes = 0d;

    private Double leaveTimes = 0d;

    private Double absentTimes = 0d;
}
