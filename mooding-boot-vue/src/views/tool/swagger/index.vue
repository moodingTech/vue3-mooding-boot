<template>
  <iFrame :src="url" />
</template>
<script lang="ts">
import iFrame from "@/components/iFrame/index.vue";
import { toRefs, reactive } from "vue";

export default {
  name: "Swagger",
  components: { iFrame },
  setup() {
    const state = reactive({
      height: document.documentElement.clientHeight - 100 + "px;",
      url: "/api/doc.html",
    });
    return {
      ...toRefs(state),
    };
  },
};
</script>
