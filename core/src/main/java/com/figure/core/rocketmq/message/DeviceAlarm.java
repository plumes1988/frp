package com.figure.core.rocketmq.message;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeviceAlarm {
	
	private Long alarmId;
	
	private String deviceCode;
	
	private Integer alarmType;
	
	private String startTime;
	
	private Long duration;
	
	//0报警结束
	//1报警中
	private Integer alarmFlag;
	
	private String alarmName;
	
	private String alarmContent;
	
	private String devName;
	
	private MsgHead messageHead;

	private String indicatorCode;
	
}
