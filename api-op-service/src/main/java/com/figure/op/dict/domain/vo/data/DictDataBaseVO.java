package com.figure.op.dict.domain.vo.data;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 字典数据 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class DictDataBaseVO {
    /**
     * 显示顺序
     */
    @NotNull(message = "显示顺序不能为空")
    private Integer sort;

    /**
     * 字典标签
     */
    @NotBlank(message = "字典标签不能为空")
    @Size(max = 100, message = "字典标签长度不能超过100个字符")
    private String label;

    /**
     * 字典值
     */
    @NotBlank(message = "字典键值不能为空")
    @Size(max = 100, message = "字典键值长度不能超过100个字符")
    private String value;

    /**
     * 字典类型
     */
    @NotBlank(message = "字典类型不能为空")
    @Size(max = 100, message = "字典类型长度不能超过100个字符")
    private String dictType;

    /**
     * 状态
     * 0=开启 1=关闭
     */
    @NotNull(message = "状态不能为空")
    private Integer status;

    /**
     * 颜色类型
     */
    private String colorType;

    /**
     * css 样式
     */
    private String cssClass;

    /**
     * 备注
     */
    private String remark;

}
