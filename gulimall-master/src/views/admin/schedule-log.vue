<template>
  <div class="mod-schedule-log">
    <el-form ref="queryCriteria" :inline="true" :model="queryCriteria" @keyup.enter.native="query">
      <el-form-item label="bean名称" prop="beanName">
        <el-input v-model="queryCriteria.beanName" placeholder="bean名称" clearable></el-input>
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
      <gulimall-search :search="query" :reset="reset"></gulimall-search>
    </el-form>
    <!--操作-->
    <gulimall-operation>
      <el-button v-if="isAuth('sys:schedule:delete')" type="danger" @click="deleteHandle()" :disabled="tableSelectData.length <= 0"><icon-svg name="delete"/>&nbsp;批量删除</el-button>
    </gulimall-operation>
    <!--列表-->
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
        prop="logId"
        header-align="center"
        align="center"
        width="80"
        label="日志ID">
      </el-table-column>
      <el-table-column
        prop="jobId"
        header-align="center"
        align="center"
        width="80"
        label="任务ID">
      </el-table-column>
      <el-table-column
        prop="beanName"
        header-align="center"
        align="center"
        label="bean名称">
      </el-table-column>
      <el-table-column
        prop="params"
        header-align="center"
        align="center"
        label="参数">
      </el-table-column>
      <el-table-column
        prop="status"
        header-align="center"
        align="center"
        label="状态">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status === 0" size="small">成功</el-tag>
          <el-tag v-else @click.native="showErrorInfo(scope.row.logId)" size="small" type="danger" style="cursor: pointer;">失败</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        prop="times"
        header-align="center"
        align="center"
        label="耗时(单位: 毫秒)">
      </el-table-column>
      <el-table-column
        prop="createTime"
        header-align="center"
        align="center"
        width="180"
        label="执行时间">
      </el-table-column>
      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="100"
        label="操作">
        <template slot-scope="scope">
          <el-button-group>
            <el-button v-if="isAuth('sys:schedule:delete')" type="danger" size="small" @click="deleteHandle(scope.row.logId)"><icon-svg name="delete"/>&nbsp;删除</el-button>
          </el-button-group>
        </template>
      </el-table-column>
    </gulimall-table>
  </div>

</template>

<script>
  import {dateFormat} from '../../filters'
  import GulimallOperation from '../../components/GulimcallOperation/GulimallOperation'
  import GulimallSearch from '../../components/GulimallSearch/GulimallSearch'
  import GulimallTable from '../../components/GulimallTable/GulimallTable'
  import {mapGetters} from 'vuex'

  export default {
    name: 'scheduleLog',
    data() {
      return {
        queryCriteria: {
          beanName: '',
          status: '',
          createTime: ['', '']
        },
        statusList: [{value: 0, label: '禁用'}, {value: 1, label: '正常'}],  // TODO 以后从字典中获取
        openDialog: false
      }
    },
    components: {
      GulimallOperation,
      GulimallSearch,
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
        const {beanName, status, createTime} = this.queryCriteria
        let start = dateFormat(createTime[0])
        let end = dateFormat(createTime[1])
        this.$store.dispatch('query', {
          url: '/sys/scheduleLog/list',
          type: type,
          formData: {
            'beanName': beanName,
            'status': status,
            'createTimeStart': start,
            'createTimeEnd': end
          }
        });
      },
      // 删除
      deleteHandle (id) {
        let ids = id ? [id] : this.tableSelectData.map(item => {
          return item.logId
        })
        this.$GulimallConfirm({
          content: `确定对[id=${ids.join(',')}]进行[<span style="color: red;display:inline;">${id ? '删除' : '批量删除'}</span>]操作?`
        }).then(res => {
          this.$http({
            url: this.$http.adornUrl('/sys/scheduleLog/delete'),
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
          }).catch(() => {})
        })
      },
      // 失败信息
      showErrorInfo (id) {
        this.$http({
          url: this.$http.adornUrl(`/sys/login/info/${id}`),
          method: 'get',
          params: this.$http.adornParams()
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.$alert(data.log.error)
          } else {
            this.$message.error(data.msg)
          }
        })
      }

    }
  }
</script>

<style scoped>

</style>
