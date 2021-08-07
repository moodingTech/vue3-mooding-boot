<template>
  <el-form class="login-content-form">
    <el-form-item>
      <el-input
        type="text"
        placeholder="请输入手机号"
        prefix-icon="el-icon-user"
        v-model="ruleForm.userName"
        clearable
        autocomplete="off"
      >
      </el-input>
    </el-form-item>
    <el-form-item>
      <el-row :gutter="15">
        <el-col :span="16">
          <el-input
            type="text"
            maxlength="4"
            placeholder="请输入验证码"
            prefix-icon="el-icon-position"
            v-model="ruleForm.code"
            clearable
            autocomplete="off"
          ></el-input>
        </el-col>
        <el-col :span="8">
          <el-button @click="getSmsCode">获取验证码</el-button>
        </el-col>
      </el-row>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" class="login-content-submit" @click="onSignIn">
        <span>登 录</span>
      </el-button>
    </el-form-item>
  </el-form>
</template>

<script lang="ts">
import { toRefs, reactive, defineComponent } from "vue";
import { ElMessage } from "element-plus";
export default defineComponent({
  name: "login",
  setup() {
    const state = reactive({
      ruleForm: {
        userName: "",
        code: "",
      },
    });

    // 登录
    const onSignIn = () => {
      ElMessage.warning("演示环境不支持手机号码登陆");
    };
	// 获取手机验证码
    const getSmsCode = () => {
      ElMessage.warning("演示环境不支持获取短信验证码");
    };
    return {
		onSignIn,
		getSmsCode,
      ...toRefs(state),
    };
  },
});
</script>

<style scoped lang="scss">
.login-content-form {
  margin-top: 20px;
  .login-content-submit {
    width: 100%;
    letter-spacing: 2px;
    font-weight: 300;
    margin-top: 15px;
  }
}
</style>
