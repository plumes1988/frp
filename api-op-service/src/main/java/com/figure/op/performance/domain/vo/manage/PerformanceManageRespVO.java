package com.figure.op.performance.domain.vo.manage;

import lombok.*;
import java.time.LocalDateTime;

/**
 * 绩效评分管理
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PerformanceManageRespVO extends PerformanceManageBaseVO {

    /**
     * id
     */
    private Long id;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

}
