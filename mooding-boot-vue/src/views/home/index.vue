<template>
<<<<<<< HEAD
	<div class="home-container">
		<el-row :gutter="15">
			<el-col :sm="6" class="mb15">
				<div class="home-card-item home-card-first">
					<div class="flex-margin flex">
						<img src="https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1813762643,1914315241&fm=26&gp=0.jpg" />
						<div class="home-card-first-right ml15">
							<div class="flex-margin">
								<div class="home-card-first-right-title">{{ currentTime }}，admin！</div>
								<div class="home-card-first-right-msg mt5">超级管理</div>
							</div>
						</div>
					</div>
				</div>
			</el-col>
			<el-col :sm="6" class="mb15" v-for="(v, k) in topCardItemList" :key="k">
				<div class="home-card-item home-card-item-box" :style="{ background: v.color }">
					<div class="home-card-item-flex">
						<div class="home-card-item-title pb3">{{ v.title }}</div>
						<div class="home-card-item-title-num pb6" :id="`titleNum${k + 1}`"></div>
						<div class="home-card-item-tip pb3">{{ v.tip }}</div>
						<div class="home-card-item-tip-num" :id="`tipNum${k + 1}`"></div>
					</div>
					<i :class="v.icon" :style="{ color: v.iconColor }"></i>
				</div>
			</el-col>
		</el-row>
		<el-row :gutter="15">
			<el-col :xs="24" :sm="14" :md="14" :lg="16" :xl="16" class="mb15">
				<el-card shadow="hover" header="商品销售情况">
					<div style="height: 200px" ref="homeLaboratoryRef"></div>
				</el-card>
			</el-col>
			<el-col :xs="24" :sm="10" :md="10" :lg="8" :xl="8">
				<el-card shadow="hover" header="环境监测">
					<div class="home-monitor">
						<div class="flex-warp">
							<div class="flex-warp-item" v-for="(v, k) in environmentList" :key="k">
								<div class="flex-warp-item-box">
									<i :class="v.icon" :style="{ color: v.iconColor }"></i>
									<span class="pl5">{{ v.label }}</span>
									<div class="mt10">{{ v.value }}</div>
								</div>
							</div>
						</div>
					</div>
				</el-card>
			</el-col>
		</el-row>
		<el-row :gutter="15">
			<el-col :xs="24" :sm="14" :md="14" :lg="16" :xl="16" class="home-warning-media">
				<el-card shadow="hover" header="预警信息" class="home-warning-card">
					<el-table :data="tableData.data" style="width: 100%" stripe>
						<el-table-column prop="date" label="时间"></el-table-column>
						<el-table-column prop="name" label="实验室名称"></el-table-column>
						<el-table-column prop="address" label="报警内容"></el-table-column>
					</el-table>
				</el-card>
			</el-col>
			<el-col :xs="24" :sm="10" :md="10" :lg="8" :xl="8" class="home-dynamic-media">
				<el-card shadow="hover" header="动态信息">
					<div class="home-dynamic">
						<el-scrollbar>
							<div class="home-dynamic-item" v-for="(v, k) in activitiesList" :key="k">
								<div class="home-dynamic-item-left">
									<div class="home-dynamic-item-left-time1 mb5">{{ v.time1 }}</div>
									<div class="home-dynamic-item-left-time2">{{ v.time2 }}</div>
								</div>
								<div class="home-dynamic-item-line">
									<i class="iconfont icon-fangkuang"></i>
								</div>
								<div class="home-dynamic-item-right">
									<div class="home-dynamic-item-right-title mb5">
										<i class="el-icon-s-comment"></i>
										<span>{{ v.title }}</span>
									</div>
									<div class="home-dynamic-item-right-label">{{ v.label }}</div>
								</div>
							</div>
						</el-scrollbar>
					</div>
				</el-card>
			</el-col>
		</el-row>
		<el-row>
			<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" class="mt15">
				<el-card shadow="hover" header="履约超时预警">
					<div style="height: 200px" ref="homeOvertimeRef"></div>
				</el-card>
			</el-col>
		</el-row>
	</div>
</template>

<script lang="ts">
import { toRefs, reactive, onMounted, nextTick, computed, getCurrentInstance } from 'vue';
import * as echarts from 'echarts';
import { CountUp } from 'countup.js';
import { formatAxis } from '@/utils/formatTime';
import { topCardItemList, environmentList, activitiesList } from './mock';
export default {
	name: 'home',
	setup() {
		const { proxy } = getCurrentInstance() as any;
		const state = reactive({
			topCardItemList,
			environmentList,
			activitiesList,
			tableData: {
				data: [
					{
						date: '2016-05-02',
						name: '1号实验室',
						address: '烟感2.1%OBS/M',
					},
					{
						date: '2016-05-04',
						name: '2号实验室',
						address: '温度30℃',
					},
					{
						date: '2016-05-01',
						name: '3号实验室',
						address: '湿度57%RH',
					},
				],
			},
		});
		// 当前时间提示语
		const currentTime = computed(() => {
			return formatAxis(new Date());
		});
		// 初始化数字滚动
		const initNumCountUp = () => {
			nextTick(() => {
				new CountUp('titleNum1', Math.random() * 10000).start();
				new CountUp('titleNum2', Math.random() * 10000).start();
				new CountUp('titleNum3', Math.random() * 10000).start();
				new CountUp('tipNum1', Math.random() * 1000).start();
				new CountUp('tipNum2', Math.random() * 1000).start();
				new CountUp('tipNum3', Math.random() * 1000).start();
			});
		};
		// 实验室使用情况
		const initHomeLaboratory = () => {
			const myChart = echarts.init(proxy.$refs.homeLaboratoryRef);
			const option = {
				grid: {
					top: 50,
					right: 20,
					bottom: 30,
					left: 30,
				},
				tooltip: {
					trigger: 'axis',
				},
				legend: {
					data: ['预购队列', '最新成交价'],
					right: 13,
				},
				xAxis: {
					data: ['衬衫', '羊毛衫', '雪纺衫', '裤子', '高跟鞋', '袜子'],
				},
				yAxis: [
					{
						type: 'value',
						name: '价格',
					},
				],
				series: [
					{
						name: '预购队列',
						type: 'bar',
						data: [5, 20, 36, 10, 10, 20],
					},
					{
						name: '最新成交价',
						type: 'line',
						data: [15, 20, 16, 20, 30, 8],
					},
				],
			};
			myChart.setOption(option);
			window.addEventListener('resize', () => {
				myChart.resize();
			});
		};
		// 履约超时预警
		const initHomeOvertime = () => {
			const myChart = echarts.init(proxy.$refs.homeOvertimeRef);
			const option = {
				grid: {
					top: 50,
					right: 20,
					bottom: 30,
					left: 30,
				},
				tooltip: {
					trigger: 'axis',
				},
				legend: {
					data: ['订单数量', '超时数量', '在线数量', '预警数量'],
					right: 13,
				},
				xAxis: {
					data: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月'],
				},
				yAxis: [
					{
						type: 'value',
						name: '数量',
					},
				],
				series: [
					{
						name: '订单数量',
						type: 'bar',
						data: [5, 20, 36, 10, 10, 20, 11, 13, 10, 9, 17, 19],
					},
					{
						name: '超时数量',
						type: 'bar',
						data: [15, 12, 26, 15, 11, 16, 31, 13, 5, 16, 13, 15],
					},
					{
						name: '在线数量',
						type: 'line',
						data: [15, 20, 16, 20, 30, 8, 16, 19, 12, 18, 19, 14],
					},
					{
						name: '预警数量',
						type: 'line',
						data: [10, 10, 13, 12, 15, 18, 19, 10, 12, 15, 11, 17],
					},
				],
			};
			myChart.setOption(option);
			window.addEventListener('resize', () => {
				myChart.resize();
			});
		};
		// 页面加载时
		onMounted(() => {
			initNumCountUp();
			initHomeLaboratory();
			initHomeOvertime();
		});
		return {
			currentTime,
			...toRefs(state),
		};
	},
=======
  <div class="app-container home">
    <el-row :gutter="20">
      <el-col :sm="24" :lg="12" style="padding-left: 20px">
        <h2>木丁后台管理框架</h2>
        <p>
          mooding-boot
          是一个轻量级的，前后端分离的J2EE快速开发平台，致力于做更简洁的快速开发平台。
          每次做项目都要去找很久适合自己的项目，有时候找到的项目技术相对比较滞后，有时候前端后和后端项目不是同时让自己满意，因此利用休息日自己撸了一个比较简洁的项目，供自己和想自己的小伙伴参考使用。
          <br />
          <br />
          该项目前端采用时比较新颖的vue3.x和element
          plus去做的，至少最近三年vue3.x是使用比较多的前端框架，项目实现的功能比较简单，学习和理解都比较容易，方便后续实现不同项目的业务需求。
          <br />
          <br />
          后续会基于该项目尝试开发出仓储管理、物流管理、社区管理和人事管理项目，如有感兴趣的朋友可以从下方找到我个人社交账号，与我联系，期待你的加入。
        </p>
        <p>
          <b>当前版本:</b> <span>v{{ version }}</span>
        </p>
        <p>
          <el-tag type="danger">&yen;免费开源</el-tag>
        </p>
        <p>
          <el-button
            type="primary"
            size="mini"
            icon="el-icon-cloudy"
            plain
            @click="goTarget('https://gitee.com/moodingtech/vue3-mooding-boot')"
            >访问码云</el-button
          >
          <el-button
            size="mini"
            icon="el-icon-s-home"
            plain
            @click="goTarget('http://49.235.52.198:8088/')"
            >访问主页</el-button
          >
        </p>
      </el-col>

      <el-col :sm="24" :lg="12" style="padding-left: 50px">
        <el-row>
          <el-col :span="12">
            <h2>技术选型</h2>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <h4>后端技术</h4>
            <ul>
              <li>SpringBoot</li>
              <li>Spring Security</li>
              <li>JWT</li>
              <li>MyBatis</li>
              <li>MyBatisPlus</li>
              <li>Druid</li>
              <li>Fastjson</li>
              <li>...</li>
            </ul>
          </el-col>
          <el-col :span="6">
            <h4>前端技术</h4>
            <ul>
              <li>Vue3.X</li>
              <li>vite2.X</li>
              <li>Vuex</li>
              <li>ElementPlus-ui</li>
              <li>Axios</li>
              <li>Sass</li>
              <li>mitt</li>
              <li>...</li>
            </ul>
          </el-col>
        </el-row>
      </el-col>
    </el-row>
    <el-divider />
    <el-row :gutter="20">
      <el-col :xs="24" :sm="24" :md="12" :lg="8">
        <el-card class="update-log">
          <div class="header clearfix">
            <span>联系信息</span>
          </div>
          <div class="body">
            <p>
              <i class="el-icon-s-promotion"></i> 邮箱：<a href="javascript:;"
                >moodingTech@gmail.com</a
              >
            </p>
            <p>
              <i class="el-icon-user-solid"></i> QQ：
              <a href="https://jq.qq.com" target="_blank"> 616003447</a>
            </p>
            <p>
              <i class="el-icon-chat-dot-round"></i> 微信：<a
                href="javascript:;"
                >616003447 木丁</a
              >
            </p>
            <p>
              <i class="el-icon-money"></i> 支付宝：<a
                href="javascript:;"
                class="支付宝信息"
                >/ *木丁</a
              >
            </p>
          </div>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="12" :lg="8">
        <el-card class="update-log">
          <div class="header clearfix">
            <span>更新日志</span>
          </div>
          <el-collapse accordion>
            <el-collapse-item title="v1.0.0 - 2021-08-08">
              <ol>
                <li>木丁前后端分离系统正式发布</li>
              </ol>
            </el-collapse-item>
          </el-collapse>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="12" :lg="8">
        <el-card class="update-log">
          <div class="header clearfix">
            <span>赞助支持</span>
          </div>
          <div class="body">
            <br />
            <div>
              <img
                src="../../assets/images/zfb-pay.png"
                alt="donate"
                title="支付宝"
                style="width: 60%; height: 50%"
              />
              &nbsp;&nbsp;
              <img
                src="../../assets/images/vx-pay.png"
                alt="donate"
                title="微信"
                style="width: 60%; height: 50%"
              />
              <br />
            </div>
            <span style="display: inline-block; height: 30px; line-height: 30px"
              >你可以请作者喝杯咖啡表示鼓励</span
            >
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script lang="ts">
import { toRefs, reactive, onMounted } from "vue";
export default {
  name: "home",
  setup() {
    // const { proxy } = getCurrentInstance() as any;
    const state = reactive({
      version: "1.0.0",
    });
    const goTarget = (href: string) => {
      window.open(href, "_blank");
    };
    // 页面加载时
    onMounted(() => {
      // initHomeOvertime();
    });
    return {
      goTarget,
      ...toRefs(state),
    };
  },
>>>>>>> master
};
</script>

<style scoped lang="scss">
<<<<<<< HEAD
.home-container {
	overflow-x: hidden;
	.home-card-item {
		width: 100%;
		height: 103px;
		background: gray;
		border-radius: 4px;
		transition: all ease 0.3s;
		&:hover {
			box-shadow: 0 2px 12px 0 rgb(0 0 0 / 10%);
			transition: all ease 0.3s;
		}
	}
	.home-card-item-box {
		display: flex;
		align-items: center;
		position: relative;
		overflow: hidden;
		&:hover {
			i {
				right: 0px !important;
				bottom: 0px !important;
				transition: all ease 0.3s;
			}
		}
		i {
			position: absolute;
			right: -10px;
			bottom: -10px;
			font-size: 70px;
			transform: rotate(-30deg);
			transition: all ease 0.3s;
		}
		.home-card-item-flex {
			padding: 0 20px;
			color: white;
			.home-card-item-title,
			.home-card-item-tip {
				font-size: 13px;
			}
			.home-card-item-title-num {
				font-size: 18px;
			}
			.home-card-item-tip-num {
				font-size: 13px;
			}
		}
	}
	.home-card-first {
		background: white;
		border: 1px solid #ebeef5;
		display: flex;
		align-items: center;
		img {
			width: 60px;
			height: 60px;
			border-radius: 100%;
			border: 2px solid var(--color-primary-light-5);
		}
		.home-card-first-right {
			flex: 1;
			display: flex;
			flex-direction: column;
			.home-card-first-right-msg {
				font-size: 13px;
				color: gray;
			}
		}
	}
	.home-monitor {
		height: 200px;
		.flex-warp-item {
			width: 50%;
			height: 100px;
			display: flex;
			.flex-warp-item-box {
				margin: auto;
				height: auto;
				text-align: center;
			}
		}
	}
	.home-warning-card {
		height: 292px;
		::v-deep(.el-card) {
			height: 100%;
		}
	}
	.home-dynamic {
		height: 200px;
		.home-dynamic-item {
			display: flex;
			width: 100%;
			height: 60px;
			overflow: hidden;
			&:first-of-type {
				.home-dynamic-item-line {
					i {
						color: orange !important;
					}
				}
			}
			.home-dynamic-item-left {
				text-align: right;
				.home-dynamic-item-left-time1 {
				}
				.home-dynamic-item-left-time2 {
					font-size: 13px;
					color: gray;
				}
			}
			.home-dynamic-item-line {
				height: 60px;
				border-right: 2px dashed #dfdfdf;
				margin: 0 20px;
				position: relative;
				i {
					color: var(--color-primary);
					font-size: 12px;
					position: absolute;
					top: 1px;
					left: -6px;
					transform: rotate(46deg);
					background: white;
				}
			}
			.home-dynamic-item-right {
				flex: 1;
				.home-dynamic-item-right-title {
					i {
						margin-right: 5px;
						border: 1px solid #dfdfdf;
						width: 20px;
						height: 20px;
						border-radius: 100%;
						padding: 3px 2px 2px;
						text-align: center;
						color: var(--color-primary);
					}
				}
				.home-dynamic-item-right-label {
					font-size: 13px;
					color: gray;
				}
			}
		}
	}
=======
.home {
  blockquote {
    padding: 10px 20px;
    margin: 0 0 20px;
    font-size: 17.5px;
    border-left: 5px solid #eee;
  }
  hr {
    margin-top: 20px;
    margin-bottom: 20px;
    border: 0;
    border-top: 1px solid #eee;
  }
  .col-item {
    margin-bottom: 20px;
  }

  ul {
    padding: 0;
    margin: 0;
  }

  font-family: "open sans", "Helvetica Neue", Helvetica, Arial, sans-serif;
  font-size: 13px;
  color: #676a6c;
  overflow-x: hidden;

  ul {
    list-style-type: none;
  }

  h4 {
    margin-top: 0px;
  }

  h2 {
    margin-top: 10px;
    font-size: 26px;
    font-weight: 100;
  }

  p {
    margin-top: 10px;

    b {
      font-weight: 700;
    }
  }

  .update-log {
    ol {
      display: block;
      list-style-type: decimal;
      margin-block-start: 1em;
      margin-block-end: 1em;
      margin-inline-start: 0;
      margin-inline-end: 0;
      padding-inline-start: 40px;
    }
  }
>>>>>>> master
}
</style>
