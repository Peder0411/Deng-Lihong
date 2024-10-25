package com.practice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.toolkit.support.IdeaProxyLambdaMeta;
import com.practice.common.result.ResultUtils;
import com.practice.common.utils.EmptyUtils;
import com.practice.entity.Dish;
import com.practice.service.IDishService;
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
@RequestMapping("/dish")
public class DishController {

    @Autowired
    private IDishService iDishService;


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
        boolean flag = iDishService.save(dish);
        return ResultUtils.returnDataSuccess(flag);
    }
    //查询菜单
    @GetMapping("/selectDish")
    public Object selectDish(@RequestParam Integer page, @RequestParam Integer limit, Dish dish){
        IPage p =new Page(page,limit);

        QueryWrapper queryWrapper =new QueryWrapper();
        if (EmptyUtils.isNotEmpty(dish.getDishName())) {
            queryWrapper.like("DishName",dish.getDishName());
        }
        if (EmptyUtils.isNotEmpty(dish.getKind())){
            queryWrapper.eq("kind",dish.getKind());
        }
        queryWrapper.orderByDesc("id");

        IPage res = iDishService.page(p,queryWrapper);
        List list =res.getRecords();
        int total = (int) res.getTotal();
        return ResultUtils.returnSuccessLayui(list,total);
    }
    //删除菜单
    @DeleteMapping("/delete/{id}")
    public Object deleteDish(@PathVariable int id){
        boolean flag =iDishService.removeById(id);
        return ResultUtils.returnDataSuccess(flag);
    }
    //修改菜单
    @PutMapping("/update")
     public Object update(@RequestBody Dish dish){
        boolean flag = iDishService.updateById(dish);
        return ResultUtils.returnDataSuccess(flag);
    }
}

