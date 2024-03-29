<template>
  <div class="system-menu-container">
    <el-dialog :title="title" v-model="isShowDialog" width="500px">
      <el-form
        :model="ruleForm"
        size="small"
        :rules="ruleRules"
        ref="ruleFormRef"
        label-width="100px"
      >
        <el-form-item label="任务名称" prop="jobName">
          <el-input v-model="ruleForm.jobName" placeholder="请输入任务名称" />
        </el-form-item>

        <el-form-item label="任务分组" prop="jobGroup">
          <el-select v-model="ruleForm.jobGroup" placeholder="请选择">
            <el-option
              v-for="dict in jobGroupOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>

        <el-form-item prop="invokeTarget">
          <template #label>
            <span>
              调用方法
              <el-tooltip placement="top">
                <template #content>
                  Bean调用示例：moodingTask.moodingParams('ry')
                  <br />Class类调用示例：com.mooding.quartz.task.moodingTask.moodingParams('ry')
                  <br />参数说明：支持字符串，布尔类型，长整型，浮点型，整型
                </template>
                <i class="el-icon-question"></i>
              </el-tooltip>
            </span>
          </template>
          <el-input
            v-model="ruleForm.invokeTarget"
            placeholder="请输入调用目标字符串"
          />
        </el-form-item>

        <el-form-item label="cron表达式" prop="cronExpression">
          <el-input
            v-model="ruleForm.cronExpression"
            placeholder="请输入cron执行表达式"
          />
        </el-form-item>

        <el-form-item label="是否并发" prop="concurrent">
          <el-radio-group v-model="ruleForm.concurrent" size="small">
            <el-radio-button label="0">允许</el-radio-button>
            <el-radio-button label="1">禁止</el-radio-button>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="错误策略" prop="misfirePolicy">
          <el-radio-group v-model="ruleForm.misfirePolicy" size="small">
            <el-radio-button label="1">立即执行</el-radio-button>
            <el-radio-button label="2">执行一次</el-radio-button>
            <el-radio-button label="3">放弃执行</el-radio-button>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="状态">
          <el-radio-group v-model="ruleForm.status">
            <el-radio
              v-for="dict in statusOptions"
              :key="dict.dictValue"
              :label="dict.dictValue"
              >{{ dict.dictLabel }}</el-radio
            >
          </el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="onCancel" size="small">取 消</el-button>
          <el-button type="primary" @click="onSubmit" size="small"
            >编 辑</el-button
          >
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script lang="ts">
import { reactive, toRefs, ref, unref, getCurrentInstance } from "vue";
import { addJob, updateJob } from "@/api/monitor/job";
import { ElMessage } from "element-plus";

export default {
  name: "editMenu",
  props: {
    // 弹窗标题
    title: {
      type: String,
      default: () => "",
    },
  },
  setup() {
    const { proxy } = getCurrentInstance() as any;
    const ruleFormRef = ref<HTMLElement | null>(null);
    const state = reactive({
      // 是否显示弹出层
      isShowDialog: false,
      // 表单参数对象
      ruleForm: {
        jobId: "", // 定时任务ID
        jobName: "", // 定时任务名称
        jobGroup: "", // 任务分组
        invokeTarget: "", //调用方法
        cronExpression: "", // cron表达式
        concurrent: "", //是否并发
        misfirePolicy: "", //错误策略
        status: "", //状态
        remark: "", // 备注
      },
      // 任务组名字典
      jobGroupOptions: [],
      // 状态字典
      statusOptions: [],
      // 表单校验
      ruleRules: {
        jobName: [
          { required: true, message: "任务名称不能为空", trigger: "blur" },
        ],
        invokeTarget: [
          {
            required: true,
            message: "调用目标字符串不能为空",
            trigger: "blur",
          },
        ],
        cronExpression: [
          {
            required: true,
            message: "cron执行表达式不能为空",
            trigger: "blur",
          },
        ],
      },
    });
    // 打开弹窗
    const openDialog = (row: any) => {
      console.log("打开弹窗", row);
      if (row.jobId && row.jobId != undefined && row.jobId != "") {
        state.ruleForm = row;
        state.ruleForm.jobId = row.jobId; // 定时任务ID
        state.ruleForm.jobName = row.jobName; // 定时任务名称
        state.ruleForm.jobGroup = row.jobGroup; // 任务分组
        state.ruleForm.invokeTarget = row.invokeTarget; //调用方法
        state.ruleForm.cronExpression = row.cronExpression; // cron表达式
        state.ruleForm.concurrent = row.concurrent; //是否并发
        state.ruleForm.misfirePolicy = row.misfirePolicy; //错误策略
        state.ruleForm.status = row.status; //状态
        state.ruleForm.remark = row.remark; // 备注
      } else {
        initForm();
      }
      state.isShowDialog = true;
      // 查询定时任务状态数据定时任务
      proxy.getDicts("sys_job_status").then((response: any) => {
        state.statusOptions = response.data;
      });
      // 查询定时任务的任务组名字典
      proxy.getDicts("sys_job_group").then((response: any) => {
        state.jobGroupOptions = response.data;
      });
    };

    // 关闭弹窗
    const closeDialog = (row?: object) => {
      proxy.mittBus.emit("onEditJobModule", row);
      state.isShowDialog = false;
    };
    // 取消
    const onCancel = () => {
      closeDialog();
    };

    // 保存
    const onSubmit = () => {
      const formWrap = unref(ruleFormRef) as any;
      if (!formWrap) return;
      formWrap.validate((valid: boolean) => {
        if (valid) {
          if (state.ruleForm.jobId != undefined && state.ruleForm.jobId != "") {
            updateJob(state.ruleForm).then((response) => {
              ElMessage.success("修改成功");
              closeDialog(state.ruleForm); // 关闭弹窗
            });
          } else {
            addJob(state.ruleForm).then((response) => {
              ElMessage.success("新增成功");
              closeDialog(state.ruleForm); // 关闭弹窗
            });
          }
        }
      });
    };
    // 表单初始化，方法：`resetFields()` 无法使用
    const initForm = () => {
      state.ruleForm.jobId = ""; // 定时任务ID
      state.ruleForm.jobName = ""; // 定时任务名称
      state.ruleForm.jobGroup = ""; // 任务分组
      state.ruleForm.invokeTarget = ""; //调用方法
      state.ruleForm.cronExpression = ""; // cron表达式
      state.ruleForm.concurrent = ""; //是否并发
      state.ruleForm.misfirePolicy = ""; //错误策略
      state.ruleForm.status = ""; //状态
      state.ruleForm.remark = ""; // 备注
    };

    return {
      ruleFormRef,
      openDialog,
      closeDialog,
      onCancel,
      initForm,
      onSubmit,
      ...toRefs(state),
    };
  },
};
</script>
