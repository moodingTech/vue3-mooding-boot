<template>
  <iFrame :src=url />
</template>
<script lang="ts">
import iFrame from "@/components/iFrame/index.vue";
import {  toRefs, reactive } from "vue";

export default {
  name: "Druid",
  components: { iFrame },
  setup() {
    const state = reactive({
      url: "http://127.0.0.1:8088/api/druid/index.html",
    });
    return {
      ...toRefs(state),
    };
  },
};
</script>