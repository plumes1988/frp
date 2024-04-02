package com.figure.op.cameramanager.domain.bo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.figure.op.common.validate.AddGroup;
import com.figure.op.common.validate.EditGroup;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @Author liqiang
 * @Date 2023/9/13 13:36
 * @Version 1.5
 */
@Data
public class CameraAreInfoBo {

    private Integer cameraAreaId;

    @NotNull(groups={AddGroup.class, EditGroup.class})
    private Integer cameraId;

    @NotNull(message="只能输入数字",groups={AddGroup.class, EditGroup.class})
    private Integer ruleAreaId;

    @NotEmpty(groups={AddGroup.class, EditGroup.class})
    private String areaName;

    @NotEmpty(groups={AddGroup.class, EditGroup.class})
    private String partCode;

    @NotEmpty(groups={AddGroup.class, EditGroup.class})
    private String partName;

    @NotNull(groups={AddGroup.class, EditGroup.class})
    private float alarmThreshold;
    @NotNull(groups={AddGroup.class, EditGroup.class})
    private String deviceName;
}
