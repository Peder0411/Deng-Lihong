package com.practice.mapper;

import com.practice.entity.OrderTableInfoDTO;
import com.practice.entity.Orders;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author demo
 * @since 2024-10-25
 */
public interface OrdersMapper extends BaseMapper<Orders> {


    //查询所有
    List<OrderTableInfoDTO> selectAll(@Param("offset") int offset, @Param("limit") int limit);

    //条件查询

    List <OrderTableInfoDTO> selectByConditios(@Param("orderTableInfoDTO") OrderTableInfoDTO orderTableInfoDTO);
}
