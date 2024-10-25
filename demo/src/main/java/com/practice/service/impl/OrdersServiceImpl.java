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
    public List<OrderTableInfoDTO> getOrderWithTableInfo(int offset, int limit) {
        // 在 XML 中定义分页查询，传入 offset 和 limit
        return ordersMapper.getOrderWithTableInfoPaged(offset, limit);
    }

    @Override
    public int countOrders() {
        // 获取订单总数
        return ordersMapper.selectCount(null);
    }
}
