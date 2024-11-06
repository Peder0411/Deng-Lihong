package com.practice.service;

import com.practice.entity.Dish;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liruirui
 * @since 2024-10-16
 */
public interface IDishService extends IService<Dish> {
    List<Dish> selectByStatus();

}
