package org.yh.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.AbstractJavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.yh.rabbitmq.config.RabbitFanoutConfig;
import org.yh.rabbitmq.config.RabbitTopicConfig;
import org.yh.rabbitmq.deadLetterQueueProject.entity.Person;
import org.yh.rabbitmq.deadLetterQueueProject.mapper.UserOrderMapper;
import org.yh.rabbitmq.event.Publisher;
import org.yh.rabbitmq.deadLetterQueue.DeadPublisher;

import static org.yh.rabbitmq.config.RabbitDirectConfig.DIRECTEXCHANGENAME;

@SpringBootTest
class RabbitmqApplicationTests {
 @Autowired
    RabbitTemplate rabbitTemplate;

    @Test
    void contextLoads(){
        //设置消息传输格式为JSON
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.setExchange(DIRECTEXCHANGENAME);
        Person p=new Person("zhangsan",123);
        rabbitTemplate.convertAndSend("directRouteKey1", p, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                MessageProperties messageProperties = message.getMessageProperties();
                messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);//设置消息持久化
                messageProperties.setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME,
                        Person.class);    //设置消息的类型
                return message;
            }
        });
    }
    @Test
    public void multiDirectTest() throws JsonProcessingException {
        rabbitTemplate.convertAndSend(DIRECTEXCHANGENAME,"directRouteKey1","hello object!");
        rabbitTemplate.convertAndSend(DIRECTEXCHANGENAME,"directRouteKey2","hello object!");
    }
    @Test
    public void fanoutTest(){
        rabbitTemplate.convertAndSend(RabbitFanoutConfig.FANOUTNAME,null,"hello-fanout");
    }
    @Test
    public void topicTest(){
        rabbitTemplate.convertAndSend(RabbitTopicConfig.TOPICEXCHANGENAME,"topic.route.java.key","javaTopic");
        rabbitTemplate.convertAndSend(RabbitTopicConfig.TOPICEXCHANGENAME,"topic.route.java.php.key","multiTopic");
    }

    @Autowired
    private Publisher publisher;
    @Test
    public void testSpringEvent(){
        publisher.sendMsg();
    }

    @Autowired
    DeadPublisher deadPublisher;
    @Test
    public void deadQueueTest(){
        Person p = new Person("zhangsan", 20);
        deadPublisher.sendMsg(p);
        p = new Person("lisi", 18);
        deadPublisher.sendMsg(p);
        try {
            Thread.sleep(30000);    //为了看到消费者监听真正消费队列的消息
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Autowired
    UserOrderMapper userOrderMapper;
    @Test
    public void testSql(){
        System.out.println("hi:"+userOrderMapper.selectByIdAndStatus(7,1));
    }
}
