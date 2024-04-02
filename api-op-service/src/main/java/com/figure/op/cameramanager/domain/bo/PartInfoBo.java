package com.figure.op.cameramanager.domain.bo;

import com.figure.op.common.validate.AddGroup;
import com.figure.op.common.validate.EditGroup;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @Author liqiang
 * @Date 2023/9/8 22:31
 * @Version 1.5
 */
@Data
public class PartInfoBo {
    @NotNull(groups={EditGroup.class})
    private Integer partId;
    @NotEmpty(groups={AddGroup.class, EditGroup.class})
    private String partName;
    @NotEmpty(groups={AddGroup.class,EditGroup.class})
    private String partCode;
    @NotEmpty(groups={AddGroup.class,EditGroup.class})
    private String deviceCode;
}
