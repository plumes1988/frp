package com.figure.core.rocketmq.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CollectServerHeartbeat {
    private String serverCode;
    private Long timestamp;
    private MsgHead messageHead;
}
