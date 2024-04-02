package com.figure.op.system.domain.vo;

import lombok.Data;

import java.util.Date;

/**
 *
 * @author fsn
 */
@Data
public class OperateTimelineListVo {

    private Integer userId;

    private String userName;

    private String operate;

    private Date time;

}
