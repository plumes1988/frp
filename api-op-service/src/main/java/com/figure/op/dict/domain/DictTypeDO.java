package com.figure.op.dict.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.figure.op.common.domain.BaseEntity;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 字典类型表
 *
 * @author ly
 */
@TableName("sys_dict_type")
@KeySequence("sys_dict_type_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DictTypeDO extends BaseEntity {

    /**
     * 字典主键
     */
    @TableId
    private Long id;
    /**
     * 字典名称
     */
    @TableField("name")
    private String name;
    /**
     * 字典类型
     */
    @TableField("type")
    private String type;
    /**
     * 状态
     * 0=开启 1=关闭
     */
    @TableField("status")
    private Integer status;
    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 删除时间
     */
    @TableField("deletedTime")
    private LocalDateTime deletedTime;

    /**
     * 删除标志
     */
    @TableLogic
    private Integer isDelete;
}
