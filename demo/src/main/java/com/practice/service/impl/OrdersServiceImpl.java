package com.practice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.practice.entity.Cart;
import com.practice.entity.OrderDetail;
import com.practice.entity.OrderTableInfoDTO;
import com.practice.entity.Orders;
import com.practice.mapper.CartMapper;
import com.practice.mapper.OrderDetailMapper;
import com.practice.mapper.OrdersMapper;
import com.practice.mapper.TableInfoMapper;
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

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private TableInfoMapper tableInfoMapper;
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

    @Override
    public boolean insertConditions(Integer tableId, int peopleCount) {
       return ordersMapper.insertConditions(tableId,peopleCount);
    }

    @Override
    public boolean saveAll(Orders orders) {
        int insert= ordersMapper.insertAll(orders);

        int update =tableInfoMapper.updeteByTableId(orders.getTableId());
        List<Cart> list =cartMapper.selectAllByTableId(orders.getTableId());

        OrderDetail od=new OrderDetail();
        for (Cart c : list){
            od.setOrderId(orders.getId());
            od.setDishId(c.getDishId());
            od.setDishName(c.getDishName());
            od.setDishImage(c.getImage());
            od.setDishPrice(c.getPrice());
            od.setQuantity(c.getQuantity());
            orderDetailMapper.insert(od);
        }
        QueryWrapper queryWrapper= new QueryWrapper();
        queryWrapper.eq("table_id",orders.getTableId());
        int delete = cartMapper.delete(queryWrapper);
        if (delete > 0 && insert >0 && update >0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean updateBy(Orders orders) {
        return ordersMapper.updateBy(orders);
    }

}



