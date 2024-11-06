package com.practice.service;

import com.practice.entity.TableInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liruirui
 * @since 2024-10-16
 */
public interface ITableInfoService extends IService<TableInfo> {

    int updeteByTableId( int tableNumber);

    List<TableInfo> selectAllByConditions(TableInfo tableInfo);
}
