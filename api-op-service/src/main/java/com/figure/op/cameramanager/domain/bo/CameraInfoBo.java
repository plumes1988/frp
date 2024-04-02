package com.figure.op.cameramanager.domain.bo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.figure.op.common.validate.AddGroup;
import com.figure.op.common.validate.EditGroup;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @Author liqiang
 * @Date 2023/9/9 23:30
 * @Version 1.5
 */
@Data
public class CameraInfoBo {
    private Integer cameraId;
    @NotEmpty(groups={AddGroup.class, EditGroup.class})
    private String cameraCode;
    @NotEmpty(groups={AddGroup.class, EditGroup.class})
    private String cameraName;
    @NotEmpty(groups={AddGroup.class, EditGroup.class})
    private String cameraTypeCode;
    @Pattern(message="ip不合法",regexp = "((2((5[0-5])|([0-4]\\d)))|([0-1]?\\d{1,2}))(\\.((2((5[0-5])|([0-4]\\d)))|([0-1]?\\d{1,2}))){3}")
    private String ip;
    @NotNull(message="端口只能输入数字",groups={AddGroup.class, EditGroup.class})
    private Integer port;
    @NotEmpty(groups={AddGroup.class, EditGroup.class})
    private String channelNo;
    @NotEmpty(groups={AddGroup.class, EditGroup.class})
    private String username;
    @NotEmpty(groups={AddGroup.class, EditGroup.class})
    private String password;
    @NotEmpty(groups={AddGroup.class, EditGroup.class})
    private String deviceCode;
    @NotNull(message="只能输入数字",groups={AddGroup.class, EditGroup.class})
    private Integer locateId;
    private String remark;
}
