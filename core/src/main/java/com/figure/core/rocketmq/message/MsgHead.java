package com.figure.core.rocketmq.message;

import java.util.Date;

import com.figure.core.helper.DateHelper;
import com.figure.core.rocketmq.RocketMQConstants;
import com.figure.core.util.DateUtils;
import lombok.Data;

@Data
public class MsgHead{
	
	private Integer version = 1;
	
	private String businessCode = RocketMQConstants.BUSINESSCODE_ADD.toString(); //1添加，2删除，3修改
	
	private Long messageID;
	
	private Integer instructType = RocketMQConstants.INSTRUCTERTYPE_REQUEST; //0请求 1应答
	
	private Integer isDownloadFile = 0;
	
	private String downloadFileURL = "";
	
	private String fromWhere = RocketMQConstants.FROM_WHERE;
	
	private String messageTime = DateHelper.format(new Date(),DateHelper.patterns_masks[1]);

	public MsgHead(String businessCode, Integer instructType) {
		this.businessCode = businessCode;
		this.instructType = instructType;
	}

	public MsgHead() {
		super();
	}
	
}
