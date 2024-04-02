package com.figure.op.dict.domain.vo.type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 管理后台 - 字典类型信息 Response VO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DictTypeRespVO extends DictTypeBaseVO {

    /**
     * 字典类型编号
     */
    private Long id;

    /**
     * 字典类型
     */
    private String type;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

}
