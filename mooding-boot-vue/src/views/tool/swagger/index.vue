<template>
  <div class="swagger" :style="'height:'+ height">
    <iframe src="/api/doc.html" class="ifm">

    </iframe>
  </div>
</template>
<script lang="ts">
import {onMounted} from 'vue'

export default {
  name: 'Swagger',
  data() {
    return {
      height: document.documentElement.clientHeight - 100 + 'px;',
    }
    onMounted(() => {
      setTimeout(() => {

      }, 230)
      const that = this
      window.onresize = function temp() {
        that.height = document.documentElement.clientHeight - 100 + 'px;'
      }
    })
  }
}
</script>

<style scoped lang="scss">
@import "src/assets/style/mixins/mixins";

.swagger {
  padding: 0px;
  margin: 0px;
  width: 100%;
  height: 1000px;
}

.ifm {
  padding: 0;
  margin: 0;
  frameborderr: no;
  border: 0;
  width: 100%;
  height: 100%;
}
</style>
