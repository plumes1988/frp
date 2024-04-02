package com.figure.op.cameramanager.domain.bo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.figure.op.common.validate.AddGroup;
import com.figure.op.common.validate.EditGroup;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @Author liqiang
 * @Date 2023/9/9 21:39
 * @Version 1.5
 */
@Data
public class CameraTypeInfoBo {

    private Integer cameraTypeId;

    @NotEmpty(groups={AddGroup.class, EditGroup.class})
    private String cameraTypeCode;

    @NotEmpty(groups={AddGroup.class, EditGroup.class})
    private String cameraTypeName;

    private String remark;
}
