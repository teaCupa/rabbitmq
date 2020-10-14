package org.yh.rabbitmq.deadLetterQueueProject.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * user_order
 * @author 
 */
@Data
@ToString
public class UserOrder implements Serializable {
    private Integer id;

    private String orderNo;

    private Integer userId;

    /**
     * 1=已保存  2=已付款 3=已取消
     */
    private Integer status;

    private Integer isActive;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;
}