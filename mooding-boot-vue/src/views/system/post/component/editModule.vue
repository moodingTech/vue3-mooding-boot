<template>
  <div class="system-menu-container">
    <el-dialog :title="title" v-model="isShowDialog" width="769px">
      <el-form
        :model="ruleForm"
        size="small"
        :rules="ruleRules"
        ref="ruleFormRef"
        label-width="80px"
      >
        <el-row :gutter="35">
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="岗位名称" prop="postName">
              <el-input
                v-model="ruleForm.postName"
                placeholder="请输入岗位名称"
              />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="岗位编码" prop="postCode">
              <el-input
                v-model="ruleForm.postCode"
                placeholder="请输入编码名称"
              />
            </el-form-item>
          </el-col>

          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="岗位顺序" prop="postSort">
              <el-input-number
                v-model="ruleForm.postSort"
                controls-position="right"
                :min="0"
              />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="岗位状态" prop="status">
              <el-radio-group v-model="ruleForm.status">
                <el-radio
                  v-for="dict in statusOptions"
                  :key="dict.dictValue"
                  :label="dict.dictValue"
                  >{{ dict.dictLabel }}
                </el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="备注" prop="remark">
              <el-input
                v-model="ruleForm.remark"
                type="textarea"
                placeholder="请输入内容"
              />
            </el-form-item>
          </el-col>
        </el-row>
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
import { updatePost, addPost } from "@/api/system/post";
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

      // 岗位对象
      ruleForm: {
        postId: "", // 岗位ID
        postName: "", // 岗位名称
        postCode: "",// 岗位编码
        postSort: 0, // 岗位排序
        status: "", //岗位状态
        remark: "", // 备注       
      },
      // 岗位状态数据字典
      statusOptions: [],
      // 岗位树选项
      deptOptions: [],
      // 表单校验
      ruleRules: {
        postName: [
          { required: true, message: "岗位名称不能为空", trigger: "blur" }
        ],
        postCode: [
          { required: true, message: "岗位编码不能为空", trigger: "blur" }
        ],
        postSort: [
          { required: true, message: "岗位顺序不能为空", trigger: "blur" }
        ]
      },
    });
    // 打开弹窗
    const openDialog = (row: any) => {
      if (row.postId && row.postId != undefined && row.postId != "") {
        state.ruleForm = row;
        state.ruleForm.postId=row.postId; // 岗位ID
        state.ruleForm.postName=row.postName; // 岗位名称
        state.ruleForm.postCode=row.postCode;// 岗位编码
        state.ruleForm.postSort=row.postSort;// 岗位排序
        state.ruleForm.status=row.status; //岗位状态
        state.ruleForm.remark=row.remark;// 备注     
      } else {
        initForm();       
      }
      state.isShowDialog = true;

      // 查询岗位状态数据字典
      proxy.getDicts("sys_normal_disable").then((response: any) => {
        state.statusOptions = response.data;
      });    
    };

    // 关闭弹窗
    const closeDialog = (row?: object) => {     
      proxy.mittBus.emit("onEditPostModule", row);
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
          if (
            state.ruleForm.postId != undefined &&
            state.ruleForm.postId != ""
          ) {
            updatePost(state.ruleForm).then((response) => {
              ElMessage.success("修改成功");
              closeDialog(state.ruleForm); // 关闭弹窗
            });
          } else {
            addPost(state.ruleForm).then((response) => {
              ElMessage.success("新增成功");
              closeDialog(state.ruleForm); // 关闭弹窗
            });
          }
        }
      });
    };
    // 表单初始化，方法：`resetFields()` 无法使用
    const initForm = () => { 
      state.ruleForm.postId = ""; // 岗位ID
      state.ruleForm.postName = ""; // 岗位名称
      state.ruleForm.postCode = ""; // 岗位编码
      state.ruleForm.postSort = 0 ; // 岗位排序
      state.ruleForm.status =  "0"; //岗位状态
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
