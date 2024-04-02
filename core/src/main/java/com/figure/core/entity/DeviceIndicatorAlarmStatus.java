package com.figure.core.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeviceIndicatorAlarmStatus {
    private Long timestamp;
    private String confirm;
}
