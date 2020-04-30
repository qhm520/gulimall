<template>
  <div class="login-container">
    <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form" autocomplete="on"
             label-position="left" @keyup.enter.native="handleLogin" status-icon>

      <div class="title-container">

        <h3 class="title"> <img v-if="logo" :src="logo" class="sidebar-logo">&nbsp;&nbsp;&nbsp;&nbsp;咕鲤商城</h3>
      </div>

      <el-form-item prop="username">
        <span class="svg-container">
           <icon-svg name="user" class="user"/>
        </span>
        <el-input
          ref="username"
          v-model="loginForm.username"
          placeholder="帐号"
          name="username"
          type="text"
          tabindex="1"
          autocomplete="on"
        />
      </el-form-item>

      <el-form-item prop="password">
        <span class="svg-container">
          <icon-svg name="password" />
        </span>
        <el-input
          :key="passwordType"
          ref="password"
          v-model="loginForm.password"
          :type="passwordType"
          placeholder="密码"
          name="password"
          tabindex="2"
          autocomplete="on"
          @keyup.native="checkCapslock"
          @blur="capsTooltip = false"
          @keyup.enter.native="handleLogin"
        />
        <span class="show-pwd" @click="showPwd">
          <icon-svg :name="passwordType === 'password' ? 'eye' : 'eye-open'"
                    :class="passwordType === 'password' ? 'eye' : 'eye-open'"/>
        </span>
      </el-form-item>

      <el-form-item prop="imageCode">
        <el-row :gutter="20">
          <el-col :span="18">
            <span class="svg-container">
            <icon-svg name="check"/>
          </span>
            <el-input
              ref="imageCode"
              v-model="loginForm.imageCode"
              placeholder="验证码"
              name="imageCode"
              type="text"
              tabindex="3"
              autocomplete="on"
            />
          </el-col>
          <el-col :span="6" class="login-imageCode">
            <img :src="imageCodePath" @click="getImageCode()" alt="">
          </el-col>
        </el-row>
      </el-form-item>
      <el-button :loading="loading" type="primary" style="width:100%;margin-bottom:30px;"
                 @click.native.prevent="handleLogin">登录
      </el-button>
    </el-form>
  </div>
</template>

<script>
  import { getUUID } from '@/utils'
  export default {
    name: 'Login',
    components: {},
    data () {
      const validateUsername = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('帐号不能为空'))
        } else {
          callback()
        }
      }
      const validatePassword = (rule, value, callback) => {
        if (value.length <= 0) {
          callback(new Error('密码不能为空'))
        } else {
          callback()
        }
      }

      const validateImageCode = (rule, value, callback) => {
        if (value.length <= 0) {
          callback(new Error('验证码不能为空'))
        } else {
          callback()
        }
      }
      return {
        loginForm: {
          username: '',
          password: '',
          imageCode: '',
          uuid: ''
        },
        loginRules: {
          username: [{required: true, trigger: 'blur', validator: validateUsername}],
          password: [{required: true, trigger: 'blur', validator: validatePassword}],
          imageCode: [{required: true, trigger: 'blur', validator: validateImageCode}]
        },
        passwordType: 'password',
        capsTooltip: false,
        loading: false,
        showDialog: false,
        redirect: undefined,
        otherQuery: {},
        imageCodePath: '',
        logo: '../../static/img/guli.png'
      }
    },
    created () {
      this.getImageCode()
    },
    mounted () {
      if (this.loginForm.username === '') {
        this.$refs.username.focus()
      } else if (this.loginForm.password === '') {
        this.$refs.password.focus()
      } else if (this.loginForm.imageCode === '') {
        this.$refs.imageCode.focus()
      }
    },
    destroyed () {
      // window.removeEventListener('storage', this.afterQRScan)
    },
    methods: {
      checkCapslock (e) {
        const {key} = e
        this.capsTooltip = key && key.length === 1 && (key >= 'A' && key <= 'Z')
      },
      showPwd () {
        if (this.passwordType === 'password') {
          this.passwordType = ''
        } else {
          this.passwordType = 'password'
        }
        this.$nextTick(() => {
          this.$refs.password.focus()
        })
      },
      handleLogin () {
        this.$refs['loginForm'].validate((valid) => {
          if (valid) {
            sessionStorage.clear()
            this.$http({
              url: this.$http.adornUrl('/authentication/login'),
              method: 'post',
              data: this.$http.adornData({
                'username': this.loginForm.username,
                'password': this.loginForm.password,
                'uuid': this.loginForm.uuid,
                'imageCode': this.loginForm.imageCode
              })
            }).then(({data}) => {
              console.log(data)
              if (data && data.code === 0) {
                sessionStorage.setItem('access_token', data.token.access_token)
                sessionStorage.setItem('refresh_token', data.token.refresh_token)
                sessionStorage.setItem('expires_in', data.token.expires_in)
                this.$router.replace({ name: 'home' })
              } else {
                this.getImageCode()
                this.$message.error(data.msg)
              }
            }).catch(error => {
              console.log(error)
            })
          }
        })
      },
      // 获取验证码
      getImageCode () {
        this.loginForm.uuid = getUUID()
        this.imageCodePath = this.$http.adornUrl(`/code/image?uuid=${this.loginForm.uuid}`)
      }
    }
  }
</script>

<style lang="scss">
  /* 修复input 背景不协调 和光标变色 */
  /* Detail see https://github.com/PanJiaChen/vue-element-admin/pull/927 */

  $bg: #283443;
  $light_gray: #fff;
  $cursor: #fff;

  @supports (-webkit-mask: none) and (not (cater-color: $cursor)) {
    .login-container .el-input input {
      color: $cursor;
    }
  }

  /* reset element-ui css */
  .login-container {
    height: 100%;
    position: fixed;

    .el-input {
      display: inline-block;
      height: 47px;
      width: 85%;

      input {
        background: transparent;
        border: 0px;
        -webkit-appearance: none;
        border-radius: 0px;
        padding: 12px 5px 12px 15px;
        color: $light_gray;
        height: 47px;
        caret-color: $cursor;

        &:-webkit-autofill {
          box-shadow: 0 0 0px 1000px $bg inset !important;
          -webkit-text-fill-color: $cursor !important;
        }
      }
    }

    .el-form-item {
      border: 1px solid rgba(255, 255, 255, 0.1);
      background: rgba(0, 0, 0, 0.1);
      border-radius: 5px;
      color: #454545;
    }
  }
</style>

<style lang="scss" scoped>
  $bg: #2d3a4b;
  $dark_gray: #889aa4;
  $light_gray: #eee;

  .login-container {
    min-height: 100%;
    width: 100%;
    height: 100%;
    background-color: $bg;
    overflow: hidden;
    background-image: url("../../../../static/img/gulimall.png");
    background-repeat: no-repeat;
    background-size: 100% 100%;

    .login-form {
      position: relative;
      width: 520px;
      max-width: 100%;
      padding: 160px 35px 0;
      margin: 0 auto;
      overflow: hidden;
    }

    .tips {
      font-size: 14px;
      color: #fff;
      margin-bottom: 10px;

      span {
        &:first-of-type {
          margin-right: 16px;
        }
      }
    }

    .svg-container {
      padding: 6px 5px 6px 15px;
      color: $dark_gray;
      vertical-align: middle;
      width: 30px;
      display: inline-block;
    }

    .title-container {
      position: relative;

      .title {
        font-size: 26px;
        color: $light_gray;
        margin: 0px auto 40px auto;
        text-align: center;
        font-weight: bold;
        .sidebar-logo {
          width: 50px;
          height: 50px;
          margin-right: 0px;
        }
      }
    }

    .show-pwd {
      position: absolute;
      right: 10px;
      top: 7px;
      font-size: 16px;
      color: $dark_gray;
      cursor: pointer;
      user-select: none;
    }

    .thirdparty-button {
      position: absolute;
      right: 0;
      bottom: 6px;
    }

    @media only screen and (max-width: 470px) {
      .thirdparty-button {
        display: none;
      }
    }
  }
</style>
