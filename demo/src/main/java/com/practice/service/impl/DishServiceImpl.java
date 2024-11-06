package com.practice.service.impl;

import com.practice.entity.Dish;
import com.practice.mapper.DishMapper;
import com.practice.service.IDishService;
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
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements IDishService {


    @Autowired
    private DishMapper dishMapper;
    @Override
    public List<Dish> selectByStatus() {
        return dishMapper.selectByStatus();
    }
}
