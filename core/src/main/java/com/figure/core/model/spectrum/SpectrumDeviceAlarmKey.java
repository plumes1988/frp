package com.figure.core.model.spectrum;

import lombok.Data;

@Data
public class SpectrumDeviceAlarmKey {

    private String deviceCode;

    private Integer alarmType;

    public SpectrumDeviceAlarmKey(){}

    public SpectrumDeviceAlarmKey(String deviceCode, Integer alarmType) {

        this.deviceCode = deviceCode;
        this.alarmType = alarmType;

    }
}
