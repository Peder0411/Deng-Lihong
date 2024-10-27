package com.practice.service.impl;

import com.practice.entity.OrderTableInfoDTO;
import com.practice.entity.Orders;
import com.practice.mapper.OrdersMapper;
import com.practice.service.IOrdersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author demo
 * @since 2024-10-25
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements IOrdersService {


    @Autowired
    private OrdersMapper ordersMapper;
    @Override
    public List<OrderTableInfoDTO> selectAll(int offset, int limit) {
        return ordersMapper.selectAll(offset, limit);
    }

    @Override
    public int countOrders() {
        // 获取订单总数
        return ordersMapper.selectCount(null);
    }


    //条件查询
    @Override
    public List<OrderTableInfoDTO> selectByConditions(OrderTableInfoDTO orderTableInfoDTO) {
        return ordersMapper.selectByConditios(orderTableInfoDTO);
    }
}
