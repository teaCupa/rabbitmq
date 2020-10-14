package org.yh.rabbitmq.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Author: yh
 * @Date: 2020/2/14
 * @Description: org.yh.rabbitmq
 */

@Component
public class TopicReceiver {
    @RabbitListener(queues = "topic_queue1")
    public void consumeTopicQueue1(String message) {
        System.out.println("consumeTopicQueue1:" + message);
    }

    @RabbitListener(queues = "topic_queue2")
    public void consumeTopicQueue2(String message) {
        System.out.println("consumeTopicQueue2:" + message);
    }

}
