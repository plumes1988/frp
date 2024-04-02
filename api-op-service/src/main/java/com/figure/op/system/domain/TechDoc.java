package com.figure.op.system.domain;

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
public class TechDoc extends BaseEntity {

    @TableId("docId")
    private Integer docId;

    @TableField("techInfoId")
    private Integer techInfoId;

    @TableField("docName")
    private String docName;

    @TableField("docType")
    private String docType;

    @TableField("path")
    private String path;

    @TableField("docDes")
    private String docDes;

    @TableLogic
    private Integer isDelete;

}
