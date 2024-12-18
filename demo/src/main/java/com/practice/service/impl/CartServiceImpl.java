package com.practice.service.impl;

import com.practice.entity.Cart;
import com.practice.mapper.CartMapper;
import com.practice.service.ICartService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liruirui
 * @since 2024-10-16
 */
@Service
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements ICartService {

    @Autowired
    private CartMapper cartMapper;

    @Override
    public boolean saveCart(Cart cart) {
        return cartMapper.saveCart(cart);
    }

    @Override
    public boolean SelectByDishId(int dishId) {
        Boolean result = cartMapper.SelectByDishId(dishId);
        return result != null && result;
    }

    @Override
    public boolean updateDishId(int dishId,int quantity) {
        return cartMapper.updateDishId(dishId, quantity);
    }

    @Override
    public List<Cart> selectAll() {
        return cartMapper.selectAll();
    }

    @Override
    public List<Cart> selectQuantity(int dishId) {
        return  cartMapper.selectQuantity(dishId);
    }


    @Override
    public boolean deleteByDishId(int dishId) {
        return cartMapper.deleteByDishId(dishId);
    }

    @Override
    public List<Cart> selectAllByTableId(int tableId) {
        return cartMapper.selectAllByTableId(tableId);
    }
}
