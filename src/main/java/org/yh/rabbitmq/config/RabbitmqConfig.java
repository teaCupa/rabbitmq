package org.yh.rabbitmq.config;

import org.slf4j.Logger;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @Author: yh
 * @Date: 2020/10/13
 * @Description:
 */

@Configuration
public class RabbitmqConfig {
    private static final Logger log = getLogger(RabbitmqConfig.class);
    @Autowired   //链接工厂实例
    private CachingConnectionFactory cachingConnectionFactory;
    @Autowired   //消息监听器所在容器工厂配置类实例
    private SimpleRabbitListenerContainerFactoryConfigurer factoryConfigurer;
    /**
     *  单一消费者实例的配置
     */
    @Bean(name = "singleListenerContainer")
    public SimpleRabbitListenerContainerFactory listenerContainerFactory(){
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(cachingConnectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter()); //设置json传输格式
        factory.setConcurrentConsumers(1);   //设置并发消费者实例数目
        factory.setMaxConcurrentConsumers(1);
        factory.setPrefetchCount(1);  //每个并发实例预拉取的消息数据量
        factory.setAcknowledgeMode(AcknowledgeMode.AUTO); //自动确认消费
        return factory;
    }
    /**
     *  多个消费者实例的配置，针对高并发业务的配置
     */
    @Bean(name = "multiListenerContainer")
    public SimpleRabbitListenerContainerFactory multiListenerContainerFactory(){
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factoryConfigurer.configure(factory,cachingConnectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter()); //设置json传输格式
        factory.setAcknowledgeMode(AcknowledgeMode.AUTO);  //设置消息的确认消费模式
        factory.setConcurrentConsumers(10);   //设置并发消费者实例数目
        factory.setMaxConcurrentConsumers(15);
        factory.setPrefetchCount(10);
        return factory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(){
        //设置"发送消息后返回确认消息"
        cachingConnectionFactory.setPublisherReturns(true);
        RabbitTemplate rabbitTemplate = new RabbitTemplate(cachingConnectionFactory);
        rabbitTemplate.setMandatory(true);
        //消息发送成功后，则输出"消息发送成功"的反馈信息
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                log.info("消息发送成功:correlationData({}),ack({}),cause({})", correlationData, ack, cause);
            }
        });
        //消息发送失败
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                log.warn("消息丢失:exchange({}),routingKey({}),replyCode({}),replyText({})" +
                        "message({})",exchange,routingKey,replyCode,replyText,message);
            }
        });
        return rabbitTemplate;
    }
}
