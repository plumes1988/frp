package com.figure.op.performance.domain.vo.manage;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;


/**
 * 绩效评分管理分页 Request VO
 */
@Data
@ToString(callSuper = true)
public class PerformanceManagePageReqVO   {

    /**
     * id
     */
    private Long id;

    /**
     * 机构id
     */
    private Integer agencyId;

    /**
     * 机构名称
     */
    private String agencyName;

    /**
     * 模板id
     */
    private Long templateId;

    /**
     * 模板名称
     */
    private String templateName;

    /**
     * 绩效评价评分
     */
    private BigDecimal score;

    /**
     * 创建人员ID
     */
    private Integer createUserId;

    /**
     * 创建时间
     */
    private LocalDateTime[] createTime;

    /**
     * 更新人员ID
     */
    private Integer updateUserId;

    /**
     * 状态：0:未删除、1:删除
     */
    private Boolean isDelete;

}
