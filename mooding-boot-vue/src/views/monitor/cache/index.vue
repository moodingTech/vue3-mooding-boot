<template>
  <div class="app-container">
    <el-row>
      <el-col :span="24" class="card-box">
        <el-card>
          <template #header> <span>基本信息</span></template>
          <div class="el-table el-table--enable-row-hover el-table--medium">
            <table cellspacing="0" style="width: 100%">
              <tbody>
                <tr>
                  <td><div class="cell">Redis版本</div></td>
                  <td>
                    <div class="cell" v-if="cache.info">
                      {{ cache.info.redis_version }}
                    </div>
                  </td>
                  <td><div class="cell">运行模式</div></td>
                  <td>
                    <div class="cell" v-if="cache.info">
                      {{
                        cache.info.redis_mode == "standalone" ? "单机" : "集群"
                      }}
                    </div>
                  </td>
                  <td><div class="cell">端口</div></td>
                  <td>
                    <div class="cell" v-if="cache.info">
                      {{ cache.info.tcp_port }}
                    </div>
                  </td>
                  <td><div class="cell">客户端数</div></td>
                  <td>
                    <div class="cell" v-if="cache.info">
                      {{ cache.info.connected_clients }}
                    </div>
                  </td>
                </tr>
                <tr>
                  <td><div class="cell">运行时间(天)</div></td>
                  <td>
                    <div class="cell" v-if="cache.info">
                      {{ cache.info.uptime_in_days }}
                    </div>
                  </td>
                  <td><div class="cell">使用内存</div></td>
                  <td>
                    <div class="cell" v-if="cache.info">
                      {{ cache.info.used_memory_human }}
                    </div>
                  </td>
                  <td><div class="cell">使用CPU</div></td>
                  <td>
                    <div class="cell" v-if="cache.info">
                      {{
                        parseFloat(cache.info.used_cpu_user_children).toFixed(2)
                      }}
                    </div>
                  </td>
                  <td><div class="cell">内存配置</div></td>
                  <td>
                    <div class="cell" v-if="cache.info">
                      {{ cache.info.maxmemory_human }}
                    </div>
                  </td>
                </tr>
                <tr>
                  <td><div class="cell">AOF是否开启</div></td>
                  <td>
                    <div class="cell" v-if="cache.info">
                      {{ cache.info.aof_enabled == "0" ? "否" : "是" }}
                    </div>
                  </td>
                  <td><div class="cell">RDB是否成功</div></td>
                  <td>
                    <div class="cell" v-if="cache.info">
                      {{ cache.info.rdb_last_bgsave_status }}
                    </div>
                  </td>
                  <td><div class="cell">Key数量</div></td>
                  <td>
                    <div class="cell" v-if="cache.dbSize">
                      {{ cache.dbSize }}
                    </div>
                  </td>
                  <td><div class="cell">网络入口/出口</div></td>
                  <td>
                    <div class="cell" v-if="cache.info">
                      {{ cache.info.instantaneous_input_kbps }}kps/{{
                        cache.info.instantaneous_output_kbps
                      }}kps
                    </div>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </el-card>
      </el-col>

      <el-col :span="12" class="card-box">
        <el-card>
          <template #header><span>命令统计</span></template>
          <div class="el-table el-table--enable-row-hover el-table--medium">
            <div ref="commandstats" style="height: 420px" />
          </div>
        </el-card>
      </el-col>

      <el-col :span="12" class="card-box">
        <el-card>
          <template #header>
            <span>内存信息</span>
          </template>
          <div class="el-table el-table--enable-row-hover el-table--medium">
            <div ref="usedmemory" style="height: 420px" />
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script lang="ts">
import {
  ref,
  toRefs,
  reactive,
  onMounted,
  getCurrentInstance,
  onUnmounted,
} from "vue";
import { ElMessageBox, ElMessage } from "element-plus";
import { getCache } from "@/api/monitor/cache";
import * as echarts from "echarts";
export default {
  name: "Job",
  // components: { echarts },
  setup() {
    const { proxy } = getCurrentInstance() as any;
    const state = reactive({
      // 加载层信息
      loading: [],
      // 统计命令信息
      commandstats: null,
      // 使用内存
      usedmemory: null,
      // cache信息
      cache: [],
    });
    /** 查缓存询信息 */
    const getList = () => {
      getCache().then((response) => {
        state.cache = response.data;
        state.loading.close();

        const myChart = echarts.init(proxy.$refs.commandstats, "macarons");
        myChart.setOption({
          tooltip: {
            trigger: "item",
            formatter: "{a} <br/>{b} : {c} ({d}%)",
          },
          series: [
            {
              name: "命令",
              type: "pie",
              roseType: "radius",
              radius: [15, 95],
              center: ["50%", "38%"],
              data: response.data.commandStats,
              animationEasing: "cubicInOut",
              animationDuration: 1000,
            },
          ],
        });

        window.addEventListener("resize", () => {
          myChart.resize();
        });

        const myChart1 = echarts.init(proxy.$refs.usedmemory, "macarons");
        myChart1.setOption({
          tooltip: {
            formatter: "{b} <br/>{a} : " + state.cache.info.used_memory_human,
          },
          series: [
            {
              name: "峰值",
              type: "gauge",
              min: 0,
              max: 1000,
              detail: {
                formatter: state.cache.info.used_memory_human,
              },
              data: [
                {
                  value: parseFloat(state.cache.info.used_memory_human),
                  name: "内存消耗",
                },
              ],
            },
          ],
        });

        window.addEventListener("resize1", () => {
          myChart1.resize();
        });
      });
    };
    // 打开加载层
    const openLoading = () => {
      state.loading = proxy.$loading({
        lock: true,
        text: "拼命读取中",
        spinner: "el-icon-loading",
        background: "rgba(0, 0, 0, 0.7)",
      });
    };
    // 页面加载时
    onMounted(() => {
       openLoading();
      getList();
     
    });
    return {
      getList,
      openLoading,
      ...toRefs(state),
    };
  },
};
</script>