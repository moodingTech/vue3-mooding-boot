
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
      <el-form-item label="系统模块" prop="title">
        <!-- <el-input
          v-model="queryParams.title"
          placeholder="请输入系统模块"
          clearable
          style="width: 240px"
          size="small"
          @keyup.enter="handleQuery"
        /> -->
        <md-input
          placeholder="请输入系统模块模糊查询"
          clearable
          size="small"
          @keyup.enter="handleQuery"
          style="width: 240px"
          v-model="queryParams.title"
        />
      </el-form-item>
      <el-form-item label="操作人员" prop="operName">
        <!-- <el-input
          v-model="queryParams.operName"
          placeholder="请输入操作人员"
          clearable
          style="width: 240px"
          size="small"
          @keyup.enter="handleQuery"
        /> -->
        <md-input
          placeholder="请输入操作人员模糊查询"
          clearable
          size="small"
          @keyup.enter="handleQuery"
          style="width: 240px"
          v-model="queryParams.operName"
        />
      </el-form-item>
      <el-form-item label="类型" prop="businessType">
        <el-select
          v-model="queryParams.businessType"
          placeholder="操作类型"
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
      <el-form-item label="状态" prop="status">
        <el-select
          v-model="queryParams.status"
          placeholder="操作状态"
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
      <el-form-item label="操作时间">
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
          v-hasPermi="['monitor:operlog:remove']"
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
          v-hasPermi="['monitor:operlog:remove']"
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
          v-hasPermi="['monitor:operlog:export']"
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
      <el-table-column label="日志编号" align="center" prop="operId" />
      <el-table-column label="系统模块" align="center" prop="title" />
      <el-table-column
        label="操作类型"
        align="center"
        prop="businessType"
        :formatter="typeFormat"
      />
      <el-table-column label="请求方式" align="center" prop="requestMethod" />
      <el-table-column label="操作人员" align="center" prop="operName" />
      <el-table-column
        label="主机"
        align="center"
        prop="operIp"
        width="130"
        :show-overflow-tooltip="true"
      />
      <el-table-column
        label="操作地点"
        align="center"
        prop="operLocation"
        :show-overflow-tooltip="true"
      />
      <el-table-column
        label="操作状态"
        align="center"
        prop="status"
        :formatter="statusFormat"
      />
      <!-- <el-table-column
        label="操作日期"
        align="center"
        prop="operTime"
        width="180"
      >
        <template #default="scope">
          <span>{{ parseTime(scope.row.operTime) }}</span>
        </template>
      </el-table-column> -->
      <el-table-column
        prop="operTime"
        label="操作日期"
        show-overflow-tooltip
      ></el-table-column>
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template #default="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleView(scope.row)"
            >详细</el-button
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

    <!-- 操作日志详细 -->
    <el-dialog title="操作日志详细" v-model="open" width="700px" append-to-body>
      <el-form
        ref="ruleFormRef"
        :model="modelForm"
        label-width="100px"
        size="mini"
      >
        <el-row>
          <el-col :span="12">
            <el-form-item label="操作模块："
              >{{ modelForm.title }} / {{ typeFormat(modelForm) }}</el-form-item
            >
            <el-form-item label="登录信息："
              >{{ modelForm.operName }} / {{ modelForm.operIp }} /
              {{ modelForm.operLocation }}</el-form-item
            >
          </el-col>
          <el-col :span="12">
            <el-form-item label="请求地址：">{{
              modelForm.operUrl
            }}</el-form-item>
            <el-form-item label="请求方式：">{{
              modelForm.requestMethod
            }}</el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="操作方法：">{{
              modelForm.method
            }}</el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="请求参数：">{{
              modelForm.operParam
            }}</el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="返回参数：">{{
              modelForm.jsonResult
            }}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="操作状态：">
              <!-- <div v-if="modelForm.status === 0">正常</div>
              <div v-else-if="modelForm.status === 1">失败</div> -->
              {{ statusFormat(modelForm) }}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="操作时间：">{{
              parseTime(modelForm.operTime)
            }}</el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="异常信息：" v-if="modelForm.status === 1">{{
              modelForm.errorMsg
            }}</el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div class="footer dialog-footer">
        <el-button @click="open = false">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script lang="ts">
import { reactive, onMounted, toRefs, ref, getCurrentInstance } from "vue";
import {
  listOperlog,
  delOperlog,
  cleanOperlog,
  exportOperlog,
} from "@/api/monitor/operlog";
import { ElMessageBox, ElMessage } from "element-plus";
export default {
  name: "Operlog",
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
      // 是否显示弹出层
      open: false,
      // 表单参数
      modelForm: {},
      // 显示搜索条件
      showSearch: true,
      // 类型数据字典
      typeOptions: [],
      // 类型数据字典
      statusOptions: [],
      // 日期范围
      dateRange: [],
      // 查询参数
      queryParams: {
        pageNo: 1,
        pageSize: 10,
        title: undefined,
        operName: undefined,
        businessType: undefined,
        status: undefined,
      },
    });

    /** 查询定时任务列表 */
    const handleQuery = () => {
      state.loading = true;
      listOperlog(proxy.addDateRange(state.queryParams, state.dateRange)).then(
        (response) => {
          state.tableData = response.data.records;
          state.total = response.data.total;
          state.loading = false;
        }
      );
    };

    /** 重置按钮操作 */
    const resetQuery = () => {
      state.queryParams.title = undefined;
      state.queryParams.operName = undefined;
      state.queryParams.businessType = undefined;
      state.queryParams.status = undefined;
      handleQuery();
    };

    /** 清空按钮操作 */
    const handleClean = () => {
      ElMessageBox({
        message: "是否确认清空所有操作日志数据项?",
        title: "警告",
        showCancelButton: true,
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return cleanOperlog();
        })
        .then(() => {
          handleQuery();
          ElMessage.success("清空成功");
        });
    };

    /** 删除按钮操作 */
    const onTabelRowDel = (row: any) => {
      const operIds = row.operId || state.ids;
      ElMessageBox({
        message: '是否确认删除日志编号为"' + operIds + '"的数据项?',
        title: "警告",
        showCancelButton: true,
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(function () {
        return delOperlog(operIds).then(() => {
          handleQuery();
          ElMessage.success("删除成功");
        });
      });
    };
    /** 导出按钮操作 */
    const handleExport = () => {
      const queryParams = state.queryParams;
      ElMessageBox({
        message: "是否确认导出所有操作日志数据项?",
        title: "警告",
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return exportOperlog(queryParams);
        })
        .then((response) => {
          console.log("导出按钮操作", response);
          proxy.download(response.data);
        });
    };

    /** 详细按钮操作 */
    const handleView = (row: any) => {
      state.open = true;
      state.modelForm = row;
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

    // 操作日志类型字典翻译
    const typeFormat = (row: any, column: any) => {
      return proxy.selectDictLabel(state.typeOptions, row.businessType);
    };
    // 多选框选中数据
    const handleSelectionChange = (selection: any) => {
      state.ids = selection.map((item: any) => item.operId);
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
      proxy.getDicts("sys_oper_type").then((response: any) => {
        state.typeOptions = response.data;
      });
    });

    return {
      ruleFormRef,
      statusFormat,
      typeFormat,
      handleView,
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
