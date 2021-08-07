
<template>
<div class="system-user-container app-container">
    <el-alert
      title="温馨提示：此页面后续开发成前端代码自动生成，根据Element Plus UI表单设计及代码生成器，可将生成的代码直接运行在基于Element的vue项目中；也可导出JSON表单，使用配套的解析器将JSON解析成真实的表单。"
      type="warning"
      :closable="false"
    ></el-alert>
    <el-result icon="warning" title="警告提示" subTitle="请根据提示进行操作">
      <template #extra>
        <el-button type="primary" size="medium">敬请期待</el-button>
      </template>
    </el-result>
    
  <el-color-picker v-model="color"></el-color-picker>
  <el-color-picker v-model="color" size="medium"></el-color-picker>
  <el-color-picker v-model="color" size="small"></el-color-picker>
  <el-color-picker v-model="color" size="mini"></el-color-picker>
</div>
</template>

<script>
export default {
  name: "Gen",
  data() {
    return {
      color: "#409EFF",
    };
  },
};
</script>

<style>
</style>
