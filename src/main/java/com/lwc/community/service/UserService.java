package com.lwc.community.service;

import com.lwc.community.dao.UserMapper;
import com.lwc.community.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 刘文长
 * @version 1.0
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;
    public User findUserById(int id){
        return userMapper.selectById(id);
    }
}
