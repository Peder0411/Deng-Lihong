package com.practice.controller;


import com.practice.common.result.ResultUtils;
import com.practice.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

    //分页查询
    @GetMapping("/getAllUser")
    public Object getAllUser(@RequestParam(value = "currentPage", defaultValue = "1") int currentPage,
                             @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        Map<String, Object> result = iUserService.getUsersByPage(currentPage, pageSize);
        return ResultUtils.returnDataSuccess(result);
    }
//登录
@PostMapping("/login")
public Object login(@RequestBody Map<String, String> loginData) {
    String username = loginData.get("username");
    String password = loginData.get("password");
    String flag = iUserService.login(username, password);
    return ResultUtils.returnDataSuccess(flag);
}
//删除用户
    @DeleteMapping("delete/{id}")
    public Object deleteUser(@PathVariable int id){
        boolean flag =iUserService.removeById(id);
        return ResultUtils.returnDataSuccess(flag);
    }
//查询用户
@PostMapping("/selectByConditions")
public Object selectByConditions(@RequestBody Map<String, String> selectConditions) {
    String name = selectConditions.get("name");
    String post = selectConditions.get("post");
    int currentPage = Integer.parseInt(selectConditions.get("currentPage"));
    int pageSize = Integer.parseInt(selectConditions.get("pageSize"));
    // 调用 service 方法
    Map<String, Object> result = iUserService.selectByCondition(currentPage, pageSize, name, post);
    return ResultUtils.returnDataSuccess(result);

}
//发送邮件
    @GetMapping("/sendMail")
    public Object sendMail(@RequestParam String email){
        boolean flag =iUserService.sendMail(email);
        return ResultUtils.returnDataSuccess(flag);
    }


//找回密码
    @PostMapping("/rePassword")
    public Object rePassword(@RequestBody Map <String, String> rePasswordCondition){
        String email = rePasswordCondition.get("email");
        String password =rePasswordCondition.get("password");
        int emailCode =Integer.parseInt(rePasswordCondition.get("emailCode"));
        return null;
    }

}
