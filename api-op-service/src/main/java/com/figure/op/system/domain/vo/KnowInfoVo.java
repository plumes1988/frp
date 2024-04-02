package com.figure.op.system.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.figure.op.common.domain.BaseEntity;
import lombok.Data;

/**
 *
 * @author fsn
 */
@Data
public class KnowInfoVo extends BaseEntity {

    private Integer knowId;

    private String topic;

    private String knowKeywords;

    private Integer deviceId;

    private String device;

    private String content;

    @TableLogic
    private Integer isDelete;

}
