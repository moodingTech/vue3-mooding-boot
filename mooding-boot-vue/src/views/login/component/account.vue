<template>
  <el-form
    class="login-content-form"
    :rules="loginRules"
    :model="loginForm"
    ref="loginFormRef"
  >
    <el-form-item prop="userName">
      <el-input
        type="text"
        placeholder="用户名 admin "
        prefix-icon="el-icon-user"
        v-model="loginForm.userName"
        clearable
        autocomplete="off"
      >
      </el-input>
    </el-form-item>
    <el-form-item prop="password">
      <el-input
        type="password"
        placeholder="密码：123456"
        prefix-icon="el-icon-lock"
        v-model="loginForm.password"
        autocomplete="off"
        show-password
      >
      </el-input>
    </el-form-item>
    <el-form-item prop="code">
      <el-row :gutter="15">
        <el-col :span="16">
          <el-input
            type="text"
            maxlength="4"
            placeholder="请输入验证码"
            prefix-icon="el-icon-position"
            v-model="loginForm.code"
            clearable
            @keypress.enter="onSignIn"
            autocomplete="off"
          ></el-input>
        </el-col>
        <el-col :span="8">
          <div class="login-content-code">
            <img
              class="login-content-code-img"
              :src="codeUrl"
              @click="getCode"
            />
          </div>
        </el-col>
      </el-row>
    </el-form-item>
    <el-form-item>
      <el-button
        type="primary"
        class="login-content-submit"
        round
        @click="onSignIn"
        :loading="loading.signIn"
      >
        <span>登 录</span>
      </el-button>
    </el-form-item>
  </el-form>
</template>

<script lang="ts">
import {
  toRefs,
  reactive,
  ref,
  unref,
  defineComponent,
  computed,
  onMounted,
} from "vue";
import { useRouter } from "vue-router";
import { ElMessage } from "element-plus";
import { useStore } from "@/store/index";
import { formatAxis } from "@/utils/formatTime";
import { getCodeImg } from "@/api/login";

export default defineComponent({
  name: "login",
  setup() {
    const store = useStore();
    const router = useRouter();
    const loginFormRef = ref<HTMLElement | null>(null);
    const state = reactive({
      loginForm: {
        userName: "admin",
        password: "123456",
        code: "1234",
        uuid: "",
      },
      codeUrl: "",
      loading: {
        signIn: false,
      },
      loginRules: {
        userName: [
          { required: true, trigger: "blur", message: "用户名不能为空" },
        ],
        password: [
          { required: true, trigger: "blur", message: "密码不能为空" },
        ],
        code: [
          { required: true, trigger: "change", message: "验证码不能为空" },
        ],
      },
    });
    //获取图片验证码
    const getCode = async () => {
      getCodeImg().then((res: any) => {
        state.codeUrl = "data:image/gif;base64," + res.data.img;
        state.loginForm.uuid = res.data.uuid;
      });
    };
    // 时间获取
    const currentTime = computed(() => {
      return formatAxis(new Date());
    });
    // 登录
    const onSignIn = () => {
      // state.loading.signIn = true
      const formWrap = unref(loginFormRef) as any;
      if (!formWrap) return;
      try {
        state.loading.signIn = true;
        formWrap.validate(async (valid: boolean) => {
          if (valid) {
            store
              .dispatch("userInfos/Login", state.loginForm)
              .then(() => {
                signInSuccess();
              })
              .catch(() => {
                state.loading.signIn = false;
                getCode();
              });
          } else {
            console.log("error submit!!");
            return false;
          }
        });
      } catch (err) {
        console.log(err);
      } finally {
        state.loading.signIn = false;
      }
    };

    // 登录成功后的跳转
    const signInSuccess = () => {
      // 初始化登录成功时间问候语
      let currentTimeInfo = currentTime.value;
      // 登录成功，跳到转首页
      router.push("/");
      // 登录成功提示
      setTimeout(() => {
        state.loading.signIn = true;
        const signInText = "欢迎回来！";
        ElMessage.success(`${currentTimeInfo}，${signInText}`);
        // 关闭 loading
      }, 300);
    };

    // 页面加载时
    onMounted(() => {
      getCode();
    });
    return {
      currentTime,
      loginFormRef,
      getCode,
      onSignIn,
      // codeUrl,
      ...toRefs(state),
    };
  },
});
</script>

<style scoped lang="scss">
.login-content-form {
  margin-top: 20px;

  .login-content-code {
    display: flex;
    align-items: center;
    justify-content: space-around;

    .login-content-code-img {
      width: 100%;
      height: 40px;
      line-height: 40px;
      background-color: #ffffff;
      //border: 1px solid rgb(220, 223, 230);
      color: #333;
      font-size: 16px;
      font-weight: 700;
      letter-spacing: 5px;
      text-indent: 5px;
      text-align: center;
      cursor: pointer;
      transition: all ease 0.2s;
      border-radius: 4px;
      user-select: none;

      &:hover {
        border-color: #c0c4cc;
        transition: all ease 0.2s;
      }
    }
  }

  .login-content-submit {
    width: 100%;
    letter-spacing: 2px;
    font-weight: 300;
    margin-top: 15px;
  }
}
</style>
