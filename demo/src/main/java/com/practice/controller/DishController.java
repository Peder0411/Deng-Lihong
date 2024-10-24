package com.practice.controller;


import com.practice.common.result.ResultUtils;
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

    @GetMapping("/getAll")
    public Object getAll(){
        List <Dish > list=iDishService.list();
        return ResultUtils.returnDataSuccess(list);
    }

    @PostMapping("/addDish")
    public Object addDish(@RequestBody Dish dish){
        boolean flag = iDishService.save(dish);
        return ResultUtils.returnDataSuccess(flag);
    }

}
