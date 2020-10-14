package org.yh.rabbitmq.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Author: yh
 * @Date: 2020/2/14
 * @Description: org.yh.rabbitmq
 */

@Component
public class FanoutReceiver {
    @RabbitListener(queues = "queue-one")
    public void handle1(String message) {
        System.out.println("FanoutReceiver:handler1:" + message);
    }

    @RabbitListener(queues = "queue-two")
    public void handle2(String message) {
        System.out.println("FanoutReceiver:handler2:" + message);
    }
}
