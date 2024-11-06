package com.practice.mapper;

import com.practice.entity.OrderDetail;
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
public interface OrderDetailMapper extends BaseMapper<OrderDetail> {

    List<OrderDetail> selectByDishId(int dishId);

    boolean updateDishId(@Param("dishId") int dishId, @Param("quantity") int quantity);

    List<OrderDetail> getOrderDetailsWithDishInfo();

    List <OrderDetail> getAllByOrder(@Param("tableId") int tableId);

    List<OrderDetail>getByOrderId(@Param("tableId") int tableId);
}
