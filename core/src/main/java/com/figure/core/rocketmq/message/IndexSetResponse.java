package com.figure.core.rocketmq.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IndexSetResponse {
	
	private String requestld;

	/***
	 *响应消息主题
	 */
	private int errorCode;

	/**
	 ** 0:未响应 1:正常 2:异常 3:超时
	 */
	private String errorDesc;
	
	private Date responseTime;

	private String setValue;

	private int deviceId;

	private String indexCode;

	private MsgHead messageHead;

}
