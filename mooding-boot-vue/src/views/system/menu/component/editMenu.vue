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
            <el-form-item label="上级菜单" prop="parentId">
              <!-- <treeselect
                v-model="ruleForm.parentId"
                :options="menuOptions"
                :normalizer="normalizer"
                :show-count="true"
                placeholder="选择上级菜单"
              /> -->
              <el-cascader
                v-model="ruleForm.parentId"
                :options="menuOptions"
                :props="{
                  label: 'label',
                  value: 'id',
                  checkStrictly: true,
                  emitPath: false,
                }"
                clearable
                filterable
                placeholder="选择上级菜单"
                :show-all-levels="false"
              ></el-cascader>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="菜单名称" prop="menuName">
              <el-input
                v-model="ruleForm.menuName"
                placeholder="请输入菜单名称"
                clearable
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col
            :xs="24"
            :sm="12"
            :md="12"
            :lg="12"
            :xl="12"
            class="mb20"
            v-if="ruleForm.menuType != 'F'"
          >
            <el-form-item label="菜单图标">
              <IconSelector
                placeholder="请输入菜单图标"
                v-model="ruleForm.icon"
              />
            </el-form-item>
          </el-col>
          <el-col
            :xs="24"
            :sm="12"
            :md="12"
            :lg="12"
            :xl="12"
            class="mb20"
            v-if="ruleForm.menuType && ruleForm.menuType == 'C'"
          >
            <el-form-item label="组件地址">
              <el-input
                v-model="ruleForm.component"
                placeholder="组件地址"
                clearable
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col
            :xs="24"
            :sm="12"
            :md="12"
            :lg="12"
            :xl="12"
            class="mb20"
            v-if="ruleForm.menuType && ruleForm.menuType == 'C'"
          >
            <el-form-item label="路由地址">
              <el-input
                v-model="ruleForm.path"
                placeholder="请输入路由地址"
                clearable
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="菜单类型" prop="menuType">
              <el-radio-group v-model="ruleForm.menuType">
                <el-radio
                  v-for="dict in menuTypeOptions"
                  :key="dict.dictValue"
                  :label="dict.dictValue"
                  >{{ dict.dictLabel }}
                </el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="是否隐藏">
              <el-radio-group v-model="ruleForm.isHide">
                <el-radio
                  v-for="dict in isHideOptions"
                  :key="dict.dictValue"
                  :label="dict.dictValue"
                  >{{ dict.dictLabel }}
                </el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="是否缓存">
              <el-radio-group v-model="ruleForm.isKeepAlive">
                <el-radio
                  v-for="dict in yesOrNoOptions"
                  :key="dict.dictValue"
                  :label="dict.dictValue"
                  >{{ dict.dictLabel }}
                </el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="是否固定">
              <el-radio-group v-model="ruleForm.isAffix">
                <el-radio
                  v-for="dict in yesOrNoOptions"
                  :key="dict.dictValue"
                  :label="dict.dictValue"
                  >{{ dict.dictLabel }}
                </el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="菜单状态" prop="status">
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
            <el-form-item label="是否外链">
              <el-radio-group v-model="ruleForm.isFrame">
                <el-radio
                  v-for="dict in yesOrNoOptions"
                  :key="dict.dictValue"
                  :label="dict.dictValue"
                  >{{ dict.dictLabel }}
                </el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="链接地址">
              <el-input
                v-model="ruleForm.isLink"
                placeholder="外链/内嵌时链接地址（http:xxx.com）"
                clearable
                :disabled="ruleForm.isFrame == '1'"
              >
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="权限标识">
              <el-input
                v-model="ruleForm.perms"
                placeholder="路由权限标识（多个请用逗号隔开）"
                clearable
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="菜单排序" prop="orderNum">
              <el-input
                v-model="ruleForm.orderNum"
                placeholder="菜单排序"
                clearable
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="备注">
              <el-input
                v-model="ruleForm.remark"
                placeholder="请输入备注内容"
                clearable
                type="textarea"
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
import { treeselect, updateMenu, addMenu } from "@/api/system/menu";
import { ElMessage } from "element-plus";

// import { setBackEndControlRefreshRoutes } from "/@/router/backEnd";
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

      /**
       * 参数请参考 `/src/router/route.ts` 中的 `dynamicRoutes` 路由菜单格式（请注意参数类型！）
       * 受到 `element plus` 类型 `string/number/object` 影响，不可使用 `:value="true"`
       * 的写法，所以传值到后台时，需要转换成布尔值，否则页面可能出现玄学。
       * 路由权限标识为数组格式，基本都需要自行转换类型
       */
      ruleForm: {
        menuId: "", // 菜单ID
        menuName: "", // 菜单名称
        parentId: "", // 父菜单ID
        component: "", // 组件地址
        menuSort: "", // 菜单排序
        status: "", //菜单状态
        title: "", // 菜单名称
        icon: "", // 菜单图标
        isHide: "", // 是否隐藏
        isKeepAlive: "", // 是否缓存
        isAffix: "", // 是否固定
        isLink: "", // 是否外链，开启外链条件，`1、isLink:true 2、链接地址不为空`
        isFrame: "", // 是否内嵌，开启条件，`1、isFrame:true 2、链接地址不为空`
        auth: "", // 路由权限标识（多个请用逗号隔开），最后转成数组格式
        remark: "", // 备注
      },
      // 显示状态数据字典
      isHideOptions: [],
      // 菜单状态数据字典
      statusOptions: [],
      // 菜单类型数据字典
      menuTypeOptions: [],
      // 数字是否数据字典
      yesOrNoOptions: [],
      // 菜单树选项
      menuOptions: [],
      // 表单校验
      ruleRules: {
        menuName: [
          { required: true, message: "菜单名称不能为空", trigger: "blur" },
        ],
        parentId: [
          { required: true, message: "父菜单不能为空", trigger: "blur" },
        ],
        menuType: [
          { required: true, message: "菜单类型不能为空", trigger: "blur" },
        ],
        status: [
          { required: true, message: "菜单状态不能为空", trigger: "blur" },
        ],
        orderNum: [
          { required: true, message: "菜单顺序不能为空", trigger: "blur" },
        ],
      },
    });
    // 打开弹窗
    const openDialog = (row: any) => {
      if (row.menuId && row.menuId != undefined && row.menuId != "") {
        state.ruleForm = row;
      } else {
        initForm();
        state.ruleForm.parentId = row.parentId;
      }

      // state.ruleForm.menuName = row.menuName;
      // state.ruleForm.component = '';
      // state.ruleForm.isLink = row.isLink ? 'true' : '';
      // state.ruleForm.menuSort = '';
      // state.ruleForm.title =  row.title;
      // // 回显时，图标选择器有这个图标才可以回显，菜单中使用了阿里的、element plus的，二者不可共存
      // state.ruleForm.icon = row.icon;
      // state.ruleForm.isHide = row.isHide ? 'true' : 'false';
      // state.ruleForm.isKeepAlive = row.isKeepAlive ? 'true' : 'false';
      // state.ruleForm.isAffix = row.isAffix ? 'true' : 'false';
      // state.ruleForm.isLink = row.isLink ? row.isLink : '';
      // state.ruleForm.isFrame = row.isFrame ? 'true' : '';
      // state.ruleForm.auth = row.auth ? row.auth.join(',') : '';
      state.isShowDialog = true;

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
      // 查询数字是否数据字典
      proxy.getDicts("sys_num_yes_no").then((response: any) => {
        state.yesOrNoOptions = response.data;
      });
      // 查询菜单下拉树结构
      treeselect().then((response: any) => {
        state.menuOptions = [];
        const menu = { id: 0, label: "主类目", children: [] };
        menu.children = response.data;
        state.menuOptions.push(menu);
        // console.log("查询菜单下拉树结构2", proxy.handleTree(v_data, "id"));
        // state.menuOptions.push(menu);
      });
    };
    // 关闭弹窗
    const closeDialog = (row?: object) => {
      state.isShowDialog = false;
    };
    // 取消
    const onCancel = () => {
      closeDialog();
      initForm();
    };
    // 保存
    const onSubmit = () => {
      console.log("保存", state.ruleForm.menuId); // 数据，请注意需要转换的类型
      const formWrap = unref(ruleFormRef) as any;
      if (!formWrap) return;
      formWrap.validate((valid: boolean) => {
        if (valid) {
          if (
            state.ruleForm.menuId != undefined &&
            state.ruleForm.menuId != ""
          ) {
            updateMenu(state.ruleForm).then((response) => {
              ElMessage.success("修改成功");
              closeDialog(); // 关闭弹窗
            });
          } else {
            addMenu(state.ruleForm).then((response) => {
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
      state.ruleForm.menuId = ""; // 菜单ID
      state.ruleForm.menuName = ""; // 菜单名称
      state.ruleForm.parentId = ""; // 父菜单ID
      state.ruleForm.component = ""; // 组件地址
      state.ruleForm.isLink = ""; // 是否外链
      state.ruleForm.menuSort = ""; // 菜单排序
      state.ruleForm.status = ""; //菜单状态
      state.ruleForm.title = ""; // 菜单名称
      state.ruleForm.icon = ""; // 菜单图标
      state.ruleForm.isHide = ""; // 是否隐藏
      state.ruleForm.isKeepAlive = ""; // 是否缓存
      state.ruleForm.isAffix = ""; // 是否固定
      state.ruleForm.isLink = ""; // 是否外链，开启外链条件，`1、isLink:true 2、链接地址不为空`
      state.ruleForm.isFrame = ""; // 是否内嵌，开启条件，`1、isFrame:true 2、链接地址不为空`
      state.ruleForm.auth = ""; // 路由权限标识（多个请用逗号隔开），最后转成数组格式
      state.ruleForm.remark = ""; // 备注
    };

    return {
      ruleFormRef,
      openDialog,
      closeDialog,
      onCancel,
      onSubmit,
      ...toRefs(state),
    };
  },
};
</script>
