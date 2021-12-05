<template>
  <iFrame :src="url" />
</template>
<script lang="ts">
import iFrame from "@/components/iFrame/index.vue";
import { toRefs, reactive } from "vue";

export default {
  name: "Druid",
  components: { iFrame },
  setup() {
    const state = reactive({
      url: "/api/druid/index.html",
    });
    return {
      ...toRefs(state),
    };
  },
};
</script>
