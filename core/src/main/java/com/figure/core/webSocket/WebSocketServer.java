package com.figure.core.webSocket;

import com.alibaba.fastjson.JSON;
import com.figure.core.model.alarm.AlarmMessageInfo;
import com.figure.core.provider.BeanProvider;
import com.figure.core.service.device.IDeviceIndicatorParamRelService;
import com.figure.core.sse.SseEmitterManager;
import com.figure.core.sse.SseEmitterWrapper;
import com.figure.core.util.JSONUtil;
import com.google.gson.Gson;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static com.figure.core.sse.Constants.SEND_REQUEST;
import static com.figure.core.sse.Constants.SIGNAL_ALARM;

@ServerEndpoint(value ="/webSocket")
@Scope("singleton")
@Component
public class WebSocketServer {

    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    public static final AtomicInteger onlineNum = new AtomicInteger();

    //concurrent包的线程安全Set，用来存放每个客户端对应的WebSocketServer对象。
    public static final ConcurrentHashMap<String, Session> sessionPools = new ConcurrentHashMap<>();

    //发送消息
    public void sendMessage(Session session, Message message) throws IOException {
        if(session != null){
            synchronized (session) {
                session.getBasicRemote().sendText(new Gson().toJson(message));
            }
        }
    }
    //给指定用户发送信息
    public void sendInfo(String token, Message message){
        Session session = sessionPools.get(token);
        try {
            sendMessage(session, message);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //给指定用户发送信息
    public void sendInfo(Message message){
        Enumeration<String> keys = sessionPools.keys();
        while(keys.hasMoreElements()) {
            String token =  keys.nextElement();
            sendInfo(token,message);
        }
    }

    //建立连接成功调用
    @OnOpen
    public void onOpen(Session session){
        addOnlineCount();
        System.out.println("加入webSocket！当前人数为" + onlineNum);
    }

    //关闭连接时调用
    @OnClose
    public void onClose(Session session){
        SseEmitterManager.removeSession(session);
        subOnlineCount();
        Enumeration<String> keys = sessionPools.keys();
        while(keys.hasMoreElements()) {
             String token =  keys.nextElement();
             if(sessionPools.get(token)==session){
                 sessionPools.remove(token);
                 System.out.println("token:"+token + "-断开webSocket连接！当前人数为" + onlineNum);
             }
        }
    }

    //收到客户端信息
    @OnMessage
    public void onMessage(Session session,String msg) throws IOException{
        System.out.println("收到数据:"+msg);
        Message message = JSON.parseObject(msg, Message.class);
        if("1".equals(message.getType())&& !SEND_REQUEST.equals(message.getTopic())){
            String topic = message.getTopic();
            String uuid =  message.getUuid();
            String params_str =  message.getParams();
            Map<String, Object> params = JSON.parseObject(params_str).getInnerMap();
            SseEmitterWrapper sseEmitterWrapper = SseEmitterManager.getEseEmitterWrapper(session,topic);
            if(sseEmitterWrapper!=null){
                sseEmitterWrapper.update(topic,uuid,params);
            }else{
                sseEmitterWrapper = new SseEmitterWrapper(uuid,topic,session,params);
                SseEmitterManager.addSseEmitter(sseEmitterWrapper);
            }
        }

        if(SEND_REQUEST.equals(message.getTopic())){
            String params_str =  message.getParams();
            Map<String, Object> params = JSON.parseObject(params_str).getInnerMap();
            if(params!=null){
                if("send_cur_device_indicator".equals(params.get("command"))){
                    IDeviceIndicatorParamRelService deviceIndicatorParamRelService = BeanProvider.getBean(IDeviceIndicatorParamRelService.class);
                    deviceIndicatorParamRelService.sendCurDeviceIndicator(params);
                }
            }
        }

        if("login".equals(message.getTopic())){
            sessionPools.put(message.getBody(), session);
        }
        if("loginOut".equals(message.getTopic())){
            onClose(session);
        }
        //todo
    }

    //错误时调用
    @OnError
    public void onError(Session session, Throwable throwable){
        System.out.println("发生错误");
        throwable.printStackTrace();
    }

    public static void addOnlineCount(){
        onlineNum.incrementAndGet();
    }

    public static void subOnlineCount() {
        onlineNum.decrementAndGet();
    }

    public void sendAlarm(AlarmMessageInfo entity) {
        Message message = new Message();
        String topic = SIGNAL_ALARM;
        message.setTopic(topic);
        message.setBody(JSONUtil.getGson().toJson(entity));
        sendInfo(message);
    }
}