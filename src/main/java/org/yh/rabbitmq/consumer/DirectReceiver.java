package org.yh.rabbitmq.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Author: yh
 * @Date: 2020/2/14
 * @Description: org.yh.rabbitmq
 */

@Component
public class DirectReceiver {
    @RabbitListener(queues = "hello-DirectQueueOne")
    public void handler1(String msg){
        System.out.println("DirectOneReceiver: "+msg);
    }
    @RabbitListener(queues = "hello-DirectQueueTwo")
    public void handler2(String msg){
        System.out.println("DirectTwoReceiver: "+msg);
    }
}
