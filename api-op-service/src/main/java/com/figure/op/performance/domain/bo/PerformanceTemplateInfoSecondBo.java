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
 * 绩效模板明细对象（子级）
 *
 * @date 2023-08-25
 */
@Data
public class PerformanceTemplateInfoSecondBo {

    /**
     * 模板明细id
     */
    @NotNull(message = "模板明细id不能为空", groups = {EditGroup.class})
    @Null(message = "新增时不可操作主键", groups = {AddGroup.class})
    private Long id;

    /**
     * 父级模板明细id
     */
    @NotNull(message = "父级模板明细id不能为空", groups = {AddGroup.class, EditGroup.class})
    private Long parentId;

    /**
     * 子级模板明细标题
     */
    @NotBlank(message = "子级模板名称不能为空", groups = {AddGroup.class, EditGroup.class})
    @Length(max = 12, message = "子级模板名称长度不能超过12个字符", groups = {AddGroup.class, EditGroup.class})
    private String title;

    /**
     * 子级评分占比
     */
    @NotNull(message = "子级评分占比不能为空", groups = {AddGroup.class, EditGroup.class})
    @Range(min = 1, max = 100, message = "子级评分占比必须在1-100之间", groups = {AddGroup.class, EditGroup.class})
    private Integer scoreRate;

    /**
     * 子级说明
     */
    @NotBlank(message = "子级说明不能为空", groups = {AddGroup.class, EditGroup.class})
    @Length(max = 200, message = "子级说明长度不能超过200个字符", groups = {AddGroup.class, EditGroup.class})
    private String info;


}
