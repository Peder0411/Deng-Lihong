package com.practice.mapper;

import com.practice.entity.TableInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liruirui
 * @since 2024-10-16
 */
public interface TableInfoMapper extends BaseMapper<TableInfo> {
    //查询更改状态

    int updeteByTableId(@Param("tableNumber") int tableNumber);

    List<TableInfo> selectAllByConditions(TableInfo tableInfo);
}
