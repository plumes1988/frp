package com.figure.op.dict.domain.vo.type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 管理后台 - 字典类型精简信息 Response VO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DictTypeSimpleRespVO {

    /**
     * 字典类型编号
     */
    private Long id;

    /**
     * 字典类型名称
     */
    private String name;

    /**
     * 字典类型
     */
    private String type;

}
