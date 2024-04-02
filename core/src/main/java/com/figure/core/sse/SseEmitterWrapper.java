package com.figure.core.sse;

import com.figure.core.model.device.DeviceIndicatorParamRel;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.websocket.Session;
import java.util.Map;


public class SseEmitterWrapper {

    private String uuid;
    private String topic;
    private SseEmitter emitter;
    private Map<String, Object> params;

    private Session session;

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public SseEmitterWrapper(String uuid, String topic, SseEmitter emitter, Map<String, Object> params) {
        this.uuid = uuid;
        this.topic = topic;
        this.emitter = emitter;
        this.params = params;
    }

    public SseEmitterWrapper(String uuid, String topic, Session session, Map<String, Object> params) {
        this.uuid = uuid;
        this.topic = topic;
        this.session = session;
        this.params = params;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public SseEmitter getEmitter() {
        return emitter;
    }

    public void setEmitter(SseEmitter emitter) {
        this.emitter = emitter;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public void update(String topic, String uuid, Map<String, Object> params) {
        this.topic= topic;
        this.uuid = uuid;
        this.params = params;
    }
}
