<template>
  <div class="system-user-container app-container">
    <el-row :gutter="20">
      <!--部门数据-->
      <el-col :span="4" :xs="24">
        <div class="head-container">
          <el-input
            v-model="deptName"
            placeholder="请输入部门名称"
            clearable
            size="small"
            prefix-icon="el-icon-search"
            style="margin-bottom: 20px"
          />
<<<<<<< HEAD
          <!-- <md-input
            placeholder="请输入部门名称模糊查询"
            clearable
            size="small"
            prefix-icon="el-icon-search"
            style="margin-bottom: 20px"
            v-model="deptName"
          /> -->
=======
>>>>>>> master
        </div>
        <div class="head-container">
          <el-tree
            :data="deptOptions"
            :props="defaultProps"
            :expand-on-click-node="false"
            :filter-node-method="filterNode"
            ref="tree"
            default-expand-all
            @node-click="handleNodeClick"
          />
        </div>
      </el-col>
<<<<<<< HEAD
      <!--		<el-card shadow="hover">-->
      <!--用户数据-->
      <el-col :span="20" :xs="24">
        <!-- 查询-->
        <div class="system-user-search">
          <el-form
            :model="queryParams"
            ref="queryForm"
            :inline="true"
            v-show="showSearch"
            label-width="70px"
          >
            <el-form-item label="用户名称" prop="userName">
              <!-- <el-input
                v-model="queryParams.userName"
                placeholder="请输入用户名称"
                clearable
                size="small"
                style="width: 240px"
                @keyup.enter="handleQuery"
              /> -->
              <md-input
                placeholder="请输入用户名称模糊查询"
                clearable
                size="small"
                @keyup.enter="handleQuery"
                style="width: 240px"
                v-model="queryParams.userName"
              />
            </el-form-item>
            <el-form-item label="手机号码" prop="phonenumber">
              <el-input
                v-model="queryParams.phonenumber"
                placeholder="请输入手机号码"
                clearable
                size="small"
                style="width: 240px"
                @keyup.enter="handleQuery"
              />
            </el-form-item>
            <el-form-item label="状态" prop="status">
              <el-select
                v-model="queryParams.status"
                placeholder="用户状态"
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
            <el-form-item class="system-user-search-btn">
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
        </div>

        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button
              type="primary"
              plain
              icon="el-icon-plus"
              size="mini"
              v-hasPermi="['system:user:add']"
              @click="handleAdd"
              >新增
            </el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="success"
              plain
              icon="el-icon-edit"
              size="mini"
              :disabled="single"
              v-hasPermi="['system:user:edit']"
              @click="handleUpdate"
              >修改
            </el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="danger"
              plain
              icon="el-icon-delete"
              size="mini"
              :disabled="multiple"
              v-hasPermi="['system:user:remove']"
              @click="handleDelete"
              >删除
            </el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="info"
              plain
              icon="el-icon-upload2"
              size="mini"
              v-hasPermi="['system:user:import']"
              @click="handleImport"
              >导入
            </el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="warning"
              plain
              icon="el-icon-download"
              size="mini"
              v-hasPermi="['system:user:export']"
              @click="handleExport"
              >导出
            </el-button>
          </el-col>
        </el-row>

        <el-table :data="tableData.data" stripe style="width: 100%">
          <el-table-column type="index" label="ID"></el-table-column>
=======

      <!--用户数据-->
      <el-col :span="20" :xs="24">
        <!-- 查询-->
        <el-form
          :model="queryParams"
          ref="queryForm"
          :inline="true"
          v-show="showSearch"
          label-width="78px"
        >
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
          <el-form-item label="手机号码" prop="phonenumber">
            <el-input
              v-model="queryParams.phonenumber"
              placeholder="请输入手机号码"
              clearable
              size="small"
              style="width: 240px"
              @keyup.enter="handleQuery"
            />
          </el-form-item>
          <el-form-item label="状态" prop="status">
            <el-select
              v-model="queryParams.status"
              placeholder="用户状态"
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
          <el-form-item class="system-user-search-btn">
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
        <el-col :span="1.5">
          <el-button
            type="primary"
            plain
            icon="el-icon-plus"
            size="mini"
            v-hasPermi="['system:user:add']"
            @click="handleAdd"
            >新增
          </el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="success"
            plain
            icon="el-icon-edit"
            size="mini"
            :disabled="single"
            v-hasPermi="['system:user:edit']"
            @click="handleUpdate"
            >修改
          </el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="danger"
            plain
            icon="el-icon-delete"
            size="mini"
            :disabled="multiple"
            v-hasPermi="['system:user:remove']"
            @click="handleDelete"
            >删除
          </el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="info"
            plain
            icon="el-icon-upload2"
            size="mini"
            v-hasPermi="['system:user:import']"
            @click="handleImport"
            >导入
          </el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="warning"
            plain
            icon="el-icon-download"
            size="mini"
            v-hasPermi="['system:user:export']"
            @click="handleExport"
            >导出
          </el-button>
        </el-col>

        <el-table :data="tableData.data" stripe style="width: 100%">
>>>>>>> master
          <el-table-column
            label="用户编号"
            align="center"
            key="userId"
            prop="userId"
          />
          <el-table-column
            label="用户名"
            prop="userName"
            show-overflow-tooltip
          ></el-table-column>
          <el-table-column label="头像" show-overflow-tooltip>
            <template #default="scope">
              <el-image
                class="system-user-photo"
<<<<<<< HEAD
                :src="scope.row.avatar"
=======
                :src="
                  scope.row.avatar
                    ? scope.row.avatar
                    : 'https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1813762643,1914315241&fm=26&gp=0.jpg'
                "
>>>>>>> master
              ></el-image>
            </template>
          </el-table-column>
          <el-table-column
<<<<<<< HEAD
=======
            label="用户性别"
            align="center"
            prop="sex"
            :formatter="sexFormat"
          />
          <el-table-column
>>>>>>> master
            prop="phonenumber"
            label="手机"
            show-overflow-tooltip
          ></el-table-column>
          <el-table-column
            prop="email"
            label="邮箱"
            show-overflow-tooltip
          ></el-table-column>
          <el-table-column label="状态" align="center" key="status">
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
            prop="loginDate"
            label="上次登陆时间"
            show-overflow-tooltip
          ></el-table-column>
          <el-table-column prop="path" label="操作" width="90">
            <template #default="scope">
              <el-button
                size="mini"
                type="text"
                @click="handleUpdate(scope.row)"
                v-hasPermi="['system:user:edit']"
                >修改</el-button
              >
              <el-button
                size="mini"
                type="text"
                @click="handleDelete(scope.row)"
                v-hasPermi="['system:user:remove']"
                >删除</el-button
              >
            </template>
          </el-table-column>
        </el-table>
<<<<<<< HEAD

        <!-- 添加或修改参数配置对话框 -->
        <el-drawer :title="title" v-model="open" size="400px">
          <el-form
            ref="userFormRef"
            :model="userForm"
            :rules="rules"
            label-width="120px"
            label-position="right"
          >
            <el-row>
              <el-col :span="20">
                <el-form-item label="用户昵称" prop="nickName">
                  <el-input
                    v-model="userForm.nickName"
                    placeholder="请输入用户昵称"
                  />
                </el-form-item>
              </el-col>
              <el-col :span="20">
                <el-form-item label="归属部门" prop="deptId">
                  <!-- <treeselect v-model="userForm.deptId" :show-count="true" :options="deptOptions"
                              placeholder="请选择归属部门"/> -->
                  <el-cascader
                    v-model="userForm.deptId"
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
            </el-row>
            <el-row>
              <el-col :span="20">
                <el-form-item label="手机号码" prop="phonenumber">
                  <el-input
                    v-model="userForm.phonenumber"
                    placeholder="请输入手机号码"
                    maxlength="11"
                  />
                </el-form-item>
              </el-col>
              <el-col :span="20">
                <el-form-item label="邮箱" prop="email">
                  <el-input
                    v-model="userForm.email"
                    placeholder="请输入邮箱"
                    maxlength="50"
                  />
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="20">
                <el-form-item
                  v-if="userForm.userId == undefined"
                  label="用户名称"
                  prop="userName"
                >
                  <el-input
                    v-model="userForm.userName"
                    placeholder="请输入用户名称"
                  />
                </el-form-item>
              </el-col>
              <el-col :span="20">
                <el-form-item
                  v-if="userForm.userId == undefined"
                  label="用户密码"
                  prop="password"
                >
                  <el-input
                    v-model="userForm.password"
                    placeholder="请输入用户密码"
                    type="password"
                  />
                </el-form-item>
              </el-col>
            </el-row>

            <el-row>
              <el-col :span="20">
                <el-form-item label="头像" prop="avatar">
                  <el-upload
                    class="avatar-uploader"
                    action="https://jsonplaceholder.typicode.com/posts/"
                    :show-file-list="false"
                    :on-success="handleAvatarSuccess"
                    :before-upload="beforeAvatarUpload"
                  >
                    <img
                      v-if="userForm.avatar"
                      :src="userForm.avatar"
                      class="avatar"
                    />
                    <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                  </el-upload>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="20">
                <el-form-item label="用户性别">
                  <el-select v-model="userForm.sex" placeholder="请选择">
                    <el-option
                      v-for="dict in sexOptions"
                      :key="dict.dictValue"
                      :label="dict.dictLabel"
                      :value="dict.dictValue"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="20">
                <el-form-item label="状态">
                  <el-radio-group v-model="userForm.status">
                    <el-radio
                      v-for="dict in statusOptions"
                      :key="dict.dictValue"
                      :label="dict.dictValue"
                      >{{ dict.dictLabel }}
                    </el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="20">
                <el-form-item label="岗位">
                  <el-select
                    v-model="userForm.postIds"
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
              <el-col :span="20">
                <el-form-item label="角色">
                  <el-select
                    v-model="userForm.roleIds"
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
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label="备注">
                  <el-input
                    v-model="userForm.remark"
                    type="textarea"
                    placeholder="请输入内容"
                  ></el-input>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
          <div class="dialog-footer">
            <el-button type="primary" @click="submitForm">确 定</el-button>
            <el-button @click="cancel">取 消</el-button>
          </div>
          <!-- <template #footer>
            <span class="dialog-footer">
              <el-button @click="onCancel" size="small">取 消</el-button>
              <el-button type="primary" @click="onSubmit" size="small"
                >编 辑</el-button
              >
            </span>
          </template> -->
        </el-drawer>

=======
>>>>>>> master
        <el-pagination
          @size-change="onHandleSizeChange"
          @current-change="onHandleCurrentChange"
          class="mt15"
          :page-sizes="[10, 20, 30]"
          :current-page="tableData.param.pageNum"
          background
          :page-size="tableData.param.pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="tableData.total"
        >
        </el-pagination>
      </el-col>
    </el-row>
<<<<<<< HEAD
=======

    <!-- 添加或修改参数配置对话框 -->
    <EditModule ref="userFormRef" :title="title" />

    <!-- 用户导入对话框 -->
    <uploadModule ref="uploadRef" />
>>>>>>> master
  </div>
</template>

<script lang="ts">
import {
  toRefs,
  reactive,
  onMounted,
<<<<<<< HEAD
  unref,
  ref,
  watch,
  getCurrentInstance,
=======
  ref,
  watch,
  getCurrentInstance,
  onUnmounted,
>>>>>>> master
} from "vue";
import {
  listUser,
  changeUserStatus,
  getUser,
  delUser,
  exportUser,
<<<<<<< HEAD
  addUser,
  updateUser,
=======
>>>>>>> master
} from "@/api/system/user";
import { treeselect } from "@/api/system/dept";
import { ElMessageBox, ElMessage } from "element-plus";
import { getDicts } from "@/api/system/dict/data";
import MDInput from "@/components/mooding/MDInput.vue";
<<<<<<< HEAD
export default {
  name: "systemUser",
  components: { MDInput },
  setup() {
    const { proxy } = getCurrentInstance() as any;
    const userFormRef = ref<HTMLElement | null>(null);
=======
import uploadModule from "./component/uploadModule.vue";
import EditModule from "./component/editModule.vue";
export default {
  name: "systemUser",
  components: { MDInput, uploadModule, EditModule },
  setup() {
    const { proxy } = getCurrentInstance() as any;
    // const userFormRef = ref<HTMLElement | null>(null);
    const userFormRef = ref();
    const uploadRef = ref();
>>>>>>> master
    const state: any = reactive({
      tableData: {
        data: [],
        total: 0,
        loading: false,
        param: {
          pageNum: 1,
          pageSize: 10,
        },
      },
<<<<<<< HEAD
      // 表单参数
      userForm: {},
=======
>>>>>>> master
      // 岗位选项
      postOptions: [],
      defaultProps: {
        children: "children",
        label: "label",
      },
<<<<<<< HEAD
=======
      // 性别状态字典
      sexOptions: [],
>>>>>>> master
      // 角色选项
      roleOptions: [],
      // 状态数据字典
      statusOptions: [],
<<<<<<< HEAD
      // 性别状态字典
      sexOptions: [],
      // 部门名称
      deptName: undefined,
      // 是否显示弹出层
      open: false,
=======
      // 部门名称
      deptName: undefined,
>>>>>>> master
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 选中数组
      ids: [],
      // 显示搜索条件
      showSearch: true,
      // 弹出层标题
      title: "",
<<<<<<< HEAD
      // 默认密码
      initPassword: undefined,
=======
>>>>>>> master
      // 日期范围
      dateRange: [],
      // 部门树选项
      deptOptions: undefined,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userName: undefined,
        phonenumber: undefined,
        status: undefined,
        deptId: undefined,
      },
      // 列信息
      columns: [
<<<<<<< HEAD
        { key: 0, label: `用户编号`, visible: true },
        { key: 1, label: `用户名称`, visible: true },
        { key: 2, label: `用户昵称`, visible: true },
=======
        { key: 0, label: `用户名称`, visible: true },
        { key: 1, label: `用户昵称`, visible: true },
        { key: 2, label: `用户性别`, visible: true },
>>>>>>> master
        { key: 3, label: `部门`, visible: true },
        { key: 4, label: `手机号码`, visible: true },
        { key: 5, label: `状态`, visible: true },
        { key: 6, label: `创建时间`, visible: true },
      ],
<<<<<<< HEAD
      // 表单校验
      rules: {
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
=======
>>>>>>> master
    });

    watch(
      () => state.deptName,
      (newValue) => {
        proxy.$refs.tree.filter(newValue);
      }
    );
    /** 查询用户列表 */
    const getList = async () => {
      state.loading = true;
<<<<<<< HEAD

=======
>>>>>>> master
      listUser(proxy.addDateRange(state.queryParams, state.dateRange)).then(
        (response) => {
          state.tableData.data = response.data.records;
          state.tableData.total = response.data.total;
          state.loading = false;
        }
      );
    };
<<<<<<< HEAD
    // 初始化表格数据
    const initTableData = async () => {
      const data: Array<object> = [];
      const options = [
        {
          dictValue: "1",
          dictLabel: "有效",
        },
        {
          dictValue: "2",
          dictLabel: "失效",
        },
      ];
      state.statusOptions = options;
      await listUser(state.queryParams).then((response) => {
        state.tableData.data = response.data.records;
        state.tableData.total = response.data.total;
      });
    };
=======
>>>>>>> master
    /** 搜索按钮操作 */
    const handleQuery = async () => {
      // console.log("查询用户列表", state.queryParams.userName);
      state.queryParams.page = 1;
<<<<<<< HEAD
      await initTableData();
=======
      await getList();
>>>>>>> master
    };
    /** 重置按钮操作 */
    const resetQuery = async () => {
      // 表单初始化，方法：`resetFields()` 无法使用
      state.dateRange = [];
      state.queryParams.pageNum = 1;
      state.queryParams.pageSize = 10;
      state.queryParams.userName = "";
      state.queryParams.phonenumber = "";
      state.queryParams.status = "";
      state.queryParams.deptId = "";
      handleQuery();
    };
    /** 新增按钮操作 */
    const handleAdd = (row: any) => {
<<<<<<< HEAD
      getTreeselect();
      state.userForm = {};
      state.userForm.deptId = null;
      getUser(0).then((response) => {
        state.postOptions = response.data.posts;
        state.roleOptions = response.data.roles;
        state.open = true;
        state.title = "添加用户";
        state.userForm.password = state.initPassword;
      });
    };
    /** 修改按钮操作 */
    const handleUpdate = (row: any) => {
      getTreeselect();
      const userId = row.userId || state.ids;
      getUser(userId).then((response) => {
        state.userForm = response.data.userInfo;
        state.postOptions = response.data.posts;
        state.roleOptions = response.data.roles;
        state.userForm.postIds = response.data.postIds;
        state.userForm.roleIds = response.data.roleIds;
        state.open = true;
        state.title = "修改用户";
      });
    };
    /** 提交按钮 */
    const submitForm = (row: any) => {
      const formWrap = unref(userFormRef) as any;
      if (!formWrap) return;
      formWrap.validate(async (valid: boolean) => {
        if (valid) {
          if (state.userForm.userId != undefined) {
            updateUser(state.userForm).then((response: any) => {
              ElMessage.success("修改成功");
              state.open = false;
              getList();
            });
          } else {
            console.log("新增用户", state.userForm);
            addUser(state.userForm).then((response: any) => {
              ElMessage.success("新增成功");
              state.open = false;
              getList();
            });
          }
        }
      });
    };
=======
      state.title = "添加用戶";
      userFormRef.value.openDialog(null);
    };
    /** 修改按钮操作 */
    const handleUpdate = (row: any) => {
      state.title = "修改用户";
      userFormRef.value.openDialog(row);
    };

>>>>>>> master
    /** 查询部门下拉树结构 */
    const getTreeselect = async () => {
      treeselect().then((response) => {
        state.deptOptions = response.data;
      });
    };
    // 用户状态修改
    const handleStatusChange = (row: any) => {
      let text = row.status === "0" ? "启用" : "停用";
      ElMessageBox({
<<<<<<< HEAD
        closeOnClickModal: false,
        closeOnPressEscape: false,
=======
>>>>>>> master
        title: "警告",
        message: '确认要"' + text + '""' + row.userName + '"用户吗?',
        showCancelButton: true,
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        beforeClose: (action, instance, done) => {
          if (action === "confirm") {
            return changeUserStatus(row.userId, row.status).then(() => {
              ElMessage.success(text + "成功");
<<<<<<< HEAD
=======
              done();
>>>>>>> master
            });
          } else {
            done();
          }
        },
<<<<<<< HEAD
      })
        .then(() => {
          ElMessage.success(text + "成功");
        })
        .catch(() => {
          row.status = row.status === "0" ? "1" : "0";
        });
=======
      }).catch(() => {
        row.status = row.status === "0" ? "1" : "0";
      });
>>>>>>> master
    };
    /** 删除按钮操作 */
    const handleDelete = (row: any) => {
      const userIds = row.userId || state.ids;
      ElMessageBox({
        message: '是否确认删除用户编号为"' + userIds + '"的数据项?',
        title: "警告",
        showCancelButton: true,
        confirmButtonText: "确定",
        cancelButtonText: "取消",
      }).then(function () {
        return delUser(userIds).then(() => {
          getList();
          ElMessage.success("删除成功");
        });
      });
    };
<<<<<<< HEAD
    // 取消按钮
    const cancel = async () => {
      state.open = false;
      reset();
    };
    // 表单重置
    const reset = async () => {
      state.form = [];
    };
    // 当前行删除
    const onRowDel = (row: object) => {
      console.log(row);
    };
    // 分页改变
    const onHandleSizeChange = (val: number) => {
      state.tableData.param.pageSize = val;
=======

    // 分页改变
    const onHandleSizeChange = (val: number) => {
      state.tableData.param.pageSize = val;
      handleQuery();
>>>>>>> master
    };
    // 分页改变
    const onHandleCurrentChange = (val: number) => {
      state.tableData.param.pageSize = val;
<<<<<<< HEAD
=======
      handleQuery();
>>>>>>> master
    };
    // 筛选节点
    const filterNode = (value: string, data: any) => {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    };
<<<<<<< HEAD

=======
>>>>>>> master
    // 节点单击事件
    const handleNodeClick = (data: any) => {
      state.queryParams.deptId = data.id;
      getList();
    };

    /** 导入按钮操作 */
    const handleImport = () => {
<<<<<<< HEAD
      state.upload.title = "用户导入";
      state.upload.open = true;
=======
      uploadRef.value.openDialog();
>>>>>>> master
    };
    /** 导出按钮操作 */
    const handleExport = () => {
      const queryParams = state.queryParams;
      ElMessageBox({
        message: "是否确认导出所有用户数据项?",
        title: "警告",
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return exportUser(queryParams);
        })
        .then((response) => {
<<<<<<< HEAD
          console.log("导出按钮操作", response);
          // this.download(response.msg);
        });
    };
    // 头像上传
    const handleAvatarSuccess = (file: any) => {
      state.imageUrl = URL.createObjectURL(file.raw);
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
    // 页面加载时
    onMounted(() => {
      initTableData();
      getTreeselect();
      getDicts("sys_normal_disable").then((response) => {
        state.statusOptions = response.data;
      });
      getDicts("sys_user_sex").then((response) => {
        state.sexOptions = response.data;
      });
      proxy.mittBus.on("onEditPostModule", (res: any) => {
=======
          proxy.download(response.data);
        });
    };
    // 字典状态字典翻译
    const sexFormat = (row: any, column: any) => {
      return proxy.selectDictLabel(state.sexOptions, row.sex);
    };
    // 页面加载时
    onMounted(() => {
      getList();
      getTreeselect();
      // 查询显示状态数据字典
      getDicts("sys_normal_disable").then((response) => {
        state.statusOptions = response.data;
      });
      // 查询显示性別数据字典
      proxy.getDicts("sys_user_sex").then((response: any) => {
        state.sexOptions = response.data;
      });

      proxy.mittBus.on("onEditUserModule", (res: any) => {
        handleQuery();
      });
      proxy.mittBus.on("onUploadModule", (res: any) => {
>>>>>>> master
        handleQuery();
      });
    });

    // 页面卸载时
<<<<<<< HEAD
    // onUnmounted(() => {
    //   proxy.mittBus.off("MDIputChange");
    // });
    return {
      userFormRef,
      onRowDel,
      handleQuery,
      resetQuery,
      cancel,
      submitForm,
=======
    onUnmounted(() => {
      proxy.mittBus.off("onEditUserModule");
      proxy.mittBus.off("onUploadModule");
    });
    return {
      userFormRef,
      uploadRef,
      sexFormat,
      handleQuery,
      resetQuery,
>>>>>>> master
      getList,
      handleAdd,
      handleUpdate,
      handleDelete,
      handleImport,
      handleExport,
      handleStatusChange,
<<<<<<< HEAD
      initTableData,
=======
>>>>>>> master
      onHandleSizeChange,
      onHandleCurrentChange,
      filterNode,
      handleNodeClick,
<<<<<<< HEAD
      handleAvatarSuccess,
      beforeAvatarUpload,
=======
>>>>>>> master
      ...toRefs(state),
    };
  },
};
</script>

<style scoped lang="scss">
.system-user-container {
  .system-user-search {
    text-align: left;

    .system-user-search-btn {
      text-align: center;
      margin-left: 70px;
    }
  }

  .system-user-photo {
    width: 40px;
    height: 40px;
    border-radius: 100%;
  }
}
<<<<<<< HEAD

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
=======
>>>>>>> master
</style>
