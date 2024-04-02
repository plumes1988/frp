package com.figure.core.disruptor;

import lombok.Data;

@Data
public class ComplexObject {
    private int id;
    private String name;

    public ComplexObject(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
