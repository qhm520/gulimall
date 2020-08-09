<template>
  <div class="mod-config">
    <div>
      <el-form ref="queryCriteria" :inline="true" :model="queryCriteria" @keyup.enter.native="query()">
        <el-form-item label="参数名" prop="paramKey">
          <el-input v-model="queryCriteria.paramKey" placeholder="参数名" clearable></el-input>
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
        <!--查询 和 重置 -->
        <search-reset :search="query" :reset="reset"></search-reset>
      </el-form>
      <operation>
        <el-button v-if="isAuth('sys:user:save')" type="primary" @click="addOrUpdateHandle()">
          <icon-svg name="add"/>
          &nbsp;新增参数
        </el-button>
        <el-button v-if="isAuth('sys:user:delete')" type="danger" @click="deleteHandle()"
                   :disabled="tableSelectData.length <= 0">
          <icon-svg name="delete"/>
          &nbsp;批量删除
        </el-button>
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
          prop="paramKey"
          header-align="center"
          align="center"
          label="参数名">
        </el-table-column>
        <el-table-column
          prop="paramValue"
          header-align="center"
          align="center"
          label="参数值">
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
            <el-tag v-if="scope.row.status === 0" size="small" type="danger">禁用</el-tag>
            <el-tag v-else size="small">正常</el-tag>
          </template>
        </el-table-column>
        <el-table-column
          fixed="right"
          header-align="center"
          align="center"
          width="180"
          label="操作">
          <template slot-scope="scope">
            <el-button-group>
              <el-button type="warning" size="small" @click.stop="addOrUpdateHandle(scope.row.id)">
                <icon-svg name="edit"/>
                修改
              </el-button>
              <el-button type="danger" size="small" @click.stop="deleteHandle(scope.row.id)">
                <icon-svg name="delete"/>
                删除
              </el-button>
            </el-button-group>
          </template>
        </el-table-column>
      </gulimall-table>

    </div>
    <!-- 弹窗, 新增 / 修改 -->
    <config-dialog v-if="openDialog" ref="configDialog" @refreshDataList="query"></config-dialog>
  </div>
</template>

<script>
  import ConfigDialog from './ConfigDialog'
  import Operation from '../../components/Operation/Operation'
  import SearchReset from '../../components/Operation/SearchReset'
  import GulimallTable from '../../components/GulimallTable/GulimallTable'
  import {mapGetters} from 'vuex'
  import {dateFormat} from "../../filters";

  export default {
    data() {
      return {
        queryCriteria: {
          paramKey: '',
          status: ''
        },
        statusList: [{value: 0, label: '禁用'}, {value: 1, label: '正常'}],  // TODO 以后从字典中获取
        openDialog: false
      }
    },
    components: {
      ConfigDialog,
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
        this.$store.dispatch('query', {
          url: '/sys/config/list',
          type: type,
          formData: {
            'paramKey': this.queryCriteria.paramKey,
            'status': this.queryCriteria.status
          }
        });
      },

      // 新增 / 修改
      addOrUpdateHandle(id) {
        this.openDialog = true
        this.$nextTick(() => {
          this.$refs.configDialog.init(id)
        })
      },
      // 删除
      deleteHandle(id) {
        var ids = id ? [id] : this.tableSelectData.map(item => {
          return item.id
        })
        this.$GulimallConfirm({
          content: `确定对[id=${ids.join(',')}]进行[<span style="color: red;display:inline;">${id ? '删除' : '批量删除'}</span>]操作?`
        }).then(res=>{
          this.$http({
            url: this.$http.adornUrl('/sys/config/delete'),
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
        })
      }
    }
  }
</script>

<style scoped>
</style>
