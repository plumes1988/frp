package com.figure.op.dict.domain.vo.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 管理后台 - 字典数据信息 Response VO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DictDataRespVO extends DictDataBaseVO {
    /**
     * 字典数据编号
     */
    private Long id;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

}
