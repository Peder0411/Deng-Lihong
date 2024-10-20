package com.practice.controller;


import com.practice.common.result.Result;
import com.practice.common.result.ResultUtils;
import com.practice.entity.User;
import com.practice.service.IUserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author liruirui
 * @since 2024-10-16
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService iUserService;
    @GetMapping("/getAllUser")
    public Object getAllUser() {
        List <User> list=iUserService.list();
        return ResultUtils.returnDataSuccess(list);
    }
//登录
    @PostMapping("/select")
    public Object select(@RequestParam String username,@RequestParam String password){
        String flag = iUserService.login(username,password);
        return ResultUtils.returnDataSuccess(flag);
    }

}
