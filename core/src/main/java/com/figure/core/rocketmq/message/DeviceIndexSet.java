package com.figure.core.rocketmq.message;

import com.figure.core.rocketmq.RocketMQConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeviceIndexSet {

	/**
	 *id标识
	 */

	private String requestId;
	
	private String deviceCode;

	private Integer deviceId;
	
	private String indexCode;
	
	private String SetData;

	/**
	 * 服务器编号
	 */

	private String serverCode;

	private Integer userId;
	
	private Integer isSuccessed;
	
	private String errorMessage;

	private MsgHead messageHead = new MsgHead(RocketMQConstants.BUSINESSCODE_EDIT.toString(),RocketMQConstants.INSTRUCTERTYPE_REQUEST);

}
