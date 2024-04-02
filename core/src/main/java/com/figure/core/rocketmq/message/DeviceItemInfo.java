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
public class DeviceItemInfo {

	public static Integer DATA_STATE_NORMAL = 0;

	public static Integer DATA_STATE_ALARM = 1;

	public static Integer DATA_STATE_OFFLINE = 2;
	
	private String indexCode;
	
	private int dataType;
	
	private int deci;
	
	private String indexData;

	//指标状态，0正常，1报警，2离线
	private int dataState;
	
	private int alarmCount;

	// isFromCache 0:不从缓存获取  1:从缓存获取

	private Integer isFromCache;
	
	private List<DeviceAlarmInfo> alarmInfoArray;


	private int alarmStatus;
	
}
