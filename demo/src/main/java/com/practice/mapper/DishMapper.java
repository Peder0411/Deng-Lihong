package com.practice.mapper;

import com.practice.entity.Dish;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liruirui
 * @since 2024-10-16
 */
public interface DishMapper extends BaseMapper<Dish> {

    List <Dish> selectByStatus();
}
