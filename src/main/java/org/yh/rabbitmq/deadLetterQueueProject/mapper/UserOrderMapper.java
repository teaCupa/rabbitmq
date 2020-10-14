package org.yh.rabbitmq.deadLetterQueueProject.mapper;

import org.apache.ibatis.annotations.Param;
import org.yh.rabbitmq.deadLetterQueueProject.entity.UserOrder;

public interface UserOrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserOrder record);

    int insertSelective(UserOrder record);

    UserOrder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserOrder record);

    int updateByPrimaryKey(UserOrder record);

    UserOrder selectByIdAndStatus(@Param("orderId") Integer orderId,@Param("status") int status);
}