package com.practice.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.toolkit.support.IdeaProxyLambdaMeta;
import com.practice.common.result.ResultUtils;
import com.practice.common.utils.EmptyUtils;
import com.practice.entity.Dish;
import com.practice.entity.User;
import com.practice.service.IDishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
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
@RequestMapping("/dish")
public class DishController {

    @Autowired
    private IDishService iDishService;





    @GetMapping("/getAllDish")
    public Object getAllDish(){
        List<Dish> list=iDishService.selectByStatus();
        return ResultUtils.returnDataSuccess(list);
    }

    //查询菜单
    @GetMapping("/getAll")
    public Object getAll(@RequestParam("page") Integer page, @RequestParam("limit") Integer limit)
    {
        IPage <Dish> p =new Page(page,limit);
        IPage <Dish> res = iDishService.page(p);
        List  <Dish> list =res.getRecords();
        int total = (int) res.getTotal();
        return ResultUtils.returnSuccessLayui(list,total);
    }


    //添加菜单
    @PostMapping("/addDish")
    public Object addDish(@RequestBody Dish dish){
        BigDecimal originalPrice= dish.getOriginalPrice();
        String discountStr =dish.getDiscount();
        BigDecimal discount = new BigDecimal(discountStr.replace("折", "")); // 去掉“折”
        discount = discount.divide(new BigDecimal(10)); // 转换为小数

        // 计算现价
        BigDecimal price = originalPrice.multiply(discount); // 现价 = 原价 * 折扣

        // 设置现价
        dish.setPrice(price);
        boolean flag = iDishService.save(dish);
        return ResultUtils.returnDataSuccess(flag);
    }
    //条件查询
    @PostMapping("/selectDish")
    public Object selectDish(@RequestParam Integer page, @RequestParam Integer limit,@RequestBody Map<String, String> selectConditions) {
        String dishName =selectConditions.get("dishName");
        String status = selectConditions.get("status");

        IPage <Dish> p =new Page(page,limit);

        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        if (dishName != null && !dishName.isEmpty()) {
            queryWrapper.eq(Dish::getDishName, dishName);
        }
        if (status != null && !status.isEmpty()) {
            queryWrapper.eq(Dish::getStatus, status);
        }
        IPage <Dish> res = iDishService.page(p,queryWrapper);
        List  <Dish> list =res.getRecords();
        int total = (int) res.getTotal();
        return ResultUtils.returnSuccessLayui(list,total);
    }


    //删除菜单
    @DeleteMapping("/delete")
    public Object deleteDish(@PathVariable int id){
        boolean flag =iDishService.removeById(id);
        return ResultUtils.returnDataSuccess(flag);
    }
    //根据Id查询
    @GetMapping("/getById/{id}")
    public Object getById(@PathVariable int id){
        Dish dish = iDishService.getById(id);
        return ResultUtils.returnDataSuccess(dish);
    }
    //修改菜单
    @PutMapping("/update")
     public Object update(@RequestBody Dish dish){
        boolean flag = iDishService.updateById(dish);
        return ResultUtils.returnDataSuccess(flag);
    }
    //监控菜品状态
    @PutMapping("/updateById")
    public Object updateById(@RequestParam int id, @RequestParam String  status) {
        Dish dish = iDishService.getById(id);
        if (dish != null) {
            dish.setStatus(status);

            boolean flag = iDishService.updateById(dish);
            return ResultUtils.returnDataSuccess(flag);
        } else {
            return false;
        }
    }

    //Echars专用
    @GetMapping("/gitAllDish")
    public Object gitAllDish(){
        List<Dish> list=iDishService.list();
        return ResultUtils.returnDataSuccess(list);
    }
}

