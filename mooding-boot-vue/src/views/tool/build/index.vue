
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
    <!-- <el-button type="primary" size="small" class="mt15" icon="el-icon-position" @click="onGoToFrontEndPage"> </el-button> -->
  </div>
</template>

<script>
export default {
  data() {
    return {
      shortcuts: [
        {
          text: "今天",
          value: new Date(),
        },
        {
          text: "昨天",
          value: () => {
            const date = new Date();
            date.setTime(date.getTime() - 3600 * 1000 * 24);
            return date;
          },
        },
        {
          text: "一周前",
          value: () => {
            const date = new Date();
            date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
            return date;
          },
        },
      ],
      value1: "",
      value2: "",
      value3: "",
      defaultTime: new Date(2000, 1, 1, 12, 0, 0), // '12:00:00'
    };
  },
};
</script>

<style>
</style>
