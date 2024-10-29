package com.practice.controller;


import com.practice.common.result.ResultUtils;
import com.practice.entity.OrderTableInfoDTO;
import com.practice.entity.Orders;
import com.practice.service.IOrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author liruirui
 * @since 2024-10-16
 */
@RestController
@RequestMapping("/orders")
public class OrdersController {

    @Autowired
    private IOrdersService iOrdersService;


    //查询订单
    @GetMapping("/getAll")
    public Object getAllOrder(@RequestParam Integer page, @RequestParam Integer limit) {
        // 计算分页的起始位置
        int offset = (page - 1) * limit;

        // 查询指定范围内的订单及餐桌信息
        List<OrderTableInfoDTO> list = iOrdersService.selectAll(offset, limit);

        // 获取总数
        int total = iOrdersService.countOrders();

        // 返回符合前端期望的分页格式
        return ResultUtils.returnSuccessLayui(list, total);
    }

    //条件查询
    @PostMapping("/selectByConditions")
    public Object selectByConditions(@RequestBody OrderTableInfoDTO orderTableInfoDTO){
        List<OrderTableInfoDTO> results = iOrdersService.selectByConditions(orderTableInfoDTO);
        return ResultUtils.returnDataSuccess(results);
    }
    //结账
    @PutMapping("/updateStatus")
    public Object updateStatus(@RequestParam int id) {
        // 查询订单
        Orders orders = iOrdersService.getById(id);
        // 如果订单不存在，返回错误信息
        if (orders == null) {
            return false;
        }
        // 更新订单状态
        orders.setStatus(1);
        boolean flag = iOrdersService.updateById(orders);

        // 返回更新结果
        return ResultUtils.returnDataSuccess(flag);
    }

}
