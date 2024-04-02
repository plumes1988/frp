package com.figure.op.system.domain.vo;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.figure.op.common.domain.BaseEntity;
import lombok.Data;

/**
 *
 * @author fsn
 */
@Data
public class KnowInfoListVo extends BaseEntity {

    private Integer knowId;

    private String topic;

    private String knowKeywords;

    private Integer deviceId;

    private String device;

    private String content;

    @TableLogic
    private Integer isDelete;

}
