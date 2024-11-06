package com.practice.controller;


import com.practice.common.result.ResultUtils;
import com.practice.entity.OrderDetail;
import com.practice.service.IOrderDetailService;
import com.practice.service.IOrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @GetMapping("/getAllOrderDetail")
    public Object getAllOrderDetail(){
        List <OrderDetail> list=iOrderDetailService.getOrderDetailsWithDishInfo();
        return ResultUtils.returnDataSuccess(list);
    }


    @GetMapping("/updateById")
    public Object updateById(@RequestParam int dishId) {
        List<OrderDetail> orderDetails = iOrderDetailService.selectByDishId(dishId);

        if (orderDetails != null && !orderDetails.isEmpty()) {
            for (OrderDetail orderDetail : orderDetails) {
                int quantity = orderDetail.getQuantity();
                boolean flag;

                if (quantity > 1) {
                    orderDetail.setQuantity(quantity - 1);
                    flag = iOrderDetailService.updateDishId(dishId, orderDetail.getQuantity());
                } else {
                    flag = iOrderDetailService.removeById(orderDetail.getId());
                }

                if (flag) {
                    // 更新或删除成功后，重新查询并返回最新的订单详情
                    List<OrderDetail> updatedOrderDetails = iOrderDetailService.getOrderDetailsWithDishInfo();
                    return ResultUtils.returnDataSuccess(updatedOrderDetails);
                } else {
                    return ResultUtils.returnFail("操作失败", "500");
                }
            }
        }
        return ResultUtils.returnFail("未找到相关记录", "404");
    }


    @GetMapping("/getAllOrder")
    public Object getAllOrder(@RequestParam int tableId){
        List <OrderDetail> list=iOrderDetailService.getAllByOrder(tableId);
        return ResultUtils.returnDataSuccess(list);
    }

    @GetMapping("/getByOrderId")
    public Object getByOrderId(@RequestParam int tableId){
        List <OrderDetail> list=iOrderDetailService.getByOrderId(tableId);
        return ResultUtils.returnDataSuccess(list);
    }
}