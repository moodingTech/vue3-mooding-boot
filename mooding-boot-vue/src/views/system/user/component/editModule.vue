<template>
  <div class="system-menu-container">
    <el-dialog :title="title" v-model="isShowDialog" width="769px">
      <el-form
        ref="ruleFormRef"
        :model="ruleForm"
        :rules="ruleRules"
        label-width="80px"
      >
        <el-row :gutter="35">
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="用户昵称" prop="nickName">
              <el-input
                v-model="ruleForm.nickName"
                placeholder="请输入用户昵称"
              />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="归属部门" prop="deptId">
              <el-cascader
                v-model="ruleForm.deptId"
                :options="deptOptions"
                :props="{
                  label: 'label',
                  value: 'id',
                  checkStrictly: true,
                  emitPath: false,
                }"
                class="w100"
                clearable
                filterable
                placeholder="请选择归属部门"
                :show-all-levels="false"
              ></el-cascader>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="手机号码" prop="phonenumber">
              <el-input
                v-model="ruleForm.phonenumber"
                placeholder="请输入手机号码"
                maxlength="11"
              />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="邮箱" prop="email">
              <el-input
                v-model="ruleForm.email"
                placeholder="请输入邮箱"
                maxlength="50"
              />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item
              v-if="ruleForm.userId == undefined"
              label="用户名称"
              prop="userName"
            >
              <el-input
                v-model="ruleForm.userName"
                placeholder="请输入用户名称"
              />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item
              v-if="ruleForm.userId == undefined"
              label="用户密码"
              prop="password"
            >
              <el-input
                v-model="ruleForm.password"
                placeholder="请输入用户密码"
                type="password"
              />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="用户性别">
              <el-select v-model="ruleForm.sex" placeholder="请选择">
                <el-option
                  v-for="dict in sexOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="状态">
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
            <el-form-item label="岗位">
              <el-select
                v-model="ruleForm.postIds"
                multiple
                placeholder="请选择"
              >
                <el-option
                  v-for="item in postOptions"
                  :key="item.postId"
                  :label="item.postName"
                  :value="item.postId"
                  :disabled="item.status == 1"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="角色">
              <el-select
                v-model="ruleForm.roleIds"
                multiple
                placeholder="请选择"
              >
                <el-option
                  v-for="item in roleOptions"
                  :key="item.roleId"
                  :label="item.roleName"
                  :value="item.roleId"
                  :disabled="item.status == 1"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="备注">
              <el-input
                v-model="ruleForm.remark"
                type="textarea"
                placeholder="请输入内容"
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
import { treeselect } from "@/api/system/dept";
import { updateUser, addUser, getUser } from "@/api/system/user";
import { ElMessage } from "element-plus";

export default {
  name: "editUser",
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
      // 默认密码
      initPassword: undefined,
      // 性别状态字典
      sexOptions: [],
      // 角色选项
      roleOptions: [],
      // 状态数据字典
      statusOptions: [],
      // 部门树选项
      deptOptions: [],
      // 岗位选项
      postOptions: [],
      ruleForm: {
        userId: "", // 用戶ID
        userName: "", // 用戶名称
        nickName: "", // 用戶昵称
        deptId: "", // 部门ID
        phonenumber: "", // 手机号
        email: "", // 邮箱
        status: "", //用户状态
        password: "", // 用户密码
        avatar: "", // 用户头像
        sex: "", // 性别
        postIds: [], // 岗位组
        roleIds: [], // 角色组
        remark: "", // 备注
      },
      // 显示状态数据字典
      isHideOptions: [],
      // 菜单类型数据字典
      menuTypeOptions: [],
      // 数字是否数据字典
      yesOrNoOptions: [],
      // 菜单树选项
      menuOptions: [],
      // 表单校验
      ruleRules: {
        userName: [
          { required: true, message: "用户名称不能为空", trigger: "blur" },
        ],
        nickName: [
          { required: true, message: "用户昵称不能为空", trigger: "blur" },
        ],
        password: [
          { required: true, message: "用户密码不能为空", trigger: "blur" },
        ],
        email: [
          {
            type: "email",
            message: "'请输入正确的邮箱地址",
            trigger: ["blur", "change"],
          },
        ],
        phonenumber: [
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
      if (row && row.userId && row.userId != undefined && row.userId != "") {
        getUser(row.userId).then((response: any) => {
          state.ruleForm = response.data.userInfo;
          state.postOptions = response.data.posts;
          state.roleOptions = response.data.roles;
          state.ruleForm.postIds = response.data.postIds;
          state.ruleForm.roleIds = response.data.roleIds;
        });
      } else {
        initForm();
      }
      getTreeselect();
      state.isShowDialog = true;

      // 查询显示性別数据字典
      proxy.getDicts("sys_user_sex").then((response: any) => {
        state.sexOptions = response.data;
      });
      // 查询显示狀態数据字典
      proxy.getDicts("sys_normal_disable").then((response: any) => {
        state.statusOptions = response.data;
      });
      proxy.mittBus.emit("onEditUserModule");
    };
    // 关闭弹窗
    const closeDialog = (row?: object) => {
      state.isShowDialog = false;
    };
    // 取消
    const onCancel = () => {
      closeDialog();
      // initForm();
    };
    /** 查询部门下拉树结构 */
    const getTreeselect = async () => {
      treeselect().then((response) => {
        state.deptOptions = response.data;
      });
    };
    /** 提交按钮 */
    // const submitForm = (row: any) => {
    //   const formWrap = unref(userFormRef) as any;
    //   if (!formWrap) return;
    //   formWrap.validate(async (valid: boolean) => {
    //     if (valid) {
    //       if (state.userForm.userId != undefined) {
    //         updateUser(state.userForm).then((response: any) => {
    //           ElMessage.success("修改成功");
    //           state.open = false;
    //           getList();
    //         });
    //       } else {
    //         console.log("新增用户", state.userForm);
    //         addUser(state.userForm).then((response: any) => {
    //           ElMessage.success("新增成功");
    //           state.open = false;
    //           getList();
    //         });
    //       }
    //     }
    //   });
    // };
    // 保存
    const onSubmit = () => {
      const formWrap = unref(ruleFormRef) as any;
      if (!formWrap) return;
      formWrap.validate((valid: boolean) => {
        if (valid) {
          if (state.ruleForm.userId != undefined) {
            updateUser(state.ruleForm).then((response) => {
              ElMessage.success("修改成功");
              closeDialog(); // 关闭弹窗
            });
          } else {
            addUser(state.ruleForm).then((response) => {
              ElMessage.success("新增成功");
              closeDialog(); // 关闭弹窗
            });
          }
        }
      });
      // console.log(state.ruleForm); // 数据，请注意需要转换的类型
      // closeDialog(); // 关闭弹窗
      // setBackEndControlRefreshRoutes() // 刷新菜单，未进行后端接口测试
    };
    // 表单初始化，方法：`resetFields()` 无法使用
    const initForm = () => {
      state.ruleForm.userId = ""; // 用戶ID
      state.ruleForm.userName = ""; // 用戶名称
      state.ruleForm.nickName = ""; // 用戶昵称
      state.ruleForm.deptId = ""; // 部门ID
      state.ruleForm.phonenumber = ""; // 手机号
      state.ruleForm.email = ""; // 邮箱
      state.ruleForm.status = ""; //用户状态
      state.ruleForm.password = ""; // 用户密码
      state.ruleForm.avatar = ""; // 用户头像
      state.ruleForm.sex = ""; // 性别
      state.ruleForm.postIds = []; // 岗位组
      state.ruleForm.roleIds = []; // 角色组
      state.ruleForm.remark = ""; // 备注
    };

    // 头像上传
    const handleAvatarSuccess = (file: any) => {
      //   state.imageUrl = URL.createObjectURL(file.raw);
    };
    // 头像上传前校验
    const beforeAvatarUpload = (file: any) => {
      const isJPG = file.type === "image/jpeg";
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        ElMessage.error("上传头像图片只能是 JPG 格式!");
      }
      if (!isLt2M) {
        ElMessage.error("上传头像图片大小不能超过 2MB!");
      }
      return isJPG && isLt2M;
    };
    return {
      ruleFormRef,
      openDialog,
      closeDialog,
      onCancel,
      onSubmit,
      beforeAvatarUpload,
      handleAvatarSuccess,
      ...toRefs(state),
    };
  },
};
</script>
<style scoped lang="scss">
.updateUser {
  height: 100%;
  overflow: auto;
  padding-bottom: 53px;
  width: 600px;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 108px;
  height: 108px;
  margin: 8px;
  line-height: 108px;
  border-radius: 4px;
  text-align: center;
  background-color: #fafafa;
  border: 1px dashed #d9d9d9;
}

.avatar {
  width: 108px;
  height: 108px;
  margin: 8px;
  border-radius: 4px;
  display: block;
}
</style>