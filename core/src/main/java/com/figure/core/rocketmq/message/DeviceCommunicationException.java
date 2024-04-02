package com.figure.core.rocketmq.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeviceCommunicationException {
    private String deviceCode;
    private Long timestamp;
    private MsgHead messageHead;
}
