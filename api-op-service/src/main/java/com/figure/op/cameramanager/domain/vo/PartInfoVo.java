package com.figure.op.cameramanager.domain.vo;

import com.figure.op.common.validate.AddGroup;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @Author liqiang
 * @Date 2023/9/9 15:52
 * @Version 1.5
 */
@Data
public class PartInfoVo {
    private Integer partId;
    private String partName;
    private String partCode;
    private String deviceCode;
}
