package com.practice.mapper;

import com.practice.entity.Cart;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

   List<Cart> selectAll();

   List<Cart> selectQuantity(@Param("dishId") int dishId);

   boolean deleteByDishId(@Param("dishId")int dishId);

   List <Cart> selectAllByTableId(@Param("tableId") int tableId);
}
