package com.practice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.practice.common.utils.PasswordUtil;
import com.practice.entity.User;
import com.practice.mapper.UserMapper;
import com.practice.service.IMailService;
import com.practice.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;

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

    @Autowired
    private IMailService iMailService;


//登录
    @Override
    public String login(String username, String password) {
        User user = userMapper.selectOne(username, password);
        if (user != null && PasswordUtil.match(password, user.getPassword())) {
        }
        return null;
    }
    //分页

    @Override
    public Map<String, Object> getUsersByPage(int currentPage, int pageSize) {
        // 创建分页对象
        Page<User> page = new Page<>(currentPage, pageSize);


        IPage<User> resultPage = this.page(page);

        Map<String, Object> result = new HashMap<>();
        result.put("data", resultPage.getRecords());  // 当前页数据
        result.put("total", resultPage.getTotal());   // 总记录数
        return result;
    }
    //分页查询

    @Override
    public Map<String, Object> selectByCondition(int currentPage, int pageSize, String name, String post) {
        // 创建分页对象
        Page<User> page = new Page<>(currentPage, pageSize);
        // 创建查询条件
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        if (name != null && !name.isEmpty()) {
            queryWrapper.eq(User::getName, name);
        }
        if (post != null && !post.isEmpty()) {
            queryWrapper.eq(User::getPost, post);
        }
        // 执行分页查询
        IPage<User> resultPage = this.page(page, queryWrapper);

        // 将查询结果封装为自定义的返回结果
        Map<String, Object> result = new HashMap<>();
        result.put("data", resultPage.getRecords());  // 当前页数据
        result.put("total", resultPage.getTotal());   // 总记录数
        return result;
    }
        //发邮件



    //找回密码
    @Override
    public String rePassword(String email, String password, int mailCode) {
      String storedCode = iMailService.getTemporaryStorage();
        System.out.println(storedCode);
        System.out.println(mailCode);
        if (storedCode== null|| !storedCode.equals(String.valueOf(mailCode))) {
            return "验证码不正确或已过期";
        }

        User user = userMapper.selectEmail(email);
        if (user == null) {
            return "该邮箱未注册";
        }
        String encryptedPassword =PasswordUtil.encodeDefaultSalt(password);  // 加密密码
        user.setPassword(encryptedPassword);
        userMapper.updateById(user);
        return "密码重置成功";
    }


}
