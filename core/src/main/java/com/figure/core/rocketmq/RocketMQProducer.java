package com.figure.core.rocketmq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.figure.core.helper.DateHelper;
import com.figure.core.rocketmq.struct.MessageBase;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class RocketMQProducer {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    private final AtomicInteger messageId = new AtomicInteger(0);

    /**
     * 消息序号生成,如果到达Integer.MAX_VALUE,则重置
     *
     * @return 消息序号
     */
    public Integer getAtomic() {
        int seq = messageId.incrementAndGet();
        if (seq == Integer.MAX_VALUE) {
            messageId.set(0);
        }
        return seq;
    }

    public String send(String topic, String tags, MessageBase base) {
        if (base.getMessageHead().getMessageID() == null) {
            base.getMessageHead().setMessageID(new Date().getTime());
        }
        base.getMessageHead().setMessageTime(DateHelper.format(new Date(), DateHelper.patterns_masks[1]));
        SendResult result = null;
        try {
            StopWatch stop = new StopWatch();
            stop.start();
            String dest = topic;
            if (tags != null && !tags.equals("")) {
                dest += ":" + tags;
            }
            SimplePropertyPreFilter filter = new SimplePropertyPreFilter();
            filter.getExcludes().add("rocketmqTopic");
            filter.getExcludes().add("rocketmqTag");
            rocketMQTemplate.convertAndSend(dest, JSON.toJSONString(base, filter).getBytes(RemotingHelper.DEFAULT_CHARSET));
            stop.stop();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
        return null;
    }
}
