package com.figure.op.duty.domain.bo;

import com.figure.op.common.validate.AddGroup;
import com.figure.op.common.validate.EditGroup;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 值班记录增改对象
 * @author fsn
 */
@Data
public class DutyWorkRecordBo {

    /**
     * 值班记录ID
     */
    @NotNull(message = "值班记录ID不能为空", groups = { EditGroup.class })
    private Integer workRecordId;

    /**
     * 值班记录标题（手动添加）
     */
    private String workTitle;

    /**
     * 排班计划ID
     */
    @NotNull(message = "排班计划ID不能为空", groups = { AddGroup.class })
    private Integer scheduleId;

    /**
     * 记录信息
     */
    @NotBlank(message = "记录信息不能为空", groups = { AddGroup.class, EditGroup.class })
    @Length(max = 200, message = "记录信息长度不能超过200个字符", groups = { AddGroup.class, EditGroup.class })
    private String recordDesc;

    /**
     * 资料上传路径
     */
    private String attachPath;

}
