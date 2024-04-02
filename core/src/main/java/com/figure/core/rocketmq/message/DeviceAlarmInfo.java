package com.figure.core.rocketmq.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeviceAlarmInfo {
	
//	private int alarmId;
//	
//	private int alarmState;
//	
//	private Long alarmDuration;
	
	private int alarmType;
	
	private String alarmStartTime;
	
	private int alarmDuration;
	
}
