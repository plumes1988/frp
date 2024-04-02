package com.figure;
import com.figure.core.disruptor.Event;
import com.figure.core.disruptor.EventFactory;
import com.figure.core.disruptor.EventProcessor;
import com.figure.core.model.device.DeviceAlarmMessage;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.Executors;

public class DisruptorTest {

    public static void main(String[] args) {

        int numConsumers = 5;

        int bufferSize = 1024;
        Disruptor<Event> disruptor = new Disruptor(new EventFactory(), bufferSize, Executors.defaultThreadFactory(), ProducerType.MULTI, new YieldingWaitStrategy());

        EventProcessor[] eventProcessors = new EventProcessor[numConsumers];
        for (int i = 0; i < numConsumers; i++) {
            eventProcessors[i] = new EventProcessor();
        }

        disruptor.handleEventsWith(eventProcessors);
        disruptor.start();


        RingBuffer<Event> ringBuffer = disruptor.getRingBuffer();

        int numProducers = 20;

        int numEventsPerProducer = 10;

        for (int i = 0; i < numProducers; i++) {
            final int producerId = i;
            new Thread(() -> {
                for (int j = 0; j < numEventsPerProducer; j++) {
                    long sequence = ringBuffer.next();
                    Event event = ringBuffer.get(sequence);
                    DeviceAlarmMessage deviceAlarmMessage = new DeviceAlarmMessage(); // 创建复杂对象
                    deviceAlarmMessage.setAlarmMsg(producerId+"-"+j);
                    event.setDeviceAlarmMessage(deviceAlarmMessage);
                    ringBuffer.publish(sequence);
                }
            }).start();
        }


    }
}