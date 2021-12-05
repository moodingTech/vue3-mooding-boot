<template>
  <div class="app-container">
    <!--查询-->
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="菜单名称" prop="menuName">
        <md-input
          placeholder="请输入菜单名称模糊查询"
          clearable
          size="small"
          @keyup.enter="handleQuery"
          style="width: 240px"
          v-model="queryParams.menuName"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select
          v-model="queryParams.status"
          placeholder="菜单状态"
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
      <el-form-item label="菜单类型" prop="status">
        <el-select
          v-model="queryParams.status"
          placeholder="菜单类型"
          clearable
          size="small"
          style="width: 240px"
        >
          <el-option
            v-for="dict in menuTypeOptions"
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

    <!--数据表格-->
    <el-table
      v-loading="loading"
      :data="menuList"
      row-key="menuId"
      :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
    >
      <el-table-column
        prop="menuName"
        label="菜单名称"
        :show-overflow-tooltip="true"
        width="150"
      ></el-table-column>
      <el-table-column prop="icon" label="图标" align="center" width="100">
        <template #default="scope">
          <i :class="scope.row.icon"></i>
        </template>
      </el-table-column>
      <el-table-column
        prop="orderNum"
        label="排序"
        width="60"
      ></el-table-column>
      <el-table-column
        prop="perms"
        label="权限标识"
        :show-overflow-tooltip="true"
      ></el-table-column>
      <el-table-column
        prop="component"
        label="组件路径"
        :show-overflow-tooltip="true"
      ></el-table-column>
      <el-table-column
        prop="status"
        label="状态"
        :formatter="statusFormat"
        width="80"
      ></el-table-column>
      <el-table-column
        prop="status"
        label="显示隐藏"
        :formatter="isHideFormat"
        width="80"
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
            icon="el-icon-edit"
            @click="onOpenEditMenu(scope.row)"
            v-hasPermi="['system:menu:edit']"
            >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-plus"
            @click="onOpenAddMenu(scope.row)"
            v-hasPermi="['system:menu:add']"
            >新增
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:menu:remove']"
            >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 添加或修改菜单对话框 -->
    <EditMenu ref="editMenuRef" :title="title" />
  </div>
</template>

<script lang="ts">
import { getCurrentInstance, onMounted, reactive, ref, toRefs } from "vue";
import { listMenu, delMenu } from "@/api/system/menu";
import EditMenu from "@/views/system/menu/component/editMenu.vue";
import { ElMessage, ElMessageBox } from "element-plus";

export default {
  name: "index",
  components: { EditMenu },
  setup() {
    const { proxy } = getCurrentInstance() as any;
    const editMenuRef = ref();
    const state: any = reactive({
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: true,
      // 菜单表格树数据
      menuList: [] as any,
      // 菜单树选项
      menuOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 显示状态数据字典
      isHideOptions: [],
      // 菜单状态数据字典
      statusOptions: [],
      // 菜单类型数据字典
      menuTypeOptions: [],
      // 查询参数
      queryParams: {
        menuName: undefined,
        status: undefined,
        menuType: undefined,
      },
      // 表单参数
      menuForm: {},
      // 表单校验
      menuRules: {
        menuName: [
          { required: true, message: "菜单名称不能为空", trigger: "blur" },
        ],
        orderNum: [
          { required: true, message: "菜单顺序不能为空", trigger: "blur" },
        ],
        path: [
          { required: true, message: "路由地址不能为空", trigger: "blur" },
        ],
      },
    });
    /** 查询菜单列表 */
    const getList = () => {
      state.loading = true;
      listMenu(state.queryParams).then((response: any) => {
        state.menuList = proxy.handleTree(response.data, "menuId");
        state.loading = false;
      });
    };
    /** 搜索按钮操作 */
    const handleQuery = () => {
      getList();
    };
    /** 重置按钮操作 */
    const resetQuery = () => {
      state.queryParams.menuName = "";
      state.queryParams.status = "";
      state.queryParams.menuType = "";
      handleQuery();
    };

    // 打开新增菜单弹窗
    const onOpenAddMenu = (row: object) => {
      state.title = "添加菜单";
      let parentId = row.menuId;
      row = [];
      row.parentId = parentId;
      editMenuRef.value.openDialog(row);
    };
    // 打开编辑菜单弹窗
    const onOpenEditMenu = (row: object) => {
      state.title = "修改菜单";
      editMenuRef.value.openDialog(row);
    };
    /** 删除按钮操作 */
    const handleDelete = (row: any) => {
      ElMessageBox({
        message: '是否确认删除名称为"' + row.menuName + '"的数据项?',
        title: "警告",
        showCancelButton: true,
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(function () {
        return delMenu(row.menuId).then(() => {
          getList();
          ElMessage.success("删除成功");
        });
      });
    };
    // 菜单显示隐藏字典翻译
    const isHideFormat = (row: any, column: any) => {
      if (row.menuType == "F") {
        return "";
      }
      return proxy.selectDictLabel(state.isHideOptions, row.isHide);
    };
    // 菜单状态字典翻译
    const statusFormat = (row: any, column: any) => {
      if (row.menuType == "F") {
        return "";
      }
      return proxy.selectDictLabel(state.statusOptions, row.status);
    };

    // 页面加载时
    onMounted(() => {
      getList();
      // 查询显示状态数据字典
      proxy.getDicts("sys_show_hide").then((response: any) => {
        state.isHideOptions = response.data;
      });
      // 查询菜单状态数据字典
      proxy.getDicts("sys_normal_disable").then((response: any) => {
        state.statusOptions = response.data;
      });
      // 查询菜单类型数据字典
      proxy.getDicts("sys_menu_type").then((response: any) => {
        state.menuTypeOptions = response.data;
      });
    });
    return {
      editMenuRef,
      getList,
      handleQuery,
      resetQuery,
      onOpenEditMenu,
      onOpenAddMenu,
      handleDelete,
      statusFormat,
      isHideFormat,
      ...toRefs(state),
    };
  },
};
</script>

<style scoped>
</style>
