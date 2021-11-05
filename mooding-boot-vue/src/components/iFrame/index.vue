<template>
  <div v-loading="loading" :style="'height:' + height">
    <iframe
      :src="src"
      frameborder="no"
      style="width: 100%; height: 100%"
      scrolling="auto"
    />
  </div>
</template>
<script  lang="ts">
import { onMounted, toRefs, reactive } from "vue";
export default {
  props: {
    src: {
      type: String,
      required: true,
    },
  },
  setup(props, context) {
    const state = reactive({
      height: document.documentElement.clientHeight - 94.5 + "px;",
      loading: true,
      url: props.src,
    });
    // 页面加载时
    onMounted(() => {
      setTimeout(() => {
        state.loading = false;
      }, 300);
      window.onresize = function temp() {
        state.height = document.documentElement.clientHeight - 94.5 + "px;";
      };
    });
    return {
      ...toRefs(state),
    };
  },
};
</script>