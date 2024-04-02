package com.figure.core.sse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.figure.core.db.JavaMemoryDb;
import javax.websocket.Session;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicReference;

import static com.figure.core.sse.Constants.*;

public class SseEmitterManager {

    // 创建一个序列化配置对象
    public static SerializeConfig FASTJSON_DEFAULT_CONFIG = new SerializeConfig(){{
        // 设置日期格式为 "yyyy-MM-dd HH:mm:ss"
        SimpleDateFormatSerializer dateFormat = new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss");
        put(Date.class, dateFormat);
    }};

    public static volatile Map<String,ConcurrentLinkedQueue<SseEmitterWrapper>>  sseEmitterWrappers = new ConcurrentHashMap(){{
        put(LOG_DEVICE_STATE_INDICATOR_CHANGE,new ConcurrentLinkedQueue<>());
        put(DEVICE_ALARM,new ConcurrentLinkedQueue<>());
        put(LOG_DEVICE_CONTROL,new ConcurrentLinkedQueue<>());
        put(DEVICE_INDICATOR,new ConcurrentLinkedQueue<>());
        put(DEVICE_SERVICE_ALARM,new ConcurrentLinkedQueue<>());
        put(LOG_SERVICE,new ConcurrentLinkedQueue<>());
        put(DEVICE_INDICATOR_FOR_DEVICE_CONTROL,new ConcurrentLinkedQueue<>());
        put(SPECTRUM_ALARM_MESSAGE,new ConcurrentLinkedQueue<>());
        put(NOTICE_MESSAGE,new ConcurrentLinkedQueue<>());
    }};

    public static void addSseEmitter(SseEmitterWrapper sseEmitterWrapper){
        String topic = sseEmitterWrapper.getTopic();
        ConcurrentLinkedQueue<SseEmitterWrapper> topic_sseEmitterWrappers = sseEmitterWrappers.get(topic);
        if(topic_sseEmitterWrappers==null){
            topic_sseEmitterWrappers = new ConcurrentLinkedQueue<>();
            sseEmitterWrappers.put(topic,topic_sseEmitterWrappers);
        }
        topic_sseEmitterWrappers.add(sseEmitterWrapper);
    }

    public static void sendMessageToAllMatchSseEmitter(SseMessage sseMessage) {
        String topic = sseMessage.getTopic();
        ConcurrentLinkedQueue<SseEmitterWrapper> topic_sseEmitterWrappers = sseEmitterWrappers.get(topic);
        if(topic_sseEmitterWrappers!=null){
            Iterator<SseEmitterWrapper> iterator = topic_sseEmitterWrappers.iterator();
            while (iterator.hasNext()) {
                SseEmitterWrapper wrapper = iterator.next();
                if(sseMessage.match(wrapper)){
                    try {
                        synchronized (wrapper) {//加锁，保证不会同时被执行
                            // 检查会话是否已关闭
                            if (wrapper.getSession().isOpen()) {
                                // 向会话发送消息
                                wrapper.getSession().getBasicRemote().sendText(JSON.toJSONString(sseMessage,FASTJSON_DEFAULT_CONFIG));
                            } else {
                                // 会话已关闭，从列表中移除
                                iterator.remove();
                                JavaMemoryDb.clear_ONLY_SEND_CHANGE_VALUE_TO_WEB_CACHE(wrapper.getUuid());
                            }
                        }
                    } catch (IOException e){
                        System.out.println("ws send exception : " +e.toString());
                    }
                }
            }
        }
    }

    public static void removeSession(Session session) {
        Set<String> keys = sseEmitterWrappers.keySet();
        for(String key:keys){
            ConcurrentLinkedQueue<SseEmitterWrapper> topic_sseEmitterWrappers = sseEmitterWrappers.get(key);
            AtomicReference<String> uuid =  new AtomicReference<>(null);
            topic_sseEmitterWrappers.removeIf(sseEmitterWrapper -> {
                Boolean flag = sseEmitterWrapper.getSession()!=null && sseEmitterWrapper.getSession().getId().equals(session.getId());
                if(flag){
                    uuid.set(sseEmitterWrapper.getUuid());
                }
                return flag;
            });
            if(uuid.get()!=null){
                JavaMemoryDb.clear_ONLY_SEND_CHANGE_VALUE_TO_WEB_CACHE(uuid.get());
            }
        }
    }

    public static SseEmitterWrapper getEseEmitterWrapper(Session session,String topic) {
        ConcurrentLinkedQueue<SseEmitterWrapper> topic_sseEmitterWrappers = sseEmitterWrappers.get(topic);
        Optional<SseEmitterWrapper> foundSseEmitterWrapper = topic_sseEmitterWrappers.stream()
                .filter(sseEmitterWrapper -> sseEmitterWrapper.getSession()!=null && sseEmitterWrapper.getSession().getId().equals(session.getId()))
                .findFirst();
        // 检查是否找到元素
        if (foundSseEmitterWrapper.isPresent()) {
            return foundSseEmitterWrapper.get();
        } else {
            return null;
        }
    }

}
