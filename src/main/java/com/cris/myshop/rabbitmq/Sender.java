package com.cris.myshop.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Sender {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 将待消费消息发送到队列中
     */
    public void send(String exchange, String routingKey, String msg) {
        rabbitTemplate.convertAndSend(exchange, routingKey, msg);
    }
}
