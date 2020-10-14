package org.yh.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



/**
 * @Author: yh
 * @Date: 2020/2/14
 * @Description: directExchange消息模型 要求指定交换机和路由，并绑定到特定的队列
 *                  是比较常用的模型
 */

@Configuration
public class RabbitDirectConfig {
    public final static String DIRECTEXCHANGENAME ="directExchangeName";
    /** 创建简单消息模型：队列、交换机和路由*/

    //创建队列1
    @Bean
    Queue directQueueOne(){
        return new Queue("hello-DirectQueueOne");
    }
    //创建队列2
    @Bean
    Queue directQueueTwo(){
        return new Queue("hello-DirectQueueTwo");
    }
    //创建交换机
    @Bean
    DirectExchange directExchange(){
        return new DirectExchange(DIRECTEXCHANGENAME,true,false);
    }
    //创建绑定1
    @Bean
    Binding DirectBindingOne(){
        return BindingBuilder.bind(directQueueOne()).to(directExchange()).with("directRouteKey1");
    }
    //创建绑定2
    @Bean
    Binding DirectBindingTwo(){
        return BindingBuilder.bind(directQueueTwo()).to(directExchange()).with("directRouteKey2");
    }
}
