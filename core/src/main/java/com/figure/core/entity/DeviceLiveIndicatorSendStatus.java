package com.figure.core.entity;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeviceLiveIndicatorSendStatus {
    private String indicatorValue;
    private Long lastSendTime;
}
