package org.yh.rabbitmq.event;

import org.slf4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @Author: yh
 * @Date: 2020/10/12
 * @Description: Spring事件驱动模型：消费者
 */

@Component
@EnableAsync
public class Consumer implements ApplicationListener<LoginEvent> {
    private static final Logger log = getLogger(Consumer.class);
    /*
     * 监听消息
     */
    @Override
    @Async
    public void onApplicationEvent(LoginEvent event) {
        log.info("Spring事件驱动模型-接收消息：{}",event);
    }
}
