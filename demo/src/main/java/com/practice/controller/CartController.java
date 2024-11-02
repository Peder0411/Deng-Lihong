package com.practice.controller;


import com.practice.common.result.ResultUtils;
import com.practice.entity.Cart;
import com.practice.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author liruirui
 * @since 2024-10-16
 */
@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private ICartService iCartService;
    @PostMapping("/InsertCart")
    public Object InsertCart(@RequestBody Cart cart) {
        // 验证 cart 对象（考虑使用 Validator 或类似的方式）
        if (cart == null || cart.getDishId() == null || cart.getQuantity() <= 0) {
            return false;
        }

        boolean exists = iCartService.SelectByDishId(cart.getDishId());
        if (!exists) {
            boolean flag = iCartService.saveCart(cart);
            return ResultUtils.returnDataSuccess(flag ? "添加成功" : "添加失败");
        } else {
            boolean flag1 = iCartService.updateDishId(cart.getDishId(), cart.getQuantity());
            return ResultUtils.returnDataSuccess(flag1 ? "更新成功" : "更新失败");
        }
    }




}
