package com.practice.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.practice.common.result.ResultUtils;
import com.practice.service.ITableInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
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
@RequestMapping("/table-info")
public class TableInfoController {

    @Autowired
    private ITableInfoService iTableService;

    @GetMapping("/selectTable")
    public Object selectTable(@RequestParam Integer page,@RequestParam Integer limit){

        IPage p = new Page(page,limit);

        IPage res =iTableService.page(p);

        List list = res.getRecords();

        int total = (int) res.getTotal();

        return ResultUtils.returnSuccessLayui(list ,total);
    }

}
