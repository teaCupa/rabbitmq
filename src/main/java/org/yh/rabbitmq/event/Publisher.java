package org.yh.rabbitmq.event;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @Author: yh
 * @Date: 2020/10/12
 * @Description: Spring事件驱动模型:生产者
 */

@Component
public class Publisher {
    private static final Logger log = getLogger(Publisher.class);
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public void sendMsg() {
        LoginEvent event = new LoginEvent(this, "zhangsan",
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), "111.111.111.111");
        applicationEventPublisher.publishEvent(event);
        log.info("Spring事件驱动模型-发送消息：{}", event);
    }
}
