<template>
  <div class="mod-login-log">
    <el-form ref="loginLogCriteria" :inline="true" :model="loginLogCriteria" @keyup.enter.native="query">
      <el-form-item label="用户名" prop="username">
        <el-input v-model="loginLogCriteria.username" placeholder="用户名" clearable></el-input>
      </el-form-item>
      <el-form-item label="用户操作" prop="operation">
        <el-input v-model="loginLogCriteria.operation" placeholder="用户操作" clearable></el-input>
      </el-form-item>
      <el-form-item label="IP地址" prop="ip">
        <el-input v-model="loginLogCriteria.ip" placeholder="IP地址" clearable></el-input>
      </el-form-item>
      <el-form-item label="创建时间" prop="createDate">
        <el-date-picker
          v-model="loginLogCriteria.createDate"
          type="datetimerange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期">
        </el-date-picker>
      </el-form-item>
      <!--查询 和 重置 -->
      <gulimall-search :search="query" :reset="reset"></gulimall-search>
    </el-form>
    <gulimall-operation>
      <el-button  type="danger" @click="deleteHandle()" :disabled="tableSelectData.length <= 0"> <icon-svg name="delete"/>&nbsp;批量删除</el-button>
    </gulimall-operation>
    <gulimall-table>
      <el-table-column
        type="selection"
        header-align="center"
        align="center"
        width="50">
      </el-table-column>
      <el-table-column
        label="序号"
        align="center"
        width="70px">
        <template slot-scope="scope">
          {{scope.$index+1}}
        </template>
      </el-table-column>
      <el-table-column
        prop="id"
        header-align="center"
        align="center"
        width="80"
        label="ID">
      </el-table-column>
      <el-table-column
        prop="username"
        header-align="center"
        align="center"
        width="100"
        label="用户名">
      </el-table-column>
      <el-table-column
        prop="operation"
        header-align="center"
        align="center"
        width="100"
        label="用户操作">
      </el-table-column>
      <el-table-column
        prop="method"
        header-align="center"
        align="center"
        width="200"
        :show-overflow-tooltip="true"
        label="请求方法">
      </el-table-column>
      <el-table-column
        prop="params"
        header-align="center"
        align="center"
        :show-overflow-tooltip="true"
        label="请求参数">
      </el-table-column>
      <el-table-column
        prop="ip"
        header-align="center"
        align="center"
        width="150"
        label="IP地址">
      </el-table-column>
      <el-table-column
        prop="createDate"
        header-align="center"
        align="center"
        width="180"
        label="创建时间">
      </el-table-column>
      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="120"
        label="操作">
        <template slot-scope="scope">
          <el-button-group>
            <el-button type="danger" size="small" @click.stop="deleteHandle(scope.row.id)"><icon-svg name="delete"/>&nbsp;删除</el-button>
          </el-button-group>
        </template>
      </el-table-column>
    </gulimall-table>
  </div>
</template>

<script>
  import GulimallOperation from '../../components/GulimcallOperation/GulimallOperation'
  import GulimallSearch from '../../components/GulimallSearch/GulimallSearch'
  import GulimallTable from "../../components/GulimallTable/GulimallTable";
  import ScheduleDialog from "./ScheduleDialog";
  import {mapGetters} from "vuex";
  import {dateFormat} from "../../filters";

  export default {
    name: "LoginLog",
    data() {
      return {
        loginLogCriteria: {
          username: '',
          status: '',
          createDate: ['', '']
        }
      }
    },
    components: {
      GulimallOperation,
      GulimallSearch,
      ScheduleDialog,
      GulimallTable
    },
    activated() {
      this.query('init')
    },
    computed: {
      ...mapGetters({tableSelectData: 'tableSelectData'})
    },
    methods: {
      /**
       * 重置查询条件
       */
      reset() {
        this.$refs.loginLogCriteria.resetFields()
      },
      /**
       * 查询列表数据
       * @param type
       */
      query(type) {
        const {username, operation, ip, createDate} = this.loginLogCriteria
        let start = dateFormat(createDate[0])
        let end = dateFormat(createDate[1])
        this.$store.dispatch('query', {
          url: '/sys/login/list',
          type: type,
          formData: {
            'username': username,
            'operation': operation,
            'ip': ip,
            'createDateStart': start,
            'createDateEnd': end
          }
        });
      },
      // 删除
      deleteHandle (id) {
        let ids = id ? [id] : this.tableSelectData.map(item => {
          return item.id
        })
        this.$GulimallConfirm({
          content: `确定对[id=${ids.join(',')}]进行[<span style="color: red;display:inline;">${id ? '删除' : '批量删除'}</span>]操作?`
        }).then(res => {
          this.$http({
            url: this.$http.adornUrl('/sys/login/delete'),
            method: 'post',
            data: this.$http.adornData(ids, false)
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.$message({
                message: `删除登录日志[id=${ids.join(',')}]成功`,
                type: 'success',
                duration: 1500,
                onClose: () => {
                  this.query('init')
                }
              })
            } else {
              this.$message.error(data.msg)
            }
          })
        })
      }
    }
  }
</script>

<style scoped>

</style>
