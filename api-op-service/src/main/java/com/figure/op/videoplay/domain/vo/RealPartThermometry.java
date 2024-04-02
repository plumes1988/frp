package com.figure.op.videoplay.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author sdt
 * @version 1.0
 * @description:TODO
 * @date 2023/9/13 16:40
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class RealPartThermometry {
    Integer ruleId;
    String partName;
    Float thermometry;
    Boolean exceedFlag;
}
