package org.yh.rabbitmq.deadLetterQueueProject.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.yh.rabbitmq.deadLetterQueueProject.entity.Response;
import org.yh.rabbitmq.deadLetterQueueProject.entity.UserOrderDto;
import org.yh.rabbitmq.deadLetterQueueProject.service.DeadUserOrderService;

/**
 * @Author: yh
 * @Date: 2020/10/13
 * @Description:
 */

@RestController
public class UserOrderController {
    private static final Logger log = LoggerFactory.getLogger(UserOrderController.class);
    @Autowired
    DeadUserOrderService deadUserOrderService;
    @PostMapping("/user/order/push")
    public Response push(@RequestBody @Validated UserOrderDto dto, BindingResult result){
        if(result.hasErrors()){
            return new Response(400,"参数不合法",null);
        }
        deadUserOrderService.pushUserOrder(dto);
        return new Response(200,"下单成功",null);
    }
}
