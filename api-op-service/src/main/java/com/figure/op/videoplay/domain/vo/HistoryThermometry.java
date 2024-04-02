package com.figure.op.videoplay.domain.vo;

import com.figure.op.cameramanager.domain.Pair;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author sdt
 * @version 1.0
 * @description:TODO
 * @date 2023/9/19 14:54
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class HistoryThermometry implements Serializable {
    private static final long serialVersionUID = -769664216639419944L;
    String partCode;
    String partName;
    /**
     * 温度列表
     */
    List<Pair<String,Float>> list;
}
