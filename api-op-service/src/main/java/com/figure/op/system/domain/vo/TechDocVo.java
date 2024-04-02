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
public class TechDocVo extends BaseEntity {

    private Integer docId;

    private Integer techInfoId;

    private String docName;

    private String docType;

    private String path;

    private String docDes;

    @TableLogic
    private Integer isDelete;

}
