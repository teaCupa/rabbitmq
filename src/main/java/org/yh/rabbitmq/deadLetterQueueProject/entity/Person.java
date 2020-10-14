package org.yh.rabbitmq.deadLetterQueueProject.entity;

import java.io.Serializable;

/**
 * @Author: yh
 * @Date: 2020/10/13
 * @Description:   一种传输对象
 */


public class Person implements Serializable {
    private String name;
    private Integer age;

    public Person() {
    }

    public Person(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
