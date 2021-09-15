<template>
  <div class="personal">
    <el-row>
      <!-- 个人信息 -->
      <el-col :xs="24" :sm="16">
        <el-card shadow="hover" header="个人信息">
          <div class="personal-user">
            <div class="personal-user-left">
              <userAvatar :user="userInfo" />
            </div>
            <div class="personal-user-right">
              <el-row>
                <el-col :span="24" class="personal-title mb18"
                  >{{ currentTime }}，{{ userInfo.nickName }}，{{ slogan }}！
                </el-col>
                <el-col :span="24">
                  <el-row>
                    <el-col :xs="24" :sm="8" class="personal-item mb6">
                      <div class="personal-item-label">昵称：</div>
                      <div class="personal-item-value">
                        {{ userInfo.nickName }}
                      </div>
                    </el-col>
                    <el-col :xs="24" :sm="16" class="personal-item mb6">
                      <div class="personal-item-label">身份：</div>
                      <div class="personal-item-value">
                        {{ userRoleName }}
                      </div>
                    </el-col>
                  </el-row>
                </el-col>
                <el-col :span="24">
                  <el-row>
                    <el-col :xs="24" :sm="8" class="personal-item mb6">
                      <div class="personal-item-label">所属部门：</div>
                      <div class="personal-item-value">
                        <div class="pull-right" v-if="userInfo.dept">
                          {{ userInfo.dept.deptName }} / {{ postGroup }}
                        </div>
                      </div>
                    </el-col>
                    <el-col :xs="24" :sm="16" class="personal-item mb6">
                      <div class="personal-item-label">手机号码：</div>
                      <div class="personal-item-value">
                        {{ userInfo.phonenumber }}
                      </div>
                    </el-col>
                  </el-row>
                </el-col>
                <el-col :span="24">
                  <el-row>
                    <el-col :xs="24" :sm="8" class="personal-item mb6">
                      <div class="personal-item-label">登录IP：</div>
                      <div class="personal-item-value">
                        {{ userInfo.loginIp }}
                      </div>
                    </el-col>
                    <el-col :xs="24" :sm="16" class="personal-item mb6">
                      <div class="personal-item-label">登录时间：</div>
                      <div class="personal-item-value">
                        {{ userInfo.loginDate }}
                      </div>
                    </el-col>
                  </el-row>
                </el-col>
              </el-row>
            </div>
          </div>
        </el-card>
      </el-col>

      <!-- 消息通知 -->
      <el-col :xs="24" :sm="8" class="pl15 personal-info">
        <el-card shadow="hover">
          <template #header>
            <span>消息通知</span>
            <span class="personal-info-more">更多</span>
          </template>
          <div class="personal-info-box">
            <ul class="personal-info-ul">
              <li
                v-for="(v, k) in newsInfoList"
                :key="k"
                class="personal-info-li"
              >
                <a
                  :href="v.link"
                  target="_block"
                  class="personal-info-li-title"
                  >{{ v.title }}</a
                >
              </li>
            </ul>
          </div>
        </el-card>
      </el-col>

      <!-- 个人信息 -->
      <!-- <el-col :span="8" :xs="24">
        <el-card class="box-card">
          <div class="header clearfix">
            <span>个人信息</span>
          </div>

          <div>
            <div class="text-center">
              <userAvatar :user="userInfo" />
            </div>
            <ul class="list-group list-group-striped">
              <li class="list-group-item">
                <svg-icon icon-class="user" />用户名称
                <div class="pull-right">{{ userInfo.userName }}</div>
              </li>
              <li class="list-group-item">
                <svg-icon icon-class="phone" />手机号码
                <div class="pull-right">{{ userInfo.phonenumber }}</div>
              </li>
              <li class="list-group-item">
                <svg-icon icon-class="email" />用户邮箱
                <div class="pull-right">{{ userInfo.email }}</div>
              </li>
              <li class="list-group-item">
                <svg-icon icon-class="tree" />所属部门
                <div class="pull-right" v-if="userInfo.dept">
                  {{ userInfo.dept.deptName }} / {{ postGroup }}
                </div>
              </li>
              <li class="list-group-item">
                <svg-icon icon-class="peoples" />所属角色
                <div class="pull-right">{{ roleGroup }}</div>
              </li>
              <li class="list-group-item">
                <svg-icon icon-class="date" />创建日期
                <div class="pull-right">{{ userInfo.createTime }}</div>
              </li>
            </ul>
          </div>
        </el-card>
         </el-col>
         -->

      <!-- <el-card shadow="hover" class="mt15" header="营销推荐">
          <el-row :gutter="15" class="personal-recommend-row">
            <el-col
              :sm="6"
              v-for="(v, k) in recommendList"
              :key="k"
              class="personal-recommend-col"
            >
              <div
                class="personal-recommend"
                :style="{ 'background-color': v.bg }"
              >
                <i :class="v.icon" :style="{ color: v.iconColor }"></i>
                <div class="personal-recommend-auto">
                  <div>{{ v.title }}</div>
                  <div class="personal-recommend-msg">{{ v.msg }}</div>
                </div>
              </div>
            </el-col>
          </el-row>
        </el-card> -->

      <!-- 更新信息 -->
      <el-col :span="24">
        <el-card shadow="hover" class="mt15 personal-edit" header="更新信息">
          <div class="personal-edit-title">基本信息</div>
          <el-form
            :model="personalForm"
            size="small"
            label-width="80px"
            class="mt35 mb35"
          >
            <el-row :gutter="35">
              <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4" class="mb20">
                <el-form-item label="昵称">
                  <el-input
                    v-model="personalForm.nickName"
                    placeholder="请输入昵称"
                    clearable
                  ></el-input>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4" class="mb20">
                <el-form-item label="用户名称">
                  <el-input
                    v-model="personalForm.userName"
                    placeholder="请输入用户名称"
                    clearable
                  ></el-input>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4" class="mb20">
                <el-form-item label="部门" prop="deptId">
                  <el-cascader
                    v-model="personalForm.deptId"
                    :options="deptOptions"
                    :props="{
                      label: 'label',
                      value: 'id',
                      checkStrictly: true,
                      emitPath: false,
                    }"
                    class="w100"
                    clearable
                    filterable
                    placeholder="请选择归属部门"
                    :show-all-levels="false"
                  ></el-cascader>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4" class="mb20">
                <el-form-item label="邮箱">
                  <el-input
                    v-model="personalForm.email"
                    placeholder="请输入邮箱"
                    clearable
                  ></el-input>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4" class="mb20">
                <el-form-item label="状态">
                  <el-radio-group v-model="personalForm.status">
                    <el-radio
                      v-for="dict in statusOptions"
                      :key="dict.dictValue"
                      :label="dict.dictValue"
                      >{{ dict.dictLabel }}
                    </el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4" class="mb20">
                <el-form-item label="职业">
                  <el-select
                    v-model="personalForm.occupation"
                    placeholder="请选择职业"
                    clearable
                    class="w100"
                  >
                    <el-option
                      label="计算机 / 互联网 / 通信"
                      value="1"
                    ></el-option>
                    <el-option label="生产 / 工艺 / 制造" value="2"></el-option>
                    <el-option label="医疗 / 护理 / 制药" value="3"></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4" class="mb20">
                <el-form-item label="手机">
                  <el-input
                    v-model="personalForm.phonenumber"
                    placeholder="请输入手机"
                    clearable
                  ></el-input>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4" class="mb20">
                <el-form-item label="性别">
                  <el-select
                    v-model="personalForm.sex"
                    placeholder="请选择"
                    class="w100"
                  >
                    <el-option
                      v-for="dict in sexOptions"
                      :key="dict.dictValue"
                      :label="dict.dictLabel"
                      :value="dict.dictValue"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                <el-form-item>
                  <el-button type="primary" icon="el-icon-position"
                    >更新个人信息</el-button
                  >
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
          <div class="personal-edit-title mb15">账号安全</div>
          <div class="personal-edit-safe-box">
            <div class="personal-edit-safe-item">
              <div class="personal-edit-safe-item-left">
                <div class="personal-edit-safe-item-left-label">账户密码</div>
                <div class="personal-edit-safe-item-left-value">
                  当前密码强度：强
                </div>
              </div>
              <div class="personal-edit-safe-item-right">
                <el-button type="text">立即修改</el-button>
              </div>
            </div>
          </div>
          <div class="personal-edit-safe-box">
            <div class="personal-edit-safe-item">
              <div class="personal-edit-safe-item-left">
                <div class="personal-edit-safe-item-left-label">密保手机</div>
                <div class="personal-edit-safe-item-left-value">
                  已绑定手机：177****5851
                </div>
              </div>
              <div class="personal-edit-safe-item-right">
                <el-button type="text">立即修改</el-button>
              </div>
            </div>
          </div>
          <div class="personal-edit-safe-box">
            <div class="personal-edit-safe-item">
              <div class="personal-edit-safe-item-left">
                <div class="personal-edit-safe-item-left-label">密保问题</div>
                <div class="personal-edit-safe-item-left-value">
                  已设置密保问题，账号安全大幅度提升
                </div>
              </div>
              <div class="personal-edit-safe-item-right">
                <el-button type="text">立即设置</el-button>
              </div>
            </div>
          </div>
          <div class="personal-edit-safe-box">
            <div class="personal-edit-safe-item">
              <div class="personal-edit-safe-item-left">
                <div class="personal-edit-safe-item-left-label">绑定QQ</div>
                <div class="personal-edit-safe-item-left-value">
                  已绑定QQ：102****60
                </div>
              </div>
              <div class="personal-edit-safe-item-right">
                <el-button type="text">立即设置</el-button>
              </div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script lang="ts">
import { toRefs, reactive, computed, onMounted } from "vue";
import { formatAxis } from "@/utils/formatTime";
import { newsInfoList, recommendList } from "./mock";
import { useStore } from "@/store/index";
import { treeselect } from "@/api/system/dept";
import { getDicts } from "@/api/system/dict/data";
import userAvatar from "./component/userAvatar.vue";
export default {
  name: "personal",
  components: { userAvatar },
  setup() {
    const store = useStore();
    const state = reactive({
      userInfo: store.state.userInfos.userInfos,
      userRoleName: "",
      newsInfoList,
      recommendList,
      // 角色选项
      roleOptions: [],
      // 状态数据字典
      statusOptions: [],
      // 性别状态字典
      sexOptions: [],
      // 部门树选项
      deptOptions: undefined,
      personalForm: {
        name: "",
        email: "",
        autograph: "",
        occupation: "",
        phone: "",
        sex: "",
      },
      postGroup: {},
      roleGroup: {},
    });
    // 当前时间提示语
    const currentTime = computed(() => {
      return formatAxis(new Date());
    });
    /** 查询部门下拉树结构 */
    const getTreeselect = async () => {
      treeselect().then((response) => {
        state.deptOptions = response.data;
      });
    };
    const slogan = computed(() => {
      const i = Math.ceil(Math.random() * 10);
      const sloganList = [
        "千古风流今在此,万里功名莫放休。",
        "岂能尽如人意,但求不愧我心。",
        "时人不识凌云木,直待凌云始道高。",
        "乘风破浪会有时,直挂云帆济沧海。",
        "莫愁前路无知己,天下谁人不识君。",
        "路漫漫其修远兮,吾将上下而求索。",
        "宝剑锋从磨砺出，梅花香自苦寒来。",
        "有花堪折直须折，莫待无花空折枝。",
        "我自横刀向天笑，去留肝胆两昆仑。",
        "春蚕到死丝方尽，蜡炬成灰泪始干。",
      ];
      return sloganList[i];
    });
    // 页面加载时
    onMounted(() => {
      //获取角色的名称
      const roles = state.userInfo.roles;
      let rolesStr = "";
      for (let i = 0; i < roles.length; i++) {
        if (i == 0) rolesStr = rolesStr + roles[i].roleName;
        else rolesStr = rolesStr + "," + roles[i].roleName;
      }
      state.userRoleName = rolesStr;
      state.personalForm = state.userInfo;

      getTreeselect();

      getDicts("sys_normal_disable").then((response) => {
        state.statusOptions = response.data;
      });
      getDicts("sys_user_sex").then((response) => {
        state.sexOptions = response.data;
      });
    });

    return {
      currentTime,
      slogan,
      ...toRefs(state),
    };
  },
};
</script>

<style scoped lang="scss">
@import "../../assets/style/mixins/mixins.scss";

.personal {
  .personal-user {
    height: 130px;
    display: flex;
    align-items: center;
    .personal-user-left {
      width: 150px;
      height: 130px;
      border-radius: 3px;
      &:deep(.el-upload) {
        height: 100%;
      }
      .personal-user-left-upload {
        img {
          width: 100%;
          height: 100%;
          border-radius: 3px;
        }
        &:hover {
          img {
            animation: logoAnimation 0.3s ease-in-out;
          }
        }
      }
    }
    .personal-user-right {
      flex: 1;
      padding: 0 15px;
      .personal-title {
        font-size: 18px;
        @include text-ellipsis(1);
      }
      .personal-item {
        display: flex;
        align-items: center;
        font-size: 13px;
        .personal-item-label {
          color: gray;
          @include text-ellipsis(1);
        }
        .personal-item-value {
          @include text-ellipsis(1);
        }
      }
    }
  }
  .personal-info {
    .personal-info-more {
      float: right;
      color: gray;
      font-size: 13px;
      &:hover {
        color: var(--color-primary);
        cursor: pointer;
      }
    }
    .personal-info-box {
      height: 130px;
      overflow: hidden;
      .personal-info-ul {
        list-style: none;
        .personal-info-li {
          font-size: 13px;
          padding-bottom: 10px;
          .personal-info-li-title {
            display: inline-block;
            @include text-ellipsis(1);
            color: grey;
            text-decoration: none;
          }
          & a:hover {
            color: var(--color-primary);
            cursor: pointer;
          }
        }
      }
    }
  }
  .personal-recommend-row {
    .personal-recommend-col {
      .personal-recommend {
        position: relative;
        height: 100px;
        color: #ffffff;
        border-radius: 3px;
        overflow: hidden;
        cursor: pointer;
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
        .personal-recommend-auto {
          padding: 15px;
          position: absolute;
          left: 0;
          top: 5%;
          .personal-recommend-msg {
            font-size: 12px;
            margin-top: 10px;
          }
        }
      }
    }
  }
  .personal-edit {
    .personal-edit-title {
      position: relative;
      padding-left: 10px;
      color: #606266;
      &::after {
        content: "";
        width: 2px;
        height: 10px;
        position: absolute;
        left: 0;
        top: 50%;
        transform: translateY(-50%);
        background: var(--color-primary);
      }
    }
    .personal-edit-safe-box {
      border-bottom: 1px solid #ebeef5;
      padding: 15px 0;
      .personal-edit-safe-item {
        width: 100%;
        display: flex;
        align-items: center;
        justify-content: space-between;
        .personal-edit-safe-item-left {
          flex: 1;
          overflow: hidden;
          .personal-edit-safe-item-left-label {
            color: #606266;
            margin-bottom: 5px;
          }
          .personal-edit-safe-item-left-value {
            color: gray;
            @include text-ellipsis(1);
            margin-right: 15px;
          }
        }
      }
      &:last-of-type {
        padding-bottom: 0;
        border-bottom: none;
      }
    }
  }
}
</style>
