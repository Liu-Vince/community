package com.lwc.community.util;

import com.lwc.community.entity.User;
import org.springframework.stereotype.Component;

/**
 * @author 刘文长
 * @version 1.0
 * 持有用户信息，用于代替session对象
 */
@Component
public class HostHolder {

    private ThreadLocal<User> users = new ThreadLocal<>();

    public void setUser(User user) {
        users.set(user);
    }

    public User getUser() {
        return users.get();
    }

    public void clear() {
        users.remove();
    }

}
