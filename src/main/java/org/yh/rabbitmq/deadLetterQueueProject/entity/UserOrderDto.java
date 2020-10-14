package org.yh.rabbitmq.deadLetterQueueProject.entity;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author: yh
 * @Date: 2020/10/14
 * @Description:
 */

@Data
@ToString
public class UserOrderDto implements Serializable {
     @NotBlank
    private String orderNo;   //订单编号
    @NotNull
    private Integer userId;  //用户id
}
