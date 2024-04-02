package com.figure.op.duty.domain.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.figure.op.common.validate.AddGroup;
import com.figure.op.common.validate.EditGroup;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 排班计划作废对象
 * @author fsn
 */
@Data
public class DutyScheduleInfoCancelBo implements Serializable {

    /**
     * 排班计划ID
     */
    @NotNull(message = "排班计划ID不能为空")
    private Integer scheduleId;

    /**
     * 作废开始日期
     */
    @JsonFormat(pattern="yyyy-MM-dd")
    @NotNull(message = "开始日期不能为空")
    private Date cancelStartDate;

}
