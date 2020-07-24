<template>
  <div class="mod-user">
    <el-form ref="queryCriteria" :inline="true" :model="queryCriteria" @keyup.enter.native="query">
      <el-form-item label="用户名" prop="username">
        <el-input v-model="queryCriteria.username" placeholder="请输入用户名" clearable></el-input>
      </el-form-item>
      <el-form-item label="手机号" prop="mobile">
        <el-input v-model="queryCriteria.mobile" placeholder="请输入手机号" clearable></el-input>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryCriteria.status" placeholder="请选择">
          <el-option
            v-for="item in statusList"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="创建时间" prop="createTime">
        <el-date-picker
          v-model="queryCriteria.createTime"
          type="datetimerange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期">
        </el-date-picker>
      </el-form-item>
      <!--查询 和 重置 -->
      <search-reset :search="query" :reset="reset"></search-reset>
    </el-form>
    <operation>
      <div>
        <el-button v-if="isAuth('sys:user:save')" type="primary" @click="addOrUpdateHandle()">
          <icon-svg name="add"/>
          &nbsp;新增用户
        </el-button>
        <el-button v-if="isAuth('sys:user:delete')" type="danger" @click="deleteHandle()"
                   :disabled="tableSelectData.length <= 0">
          <icon-svg name="delete"/>
          &nbsp;批量删除
        </el-button>
      </div>
    </operation>
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
        prop="userId"
        header-align="center"
        align="center"
        width="80"
        label="ID">
      </el-table-column>
      <el-table-column
        prop="username"
        header-align="center"
        align="center"
        label="用户名">
      </el-table-column>
      <el-table-column
        prop="email"
        header-align="center"
        align="center"
        label="邮箱">
      </el-table-column>
      <el-table-column
        prop="mobile"
        header-align="center"
        align="center"
        label="手机号">
      </el-table-column>
      <el-table-column
        prop="status"
        header-align="center"
        align="center"
        label="状态">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status === 0" size="small" type="danger">禁用</el-tag>
          <el-tag v-else size="small">正常</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        prop="createTime"
        header-align="center"
        align="center"
        width="180"
        label="创建时间">
      </el-table-column>
      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="180"
        label="操作">
        <template slot-scope="scope">
          <el-button-group>
            <el-button v-if="isAuth('sys:user:update')" type="warning" size="small"
                       @click.stop="addOrUpdateHandle(scope.row.userId)">
              <icon-svg name="edit"/>
              &nbsp;修改
            </el-button>
            <el-button v-if="isAuth('sys:user:delete')" type="danger" size="small"
                       @click.stop="deleteHandle(scope.row.userId)">
              <icon-svg name="delete"/>
              &nbsp;删除
            </el-button>
          </el-button-group>
        </template>
      </el-table-column>
    </gulimall-table>
    <!-- 弹窗, 新增 / 修改 -->
    <user-dialog ref="userDialog" v-if="openDialog" @refreshDataList="query"></user-dialog>
  </div>
</template>

<script>
  import UserDialog from './UserDialog'
  import {dateFormat} from '../../filters'
  import Operation from '../../components/Operation/Operation'
  import SearchReset from '../../components/Operation/SearchReset'
  import GulimallTable from '../../components/GulimallTable/GulimallTable'
  import {mapGetters} from 'vuex'

  export default {
    name: 'user',
    data() {
      return {
        queryCriteria: {
          username: '',
          mobile: '',
          status: '',
          createTime: ['', '']
        },
        statusList: [{value: 0, label: '禁用'}, {value: 1, label: '正常'}],  // TODO 以后从字典中获取
        openDialog: false
      }
    },
    components: {
      UserDialog,
      Operation,
      SearchReset,
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
        this.$refs.queryCriteria.resetFields()
      },
      /**
       * 查询列表数据
       * @param type
       */
      query(type) {
        const {username, mobile, status, createTime} = this.queryCriteria
        let start = dateFormat(createTime[0])
        let end = dateFormat(createTime[1])
        this.$store.dispatch('query', {
          url: '/sys/user/list',
          type: type,
          formData: {
            'username': username,
            'mobile': mobile,
            'status': status,
            'createTimeStart': start,
            'createTimeEnd': end
          }
        });
      },
      // 新增 / 修改
      addOrUpdateHandle(id) {
        this.openDialog = true
        this.$nextTick(() => {
          this.$refs.userDialog.init(id)
        })
      },
      // 删除
      deleteHandle(id) {
        var userIds = id ? [id] : this.tableSelectData.map(item => {
          return item.userId
        })
        this.$confirm(`确定对[id=${userIds.join(',')}]进行[${id ? '删除' : '批量删除'}]操作?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl('/sys/user/delete'),
            method: 'post',
            data: this.$http.adornData(userIds, false)
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.$message({
                message: '操作成功',
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
        }).catch(() => {
        })
      }
    }
  }
</script>
