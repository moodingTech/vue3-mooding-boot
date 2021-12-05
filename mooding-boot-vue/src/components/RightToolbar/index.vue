<template>
  <div class="top-right-btn">
    <el-row>
      <el-tooltip class="item" effect="dark" :content="showSearch ? '隐藏搜索' : '显示搜索'" placement="top">
        <el-button size="mini" circle icon="el-icon-search" @click="toggleSearch()"/>
      </el-tooltip>
      <el-tooltip class="item" effect="dark" content="刷新" placement="top">
        <el-button size="mini" circle icon="el-icon-refresh" @click="refresh()"/>
      </el-tooltip>
      <el-tooltip class="item" effect="dark" content="显隐列" placement="top" v-if="columns">
        <el-button size="mini" circle icon="el-icon-menu" @click="showColumn()"/>
      </el-tooltip>
    </el-row>
    <el-dialog :title="title" v-model="open"  append-to-body center>
      <el-transfer
          :titles="['显示', '隐藏']"
          v-model="value"
          :data="columns"
          @change="dataChange"
      ></el-transfer>
    </el-dialog>
  </div>
</template>
<script>
import {onMounted, reactive, toRefs, defineComponent} from 'vue';

export default defineComponent({
  name: "RightToolbar",
  props: {
    showSearch: {
      type: Boolean,
      default: true,
    },
    columns: {
      type: Array,
    },
  },
  setup(props) {
    const state = reactive({
      // 显隐数据
      value: [],
      // 弹出层标题
      title: "显示/隐藏",
      // 是否显示弹出层
      open: false,
    });
    // 打开显隐列dialog
    const showColumn = () => {
      state.open = true;
    };
    // 右侧列表元素变化
    const dataChange = (data) => {
      for (var item in props.columns) {
        const key = props.columns[item].key;
        props.columns[item].visible = !data.includes(key);
      }
    };
    // 搜索
    const toggleSearch = () => {
      console.log('dgl')
      // this.$emit("update:showSearch", !this.showSearch);
    };
    // 刷新
    const refresh = () => {
      console.log('dgl')
      // this.$emit("queryTable");
    };
    // 页面加载时
    onMounted(() => {
      // 显隐列初始默认隐藏列
      for (let item in props.columns) {
        if (props.columns[item].visible === false) {
          state.value.push(parseInt(item));
        }
      }
    });
    return {
      showColumn,
      dataChange,
      refresh,
      toggleSearch,
      ...toRefs(state),
    };
  }
});
</script>
<style lang="scss" scoped>
:deep() .el-transfer__button {
  border-radius: 50%;
  padding: 12px;
  display: block;
  margin-left: 0px;
}

:deep() .el-transfer__button:first-child {
  margin-bottom: 10px;
}
</style>
