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
      <el-form-item label="任务名称" prop="jobName">
        <!-- <el-input
          v-model="queryParams.jobName"
          placeholder="请输入任务名称"
          clearable
          size="small"
          @keyup.enter="handleQuery"
        /> -->
        <md-input
          placeholder="请输入任务名称模糊查询"
          clearable
          size="small"
          @keyup.enter="handleQuery"
          style="width: 240px"
          v-model="queryParams.jobName"
        />
      </el-form-item>
      <el-form-item label="任务组名" prop="jobGroup">
        <el-select
          v-model="queryParams.jobGroup"
          placeholder="请选择任务组名"
          clearable
          size="small"
        >
          <el-option
            v-for="dict in jobGroupOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="任务状态" prop="status">
        <el-select
          v-model="queryParams.status"
          placeholder="请选择任务状态"
          clearable
          size="small"
        >
          <el-option
            v-for="dict in statusOptions"
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
          v-hasPermi="['monitor:job:add']"
          @click="onOpenAddModule"
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
          v-hasPermi="['monitor:job:edit']"
          @click="onOpenEditModule"
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
          v-hasPermi="['monitor:job:remove']"
          >删除</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-s-operation"
          size="mini"
          @click="handleJobLog"
          v-hasPermi="['monitor:job:query']"
          >日志</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          v-hasPermi="['monitor:job:export']"
          @click="handleExport"
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
      <el-table-column label="任务编号" align="center" prop="jobId" />
      <el-table-column
        label="任务名称"
        align="center"
        prop="jobName"
        :show-overflow-tooltip="true"
      />
      <el-table-column
        label="任务组名"
        align="center"
        prop="jobGroup"
        :formatter="jobGroupFormat"
      />
      <el-table-column
        label="调用目标字符串"
        align="center"
        prop="invokeTarget"
        :show-overflow-tooltip="true"
      />
      <el-table-column
        label="cron执行表达式"
        align="center"
        prop="cronExpression"
        :show-overflow-tooltip="true"
      />
      <el-table-column label="状态" align="center">
        <template #default="scope">
          <el-switch
            v-model="scope.row.status"
            active-value="0"
            inactive-value="1"
            @click="handleStatusChange(scope.row)"
          ></el-switch>
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
            icon="el-icon-caret-right"
            @click="handleRun(scope.row)"
            >执行一次</el-button
          >
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
      :current-page="queryParams.pageNum"
      :page-sizes="[10, 20, 30, 40]"
      :page-size="queryParams.pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />
    <!-- 添加或修改定时任务对话框 -->
    <EditModule ref="editModuleRef" :title="title" />

    <!-- 任务日志详细 -->
    <el-dialog title="任务详细" v-model="open" width="700px" append-to-body>
      <el-form
        ref="ruleFormRef"
        :model="modelForm"
        label-width="120px"
        size="mini"
      >
        <el-row>
          <el-col :span="12">
            <el-form-item label="任务编号：">{{
              modelForm.jobId
            }}</el-form-item>
            <el-form-item label="任务名称：">{{
              modelForm.jobName
            }}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="任务分组：">{{
              jobGroupFormat(modelForm)
            }}</el-form-item>
            <el-form-item label="创建时间：">{{
              modelForm.createTime
            }}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="cron表达式：">{{
              modelForm.cronExpression
            }}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="下次执行时间：">{{
              parseTime(modelForm.nextValidTime)
            }}</el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="调用目标方法：">{{
              modelForm.invokeTarget
            }}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="任务状态：">
              <div v-if="modelForm.status == 0">正常</div>
              <div v-else-if="modelForm.status == 1">失败</div>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="是否并发：">
              <div v-if="modelForm.concurrent == 0">允许</div>
              <div v-else-if="modelForm.concurrent == 1">禁止</div>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="执行策略：">
              <div v-if="modelForm.misfirePolicy == 0">默认策略</div>
              <div v-else-if="modelForm.misfirePolicy == 1">立即执行</div>
              <div v-else-if="modelForm.misfirePolicy == 2">执行一次</div>
              <div v-else-if="modelForm.misfirePolicy == 3">放弃执行</div>
            </el-form-item>
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
import {
  ref,
  toRefs,
  reactive,
  onMounted,
  getCurrentInstance,
  onUnmounted,
} from "vue";
import { ElMessageBox, ElMessage } from "element-plus";
import {
  listJob,
  getJob,
  runJob,
  delJob,
  changeJobStatus,
  exportJob,
} from "@/api/monitor/job";
import EditModule from "./component/editModule.vue";
import { useRouter } from "vue-router";

export default {
  name: "Job",
  components: { EditModule },
  setup() {
    const { proxy } = getCurrentInstance() as any;
    const editModuleRef = ref();
    const ruleFormRef = ref<HTMLElement | null>(null);
    const router = useRouter();
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
      // 定时任务表格数据
      tableData: [],
      // 总条数
      total: 0,
      // 是否显示弹出层
      open: false,
      // 表单参数
      modelForm: {},
      // 任务组名字典
      jobGroupOptions: [],
      // 状态字典
      statusOptions: [],
      // 日期范围
      dateRange: [],
      // 查询参数
      queryParams: {
        // 页码
        pageNo: 1,
        // 每页大小
        pageSize: 10,
        jobName: undefined,
        jobGroup: undefined,
        status: undefined,
      },
    });

    /** 查询定时任务列表 */
    const handleQuery = () => {
      state.loading = true;
      listJob(proxy.addDateRange(state.queryParams, state.dateRange)).then(
        (response) => {
          state.tableData = response.data.records;
          state.total = response.data.total;
          state.loading = false;
        }
      );
    };
    /** 重置按钮操作 */
    const resetQuery = () => {
      state.queryParams.jobName = undefined;
      state.queryParams.jobGroup = undefined;
      state.queryParams.status = undefined;
      handleQuery();
    };

    // 定时任务状态定时任务翻译
    const statusFormat = (row: any, column: any) => {
      return proxy.selectDictLabel(state.statusOptions, row.status);
    };
    // 任务组名字典翻译
    const jobGroupFormat = (row: any, column: any) => {
      return proxy.selectDictLabel(state.jobGroupOptions, row.status);
    };

    // 打开新增定时任务弹窗
    const onOpenAddModule = (row: object) => {
      row = [];
      state.title = "添加定时任务";
      editModuleRef.value.openDialog(row);
    };
    // 打开编辑定时任务弹窗
    const onOpenEditModule = (row: object) => {
      const jobIds = row.jobId || state.ids;
      getJob(jobIds).then((response) => {
        row = response.data;
        console.log("打开编辑定时任务弹窗", row);

        state.title = "修改定时任务";
        editModuleRef.value.openDialog(row);
      });
    };
    /** 删除按钮操作 */
    const onTabelRowDel = (row: any) => {
      const dictIds = row.jobId || state.ids;
      ElMessageBox({
        message: '是否确认删除定时任务编号为"' + dictIds + '"的数据项?',
        title: "警告",
        showCancelButton: true,
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(function () {
        return delJob(dictIds).then(() => {
          handleQuery();
          ElMessage.success("删除成功");
        });
      });
    };
    /** 导出按钮操作 */
    const handleExport = () => {
      const queryParams = state.queryParams;
      ElMessageBox({
        message: "是否确认导出所有定时任务数据项?",
        title: "警告",
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return exportJob(queryParams);
        })
        .then((response) => {
          console.log("导出按钮操作", response);
          proxy.download(response.data);
        });
    };

    // 多选框选中数据
    const handleSelectionChange = (selection: any) => {
      state.ids = selection.map((item: any) => item.jobId);
      state.single = selection.length != 1;
      state.multiple = !selection.length;
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

    // 任务状态修改
    const handleStatusChange = (row: any) => {
      let text = row.status === "0" ? "启用" : "停用";
      ElMessageBox({
        message: '确认要"' + text + '""' + row.jobName + '"任务吗?',
        title: "警告",
        showCancelButton: true,
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return changeJobStatus(row.jobId, row.status);
        })
        .then(() => {
          ElMessage(text + "成功");
        })
        .catch(function () {
          row.status = row.status === "0" ? "1" : "0";
        });
    };
    /* 立即执行一次 */
    const handleRun = (row: any) => {
      ElMessageBox({
        message: '确认要立即执行一次"' + row.jobName + '"任务吗?',
        title: "警告",
        showCancelButton: true,
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return runJob(row.jobId, row.jobGroup);
        })
        .then(() => {
          ElMessage("执行成功");
        });
    };

    /** 任务日志列表查询 */
    const handleJobLog = () => {
      router.push("/job/log");
    };
    /** 详细按钮操作 */
    const handleView = (row: any) => {
      state.open = true;
      state.modelForm = row;
    };
    // 页面加载时
    onMounted(() => {
      // 查询定时任务信息
      handleQuery();
      proxy.getDicts("sys_job_status").then((response: any) => {
        state.statusOptions = response.data;
      });
      proxy.getDicts("sys_job_group").then((response: any) => {
        state.jobGroupOptions = response.data;
      });
      proxy.mittBus.on("onEditJobModule", (res: any) => {
        handleQuery();
      });
    });
    // 页面卸载时
    onUnmounted(() => {
      proxy.mittBus.off("onEditJobModule");
    });
    return {
      editModuleRef,
      ruleFormRef,
      handleExport,
      handleView,
      handleSelectionChange,
      handleSizeChange,
      handleCurrentChange,
      handleQuery,
      resetQuery,
      onOpenAddModule,
      onOpenEditModule,
      statusFormat,
      jobGroupFormat,
      onTabelRowDel,
      handleRun,
      handleJobLog,
      handleStatusChange,
      ...toRefs(state),
    };
  },
};
</script>
