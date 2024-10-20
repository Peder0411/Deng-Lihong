<template>
    <div class="table-management">
      <!-- 筛选区域 -->
      <el-form :inline="true" :model="formInline" class="filter-form">
        <el-form-item label="桌位">
          <el-select v-model="formInline.table" placeholder="请选择">
            <el-option label="桌位 1" value="1"></el-option>
            <el-option label="桌位 2" value="2"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="桌型">
          <el-select v-model="formInline.type" placeholder="请选择">
            <el-option label="4人桌" value="4"></el-option>
            <el-option label="6人桌" value="6"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="使用状态">
          <el-select v-model="formInline.status" placeholder="请选择">
            <el-option label="使用" value="used"></el-option>
            <el-option label="空闲" value="free"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSearch">查询</el-button>
        </el-form-item>
      </el-form>
  
      <!-- 操作按钮 -->
      <div class="operation-buttons">
        <el-button type="primary" @click="manageTables">桌位管理</el-button>
        <el-button type="warning" @click="manageTypes">桌型管理</el-button>
        <el-button type="success" @click="createNew">新建</el-button>
      </div>
  
      <!-- 数据表格 -->
      <el-table :data="tableData" stripe style="width: 200%">
        <el-table-column prop="id" label="编号" width="100"></el-table-column>
        <el-table-column prop="tableNumber" label="桌号" width="100"></el-table-column>
        <el-table-column prop="location" label="桌位" width="120"></el-table-column>
        <el-table-column prop="type" label="桌型" width="100"></el-table-column>
        <el-table-column prop="status" label="桌台状态" width="120">
          <template slot-scope="scope">
            <span :style="{ color: scope.row.status === '使用' ? 'orange' : 'green' }">{{ scope.row.status }}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="180">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="handleView(scope.row)">查看</el-button>
            <el-button type="text" size="small" @click="handleEdit(scope.row)">编辑</el-button>
            <el-button type="text" size="small" @click="handleDelete(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        formInline: {
          table: '',
          type: '',
          status: ''
        },
        tableData: [
          { id: '1000001', tableNumber: '101', location: '大厅', type: '4人', status: '使用' },
          { id: '1000002', tableNumber: '102', location: '大厅', type: '4人', status: '空闲' },
          // 可根据需求添加更多数据
        ]
      };
    },
    methods: {
      onSearch() {
        console.log('查询', this.formInline);
        // 执行查询逻辑
      },
      manageTables() {
        console.log('桌位管理');
        // 桌位管理的逻辑
      },
      manageTypes() {
        console.log('桌型管理');
        // 桌型管理的逻辑
      },
      createNew() {
        console.log('新建');
        // 新建的逻辑
      },
      handleView(row) {
        console.log('查看', row);
      },
      handleEdit(row) {
        console.log('编辑', row);
      },
      handleDelete(row) {
        console.log('删除', row);
      }
    }
  };
  </script>
  
  <style scoped>
  .table-management {
    padding: 20px;
  }
  
  .filter-form {
    margin-bottom: 20px;
  }
  
  .operation-buttons {
    margin-bottom: 20px;
  }
  
  .operation-buttons .el-button {
    margin-right: 10px;
  }
  </style>
  