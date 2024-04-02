package com.figure.core.sse;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public abstract class SseMessage {

    @TableField(exist = false)
    private String topic;

    public abstract boolean matchParams(SseEmitterWrapper sseEmitterWrapper);

    public boolean match(SseEmitterWrapper sseEmitterWrapper){
        if(sseEmitterWrapper==null){
            return false;
        }
        if(!sseEmitterWrapper.getTopic().equals(topic)){
            return false;
        }
        return matchParams(sseEmitterWrapper);
    }
}
