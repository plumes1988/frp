package com.figure.op.system.domain.vo;

import lombok.Data;

import java.util.Date;

/**
 *
 * @author fsn
 */
@Data
public class TechInfoVo {

    private Integer techInfoId;

    private String techName;

    private String techType;

    private String techDes;

    /**
     * 创建人员ID
     */
    private Integer createUserId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新人员ID
     */
    private Integer updateUserId;

    /**
     * 更新时间
     */
    private Date updateTime;

}
