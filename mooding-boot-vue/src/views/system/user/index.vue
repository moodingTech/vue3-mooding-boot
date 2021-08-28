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
          <!-- <md-input
            placeholder="请输入部门名称模糊查询"
            clearable
            size="small"
            prefix-icon="el-icon-search"
            style="margin-bottom: 20px"
            v-model="deptName"
          /> -->
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
          <!-- <el-table-column type="index" label="ID"></el-table-column> -->
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
                :src="
                  scope.row.avatar
                    ? scope.row.avatar
                    : 'https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1813762643,1914315241&fm=26&gp=0.jpg'
                "
              ></el-image>
            </template>
          </el-table-column>
          <el-table-column
            label="用户性别"
            align="center"
            prop="sex"
            :formatter="sexFormat"
          />
          <el-table-column
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

    <!-- 添加或修改参数配置对话框 -->
    <EditModule ref="userFormRef" :title="title" />

    <!-- 用户导入对话框 -->
    <uploadModule ref="uploadRef" />
  </div>
</template>

<script lang="ts">
import {
  toRefs,
  reactive,
  onMounted,
  ref,
  watch,
  getCurrentInstance,
  onUnmounted,
} from "vue";
import {
  listUser,
  changeUserStatus,
  getUser,
  delUser,
  exportUser,
} from "@/api/system/user";
import { treeselect } from "@/api/system/dept";
import { ElMessageBox, ElMessage } from "element-plus";
import { getDicts } from "@/api/system/dict/data";
import MDInput from "@/components/mooding/MDInput.vue";
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
      // 岗位选项
      postOptions: [],
      defaultProps: {
        children: "children",
        label: "label",
      },
      // 性别状态字典
      sexOptions: [],
      // 角色选项
      roleOptions: [],
      // 状态数据字典
      statusOptions: [],
      // 部门名称
      deptName: undefined,
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
        { key: 0, label: `用户名称`, visible: true },
        { key: 1, label: `用户昵称`, visible: true },
        { key: 2, label: `用户性别`, visible: true },
        { key: 3, label: `部门`, visible: true },
        { key: 4, label: `手机号码`, visible: true },
        { key: 5, label: `状态`, visible: true },
        { key: 6, label: `创建时间`, visible: true },
      ],
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
      listUser(proxy.addDateRange(state.queryParams, state.dateRange)).then(
        (response) => {
          state.tableData.data = response.data.records;
          state.tableData.total = response.data.total;
          state.loading = false;
        }
      );
    };
    /** 搜索按钮操作 */
    const handleQuery = async () => {
      // console.log("查询用户列表", state.queryParams.userName);
      state.queryParams.page = 1;
      await getList();
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
      state.title = "添加用戶";
      userFormRef.value.openDialog(null);
    };
    /** 修改按钮操作 */
    const handleUpdate = (row: any) => {
      state.title = "修改用户";
      userFormRef.value.openDialog(row);
    };

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
        title: "警告",
        message: '确认要"' + text + '""' + row.userName + '"用户吗?',
        showCancelButton: true,
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        beforeClose: (action, instance, done) => {
          if (action === "confirm") {
            return changeUserStatus(row.userId, row.status).then(() => {
              ElMessage.success(text + "成功");
              done();
            });
          } else {
            done();
          }
        },
      }).catch(() => {
          row.status = row.status === "0" ? "1" : "0";
        });
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

    // 分页改变
    const onHandleSizeChange = (val: number) => {
      state.tableData.param.pageSize = val;
      handleQuery();
    };
    // 分页改变
    const onHandleCurrentChange = (val: number) => {
      state.tableData.param.pageSize = val;
      handleQuery();
    };
    // 筛选节点
    const filterNode = (value: string, data: any) => {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    };
    // 节点单击事件
    const handleNodeClick = (data: any) => {
      state.queryParams.deptId = data.id;
      getList();
    };

    /** 导入按钮操作 */
    const handleImport = () => {
      uploadRef.value.openDialog();
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
        handleQuery();
      });
    });

    // 页面卸载时
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
      getList,
      handleAdd,
      handleUpdate,
      handleDelete,
      handleImport,
      handleExport,
      handleStatusChange,
      onHandleSizeChange,
      onHandleCurrentChange,
      filterNode,
      handleNodeClick,
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
</style>
