package com.figure.op.dict.domain.vo.data;

import lombok.Data;

/**
 * 管理后台 - 数据字典精简 Response VO
 */
@Data
public class DictDataSimpleRespVO {
    /**
     * 字典类型
     */
    private String dictType;
    /**
     * 字典键值
     */
    private String value;
    /**
     * 字典标签
     */
    private String label;
    /**
     * 颜色类型
     */
    private String colorType;
    /**
     * css 样式
     */
    private String cssClass;

}
