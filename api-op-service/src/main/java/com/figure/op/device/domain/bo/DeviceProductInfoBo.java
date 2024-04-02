package com.figure.op.device.domain.bo;

import com.figure.op.common.validate.AddGroup;
import com.figure.op.common.validate.EditGroup;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;

/**
 * 品牌增改对象
 * @author fsn
 */
@Data
public class DeviceProductInfoBo implements Serializable {

    private Integer deviceId;

    private String deviceName;

    private String deviceCode;

    private String deviceType;


}
