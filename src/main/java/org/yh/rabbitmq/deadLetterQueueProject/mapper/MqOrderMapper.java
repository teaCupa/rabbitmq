package org.yh.rabbitmq.deadLetterQueueProject.mapper;

import org.yh.rabbitmq.deadLetterQueueProject.entity.MqOrder;

public interface MqOrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MqOrder record);

    int insertSelective(MqOrder record);

    MqOrder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MqOrder record);

    int updateByPrimaryKey(MqOrder record);
}