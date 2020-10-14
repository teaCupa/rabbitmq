package org.yh.rabbitmq.deadLetterQueueProject.entity;

import lombok.Data;
import lombok.ToString;

/**
 * @Author: yh
 * @Date: 2020/10/14
 * @Description:
 */

@Data
@ToString
public class Response<T> {
    private Integer code;
    private String msg;
    private T data;

    public Response(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
