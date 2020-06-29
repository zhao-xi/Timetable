package evostar.service;

import evostar.mapper.UserMapper;
import evostar.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    public User getUserByUsername(String username) {
        User user = userMapper.getUserByUsername(username);
        return user;
    }
}
