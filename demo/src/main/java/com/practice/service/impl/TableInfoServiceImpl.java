package com.practice.service.impl;

import com.practice.entity.TableInfo;
import com.practice.mapper.TableInfoMapper;
import com.practice.service.ITableInfoService;
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
public class TableInfoServiceImpl extends ServiceImpl<TableInfoMapper, TableInfo> implements ITableInfoService {

    @Autowired
    private  TableInfoMapper tableInfoMapper;
    @Override
    public int updeteByTableId(int tableNumber) {
        return tableInfoMapper.updeteByTableId(tableNumber);
    }

    @Override
    public List<TableInfo> selectAllByConditions(TableInfo tableInfo) {
        return tableInfoMapper.selectAllByConditions(tableInfo);
    }

    @Override
    public boolean updateStatus(int tableId) {
        return tableInfoMapper.updateStatus(tableId);
    }
}
