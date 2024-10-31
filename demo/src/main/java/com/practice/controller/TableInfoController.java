package com.practice.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.practice.common.result.ResultUtils;
import com.practice.entity.Cart;
import com.practice.entity.OrderDetail;
import com.practice.entity.Orders;
import com.practice.entity.TableInfo;
import com.practice.service.ICartService;
import com.practice.service.IOrderDetailService;
import com.practice.service.IOrdersService;
import com.practice.service.ITableInfoService;
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
@RequestMapping("/tableInfo")
public class TableInfoController {

    @Autowired
    private ITableInfoService iTableService;
    @Autowired
    private ICartService iCartService;
    @Autowired
    private IOrdersService iOrdersService;
    //管理端
    @GetMapping("/selectTable")
    public Object selectTable(@RequestParam Integer page,@RequestParam Integer limit){

        IPage p = new Page(page,limit);

        IPage res =iTableService.page(p);

        List list = res.getRecords();

        int total = (int) res.getTotal();

        return ResultUtils.returnSuccessLayui(list ,total);
    }

    //客户端
    @GetMapping("/getAllTable")
    public Object getAllTable(){
        List<TableInfo> list =iTableService.list();
        return ResultUtils.returnDataSuccess(list);
    }

    @PostMapping("/insertId")
    public  Object insertId(@RequestParam int id){
        TableInfo tableInfo = iTableService.getById(id);
        // 如果订单不存在，返回错误信息
        if (tableInfo == null) {
            return false;
        }
        // 更新订单状态
        tableInfo.setStatus(1);
        boolean flag = iTableService.updateById(tableInfo);
        if (!flag) {
            return "Failed to update table status";
        }
        Cart cart = new Cart();
        cart.setTableId(id);

        Orders orders = new Orders();
        orders.setTableId(id);
        boolean cartFlag = iCartService.save(cart);
        boolean orderDetailFlag = iOrdersService.save(orders);
        if (cartFlag && orderDetailFlag){
            return ResultUtils.returnSuccess();
        }
        return false;
    }

}
