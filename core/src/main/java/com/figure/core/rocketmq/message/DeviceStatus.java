package com.figure.core.rocketmq.message;

import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeviceStatus {
	
	private String deviceCode;
	
	private int indexCount;
	
	private int dataState;
	
	private List<DeviceItemInfo> indexInfoArray;
	
	private MsgHead messageHead;

	private int deviceId;
}
