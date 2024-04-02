package com.figure.op.performance.domain.vo.manage;

import lombok.*;
import javax.validation.constraints.*;

/**
* 绩效评分管理 Base VO，提供给添加、修改、详细的子 VO 使用
* 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
*/
@Data
public class PerformanceManageBaseVO {
    /**
     * 机构id
     */
    @NotNull(message = "机构id不能为空")
    private Integer agencyId;

    /**
     * 模板id
     */
    @NotNull(message = "模板id不能为空")
    private Long templateId;

    /**
     * 绩效评价评分
     */
    private Long score;

    /**
     * 创建人员ID
     */
    private Integer createUserId;

    /**
     * 更新人员ID
     */
    private Integer updateUserId;

    /**
     * 状态：0:未删除、1:删除
     */
    private Boolean isDelete;

}
