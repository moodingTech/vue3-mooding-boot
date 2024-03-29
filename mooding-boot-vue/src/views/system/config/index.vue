<template>
  <div class="app-container">
    <!-- 查询 -->
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="参数名称" prop="configName">       
         <md-input
          placeholder="请输入参数名称模糊查询"
          clearable
          size="small"
          @keyup.enter="handleQuery"
          style="width: 240px"
          v-model="queryParams.configName"
        />
      </el-form-item>
      <el-form-item label="参数键名" prop="configKey">
         <md-input
          placeholder="请输入参数键名模糊查询"
          clearable
          size="small"
          @keyup.enter="handleQuery"
          style="width: 240px"
          v-model="queryParams.configKey"
        />
      </el-form-item>
      <el-form-item label="系统内置" prop="configType">
        <el-select
          v-model="queryParams.configType"
          placeholder="系统内置"
          clearable
          size="small"
          style="width: 240px"
        >
          <el-option
            v-for="dict in typeOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
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
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="onOpenAddModule"
          v-hasPermi="['system:config:add']"
          >新增</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="onOpenEditModule"
          v-hasPermi="['system:config:edit']"
          >修改</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="onTabelRowDel"
          v-hasPermi="['system:config:remove']"
          >删除</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:config:export']"
          >导出</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-refresh"
          size="mini"
          @click="handleClearCache"
          v-hasPermi="['system:config:edit']"
          >清理缓存</el-button
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
      <el-table-column label="参数主键" align="center" prop="configId" />
      <el-table-column
        label="参数名称"
        align="center"
        prop="configName"
        :show-overflow-tooltip="true"
      />
      <el-table-column
        label="参数键名"
        align="center"
        prop="configKey"
        :show-overflow-tooltip="true"
      />
      <el-table-column label="参数键值" align="center" prop="configValue" />
      <el-table-column
        label="系统内置"
        align="center"
        prop="configType"
        :formatter="typeFormat"
      />
      <el-table-column
        label="备注"
        align="center"
        prop="remark"
        :show-overflow-tooltip="true"
      />
      <el-table-column
        label="创建时间"
        align="center"
        prop="createTime"
        width="180"
      >
        <template #default="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="操作"
        align="center"
        width="200"
        class-name="medium-padding fixed-width"
      >
        <template #default="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="onOpenEditModule(scope.row)"
            v-hasPermi="['system:config:edit']"
            >修改</el-button
          >
          <el-button
            v-if="scope.row.parentId != 0"
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="onTabelRowDel(scope.row)"
            v-hasPermi="['system:config:remove']"
            >删除</el-button
          >
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
    <!-- 添加或修改配置参数对话框 -->
    <EditModule ref="editModuleRef" :title="title" />
  </div>
</template>

<script lang="ts">
import {
  ref,
  toRefs,
  reactive,
  onMounted,
  getCurrentInstance,
  onUnmounted,
} from "vue";
import { ElMessageBox, ElMessage } from "element-plus";
import { listConfig, delConfig, clearCache,exportConfig } from "@/api/system/config";
import EditModule from "./component/editModule.vue";

export default {
  name: "index",
  components: { EditModule },
  setup() {
    const { proxy } = getCurrentInstance() as any;
    const editModuleRef = ref();
    const state = reactive({
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 弹出层标题
      title: "",
      // 配置参数表格数据
      tableData: [],
      // 总条数
      total: 0,
      // 类型数据配置参数
      typeOptions: [],
      // 日期范围
      dateRange: [],
      // 查询参数
      queryParams: {
        // 页码
        pageNo: 1,
        // 每页大小
        pageSize: 10,
        configName: undefined,
        configKey: undefined,
        configType: undefined,
      },
    });

    /** 查询配置参数列表 */
    const handleQuery = () => {
      state.loading = true;
      listConfig(proxy.addDateRange(state.queryParams, state.dateRange)).then(
        (response) => {
          state.tableData = response.data.records;
          state.total = response.data.total;
          state.loading = false;
        }
      );
    };
    /** 重置按钮操作 */
    const resetQuery = () => {
      state.queryParams.configName = undefined;
      state.queryParams.configKey = undefined;
      state.queryParams.configType = undefined;
      handleQuery();
    };

    // 配置参数状态配置参数翻译
    const typeFormat = (row: any, column: any) => {
      return proxy.selectDictLabel(state.typeOptions, row.status);
    };

    // 打开新增配置参数弹窗
    const onOpenAddModule = (row: object) => {
      row = [];
      state.title = "添加配置参数";
      editModuleRef.value.openDialog(row);
    };
    // 打开编辑配置参数弹窗
    const onOpenEditModule = (row: object) => {
      state.title = "修改配置参数";
      editModuleRef.value.openDialog(row);
    };
    /** 删除按钮操作 */
    const onTabelRowDel = (row: any) => {
      const dictIds = row.dictId || state.ids;
      ElMessageBox({
        message: '是否确认删除配置参数编号为"' + dictIds + '"的数据项?',
        title: "警告",
        showCancelButton: true,
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(function () {
        return delConfig(dictIds).then(() => {
          handleQuery();
          ElMessage.success("删除成功");
        });
      });
    };
    // 多选框选中数据
    const handleSelectionChange = (selection: any) => {
      state.ids = selection.map((item: any) => item.postId);
      state.single = selection.length != 1;
      state.multiple = !selection.length;
    };
    /** 清理缓存按钮操作 */
    const handleClearCache = () => {
      clearCache().then((response) => {
        ElMessage.success("清理成功");
      });
    };
/** 导出按钮操作 */
    const handleExport=() =>{
      const queryParams = state.queryParams;
      
          ElMessageBox({
        message: '是否确认导出所有参数数据项?',
        title: "警告",
        showCancelButton: true,
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
        }).then(function() {
          return exportConfig(queryParams);
        }).then(response => {
          proxy.download(response.data);
        })
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
   

    // 页面加载时
    onMounted(() => {
      // 查询配置参数信息
      handleQuery();
      // 查询配置参数状态数据配置参数
      proxy.getDicts("sys_yes_no").then((response: any) => {
        state.typeOptions = response.data;
      });
      proxy.mittBus.on("onEditPostModule", (res: any) => {
        handleQuery();
      });
    });
    // 页面卸载时
    onUnmounted(() => {
      proxy.mittBus.off("onEditPostModule");
    });
    return {
      editModuleRef,
      handleSelectionChange,
      handleSizeChange,
      handleCurrentChange,
      handleQuery,
      resetQuery,
      handleExport,
      onOpenAddModule,
      onOpenEditModule,
      typeFormat,
      onTabelRowDel,
      handleClearCache,
      ...toRefs(state),
    };
  },
};
</script>
