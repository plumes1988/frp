package com.figure.core.rocketmq.struct;

import com.figure.core.rocketmq.RocketMQConstants;
import lombok.Data;

@Data
public class MessageHead {

    private String version = "1";

    private Integer businessCode = RocketMQConstants.BUSINESSCODE_ADD; //1添加，2删除，3修改

    private Long messageID;

    private Integer instructType = RocketMQConstants.INSTRUCTERTYPE_REQUEST; //0请求 1应答

    private Integer isDownloadFile = 0;

    private String downloadFileUrl = "";

    private String fromWhere = RocketMQConstants.FROM_WHERE;

    private String messageTime;

    public MessageHead(Integer businessCode, Integer instructType) {
        this.businessCode = businessCode;
        this.instructType = instructType;
    }

    public MessageHead() {

    }
}
