package com.figure.op.device.domain.bo;

import com.figure.op.common.domain.BaseEntity;
import lombok.Data;

/**
 * 品牌实体对象
 * @author fsn
 */
@Data
public class DeviceTypeInfoQueryBo extends BaseEntity {

    private Integer deviceTypeId;

    private Integer parentId;

    private String deviceTypeName;

    private String sort;
    
    private Integer isDelete;
}
