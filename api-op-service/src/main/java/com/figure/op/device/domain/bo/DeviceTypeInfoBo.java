package com.figure.op.device.domain.bo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.figure.op.common.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 品牌实体对象
 * @author fsn
 */
@Data
public class DeviceTypeInfoBo extends BaseEntity {

    private Integer deviceTypeId;

    private Integer parentId;

    private String deviceTypeName;

    private String sort;
    
    private Integer isDelete;
}
