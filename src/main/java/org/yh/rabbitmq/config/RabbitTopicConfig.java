package org.yh.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: yh
 * @Date: 2020/2/14
 * @Description: topicExchange消息模型 和directExchange模型一样，需要指定交换机和路由
 *                     其功能更强大，支持通配符*，#
 *               *：匹配一个任意的单词  #:匹配0个或一个或多个单词
 *               以 . 作为分词器，比如topic.*.queue 匹配topic.a_b.queue
 *               但不匹配topic.a.b.queue,而topic.#.queue匹配这两个样例
 */

@Configuration
public class RabbitTopicConfig {
    public final static String TOPICEXCHANGENAME = "sang-topic";

    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange(TOPICEXCHANGENAME, true, false);
    }

    //创建队列1
    @Bean
    Queue topicQueueOne() {
        return new Queue("topic_queue1");
    }

    //创建队列2
    @Bean
    Queue topicQueueTwo() {
        return new Queue("topic_queue2");
    }


    @Bean
    Binding topicBindingOne() {
        return BindingBuilder.bind(topicQueueOne()).to(topicExchange()).with("topic.route.*.key");
    }

    @Bean
    Binding topicBindingTwo() {
        return BindingBuilder.bind(topicQueueTwo()).to(topicExchange()).with("topic.route.#.key");
    }

}
