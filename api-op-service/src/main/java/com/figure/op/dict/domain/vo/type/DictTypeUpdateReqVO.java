package com.figure.op.dict.domain.vo.type;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * 管理后台 - 字典类型更新 Request VO
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DictTypeUpdateReqVO extends DictTypeBaseVO {

    /**
     * 字典类型编号
     */
    @NotNull(message = "字典类型编号不能为空")
    private Long id;

}
