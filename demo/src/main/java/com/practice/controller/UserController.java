package com.practice.controller;


import com.practice.common.result.ResultUtils;
import com.practice.entity.Dish;
import com.practice.entity.User;
import com.practice.service.IMailService;
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

    @Autowired
    private IMailService iMailService;


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
@PostMapping("/sendMail")
public Object sendMail(@RequestBody Map<String, String> sendData) {
    String email = sendData.get("email");
    boolean flag = iMailService.sendMail(email);
    return ResultUtils.returnDataSuccess(flag);
}


//找回密码
    @PostMapping("/rePassword")
    public Object rePassword(@RequestBody Map <String, String> rePasswordCondition){
        String email = rePasswordCondition.get("email");
        String password =rePasswordCondition.get("password");
        int mailCode =Integer.parseInt(rePasswordCondition.get("mailCode"));
        String site = iUserService.rePassword(email,password,mailCode);
        return ResultUtils.returnDataSuccess(site);
    }
    //添加用户
    @PostMapping("/addUser")
    public Object addUser(@RequestBody User user){
        boolean flag = iUserService.save(user);
        return ResultUtils.returnDataSuccess(flag);
    }
    //修改
    @PutMapping("/update")
    public Object update (@RequestBody User user){
        boolean flag =iUserService.updateById(user);
        return ResultUtils.returnDataSuccess(flag);
    }
    //监控员工状态
    @PutMapping("/updateById")
    public Object updateById(@RequestParam int id, @RequestParam String  status) {
      User user = iUserService.getById(id);
        if (user != null) {
            user.setStatus(status);

            boolean flag = iUserService.updateById(user);
            return ResultUtils.returnDataSuccess(flag);
        } else {
            return false;
        }
    }
}
