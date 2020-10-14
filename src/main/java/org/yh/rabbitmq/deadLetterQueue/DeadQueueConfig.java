package org.yh.rabbitmq.deadLetterQueue;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: yh
 * @Date: 2020/10/13
 * @Description: 死信队列(延迟队列)的配置, 其具有延迟处理消息的功能
 *         消息进入死信队列，过TTL时间后,消息进入真正的队列被消费掉
 */

@Configuration
public class DeadQueueConfig {
    public static final String DEADQUEUENAME="deadQueue";      //死信队列名称
    public static final String BASICEXCHANGE="basicExchange";   //基本交换机名称
    public static final String BASICROUTINGKEY="basicRouting";   //基本路由名称
    public static final String REALQUEUENAME="realQueue";    //真正的消费队列名称
    public static final String DEADEXCHANGE="deadExchange";   //死信交换机名称
    public static final String DEADROUTINGKEY="deadRouting";   //死信路由名称
    @Bean
    public Queue basicDeadQueue(){
        //创建map,用于存放死信队列的相关成员
        Map<String,Object> map=new HashMap<>();
        map.put("x-dead-letter-exchange",DEADEXCHANGE);  //死信交换机
        map.put("x-dead-letter-routing-key",DEADROUTINGKEY); //死信路由
        map.put("x-message-ttl",30000);       //TTL 30s
        return new Queue(DEADQUEUENAME,true,false,false,map);
    }

    //创建"基本消息模型"的交换机，面向生产者
    @Bean
    public TopicExchange basicProducerExchange(){
        return new TopicExchange(BASICEXCHANGE,true,false);
    }

    //创建"基本消息模型"的绑定,面向生产者
    @Bean
    public Binding basicProducerBinding(){
        return BindingBuilder.bind(basicDeadQueue()).to(basicProducerExchange())
                .with(BASICROUTINGKEY);
    }

    //创建真正的队列,面向消费者
    @Bean
    public Queue realConsumerQueue(){
        return new Queue(REALQUEUENAME,true);
    }

    //创建死信交换机
    @Bean
    public TopicExchange basicDeadExchange(){
        return new TopicExchange(DEADEXCHANGE,true,false);
    }

    //创建死信路由
    @Bean
    public Binding basicDeadBinding(){
        return BindingBuilder.bind(realConsumerQueue()).to(basicDeadExchange())
                .with(DEADROUTINGKEY);
    }

}
