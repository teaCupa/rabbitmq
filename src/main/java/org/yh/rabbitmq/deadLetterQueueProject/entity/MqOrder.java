package org.yh.rabbitmq.deadLetterQueueProject.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * mq_order
 * @author 
 */
@Data
public class MqOrder implements Serializable {
    private Integer id;

    /**
     * 下单记录id
     */
    private Integer orderId;

    /**
     * 下单记录失效的时刻
     */
    private Date businessTime;

    /**
     * 备注信息
     */
    private String memo;

    private static final long serialVersionUID = 1L;
}