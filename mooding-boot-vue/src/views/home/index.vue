<template>
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
};
</script>

<style scoped lang="scss">
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
}
</style>
