package com.figure.op.videoplay.domain.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @author sdt
 * @version 1.0
 * @description:TODO
 * @date 2023/9/19 16:06
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class WarningRecordListSelectQuery {
    String deviceName;
    String partName;
    String cameraName;
    String startTime;
    String endTime;
}
