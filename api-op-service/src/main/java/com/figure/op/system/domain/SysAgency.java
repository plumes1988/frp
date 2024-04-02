package com.figure.op.system.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.figure.op.common.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 机构do
 * @author ly
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_agency_info")
public class SysAgency extends BaseEntity {

    @TableId(value = "agencyId")
    private Integer agencyId;

    @TableField("agencyName")
    private String agencyName;

}
