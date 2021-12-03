<template>
  <div class="app-container">
    <!-- 查询 -->
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
<<<<<<< HEAD
    >
      <el-form-item label="部门名称" prop="deptName">
        <!-- <el-input
          v-model="queryParams.deptName"
          placeholder="请输入部门名称"
          clearable
          size="small"
          @keyup.enter="handleQuery"
        /> -->
=======
      label-width="68px"
    >
      <el-form-item label="部门名称" prop="deptName">
>>>>>>> master
        <md-input
          placeholder="请输入部门名称模糊查询"
          clearable
          size="small"
          @keyup.enter="handleQuery"
          style="width: 240px"
          v-model="queryParams.deptName"
        />
      </el-form-item>
<<<<<<< HEAD
      <el-form-item label="状态" prop="status">
=======
      <el-form-item label="状态" prop="status" >
>>>>>>> master
        <el-select
          v-model="queryParams.status"
          placeholder="部门状态"
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
      :data="tableData"
      row-key="deptId"
      default-expand-all
      :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
    >
      <el-table-column
        prop="deptName"
        label="部门名称"
        width="260"
      ></el-table-column>
      <el-table-column
        prop="orderNum"
        label="排序"
        width="200"
      ></el-table-column>
      <el-table-column
        prop="status"
        label="状态"
        :formatter="statusFormat"
        width="100"
      ></el-table-column>
      <el-table-column label="负责人" align="center" prop="leader" width="200">
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
            icon="el-icon-edit"
            @click="onOpenEditModule(scope.row)"
            v-hasPermi="['system:dept:edit']"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-plus"
            @click="onOpenAddModule(scope.row)"
            v-hasPermi="['system:dept:add']"
            >新增</el-button
          >
          <el-button
            v-if="scope.row.parentId != 0"
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="onTabelRowDel(scope.row)"
            v-hasPermi="['system:dept:remove']"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>

<<<<<<< HEAD
     <!-- 添加或修改部门对话框 -->
    <EditModule ref="editModuleRef" :title="title" />
  </div>


</template>

<script lang="ts">
import { ref, toRefs, reactive, onMounted, getCurrentInstance,onUnmounted } from "vue";
=======
    <!-- 添加或修改部门对话框 -->
    <EditModule ref="editModuleRef" :title="title" />
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
>>>>>>> master
import { ElMessageBox, ElMessage } from "element-plus";
import { listDept, delDept } from "@/api/system/dept";
import EditModule from "@/views/system/dept/component/editModule.vue";

export default {
  name: "index",
  components: { EditModule },
  setup() {
    const { proxy } = getCurrentInstance() as any;
    const editModuleRef = ref();
    const state = reactive({
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: true,
<<<<<<< HEAD
       // 弹出层标题
=======
      // 弹出层标题
>>>>>>> master
      title: "",
      // 部门表格树数据
      tableData: [] as any,
      // 状态数据字典
      statusOptions: [],
      // 查询参数
      queryParams: {
        deptName: undefined,
        status: undefined,
      },
    });

    /** 查询部门列表 */
    const handleQuery = () => {
      state.loading = true;
      listDept(state.queryParams).then((response: any) => {
        state.tableData = proxy.handleTree(response.data, "deptId");
        state.loading = false;
      });
    };
    /** 重置按钮操作 */
    const resetQuery = () => {
      state.queryParams.deptName = undefined;
      state.queryParams.status = undefined;
      handleQuery();
    };

    // 部门状态字典翻译
    const statusFormat = (row: any, column: any) => {
      return proxy.selectDictLabel(state.statusOptions, row.status);
    };

    // 打开新增部门弹窗
    const onOpenAddModule = (row: object) => {
      let parentId = row.deptId;
      row = [];
      row.parentId = parentId;
      state.title = "添加部门";
      editModuleRef.value.openDialog(row);
    };
    // 打开编辑部门弹窗
    const onOpenEditModule = (row: object) => {
      state.title = "修改部门";
      editModuleRef.value.openDialog(row);
    };
    /** 删除按钮操作 */
    const onTabelRowDel = (row: any) => {
      ElMessageBox({
        message: '是否确认删除名称为"' + row.deptName + '"的数据项?',
        title: "警告",
        showCancelButton: true,
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(function () {
        return delDept(row.deptId).then(() => {
          handleQuery();
          ElMessage.success("删除成功");
        });
      });
    };
    // 页面加载时
    onMounted(() => {
      // 查询部门信息
      handleQuery();
      // 查询部门状态数据字典
      proxy.getDicts("sys_normal_disable").then((response: any) => {
        state.statusOptions = response.data;
      });
<<<<<<< HEAD
      proxy.mittBus.on('onEditDeptModule', (res: any) => {
				handleQuery();
			});
    });
    	// 页面卸载时
		onUnmounted(() => {
			proxy.mittBus.off('onEditDeptModule');
		});
=======
      proxy.mittBus.on("onEditDeptModule", (res: any) => {
        handleQuery();
      });
    });
    // 页面卸载时
    onUnmounted(() => {
      proxy.mittBus.off("onEditDeptModule");
    });
>>>>>>> master
    return {
      editModuleRef,
      handleQuery,
      resetQuery,
      onOpenAddModule,
      onOpenEditModule,
      statusFormat,
      onTabelRowDel,
      ...toRefs(state),
    };
  },
};
</script>
