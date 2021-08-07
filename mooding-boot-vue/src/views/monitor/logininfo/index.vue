
<template>
  <div class="system-user-container app-container">
    <!-- 查询 -->
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="登录地址" prop="ipaddr">
        <md-input
          placeholder="请输入登录地址模糊查询"
          clearable
          size="small"
          @keyup.enter="handleQuery"
          style="width: 240px"
          v-model="queryParams.ipaddr"
        />
      </el-form-item>
      <el-form-item label="用户名称" prop="userName">
        <md-input
          placeholder="请输入用户名称模糊查询"
          clearable
          size="small"
          @keyup.enter="handleQuery"
          style="width: 240px"
          v-model="queryParams.userName"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select
          v-model="queryParams.status"
          placeholder="登录状态"
          clearable
          size="small"
          style="width: 240px"
        >
          <el-option
            v-for="dict in statusOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="登录时间">
        <el-date-picker
          v-model="dateRange"
          size="small"
          style="width: 240px"
          format="YYYY-MM-DD"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button
          type="primary"
          icon="el-icon-search"
          size="mini"
          @click="handleQuery"
          >搜索</el-button
        >
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
          >重置</el-button
        >
      </el-form-item>
    </el-form>

    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="onTabelRowDel"
          v-hasPermi="['monitor:logininfo:remove']"
          >删除</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          @click="handleClean"
          v-hasPermi="['monitor:logininfo:remove']"
          >清空</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['monitor:logininfo:export']"
          >导出</el-button
        >
      </el-col>
    </el-row>

    <!--数据表格-->
    <el-table
      v-loading="loading"
      :data="tableData"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="访问编号" align="center" prop="infoId" />
      <el-table-column label="用户名称" align="center" prop="userName" />
      <el-table-column
        label="登录地址"
        align="center"
        prop="ipaddr"
        width="130"
        :show-overflow-tooltip="true"
      />
      <el-table-column
        label="登录地点"
        align="center"
        prop="loginLocation"
        :show-overflow-tooltip="true"
      />
      <el-table-column label="浏览器" align="center" prop="browser" />
      <el-table-column label="操作系统" align="center" prop="os" />
      <el-table-column
        label="登录状态"
        align="center"
        prop="status"
        :formatter="statusFormat"
      />
      <el-table-column label="操作信息" align="center" prop="msg" />
      <el-table-column
        label="登录日期"
        align="center"
        prop="loginTime"
        width="180"
      >
        <template #default="scope">
          <span>{{ scope.row.loginTime }}</span>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页设置-->
    <el-pagination
      v-show="total > 0"
      :total="total"
      :current-page="queryParams.pageNo"
      :page-sizes="[10, 20, 30, 40]"
      :page-size="queryParams.pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />
  </div>
</template>

<script lang="ts">
import { reactive, onMounted, toRefs, ref, getCurrentInstance } from "vue";
import {
  listLoginInfo,
  delLoginInfo,
  cleanLoginInfo,
  exportLoginInfo,
} from "@/api/monitor/loginInfo";
import { ElMessageBox, ElMessage } from "element-plus";
export default {
  name: "Logininfo",
  setup() {
    const { proxy } = getCurrentInstance() as any;
    const ruleFormRef = ref<HTMLElement | null>(null);
    const state = reactive({
      // 遮罩层
      loading: true,
      // 总条数
      total: 0,
      // 列表表格数据
      tableData: [],
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 弹出层标题
      title: "",
      // 显示搜索条件
      showSearch: true,
      // 类型数据字典
      statusOptions: [],
      // 日期范围
      dateRange: [],
      // 查询参数
      queryParams: {
        pageNo: 1,
        pageSize: 10,
        ipaddr: undefined,
        userName: undefined,
        status: undefined,
      },
    });

    /** 查询定时任务列表 */
    const handleQuery = () => {
      state.loading = true;
      listLoginInfo(
        proxy.addDateRange(state.queryParams, state.dateRange)
      ).then((response) => {
        state.tableData = response.data.records;
        state.total = response.data.total;
        state.loading = false;
      });
    };

    /** 重置按钮操作 */
    const resetQuery = () => {
      state.queryParams.ipaddr = undefined;
      state.queryParams.userName = undefined;
      state.queryParams.status = undefined;
      handleQuery();
    };

    /** 清空按钮操作 */
    const handleClean = () => {
      ElMessageBox({
        message: "是否确认清空所有登录日志数据项?",
        title: "警告",
        showCancelButton: true,
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return cleanLoginInfo();
        })
        .then(() => {
          handleQuery();
          ElMessage.success("清空成功");
        });
    };

    /** 删除按钮操作 */
    const onTabelRowDel = (row: any) => {
      const infoIds = row.infoId || state.ids;
      ElMessageBox({
        message: '是否确认删除访问编号为"' + infoIds + '"的数据项?',
        title: "警告",
        showCancelButton: true,
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(function () {
        return delLoginInfo(infoIds).then(() => {
          handleQuery();
          ElMessage.success("删除成功");
        });
      });
    };
    /** 导出按钮操作 */
    const handleExport = () => {
      const queryParams = state.queryParams;
      ElMessageBox({
        message: "是否确认导出所有登录日志数据项?",
        title: "警告",
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return exportLoginInfo(queryParams);
        })
        .then((response) => {
          console.log("导出按钮操作", response);
          proxy.download(response.data);
        });
    };

    //分页页面大小发生变化
    const handleSizeChange = (val: any) => {
      state.queryParams.pageSize = val;
      handleQuery();
    };
    //当前页码发生变化
    const handleCurrentChange = (val: any) => {
      state.queryParams.pageNo = val;
      handleQuery();
    };

    // 操作日志状态字典翻译
    const statusFormat = (row: any, column: any) => {
      return proxy.selectDictLabel(state.statusOptions, row.status);
    };
    // 多选框选中数据
    const handleSelectionChange = (selection: any) => {
      state.ids = selection.map((item: any) => item.infoId);
      state.single = selection.length != 1;
      state.multiple = !selection.length;
    };

    // 页面加载时调用
    onMounted(() => {
      //   proxy.mittBus.on("onEditPostModule", (res: any) => {
      //     handleQuery();
      //   });
      // 查询列表数据信息
      handleQuery();
      proxy.getDicts("sys_common_status").then((response: any) => {
        state.statusOptions = response.data;
      });
    });

    return {
      ruleFormRef,
      statusFormat,
      handleSelectionChange,
      handleSizeChange,
      handleCurrentChange,
      resetQuery,
      handleQuery,
      handleClean,
      handleExport,
      onTabelRowDel,
      ...toRefs(state),
    };
  },
};
</script>

<style>
</style>
