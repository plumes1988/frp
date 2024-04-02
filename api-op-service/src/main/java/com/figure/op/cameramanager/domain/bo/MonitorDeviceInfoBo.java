package com.figure.op.cameramanager.domain.bo;

import com.figure.op.common.validate.AddGroup;
import com.figure.op.common.validate.EditGroup;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @Author liqiang
 * @Date 2023/9/8 22:31
 * @Version 1.5
 */
@Data
public class MonitorDeviceInfoBo {
    private Integer deviceId;
    @NotEmpty(groups={AddGroup.class, EditGroup.class})
    private String deviceName;
    @NotEmpty(groups={AddGroup.class,EditGroup.class})
    private String deviceCode;
    @NotNull(groups = {AddGroup.class,EditGroup.class})
    private Integer frontId;
    @NotNull(message="只能输入数字",groups = {AddGroup.class,EditGroup.class})
    private Integer locateId;
    private String imageUrl;

}
