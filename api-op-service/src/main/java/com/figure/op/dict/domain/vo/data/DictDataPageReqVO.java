package com.figure.op.dict.domain.vo.data;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Size;

/**
 * 管理后台 - 字典类型分页列表 Request VO
 */
@Data
public class DictDataPageReqVO {
    /**
     * 字典标签
     */
    @Size(max = 100, message = "字典标签长度不能超过100个字符")
    private String label;
    /**
     * 字典类型,模糊匹配
     */
    @Size(max = 100, message = "字典类型类型长度不能超过100个字符")
    private String dictType;
    /**
     * 展示状态
     * 0=开启 1=关闭
     */
    private Integer status;

}
