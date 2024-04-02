package com.figure.system.eventBus.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateRedisCacheEvent {
    private String redisKey;
}
