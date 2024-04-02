package com.figure.core.entity;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeviceCurIndicatorValue {
    private String indicatorValue;
    private String collectTime;
    private String updateTime;
}
