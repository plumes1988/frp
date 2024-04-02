package com.figure.op.dict.domain.vo.type;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;


/**
 * 管理后台 - 字典类型分页列表 Request VO
 */
@Data
public class DictTypePageReqVO   {
    /**
     * 字典类型名称,模糊匹配
     */
    private String name;
    /**
     * 字典类型,模糊匹配
     */
    @Size(max = 100, message = "字典类型类型长度不能超过100个字符")
    private String type;
    /**
     * 展示状态
     * 0=开启 1=关闭
     */
    private Integer status;
    /**
     * 创建时间
     */
    private LocalDateTime[] createTime;

}
