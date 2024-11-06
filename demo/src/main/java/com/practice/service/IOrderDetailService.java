package com.practice.service;

import com.practice.entity.OrderDetail;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liruirui
 * @since 2024-10-16
 */
public interface IOrderDetailService extends IService<OrderDetail> {

    List<OrderDetail> selectByDishId(int dishId);

    boolean updateDishId(int dishId,int quantity);

    List<OrderDetail> getOrderDetailsWithDishInfo();

    List <OrderDetail> getAllByOrder( int tableId);

    List<OrderDetail>getByOrderId( int tableId);
}
