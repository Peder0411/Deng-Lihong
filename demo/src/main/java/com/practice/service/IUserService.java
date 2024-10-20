package com.practice.service;

import com.practice.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liruirui
 * @since 2024-10-16
 */
public interface IUserService extends IService<User> {

    //登录
      String login(String username,String password);


}
