package com.figure.op.cameramanager.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author sdt
 * @version 1.0
 * @description:TODO
 * @date 2023/9/13 10:49
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pair<T,T1> {
    T key1;
    T1 key2;
}
