package com.practice.service;

import com.practice.entity.OrderTableInfoDTO;
import com.practice.entity.Orders;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author demo
 * @since 2024-10-25
 */
public interface IOrdersService extends IService<Orders> {

    List<OrderTableInfoDTO> getOrderWithTableInfo(int offset, int limit);
    int countOrders();
}
