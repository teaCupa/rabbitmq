package org.yh.rabbitmq.deadLetterQueueProject.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yh.rabbitmq.deadLetterQueue.DeadPublisher;
import org.yh.rabbitmq.deadLetterQueueProject.entity.MqOrder;
import org.yh.rabbitmq.deadLetterQueueProject.entity.UserOrder;
import org.yh.rabbitmq.deadLetterQueueProject.entity.UserOrderDto;
import org.yh.rabbitmq.deadLetterQueueProject.mapper.MqOrderMapper;
import org.yh.rabbitmq.deadLetterQueueProject.mapper.UserOrderMapper;

import java.util.Date;

/**
 * @Author: yh
 * @Date: 2020/10/13
 * @Description:
 */

@Service
public class DeadUserOrderService {
    private static final Logger log = LoggerFactory.getLogger(DeadUserOrderService.class);
    @Autowired
    UserOrderMapper userOrderMapper;
    @Autowired
    MqOrderMapper mqOrderMapper;
    @Autowired
    DeadPublisher deadPublisher;
    public void pushUserOrder(UserOrderDto dto) {
        UserOrder userOrder = new UserOrder();
        BeanUtils.copyProperties(dto,userOrder);
        userOrder.setStatus(1);  //设置支付状态为已保存
        userOrder.setCreateTime(new Date());
        userOrderMapper.insertSelective(userOrder);
        log.info("用户下单成功,下单信息：{}",userOrder);
        Integer orderId = userOrder.getId();
        deadPublisher.sendOrderId(orderId);  //死信队列TTL=10s,意味着10s后开始处理消息
    }

    public void updateUserOrderRecord(UserOrder userOrder) {
        if(userOrder!=null){
            userOrder.setIsActive(0);   //让订单失效
            userOrder.setUpdateTime(new Date());
            userOrderMapper.updateByPrimaryKey(userOrder);
            //记录失效订单信息至mq_order表
            MqOrder mqOrder = new MqOrder();
            mqOrder.setBusinessTime(new Date());  //失效时间
            mqOrder.setOrderId(userOrder.getId());
            mqOrder.setMemo("用户订单失效，失效id为orderId="+userOrder.getId());
            mqOrderMapper.insertSelective(mqOrder);
        }
    }
}
