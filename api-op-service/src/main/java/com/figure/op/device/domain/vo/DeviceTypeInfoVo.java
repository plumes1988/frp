package com.figure.op.device.domain.vo;

import com.figure.op.common.domain.BaseEntity;
import lombok.Data;

/**
 * 品牌实体对象
 * @author fsn
 */
@Data
public class DeviceTypeInfoVo extends BaseEntity {

    private Integer deviceTypeId;

    private Integer parentId;

    private String deviceTypeName;

    private String sort;
    
    private Integer isDelete;
}
