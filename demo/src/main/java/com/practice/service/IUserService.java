package com.practice.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.practice.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

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

      //分页
      Map<String, Object> getUsersByPage(int currentPage, int pageSize);

      //分页查询
      Map<String, Object> selectByCondition(int currentPage, int pageSize, String name, String post);

      //找回密码
      public String rePassword(String email,String password, int mailCode);

}
