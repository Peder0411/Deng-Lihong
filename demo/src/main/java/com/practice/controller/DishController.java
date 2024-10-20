package com.practice.controller;


import com.practice.common.result.ResultUtils;
import com.practice.entity.Dish;
import com.practice.service.IDishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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
    public Object getall(){
        List <Dish > list=iDishService.list();
        return ResultUtils.returnDataSuccess(list);
    }

}
