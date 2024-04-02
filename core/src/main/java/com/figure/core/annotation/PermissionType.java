package com.figure.core.annotation;

import lombok.Getter;

@Getter
public enum PermissionType {

    /**
     * <p>查看权限</p>
     */
    VIEW(1),
    /**
     * <p>修改权限</p>
     */
    EDIT(2),
    /**
     * <p>操作权限</p>
     */
    OPERATE(4);


    private final int value;

    PermissionType(int value) {
        this.value = value;
    }
}
