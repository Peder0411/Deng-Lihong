package com.practice.mapper;

import com.practice.entity.Cart;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liruirui
 * @since 2024-10-16
 */
public interface CartMapper extends BaseMapper<Cart> {

    boolean saveCart(Cart cart);

    boolean SelectByDishId(@Param("dishId") int dishId);

   boolean updateDishId(@Param("dishId") int dishId,@Param("quantity") int quantity);
}
