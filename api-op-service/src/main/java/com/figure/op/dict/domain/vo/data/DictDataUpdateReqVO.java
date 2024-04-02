package com.figure.op.dict.domain.vo.data;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * 管理后台 - 字典数据更新 Request VO
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DictDataUpdateReqVO extends DictDataBaseVO {
    /**
     * 字典数据编号
     */
    @NotNull(message = "字典数据编号不能为空")
    private Long id;

}
