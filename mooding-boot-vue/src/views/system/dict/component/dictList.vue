<template>
  <el-card>
    <!-- 抽屉 -->
    <el-drawer title="字典列表" :size="screenWidth" v-model="isShowDrawer">
      <!-- 抽屉内容的border -->
      <div
        :style="{
          padding: '10px',
          border: '1px solid #e9e9e9',
          background: '#fff',
        }"
      >
        <el-form
          :model="queryParams"
          ref="queryForm"
          :inline="true"
          size="mini"
        >
          <el-row :gutter="12">
            <el-col :md="8" :sm="8" :xs="12">
              <el-form-item label="名称" prop="dictName">
                <!-- <el-input
                  v-model="queryParams.dictName"
                  placeholder="请输入名称"
                  clearable
                  size="small"
                  style="width: 120px"
                  @keyup.enter="handleQuery"
                /> -->
                <md-input
                  placeholder="请输入字典标签模糊查询"
                  clearable
                  size="small"
                  @keyup.enter="handleQuery"
                  style="width: 120px"
                  v-model="queryParams.dictLabel"
                />
              </el-form-item>
            </el-col>
            <el-col :md="8" :sm="8" :xs="24">
              <el-form-item label="状态" prop="status">
                <el-select
                  v-model="queryParams.status"
                  placeholder="状态"
                  clearable
                  size="mini"
                  style="width: 100px"
                >
                  <el-option
                    v-for="dict in statusOptions"
                    :key="dict.dictValue"
                    :label="dict.dictLabel"
                    :value="dict.dictValue"
                  />
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :md="8" :sm="8" :xs="24">
              <el-form-item>
                <el-button
                  type="primary"
                  icon="el-icon-search"
                  @click="handleQuery"
                  size="mini"
                  >搜索</el-button
                >
                <el-button
                  icon="el-icon-refresh"
                  size="mini"
                  @click="resetQuery"
                  >重置</el-button
                >
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :md="2" :sm="2" :xs="8">
              <el-button
                style="margin-bottom: 10px; margin-top: 10px"
                size="mini"
                type="primary"
                @click="onOpenAddModule"
                v-hasPermi="['system:dict:add']"
                >新增</el-button
              >
            </el-col>
          </el-row>
        </el-form>
      </div>
      <div>
        <!--数据表格-->
        <el-table v-loading="loading" :data="tableData">
          <el-table-column
            label="字典键值"
            align="center"
            width="50"
            prop="dictValue"
            :show-overflow-tooltip="true"
          />
          <el-table-column
            label="字典标签"
            align="center"
            prop="dictLabel"
            :show-overflow-tooltip="true"
          />
          <el-table-column
            label="字典类型"
            align="center"
            prop="dictType"
            :show-overflow-tooltip="true"
          />
          <el-table-column
            label="状态"
            align="center"
            prop="status"
            :formatter="statusFormat"
          />
          <el-table-column
            label="字典排序"
            align="center"
            width="50"
            prop="dictSort"
          />

          <el-table-column
            label="操作"
            align="center"
            class-name="medium-padding fixed-width"
          >
            <template #default="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="onOpenEditModule(scope.row)"
                v-hasPermi="['system:dict:edit']"
                >修改</el-button
              >
              <el-button
                v-if="scope.row.parentId != 0"
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="onTabelRowDel(scope.row)"
                v-hasPermi="['system:dict:remove']"
                >删除</el-button
              >
            </template>
          </el-table-column>
        </el-table>
      </div>
      <!-- 添加或修改字典对话框 -->
      <editDictItem ref="dictItemModuleRef" :title="title" />
    </el-drawer>
  </el-card>
</template>

<script lang="ts">
import {
  reactive,
  toRefs,
  ref,
  getCurrentInstance,
  onUnmounted,
  onMounted,
} from "vue";
import { delData, listData } from "@/api/system/dict/data";
import { ElMessageBox, ElMessage } from "element-plus";
import editDictItem from "./editDictItem.vue";

export default {
  name: "dictList",
  components: { editDictItem },
  setup() {
    const { proxy } = getCurrentInstance() as any;
    const dictItemModuleRef = ref();
    const state = reactive({
      // 遮罩层
      loading: true,
      // 弹出层标题
      title: "",
      // 字典表格数据
      tableData: [],
      // 是否显示抽屉
      isShowDrawer: false,
      //   抽屉的宽度
      screenWidth: 800,
      // 状态数据字典
      statusOptions: [],

      // 查询参数
      queryParams: {
        dictType: undefined,
        dictLabel: undefined,
        status: undefined,
      },
    });

    // 页面加载时
    const openDrawer = (row: any) => {
      resetScreenSize();
      state.queryParams.dictType = row.dictType;
      proxy.getDicts(row.dictType).then((response: any) => {
        state.tableData = response.data;
        state.loading = false;
      });
      state.isShowDrawer = true;

      // 查询字典状态数据字典
      proxy.getDicts("sys_normal_disable").then((response: any) => {
        state.statusOptions = response.data;
      });
    };

    // 字典状态字典翻译
    const statusFormat = (row: any, column: any) => {
      return proxy.selectDictLabel(state.statusOptions, row.status);
    };
    /** 查询字典列表 */
    const handleQuery = () => {
      state.loading = true;
      listData(state.queryParams).then((response) => {
        state.tableData = response.data.records;
        state.loading = false;
      });
    };
    // 抽屉的宽度随着屏幕大小来改变
    const resetScreenSize = () => {
      let screenWidth = document.body.clientWidth;
      if (screenWidth < 600) {
        state.screenWidth = screenWidth;
      } else {
        state.screenWidth = 600;
      }
      console.log("抽屉的宽度随着屏幕大小来改变", state.screenWidth);
    };
    /** 重置按钮操作 */
    const resetQuery = () => {
      state.queryParams.dictLabel = undefined;
      state.queryParams.status = undefined;
      handleQuery();
    };
    // 打开新增字典数据弹窗
    const onOpenAddModule = (row: any) => {
      row.dictType = state.queryParams.dictType;
      state.title = "添加字典数据";
      dictItemModuleRef.value.openDialog(row);
    };
    // 打开编辑字典数据弹窗
    const onOpenEditModule = (row: object) => {
      state.title = "修改字典数据";
      dictItemModuleRef.value.openDialog(row);
    };
    /** 删除按钮操作 */
    const onTabelRowDel = (row: any) => {
      const dictCode = row.dictCode;
      ElMessageBox({
        message: '是否确认删除字典编号为"' + dictCode + '"的数据项?',
        title: "警告",
        showCancelButton: true,
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(function () {
        return delData(dictCode).then(() => {
          handleQuery();
          ElMessage.success("删除成功");
        });
      });
    };
    // 页面加载时
    onMounted(() => {
      proxy.mittBus.on("onEditDictItemModule", (res: any) => {
        handleQuery();
      });
    });
    // 页面卸载时
    onUnmounted(() => {
      proxy.mittBus.off("onEditDictItemModule");
    });
    return {
      dictItemModuleRef,
      resetScreenSize,
      onOpenAddModule,
      onOpenEditModule,
      onTabelRowDel,
      statusFormat,
      resetQuery,
      openDrawer,
      handleQuery,
      ...toRefs(state),
    };
  },
};
</script>
