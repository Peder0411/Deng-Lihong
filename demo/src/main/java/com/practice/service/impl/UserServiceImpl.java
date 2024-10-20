package com.practice.service.impl;

import com.practice.common.utils.PasswordUtil;
import com.practice.entity.User;
import com.practice.mapper.UserMapper;
import com.practice.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liruirui
 * @since 2024-10-16
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    private UserMapper userMapper;

//登录
    @Override
    public String login(String username, String password) {
        User user =userMapper.selectOne(username,password);
        if (user!= null && PasswordUtil.match(password,user.getPassword())) {
        }
        return null;
    }
}
