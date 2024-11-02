package com.practice.service;

import com.practice.entity.Cart;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liruirui
 * @since 2024-10-16
 */
public interface ICartService extends IService<Cart> {

    boolean saveCart(Cart cart);

    boolean SelectByDishId(int dishId);

    boolean updateDishId(int dishId, int quantity);
}
