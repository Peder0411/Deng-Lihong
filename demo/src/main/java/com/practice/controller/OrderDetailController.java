package com.practice.controller;


import com.practice.common.result.ResultUtils;
import com.practice.entity.OrderDetail;
import com.practice.service.IOrderDetailService;
import com.practice.service.IOrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/order-detail")
public class OrderDetailController {

    @Autowired
    private IOrderDetailService iOrderDetailService;

    @Autowired
    private IOrdersService iOrdersService;
    @PostMapping("/saveOrderInfo")
    public Object saveOrderInfo(@RequestBody List<OrderDetail> orderDetails) {
        for (OrderDetail orderDetail : orderDetails) {
            Integer orderId = iOrdersService.selectByTableId(orderDetail.getTableId());
            if (orderId != null) {
                orderDetail.setOrderId(orderId);
                boolean flag = iOrderDetailService.save(orderDetail);
                if (!flag) {
                    return false;
                }
            }
        }
        return ResultUtils.returnDataSuccess(true);
    }

}
