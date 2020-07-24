<template>
  <div class="mod-schedule">
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
      <search-reset :search="query" :reset="reset"></search-reset>
    </el-form>
    <operation>
      <el-button v-if="isAuth('sys:schedule:save')" type="primary" @click="addOrUpdateHandle()"><icon-svg name="add"/>&nbsp;新增定时任务</el-button>
      <el-button v-if="isAuth('sys:schedule:delete')" type="danger" @click="deleteHandle()" :disabled="tableSelectData.length <= 0"><icon-svg name="delete"/>&nbsp;批量删除</el-button>
      <el-button v-if="isAuth('sys:schedule:pause')" type="info" @click="pauseHandle()" :disabled="tableSelectData.length <= 0"><icon-svg name="pause"/>&nbsp;批量暂停</el-button>
      <el-button v-if="isAuth('sys:schedule:resume')" type="warning" @click="resumeHandle()" :disabled="tableSelectData.length <= 0"><icon-svg name="resume"/>&nbsp;批量恢复</el-button>
      <el-button v-if="isAuth('sys:schedule:run')" type="danger" @click="runHandle()" :disabled="tableSelectData.length <= 0"><icon-svg name="run"/>&nbsp;批量立即执行</el-button>
      <el-button v-if="isAuth('sys:schedule:log')" type="success" @click="logHandle()"><icon-svg name="log"/>&nbsp;日志列表</el-button>
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
        prop="jobId"
        header-align="center"
        align="center"
        width="80"
        label="ID">
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
        prop="cronExpression"
        header-align="center"
        align="center"
        label="cron表达式">
      </el-table-column>
      <el-table-column
        prop="remark"
        header-align="center"
        align="center"
        label="备注">
      </el-table-column>
      <el-table-column
        prop="status"
        header-align="center"
        align="center"
        label="状态">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status === 0" size="small">正常</el-tag>
          <el-tag v-else size="small" type="danger">暂停</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="400"
        label="操作">
        <template slot-scope="scope">
          <el-button-group>
            <el-button v-if="isAuth('sys:schedule:update')" type="warning" size="small" @click="addOrUpdateHandle(scope.row.jobId)"><icon-svg name="edit"/>&nbsp;修改</el-button>
            <el-button v-if="isAuth('sys:schedule:delete')" type="danger" size="small" @click="deleteHandle(scope.row.jobId)"><icon-svg name="delete"/>&nbsp;删除</el-button>
            <el-button v-if="isAuth('sys:schedule:pause')" type="info" size="small" @click="pauseHandle(scope.row.jobId)"><icon-svg name="pause"/>&nbsp;暂停</el-button>
            <el-button v-if="isAuth('sys:schedule:resume')" type="warning" size="small" @click="resumeHandle(scope.row.jobId)"><icon-svg name="resume"/>&nbsp;恢复</el-button>
            <el-button v-if="isAuth('sys:schedule:run')" type="danger" size="small" @click="runHandle(scope.row.jobId)"><icon-svg name="run"/>&nbsp;立即执行</el-button>
          </el-button-group>
        </template>
      </el-table-column>
    </gulimall-table>

    <!-- 弹窗, 新增 / 修改 -->
    <schedule-dialog v-if="openDialog" ref="addOrUpdate" @refreshDataList="query"></schedule-dialog>
  </div>

</template>

<script>
  import {dateFormat} from '../../filters'
  import Operation from '../../components/Operation/Operation'
  import SearchReset from '../../components/Operation/SearchReset'
  import GulimallTable from '../../components/GulimallTable/GulimallTable'
  import ScheduleDialog from "./ScheduleDialog";
  import {mapGetters} from 'vuex'

  export default {
    name: 'schedule',
    data() {
      return {
        queryCriteria: {
          beanName: '',
          status: '',
          createTime: ['', '']
        },
        statusList: [{value: 1, label: '暂停'}, {value: 0, label: '正常'}],  // TODO 以后从字典中获取
        openDialog: false
      }
    },
    components: {
      Operation,
      SearchReset,
      GulimallTable,
      ScheduleDialog
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
          url: '/sys/schedule/list',
          type: type,
          formData: {
            'beanName': beanName,
            'status': status,
            'createTimeStart': start,
            'createTimeEnd': end
          }
        });
      },
      // 新增 / 修改
      addOrUpdateHandle (id) {
        this.openDialog = true
        this.$nextTick(() => {
          this.$refs.addOrUpdate.init(id)
        })
      },
      // 删除
      deleteHandle (id) {
        var ids = id ? [id] : this.tableSelectData.map(item => {
          return item.jobId
        })
        this.$confirm(`确定对[id=${ids.join(',')}]进行[${id ? '删除' : '批量删除'}]操作?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl('/sys/schedule/delete'),
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
      },
      // 暂停
      pauseHandle (id) {
        var ids = id ? [id] : this.tableSelectData.map(item => {
          return item.jobId
        })
        this.$confirm(`确定对[id=${ids.join(',')}]进行[${id ? '暂停' : '批量暂停'}]操作?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl('/sys/schedule/pause'),
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
      },
      // 恢复
      resumeHandle (id) {
        var ids = id ? [id] : this.tableSelectData.map(item => {
          return item.jobId
        })
        this.$confirm(`确定对[id=${ids.join(',')}]进行[${id ? '恢复' : '批量恢复'}]操作?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl('/sys/schedule/resume'),
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
      },
      // 立即执行
      runHandle (id) {
        var ids = id ? [id] : this.tableSelectData.map(item => {
          return item.jobId
        })
        this.$confirm(`确定对[id=${ids.join(',')}]进行[${id ? '立即执行' : '批量立即执行'}]操作?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl('/sys/schedule/run'),
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
      },
      // 日志列表
      logHandle () {
        this.logVisible = true
        this.$nextTick(() => {
          this.$refs.log.init()
        })
      }

    }
  }
</script>

<style scoped>

</style>
