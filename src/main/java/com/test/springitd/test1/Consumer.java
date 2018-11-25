package com.test.springitd.test1;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

    public void handleMessage(Message<?> message) throws MessagingException {
        Object payload = message.getPayload();

        System.out.println("Thread:" + Thread.currentThread().getName() + "Received  - " + payload + "\n");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
        }

    }
}