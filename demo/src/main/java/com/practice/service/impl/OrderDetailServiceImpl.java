package com.practice.service.impl;

import com.practice.entity.OrderDetail;
import com.practice.mapper.OrderDetailMapper;
import com.practice.service.IOrderDetailService;
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
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements IOrderDetailService {


    @Autowired
    private  OrderDetailMapper orderDetailMapper;


    @Override
    public List<OrderDetail> selectByDishId(int dishId) {
        return orderDetailMapper.selectByDishId(dishId);
    }

    @Override
    public boolean updateDishId(int dishId,int quantity) {
        return orderDetailMapper.updateDishId(dishId,quantity);
    }

    @Override
    public List<OrderDetail> getOrderDetailsWithDishInfo() {
        return orderDetailMapper.getOrderDetailsWithDishInfo();
    }

    @Override
    public List<OrderDetail> getAllByOrder(int tableId) {
        return orderDetailMapper.getAllByOrder(tableId);
    }

    @Override
    public List<OrderDetail> getByOrderId(int tableId) {
        return orderDetailMapper.getByOrderId(tableId);
    }
}
