package com.figure.op.cameramanager.domain.bo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.figure.op.common.validate.AddGroup;
import com.figure.op.common.validate.EditGroup;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @Author liqiang
 * @Date 2023/9/14 10:33
 * @Version 1.5
 */
@Data
public class ThermalImageInfoBo {
    @NotNull(groups={ EditGroup.class})
    private Integer id;
    @NotNull(groups={AddGroup.class, EditGroup.class})
    private Integer analysisSwitch;
    @NotEmpty(groups={AddGroup.class, EditGroup.class})
    private String referenceImagePath;
    @NotEmpty(groups={AddGroup.class, EditGroup.class})
    private String analysisImagePath;
    @NotEmpty(groups={AddGroup.class, EditGroup.class})
    private String analysisImageOutPath;
    @NotEmpty(groups={AddGroup.class, EditGroup.class})
    private String analysisCron;
    @NotEmpty(groups={AddGroup.class, EditGroup.class})
    private String monitorCron;
    @NotNull(message="分析时间只能输入数字",groups={AddGroup.class, EditGroup.class})
    private Integer analysisCronTime;
    @NotNull(message="监测时间只能输入数字",groups={AddGroup.class, EditGroup.class})
    private Integer monitorCronTime;
    @NotNull(groups={ EditGroup.class})
    private Integer cameraId;
}
