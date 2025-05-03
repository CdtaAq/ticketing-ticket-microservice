package com.example.ticketing.messaging;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class TicketMessageListener {

    @JmsListener(destination = "ticket.notification")
    public void receiveMessage(String message) {
        System.out.println("Received from ActiveMQ: " + message);
    }
}
