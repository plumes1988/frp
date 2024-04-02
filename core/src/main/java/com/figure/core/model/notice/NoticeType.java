package com.figure.core.model.notice;

public enum NoticeType {
    DEVICE_OPERATION("设备操作", 0),
    DEVICE_ALARM("设备报警", 1),
    SIGNAL_ALARM("信号报警", 2),
    CHANNEL_ALARM("节目报警", 3),
    DEVICE_STATE_CHANGE("设备变更", 4);

    private final String description;

    private final Integer typeValue;

    NoticeType(String description, Integer typeValue) {
        this.typeValue = typeValue;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public Integer getTypeValue() {
        return typeValue;
    }
}
