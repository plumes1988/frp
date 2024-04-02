package com.figure.core.entity;

import lombok.Data;

@Data
public class ServiceHealthStatus {
    private boolean isOK;
    private String desc;
}
