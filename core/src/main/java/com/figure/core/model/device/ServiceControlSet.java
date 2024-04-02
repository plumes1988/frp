package com.figure.core.model.device;

import lombok.Data;

import java.util.List;

@Data
public class ServiceControlSet {

    private List<String> serviceCodeArray;

    private Integer controlMode;
}
