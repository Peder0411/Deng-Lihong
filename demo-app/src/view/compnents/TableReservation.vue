<template>
  <div class="tables-view">
    <div class="shop-info">
      <img src="https://s2.loli.net/2024/10/30/tBZ5912kiNfUQDL.png" class="shop-logo" />
      <div class="shop-details">
        <h3>美滋滋汉堡</h3>
        <p>188 8888 8888</p>
        <el-button size="mini" type="success">营业中</el-button>
      </div>
    </div>
    <el-row :gutter="10" justify="start">
      <el-col :span="12" v-for="table in sortedTables" :key="table.tableNumber">
        <el-card
          class="table-card"
          :body-style="{ padding: '8px' }"
          :class="{ selected: selectedTable === table.tableNumber, disabled: table.status === 1 }"
        >
          <div @click.stop="selectTable(table)">
            <div class="table-status">
              <el-tag :type="table.status === 1 ? 'danger' : 'success'" size="mini">
                {{ table.status === 1 ? '使用中' : '空闲中' }}
              </el-tag>
            </div>
            <h3>{{ table.tableNumber }}号桌</h3>
            <p class="table-capacity">{{ table.tableType }}桌</p>
          </div>
          <!-- 新增人数输入框 -->
          <div v-if="selectedTable === table.tableNumber" class="table-guest-input">
            <el-input-number
              v-model="guestCount"
              :min="1"
              :max="10"
              placeholder="人数"
              size="mini"
            ></el-input-number>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <div class="submit-container">
      <el-button type="primary" @click="submitSelection" :disabled="!selectedTable || !guestCount">
        提交选中的桌位
      </el-button>
    </div>
  </div>
</template>


<script>
import axios from 'axios';
export default {
  data() {
    return {
      guestCount: null,
      tables: [],
      selectedTable: null // 存储单个选中桌位的编号
    };
  },
  computed: {
    sortedTables() {
      // 将 '空闲' 状态的桌位放在前面
      return this.tables.slice().sort((a) => (a.status === 0 ? -1 : 1));
    }
  },
  methods: {
    getAllTables() {
      axios.get("http://localhost:80/tableInfo/getAllTable")
        .then((res) => {
          if (res.data.code === "200") {
            this.tables = res.data.data.map(table => ({
              ...table,
              status: Number(table.status) // 确保 status 为数字类型
            }));
            this.$message({
              message: '数据加载成功',
              type: 'success'
            });
          } else {
            this.$message({
              message: '数据加载失败',
              type: 'error'
            });
          }
        })
        .catch((error) => {
          this.$message({
            message: `请求失败: ${error.message}`,
            type: 'error'
          });
        });
    },
    selectTable(table) {
      // 仅当桌位是空闲状态时才能选择
      if (table.status === 0) {
        this.selectedTable = this.selectedTable === table.tableNumber ? null : table.tableNumber;
        this.selectedTableId = this.selectedTable !== null ? table.id : null;
        if (this.selectedTable !== null) {
      console.log(`选中桌子的 ID: ${table.id}`);
    }
      }
    },
    submitSelection() {
      if (!this.guestCount) {
        this.$message.error("请选择人数");
        return;
      }
      axios.get(`http://localhost:80/tableInfo/insertId?id=${this.selectedTableId}`)
    .then((res) => {
      if (res.data.code === "200") {
        this.$message.success(`提交成功：桌位号 ${this.selectedTableId}`);
        this.getAllTables(); // 提交成功后刷新桌位状态
      } else {
        this.$message.error("提交失败");
      }
    })
    .catch((error) => {
      this.$message.error(`提交失败: ${error.message}`);
    });
}
  },
  mounted() {
    this.getAllTables();
  }
};
</script>

<style scoped>
.shop-info {
  display: flex;
  align-items: center;
  padding: 16px;
  background-color: #fff;
  margin-top: 8px;
}

.shop-logo {
  width: 60px;
  height: 60px;
  border-radius: 50%;
  margin-right: 16px;
}

.shop-details h3 {
  margin: 0;
  font-size: 18px;
}

.shop-more {
  margin-left: auto;
}
.table-guest-input {
  margin-top: 10px;
}
.tables-view {
  width: 390px; /* 模拟手机宽度 */
  height: 844px; /* 模拟手机高度 */
  border: 1px solid #ddd; /* 添加边框便于在电脑上观察 */
  display: flex;
  flex-direction: column;
  overflow-y: auto;
  padding: 10px;
  margin: 0 auto;
  box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1); /* 模拟手机屏幕效果 */
}

.table-card {
  text-align: center;
  margin-bottom: 10px;
  position: relative;
  font-size: 14px;
  cursor: pointer;
  transition: 0.3s;
}

.table-card.selected {
  border: 2px solid #409EFF;
  background-color: #f0faff;
}

.table-card.disabled {
  opacity: 0.6;
}

.table-card h3 {
  font-size: 16px;
  margin: 10px 0 5px;
}

.table-capacity {
  font-size: 12px;
  color: #666;
  margin-bottom: 10px;
}

.table-status {
  position: absolute;
  top: 8px;
  right: 8px;
}

.submit-container {
  padding: 10px;
  text-align: center;
  position: sticky;
  bottom: 0;
  background: white;
}

@media (max-width: 768px) {
  .table-card {
    margin: 5px auto;
  }
}
</style>
