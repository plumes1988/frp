package com.figure.op.performance.domain.bo;

import com.figure.op.common.validate.AddGroup;
import com.figure.op.common.validate.EditGroup;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 * 绩效模板明细对象（父级）
 *
 * @author 39827
 * @date 2023-08-25
 */
@Data
public class PerformanceTemplateInfoFirstBo {

    /**
     * 模板明细id
     */
    @NotNull(message = "模板明细id不能为空", groups = {EditGroup.class})
    @Null(message = "新增时不可操作主键", groups = {AddGroup.class})
    private Long id;

    /**
     * 模板id
     */
    @NotNull(message = "模板id不能为空", groups = {AddGroup.class})
    @Null(message = "编辑时不可操作模板id", groups = {EditGroup.class})
    private Long templateId;

    /**
     * 父级模板明细名称
     */
    @NotBlank(message = "父级模板名称不能为空", groups = {AddGroup.class, EditGroup.class})
    @Length(max = 12, message = "父级模板名称长度不能超过12个字符", groups = {AddGroup.class, EditGroup.class})
    private String title;

    /**
     * 父级评分占比
     */
    @NotNull(message = "父级评分占比不能为空", groups = {AddGroup.class, EditGroup.class})
    @Range(min = 1, max = 100, message = "父级评分占比必须在1-100之间", groups = {AddGroup.class, EditGroup.class})
    private Integer scoreRate;


}
