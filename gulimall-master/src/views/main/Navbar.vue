<template>
  <nav class="site-navbar" :class="'site-navbar--' + navbarLayoutType">
    <div class="site-navbar__header">
      <h1 class="site-navbar__brand" @click="$router.push({ name: 'home' })">
        <a class="site-navbar__brand-lg" href="javascript:;">
          <img v-if="logo" :src="logo" class="sidebar-logo">&nbsp;&nbsp;&nbsp;&nbsp;{{ title }}</a>
        <a class="site-navbar__brand-mini" href="javascript:;">
          <img v-if="logo" :src="logo" class="sidebar-logo">
        </a>
      </h1>
    </div>

    <div class="site-navbar__body clearfix">
      <el-menu
        class="site-navbar__menu"
        mode="horizontal">
        <el-menu-item class="site-navbar__switch" index="0" @click="sidebarFold = !sidebarFold">
          <icon-svg name="fold"></icon-svg>
        </el-menu-item>
      </el-menu>
      <el-menu
        class="site-navbar__menu site-navbar__menu--right"
        mode="horizontal">
        <el-menu-item index="1" @click="messageHandler">
          <template slot="title">
            <el-badge :value="alert" :max="99">
              <icon-svg name="msgAlert" class="el-icon-setting"></icon-svg>
            </el-badge>
          </template>
        </el-menu-item>
        <el-menu-item class="site-navbar__avatar" index="2">
          <el-dropdown :show-timeout="0" placement="bottom">
            <span class="el-dropdown-link">
              <img src="../../../static/img/me.png" :alt="userName">{{ userName }}
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item>
                <div>
                  <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                    <img src="../../../static/img/me.png" style="width: 300px; height: 300px;" class="user-image"
                         alt="User Image">
                  </a>
                </div>
                <div style="text-align: center; color: #2D64B3; font-size: 20px;">
                  {{userName}}
                </div>
                <div style="text-align: center">
                  <el-button-group>
                    <el-button @click.native="updatePasswordHandle" type="warning">
                      <icon-svg name="editpwd" class="el-icon-setting"></icon-svg>&nbsp;修改密码
                    </el-button>
                    <el-button @click.native="logoutHandle" type="danger">
                      <icon-svg name="logout" class="el-icon-setting"></icon-svg>&nbsp;退出登录
                    </el-button>
                  </el-button-group>
                </div>
              </el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </el-menu-item>
      </el-menu>
    </div>
    <!-- 弹窗, 修改密码 -->
    <update-password-dialog v-if="updatePassowrdVisible" ref="updatePassowrd"></update-password-dialog>
  </nav>
</template>

<script>
  import UpdatePasswordDialog from './UpdatePasswordDialog'
  import {clearLoginInfo} from '@/utils'

  export default {
    name: 'Navbar',
    data() {
      return {
        updatePassowrdVisible: false,
        title: '谷粒商城',
        logo: '../static/img/guli.png',
        alert: 1000
      }
    },
    components: {
      UpdatePasswordDialog
    },
    computed: {
      navbarLayoutType: {
        get() {
          return this.$store.state.common.navbarLayoutType
        }
      },
      sidebarFold: {
        get() {
          return this.$store.state.common.sidebarFold
        },
        set(val) {
          this.$store.commit('common/updateSidebarFold', val)
        }
      },
      mainTabs: {
        get() {
          return this.$store.state.common.mainTabs
        },
        set(val) {
          this.$store.commit('common/updateMainTabs', val)
        }
      },
      userName: {
        get() {
          return this.$store.state.user.name
        }
      }
    },
    methods: {
      // 修改密码
      updatePasswordHandle() {
        this.updatePassowrdVisible = true
        this.$nextTick(() => {
          this.$refs.updatePassowrd.init()
        })
      },
      // 退出
      logoutHandle() {
        this.$GulimallConfirm({
          content: `确定进行[<span style="color: red;display:inline;">退出</span>]操作?`
        }).then(res => {
          this.$http({
            url: this.$http.adornUrl('/authentication/logout'),
            method: 'get'
          }).then(({data}) => {
            if (data && data.code === 0) {
              clearLoginInfo()
              this.$router.push({name: 'login'})
            }
          })
        })
      },
      // 处理消息
      messageHandler() {
        console.log('处理消息')
      }
    }
  }
</script>


<style lang="scss" scoped>
  .sidebar-logo {
    width: 30px;
    height: 30px;
    margin-right: 0px;
  }

  ul li {
    list-style-type: none;
  }
</style>
