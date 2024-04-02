package com.figure.op.system.domain.vo;

import com.figure.op.common.domain.BaseEntity;
import lombok.Data;

/**
 * 机构简单实体类
 *
 * @author ly
 */
@Data
public class SysAgencySimpleVo {

    /**
     * 机构编号
     */
    private Integer agencyId;

    /**
     * 机构名称
     */
    private String agencyName;


}
