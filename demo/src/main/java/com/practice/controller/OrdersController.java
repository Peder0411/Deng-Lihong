package com.practice.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.practice.common.result.ResultUtils;
import com.practice.entity.OrderTableInfoDTO;
import com.practice.entity.Orders;
import com.practice.entity.TableInfo;
import com.practice.service.IOrdersService;
import com.practice.service.ITableInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        List<OrderTableInfoDTO> list = iOrdersService.getOrderWithTableInfo(offset, limit);

        // 获取总数
        int total = iOrdersService.countOrders();

        // 返回符合前端期望的分页格式
        return ResultUtils.returnSuccessLayui(list, total);
    }

}
