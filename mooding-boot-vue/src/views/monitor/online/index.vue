<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      label-width="68px"
    >
      <el-form-item label="登录地址" prop="ipaddr">
        <el-input
          v-model="queryParams.ipaddr"
          placeholder="请输入登录地址"
          clearable
          size="small"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="用户名称" prop="userName">
        <el-input
          v-model="queryParams.userName"
          placeholder="请输入用户名称"
          clearable
          size="small"
          @keyup.enter="handleQuery"
        />
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

    <el-table
      v-loading="loading"
      :data="list.slice((pageNo - 1) * pageSize, pageNo * pageSize)"
      style="width: 100%"
    >
      <el-table-column label="序号" type="index" align="center">
        <template #default="scope">
          <span>{{ (pageNo - 1) * pageSize + scope.$index + 1 }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="会话编号"
        align="center"
        prop="tokenId"
        :show-overflow-tooltip="true"
      />
      <el-table-column
        label="登录名称"
        align="center"
        prop="userName"
        :show-overflow-tooltip="true"
      />
      <el-table-column label="部门名称" align="center" prop="deptName" />
      <el-table-column
        label="主机"
        align="center"
        prop="ipaddr"
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
        label="登录时间"
        align="center"
        prop="loginTime"
        width="180"
      >
        <template #default="scope">
          <span>{{ parseTime(scope.row.loginTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template #default="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleForceLogout(scope.row)"
            v-hasPermi="['monitor:online:forceLogout']"
            >强退</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页设置-->
    <el-pagination
      v-show="total > 0"
      :total="total"
      :current-page="pageNo"
      :page-sizes="[10, 20, 30, 40]"
      :page-size="pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />
  </div>
</template>

<script lang="ts">
import { toRefs, reactive, onMounted } from "vue";
import { ElMessageBox, ElMessage } from "element-plus";
import { list, forceLogout } from "@/api/monitor/online";
export default {
  name: "Online",
  //   components: { EditModule },
  setup() {
    const state = reactive({
      // 遮罩层
      loading: true,
      // 总条数
      total: 0,
      // 表格数据
      list: [],
      // 查询参数
      // 页码
      pageNo: 1,
      // 每页大小
      pageSize: 5,
      queryParams: {
        ipaddr: undefined,
        userName: undefined,
      },
    });
    /** 查询登录日志列表 */
    const getList = () => {
      state.loading = true;
      list(state.queryParams).then((response) => {
        state.list = response.data;
        state.total = state.list.length;
        state.loading = false;
      });
    };
    /** 搜索按钮操作 */
    const handleQuery = () => {
      getList();
    };
    /** 重置按钮操作 */
    const resetQuery = () => {
      state.queryParams.ipaddr = undefined;
      state.queryParams.userName = undefined;
      handleQuery();
    };

    /** 强退按钮操作 */
    const handleForceLogout = (row: any) => {
      ElMessageBox({
        message: '是否确认强退名称为"' + row.userName + '"的数据项?',
        title: "警告",
        showCancelButton: true,
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(function () {
        return forceLogout(row.tokenId).then(() => {
          handleQuery();
          ElMessage.success("强退成功");
        });
      });
    };
    //分页页面大小发生变化
    const handleSizeChange = (val: any) => {
      state.pageSize = val;
    };
    //当前页码发生变化
    const handleCurrentChange = (val: any) => {
      state.pageNo = val;
    };
    // 页面加载时
    onMounted(() => {
      getList();
    });
    return {
      getList,
      resetQuery,
      handleQuery,
      handleSizeChange,
      handleCurrentChange,
      handleForceLogout,
      ...toRefs(state),
    };
  },
};
</script>