package org.yh.rabbitmq.event;

import org.springframework.context.ApplicationEvent;

import java.io.Serializable;

/**
 * @Author: yh
 * @Date: 2020/10/12
 * @Description:   事件实体
 */


public class LoginEvent extends ApplicationEvent implements Serializable {
    private String username;
    private String password;
    private String ip;
    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
//    public LoginEvent(Object source) {
//        super(source);
//    }

    public LoginEvent(Object source, String username, String password, String ip) {
        super(source);
        this.username = username;
        this.password = password;
        this.ip = ip;
    }

    @Override
    public String toString() {
        return "LoginEvent{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", ip='" + ip + '\'' +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
