<template>
  <div class="mod-log">
    <el-form ref="queryCriteria" :inline="true" :model="queryCriteria" @keyup.enter.native="query">
      <el-form-item label="用户名" prop="username">
        <el-input v-model="queryCriteria.username" placeholder="用户名" clearable></el-input>
      </el-form-item>
      <el-form-item label="用户操作" prop="operation">
        <el-input v-model="queryCriteria.operation" placeholder="用户操作" clearable></el-input>
      </el-form-item>
      <el-form-item label="IP地址" prop="ip">
        <el-input v-model="queryCriteria.ip" placeholder="IP地址" clearable></el-input>
      </el-form-item>
      <el-form-item label="创建时间" prop="createDate">
        <el-date-picker
          v-model="queryCriteria.createDate"
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
        <el-button  type="danger" @click="deleteHandle()" :disabled="tableSelectData.length <= 0"> <icon-svg name="delete"/>&nbsp;批量删除</el-button>
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
        label="用户名">
      </el-table-column>
      <el-table-column
        prop="operation"
        header-align="center"
        align="center"
        label="用户操作">
      </el-table-column>
      <el-table-column
        prop="method"
        header-align="center"
        align="center"
        width="150"
        :show-overflow-tooltip="true"
        label="请求方法">
      </el-table-column>
      <el-table-column
        prop="params"
        header-align="center"
        align="center"
        width="150"
        :show-overflow-tooltip="true"
        label="请求参数">
      </el-table-column>
      <el-table-column
        prop="time"
        header-align="center"
        align="center"
        label="执行时长(毫秒)">
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
            <el-button type="danger" size="small" @click.stop="deleteHandle(scope.row.roleId)"><icon-svg name="delete"/>&nbsp;删除</el-button>
          </el-button-group>
        </template>
      </el-table-column>
    </gulimall-table>
  </div>
</template>

<script>
  import Operation from '../../components/Operation/Operation'
  import SearchReset from '../../components/Operation/SearchReset'
  import GulimallTable from '../../components/GulimallTable/GulimallTable'
  import {mapGetters} from 'vuex'
  import {dateFormat} from '../../filters'

  export default {
    name: 'log',
    components: {
      Operation,
      SearchReset,
      GulimallTable
    },
    data () {
      return {
        queryCriteria: {
          username: '',
          operation: '',
          ip: '',
          createDate: ['', '']
        },
        dataList: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        dataListLoading: false,
        selectionDataList: []
      }
    },
    created () {
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
        const {username, operation, ip, createDate} = this.queryCriteria
        let start = dateFormat(createDate[0])
        let end = dateFormat(createDate[1])
        this.$store.dispatch('query', {
          url: '/sys/log/list',
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
        this.$confirm(`确定对[id=${ids.join(',')}]进行[${id ? '删除' : '批量删除'}]操作?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl('/sys/log/delete'),
            method: 'post',
            data: this.$http.adornData(ids, false)
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
        }).catch(() => {})
      }
    }
  }
</script>
