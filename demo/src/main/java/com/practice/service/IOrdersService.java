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

    List<OrderTableInfoDTO> selectAll(int offset, int limit);
    int countOrders();

    List <OrderTableInfoDTO> selectByConditions(OrderTableInfoDTO orderTableInfoDTO);


    public boolean insertConditions(Integer tableId, int peopleCount);

    Integer selectByTableId(int tableId);
}
