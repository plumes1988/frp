package com.figure.system.eventBus.service;

import com.figure.system.eventBus.subscriber.EventSubscriber;
import com.google.common.eventbus.EventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventBusService {

    private final EventBus eventBus;

    @Autowired
    public EventBusService(EventSubscriber eventSubscriber) {
        this.eventBus = new EventBus();
        eventBus.register(eventSubscriber);
    }

    public void postEvent(Object event) {
        eventBus.post(event);
    }
}