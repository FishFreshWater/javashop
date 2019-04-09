<template>
  <div class="page">
    <header class="header">
      <div class="wrap clearfix">
        <a class="logo" href="/" title="esb"></a>
      </div>
    </header>
    <div class="main">
      <div class="slogan">
        <h3 class="slogan__h3">助力企业客户迎接互联网时代</h3>
        <p class="slogan__p">开源微信项目venice-shop，https://gitee.com/xunli/venice-shop</p>
        <p class="slogan__p">spring-boot+vue</p>
        <p class="slogan__p">代码干净简洁，可读性高。打造高颜值，高质量的开源项目。</p>
      </div>
      <div class="login">
        <h3 class="login__header">
          <span class="login__cap">用户登录</span>
          <div class="login__msg">{{errMsg}}</div>
        </h3>
        <form class="login__frm" @submit.prevent="login">
          <div class="login__frmGrp">
            <input class="login__inp" v-model="dataForm.account" placeholder="用户账号" type="text" />
          </div>
          <div class="login__frmGrp">
            <input class="login__inp" v-model="dataForm.password" placeholder="登录密码" type="password" />
          </div>
          <div class="login__frmGrp clearfix">
            <input style="width: 208px;float: left;" class="login__inp" v-model="dataForm.verification" placeholder="输入验证码" type="text" />
            <img style="float: right;" class="login__pin" :src="captchaPath" @click="getcaptcha" alt="验证码">
          </div>
          <div class="login__frmGrp">
            <button type="submit" :loading="isLoging" class="login__sub">登录</button>
          </div>
        </form>
        <div class="login__footer">
          <router-link class="text--muted fr" to="forgetpwd">忘记密码</router-link>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { getUUID } from '@/utils'
export default {
  data() {
    return {
      isLoging: false,
      errMsg: "",
      captchaPath:'',
      dataForm: {
        account: "",
        password: "",
        verification: ""
      }
    };
  },
  created(){
    this.getcaptcha();
  },
  methods: {
    login() {
      console.log('this---1',this)
      if (!this.dataForm.account) {
        this.errMsg = "请您输入用户账号";
        return false;
      }
      if (!this.dataForm.password) {
        this.errMsg = "请您输入登录密码";
        return false;
      }
      if (!this.dataForm.verification) {
        this.errMsg = "请您输入验证码";
        return false;
      }
      this.isLoging = true;
      this.$http({
        url: this.$http.adornUrl('/sys/login'),
        method: 'post',
        data: this.$http.adornData({
          uuid: localStorage.getItem('uuid'),
          username: this.dataForm.account,
          password: this.dataForm.password,
          captcha: this.dataForm.verification
        })
      }).then(({data}) => {
        if (data && data.code === 0) {
          this.errMsg = "";
          this.$cookie.set("token", data.token);
          this.$cookie.set("appid", '798fcb91187549a5b3b9338b0e8d9485');
          this.$router.replace({ name: "system/home/home.html" });
        } else {
          this.getcaptcha();
          this.$message.error(data.msg)
        }
        this.isLoging = false;
      })
    },
    getcaptcha() {
      this.dataForm.uuid = getUUID()
      localStorage.setItem('uuid',this.dataForm.uuid);
      this.captchaPath = this.$http.adornUrl(`/captcha.jpg?uuid=${this.dataForm.uuid}`)
    }
  }
};
</script>
<style scoped lang="scss">
@media (min-width: 1024px) {
  .wrap {
    width: 960px;
  }
  .main {
    width: 960px;
    margin-left: -480px;
  }
}
@media (min-width: 1300px) {
  .wrap {
    width: 1080px;
  }
  .main {
    width: 1080px;
    margin-left: -540px;
  }
}
@media (min-width: 1600px) {
  .wrap {
    width: 1300px;
  }
  .main {
    width: 1300px;
    margin-left: -650px;
  }
}
.wrap {
  margin: 0 auto;
}
.page {
  background: url(~@/assets/images/login-bg.jpg) no-repeat center top #212425;
  background-size: auto 100%;
  height: 100%;
  width: 100%;
  position: relative;
}
.header {
  width: 100%;
  height: 99px;
  line-height: 99px;
  background: none;
  border-bottom: 1px solid #646667;
}
.main {
  position: absolute;
  padding-top: 100px;
  height: 100%;
  left: 50%;
  top: 0;
  box-sizing: border-box;
}
.logo {
  margin-top: 31px;
  float: left;
  height: 37px;
  width: 104px;
  background: url(~@/assets/images/logo--white.png) no-repeat;
}
.slogan {
  position: absolute;
  top: 50%;
  margin-top: -110px;
  left: 20px;
  color: #fff;
}
.slogan__h3 {
  font-size: 28px;
  margin: 30px 0;
}
.slogan__p {
  margin-bottom: 25px;
  font-size: 16px;
  position: relative;
  line-height: 22px;
  padding-left: 16px;
  &:before {
    content: "";
    position: absolute;
    width: 5px;
    height: 5px;
    border: 1px solid #fff;
    left: 0;
    top: 8px;
    border-radius: 100%;
  }
}
.login {
  position: absolute;
  top: 50%;
  margin-top: -130px;
  right: 30px;
  height: 360px;
  width: 408px;
  box-sizing: border-box;
  background: #fff;
  border-radius: 5px;
  padding: 25px 30px;
}
.login__header {
  line-height: 38px;
  margin: 0;
  font-weight: normal;
}
.login__cap {
  font-size: 20px;
  color: #1b1e1f;
}
.login__msg {
  line-height: 38px;
  float: right;
  color: red;
  font-size: 14px;
}
.login__frm {
  width: 100%;
}
.login__frmGrp {
  width: 100%;
  margin-top: 17px;
}
.login__inp {
  height: 39px;
  line-height: 37px;
  border: 1px solid #d7d7d7;
  padding: 0 12px;
  width: 100%;
  box-sizing: border-box;
}
.login__pin {
  background: #fff;
  color: #878787;
  border: 1px solid #d7d7d7;
  text-align: center;
  width: 130px;
  height: 39px;
  cursor: pointer;
  &[disabled] {
    background: #f3f3f3;
    cursor: not-allowed;
  }
}
.login__sub {
  transition-duration: 0.3s;
  background: #108cee;
  line-height: 37px;
  border: 0 none;
  color: #fff;
  font-size: 18px;
  width: 100%;
  display: block;
  cursor: pointer;
  &:hover {
    background: darken($color: #108cee, $amount: 10%);
  }
  &[disabled] {
    background: lighten($color: #108cee, $amount: 10%);
    cursor: not-allowed;
  }
}
.login__footer {
  margin-top: 15px;
}
</style>
