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
      <el-form-item label="字典名称" prop="dictName">
<<<<<<< HEAD
        <!-- <el-input
          v-model="queryParams.dictName"
          placeholder="请输入字典名称"
          clearable
          size="small"
          style="width: 240px"
          @keyup.enter="handleQuery"
        /> -->
=======
>>>>>>> master
        <md-input
          placeholder="请输入字典名称模糊查询"
          clearable
          size="small"
          @keyup.enter="handleQuery"
          style="width: 240px"
          v-model="queryParams.dictName"
        />
      </el-form-item>
      <el-form-item label="字典类型" prop="dictType">
        <el-input
          v-model="queryParams.dictType"
          placeholder="请输入字典类型"
          clearable
          size="small"
          style="width: 240px"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select
          v-model="queryParams.status"
          placeholder="字典状态"
          clearable
          size="small"
<<<<<<< HEAD
=======
          style="width: 240px"
>>>>>>> master
        >
          <el-option
            v-for="dict in statusOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="创建时间">
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
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="onOpenAddModule"
          v-hasPermi="['system:dict:add']"
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
          v-hasPermi="['system:dict:edit']"
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
          v-hasPermi="['system:dict:remove']"
          >删除</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
<<<<<<< HEAD
          @click="onTabelRowDel"
=======
          @click="handleExport"
>>>>>>> master
          v-hasPermi="['system:dict:export']"
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
          v-hasPermi="['system:dict:remove']"
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
      <el-table-column type="selection" align="center" />
      <el-table-column label="字典编号" align="center" prop="dictId" />
      <el-table-column
        label="字典名称"
        align="center"
        prop="dictName"
        :show-overflow-tooltip="true"
      />
      <el-table-column
        label="字典类型"
        align="center"
        :show-overflow-tooltip="true"
      >
        <template #default="scope">
          <router-link
            :to="'/dict/type/data/' + scope.row.dictId"
            class="link-type"
          >
            <span>{{ scope.row.dictType }}</span>
          </router-link>
        </template>
      </el-table-column>
      <el-table-column
        label="状态"
        align="center"
        prop="status"
        :formatter="statusFormat"
      />
      <el-table-column
        label="备注"
        align="center"
        prop="remark"
        :show-overflow-tooltip="true"
      />

      <el-table-column
        label="状态"
        align="center"
        prop="status"
        :formatter="statusFormat"
      />
      <el-table-column
        label="操作"
        align="center"
        width="200"
        class-name="medium-padding fixed-width"
      >
        <template #default="scope">
          <el-button
            v-if="scope.row.parentId != 0"
            size="mini"
            type="text"
            icon="el-icon-s-tools"
            @click="editDictItem(scope.row)"
            >字典配置</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="onOpenEditModule(scope.row)"
            v-hasPermi="['system:dict:edit']"
            >修改</el-button
          >
          <el-button
            v-if="scope.row.parentId != 0"
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="onTabelRowDel(scope.row)"
            v-hasPermi="['system:dict:remove']"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页设置-->
    <el-pagination
      v-show="total > 0"
      :total="total"
      :current-page="queryParams.pageNum"
      :page-sizes="[10, 20, 30, 40]"
      :page-size="queryParams.pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />
    <!-- 添加或修改字典对话框 -->
    <EditModule ref="editModuleRef" :title="title" />

    <!-- 字典列表对话框 -->
    <dictList ref="dictItemModuleRef" />
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
import { listType, delType, clearCache } from "@/api/system/dict/type";
import EditModule from "@/views/system/dict/component/editModule.vue";
import dictList from "./component/dictList.vue";
<<<<<<< HEAD
=======
import { exportData } from "@/api/system/dict/data";
>>>>>>> master

export default {
  name: "index",
  components: { EditModule, dictList },
  setup() {
    const { proxy } = getCurrentInstance() as any;
    const editModuleRef = ref();
    const dictItemModuleRef = ref();
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
      // 字典表格数据
      tableData: [],
      // 总条数
      total: 0,
      // 状态数据字典
      statusOptions: [],
      // 日期范围
      dateRange: [],
      // 查询参数
      queryParams: {
        // 页码
        pageNo: 1,
        // 每页大小
        pageSize: 10,
        dictName: undefined,
        dictType: undefined,
        status: undefined,
      },
      //字典数据表单参数
      dictItemForm: [],
    });

    /** 查询字典列表 */
    const handleQuery = () => {
      state.loading = true;
      listType(proxy.addDateRange(state.queryParams, state.dateRange)).then(
        (response) => {
          state.tableData = response.data.records;
          state.total = response.data.total;
          state.loading = false;
        }
      );
    };
    /** 重置按钮操作 */
    const resetQuery = () => {
      state.queryParams.dictName = undefined;
      state.queryParams.dictType = undefined;
      state.queryParams.status = undefined;
      handleQuery();
    };

    // 字典状态字典翻译
    const statusFormat = (row: any, column: any) => {
      return proxy.selectDictLabel(state.statusOptions, row.status);
    };

    // 打开新增字典弹窗
    const onOpenAddModule = (row: object) => {
      row = [];
      state.title = "添加字典";
      editModuleRef.value.openDialog(row);
    };
    // 打开编辑字典弹窗
    const onOpenEditModule = (row: object) => {
      state.title = "修改字典";
      editModuleRef.value.openDialog(row);
    };
    /** 删除按钮操作 */
    const onTabelRowDel = (row: any) => {
      const dictIds = row.dictId || state.ids;
      ElMessageBox({
        message: '是否确认删除字典编号为"' + dictIds + '"的数据项?',
        title: "警告",
        showCancelButton: true,
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(function () {
        return delType(dictIds).then(() => {
          handleQuery();
          ElMessage.success("删除成功");
        });
      });
    };
    // 多选框选中数据
    const handleSelectionChange = (selection: any) => {
<<<<<<< HEAD
      state.ids = selection.map((item: any) => item.postId);
=======
      state.ids = selection.map((item: any) => item.dictId);
>>>>>>> master
      state.single = selection.length != 1;
      state.multiple = !selection.length;
    };
    /** 清理缓存按钮操作 */
    const handleClearCache = () => {
      clearCache().then((response) => {
        ElMessage.success("清理成功");
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
    //编辑字典数据
    const editDictItem = (record: any) => {
      dictItemModuleRef.value.openDrawer(record);
    };

<<<<<<< HEAD
=======
    /** 导出按钮操作 */
    const handleExport = () => {
      const queryParams = state.queryParams;
      ElMessageBox({
        message: "是否确认导出所有类型数据项?",
        title: "警告",
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return exportData(queryParams);
        })
        .then((response) => {
          proxy.download(response.data);
        });
    };
>>>>>>> master
    // 页面加载时
    onMounted(() => {
      // 查询字典信息
      handleQuery();
      // 查询字典状态数据字典
      proxy.getDicts("sys_normal_disable").then((response: any) => {
        state.statusOptions = response.data;
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
      dictItemModuleRef,
      handleSelectionChange,
      handleSizeChange,
      handleCurrentChange,
      editDictItem,
      handleQuery,
      resetQuery,
      onOpenAddModule,
      onOpenEditModule,
      statusFormat,
      onTabelRowDel,
<<<<<<< HEAD
=======
      handleExport,
>>>>>>> master
      handleClearCache,
      ...toRefs(state),
    };
  },
};
</script>
