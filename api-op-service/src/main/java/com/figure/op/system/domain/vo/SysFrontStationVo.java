package com.figure.op.system.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.figure.op.common.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author fsn
 */
@Data
public class SysFrontStationVo extends BaseEntity {

    private Integer frontId;

    private String frontName;

    private Integer supFrontId;

    private String frontType;

}
