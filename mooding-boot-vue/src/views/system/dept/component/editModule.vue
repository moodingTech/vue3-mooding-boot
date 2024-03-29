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
            <el-form-item label="上级部门" prop="parentId">
              <el-cascader
                v-model="ruleForm.parentId"
                :options="deptOptions"
                :props="{
                  label: 'label',
                  value: 'id',
                  checkStrictly: true,
                  emitPath: false,
                }"
                clearable
                filterable
                placeholder="选择上级部门"
                :show-all-levels="false"
              ></el-cascader>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="部门名称" prop="deptName">
              <el-input
                v-model="ruleForm.deptName"
                placeholder="请输入部门名称"
                clearable
              ></el-input>
            </el-form-item>
          </el-col>

          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="负责人">
              <el-input
                v-model="ruleForm.leader"
                placeholder="请输入负责人"
                clearable
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="联系电话" prop="phone">
              <el-input
                v-model="ruleForm.phone"
                placeholder="请输入联系电话"
                clearable
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="邮箱" prop="email">
              <el-input
                v-model="ruleForm.email"
                placeholder="请输入邮箱"
                clearable
              ></el-input>
            </el-form-item>
          </el-col>

          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="部门状态" prop="status">
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
            <el-form-item label="部门排序" prop="orderNum">
              <el-input
                v-model="ruleForm.orderNum"
                placeholder="部门排序"
                clearable
              ></el-input>
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
import IconSelector from "@/components/iconSelector/index.vue";
import { treeselect, updateDept, addDept } from "@/api/system/dept";
import { ElMessage } from "element-plus";

export default {
  name: "editMenu",
  components: { IconSelector },
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

      // 部门对象
      ruleForm: {
        deptId: "", // 部门ID
        deptName: "", // 部门名称
        parentId: "", // 父部门ID
        menuSort: "", // 部门排序
        status: "", //部门状态
        leader: "", // 部门负责人
        phone: "", // 联系电话
        email: "", // 邮箱
      },
      // 部门状态数据字典
      statusOptions: [],
      // 部门树选项
      deptOptions: [],
      // 表单校验
      ruleRules: {
        deptName: [
          { required: true, message: "部门名称不能为空", trigger: "blur" },
        ],
        parentId: [
          { required: true, message: "父部门不能为空", trigger: "blur" },
        ],
        status: [
          { required: true, message: "部门状态不能为空", trigger: "blur" },
        ],
        orderNum: [
          { required: true, message: "部门顺序不能为空", trigger: "blur" },
        ],
        email: [
          {
            type: "email",
            message: "'请输入正确的邮箱地址",
            trigger: ["blur", "change"],
          },
        ],
        phone: [
          {
            pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
            message: "请输入正确的手机号码",
            trigger: "blur",
          },
        ],
      },
    });
    // 打开弹窗
    const openDialog = (row: any) => {
      if (row.deptId && row.deptId != undefined && row.deptId != "") {
        state.ruleForm = row;
      } else {
        initForm();
        state.ruleForm.parentId = row.parentId;
      }

      state.isShowDialog = true;

      // 查询部门状态数据字典
      proxy.getDicts("sys_normal_disable").then((response: any) => {
        state.statusOptions = response.data;
      });

      // 查询部门下拉树结构
      treeselect().then((response: any) => {
        state.deptOptions = [];
        const dataList = response.data;

        state.deptOptions = dataList;
      });
    };

    // 关闭弹窗
    const closeDialog = (row?: object) => {
      proxy.mittBus.emit("onEditDeptModule", row);
      state.isShowDialog = false;
    };
    // 取消
    const onCancel = () => {
      closeDialog();
      // initForm();
    };
    /** 转换部门数据结构 */
    const normalizer = (node: any) => {
      if (node.children && !node.children.length) {
        delete node.children;
      }
      return {
        id: node.deptId,
        label: node.deptName,
        children: node.children,
      };
    };
    // 保存
    const onSubmit = () => {
      const formWrap = unref(ruleFormRef) as any;
      if (!formWrap) return;
      formWrap.validate((valid: boolean) => {
        if (valid) {
          if (
            state.ruleForm.deptId != undefined &&
            state.ruleForm.deptId != ""
          ) {
            updateDept(state.ruleForm).then((response) => {
              ElMessage.success("修改成功");
              closeDialog(state.ruleForm); // 关闭弹窗
            });
          } else {
            addDept(state.ruleForm).then((response) => {
              ElMessage.success("新增成功");
              closeDialog(state.ruleForm); // 关闭弹窗
            });
          }
        }
      });
    };
    // 表单初始化，方法：`resetFields()` 无法使用
    const initForm = () => {
      state.ruleForm.deptId = ""; // 部门ID
      state.ruleForm.deptName = ""; // 部门名称
      state.ruleForm.parentId = ""; // 父部门ID
      state.ruleForm.menuSort = ""; // 部门排序
      state.ruleForm.status = state.ruleForm.status
        ? state.ruleForm.status
        : "0"; //部门状态
      state.ruleForm.leader = ""; // 部门负责人
      state.ruleForm.phone = ""; // 联系电话
      state.ruleForm.email = ""; // 邮箱
    };

    return {
      ruleFormRef,
      openDialog,
      closeDialog,
      onCancel,
      onSubmit,
      normalizer,
      ...toRefs(state),
    };
  },
};
</script>
