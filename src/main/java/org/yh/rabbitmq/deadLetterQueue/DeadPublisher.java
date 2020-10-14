package org.yh.rabbitmq.deadLetterQueue;

import org.slf4j.Logger;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.AbstractJavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yh.rabbitmq.deadLetterQueueProject.entity.Person;

import static org.slf4j.LoggerFactory.getLogger;
import static org.yh.rabbitmq.deadLetterQueue.DeadQueueConfig.*;

/**
 * @Author: yh
 * @Date: 2020/10/13
 * @Description:
 */

@Component
public class DeadPublisher {
    private static final Logger log = getLogger(DeadPublisher.class);
    @Autowired
    RabbitTemplate rabbitTemplate;
    public void sendMsg(Person p){
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.setExchange(BASICEXCHANGE);  //设置基本交换机
        rabbitTemplate.setRoutingKey(BASICROUTINGKEY);  //设置基本路由
        rabbitTemplate.convertAndSend(p, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                MessageProperties messageProperties = message.getMessageProperties();
                messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                messageProperties.setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME,Person.class);
                messageProperties.setExpiration(String.valueOf(10000));
                return message;
            }
        });
    }

    /**
     *   用户下单支付超时,将用户下单的记录id发到死信队列
     * @param orderId
     */
    public void sendOrderId(Integer orderId){
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.setExchange(BASICEXCHANGE);  //设置基本交换机
        rabbitTemplate.setRoutingKey(BASICROUTINGKEY);  //设置基本路由
        rabbitTemplate.convertAndSend(orderId, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                MessageProperties messageProperties = message.getMessageProperties();
                messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                messageProperties.setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME,Integer.class);
                messageProperties.setExpiration(String.valueOf(300000));
                return message;
            }
        });
        //消息进入死信队列，过TTL时间后,消息进入真正的队列被消费掉
        log.info("用户下单支付超时-发送用户下单记录id的消息至死信队列-内容为：orderId={}",orderId);
    }
}
