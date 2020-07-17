<template>
  <div class="mod-role">
    <el-form ref="queryCriteria" :inline="true" :model="queryCriteria" @keyup.enter.native="query()">
      <el-form-item label="角色名称" prop="roleName">
        <el-input v-model="queryCriteria.roleName" placeholder="角色名称" clearable></el-input>
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
        <el-button v-if="isAuth('sys:role:save')" type="primary" @click="addOrUpdateHandle()"><icon-svg name="add"/>&nbsp;新增角色</el-button>
        <el-button v-if="isAuth('sys:role:delete')" type="danger" @click="deleteHandle()" :disabled="tableSelectData.length <= 0"> <icon-svg name="delete"/>&nbsp;批量删除</el-button>
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
        prop="roleId"
        header-align="center"
        align="center"
        width="80"
        label="ID">
      </el-table-column>
      <el-table-column
        prop="roleName"
        header-align="center"
        align="center"
        label="角色名称">
      </el-table-column>
      <el-table-column
        prop="remark"
        header-align="center"
        align="center"
        label="备注">
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
            <el-button v-if="isAuth('sys:role:update')" type="warning" size="small" @click.stop="addOrUpdateHandle(scope.row.roleId)"><icon-svg name="edit"/>&nbsp;修改</el-button>
            <el-button v-if="isAuth('sys:role:delete')" type="danger" size="small" @click.stop="deleteHandle(scope.row.roleId)"><icon-svg name="delete"/>&nbsp;删除</el-button>
          </el-button-group>
        </template>
      </el-table-column>
    </gulimall-table>
    <!-- 弹窗, 新增 / 修改 -->
    <role-dialog v-if="openDialog" ref="roleDialog" @refreshDataList="query"></role-dialog>
  </div>
</template>

<script>
  import RoleDialog from './RoleDialog'
  import {dateFormat} from '../../filters'
  import Operation from '../../components/Operation/Operation'
  import SearchReset from '../../components/Operation/SearchReset'
  import GulimallTable from '../../components/GulimallTable/GulimallTable'
  import {mapGetters} from 'vuex'
  export default {
    data () {
      return {
        queryCriteria: {
          roleName: '', // 角色名称
          createTime: ['', '']  // 创建时间
        },
        openDialog: false // 打开弹框
      }
    },
    components: {
      RoleDialog,
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
        this.$nextTick(()=>{
          this.$refs.queryCriteria.resetFields()
        })
      },
      /**
       * 查询列表数据
       * @param type
       */
      query (type) {
        const {roleName, createTime} = this.queryCriteria
        let start = dateFormat(createTime[0])
        let end = dateFormat(createTime[1])
        this.$store.dispatch('query', {
          url: '/sys/role/list',
          type: type,
          formData: {
            'roleName': roleName,
            'createTimeStart': start,
            'createTimeEnd': end
          }
        });
      },
      // 新增 / 修改
      addOrUpdateHandle (id) {
        this.openDialog = true
        this.$nextTick(() => {
          this.$refs.roleDialog.init(id)
        })
      },
      // 删除
      deleteHandle (id) {
        var ids = id ? [id] : this.tableSelectData.map(item => {
          return item.roleId
        })
        this.$confirm(`确定对[id=${ids.join(',')}]进行[${id ? '删除' : '批量删除'}]操作?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl('/sys/role/delete'),
            method: 'post',
            data: this.$http.adornData(ids, false)
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.$message({
                message: '操作成功',
                type: 'success',
                duration: 1500,
                onClose: () => {
                  this.query()
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
