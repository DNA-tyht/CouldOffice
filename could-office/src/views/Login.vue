<template>
  <div class="login">
    <el-form class="login-container" :rules="rules" ref="loginForm"
             :model="loginForm" label-width="80px" v-loading="loading">
      <h3 class="login-title">系统登录</h3>
      <el-form-item prop="username">
        <el-input type="text" autocomplete="false" v-model="loginForm.username" placeholder="请输入用户名"></el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input type="password" autocomplete="false" v-model="loginForm.password" placeholder="请输入密码"></el-input>
      </el-form-item>
      <el-form-item prop="code">
        <el-input type="text" autocomplete="false" v-model="loginForm.code" placeholder="点击图片更换验证码"
                  style="width: 250px;margin-right: 5px;"></el-input>
        <el-image class="icon" :src="captchaUrl" @click="updateCaptcha"></el-image><br/>
        <el-checkbox class="login-remember" v-model="checked">记住我</el-checkbox>
        <el-button type="primary" style="width: 100%" @click="clickLogin">登录</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  name: "Login",
  data() {
    return {
      loginForm: {
        username: "DNA",
        password: "123",
        code: "1234",
      },
      captchaUrl: "/captcha?time=" + new Date(),
      checked: true,
      rules: { //校验规则
        username: [{required: true, message: "请输入用户名", trigger: "blur"}],
        password: [{required: true, message: "请输入密码", trigger: "blur"}],
        code: [{required: true, message: "请输入验证码", trigger: "blur"}],
      },
      loading: false
    }
  },
  methods: {
    clickLogin() {
      this.$refs.loginForm.validate((valid) => {
        if (valid) {
          this.loading = true;
          this.postRequest("/login", this.loginForm).then(resp => {
            if (resp) {
              this.loading = false;
              //存储用户token
              const tokenStr = resp.obj.tokenHead + resp.obj.token;
              window.sessionStorage.setItem("tokenStr", tokenStr);
              //跳转到首页
              let path = this.$route.query.redirect;
              this.$router.replace("/home");
            }
          });
        } else {
          this.$message.error("请输入所有信息");
          return false;
        }
      });
    },
    updateCaptcha() {
      this.captchaUrl = "/captcha?time" + new Date();
    }
  }
}
</script>

<style>
.login-container {
  border-radius: 15px;
  background-clip: padding-box;
  margin: 180px auto;
  width: 350px;
  padding: 15px 35px 15px 35px;
  background: #fffff;
  border: 1px solid var(--color-background);
  box-shadow: 0 0 25px var(--color-background);
}
.login-title {
  text-align: center;
  margin-bottom: 10px;
}
.login-remember {
  text-align: left;
}
.el-form-item__content {
  margin-left: 0 !important;
}
.icon{
  width: 90px;
  vertical-align:middle;
}
</style>