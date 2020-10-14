package org.yh.rabbitmq.deadLetterQueue;

import org.slf4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yh.rabbitmq.deadLetterQueueProject.entity.UserOrder;
import org.yh.rabbitmq.deadLetterQueueProject.mapper.UserOrderMapper;
import org.yh.rabbitmq.deadLetterQueueProject.service.DeadUserOrderService;

import static org.slf4j.LoggerFactory.getLogger;
import static org.yh.rabbitmq.deadLetterQueue.DeadQueueConfig.REALQUEUENAME;

/**
 * @Author: yh
 * @Date: 2020/10/13
 * @Description: 队列消费者
 */

@Component
public class DeadConsumer {
    private static final Logger log = getLogger(DeadConsumer.class);
    @Autowired
    UserOrderMapper userOrderMapper;
    @Autowired
    DeadUserOrderService deadUserOrderService;
    /**
     * 监听真正队列的消息
     */
//    @RabbitListener(queues = REALQUEUENAME, containerFactory = "singleListenerContainer")
//    public void consumeMsg(@Payload Person p) {
//        log.info("死信队列实战，监听真正队列,消息内容为: {}", p);
//    }

    //要么把上面的监听配置注释掉，要么新创建一个监听队列
    @RabbitListener(queues = REALQUEUENAME, containerFactory = "singleListenerContainer")
    public void consumeMsg(Integer orderId) {
        log.info("用户下单支付超时，监听真正队列,消息内容为:orderId={}",orderId);
        //查询该用户下单记录id对应的支付状态是否为“已保存”  1-已保存 2-已付款 3-已取消
        UserOrder userOrder=userOrderMapper.selectByIdAndStatus(orderId,1);
        if(userOrder!=null){
            //该用户超时没有支付该笔订单,需要将该下单记录失效
            deadUserOrderService.updateUserOrderRecord(userOrder);
        }
    }
}
