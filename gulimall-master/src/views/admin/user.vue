<template>
  <div class="mod-user">
    <el-form ref="queryCriteria" :inline="true" :model="queryCriteria" @keyup.enter.native="getDataList()">
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
      <search-reset :search="getDataList" :reset="reset"></search-reset>
    </el-form>
    <operation>
      <el-button slot="add" v-if="isAuth('sys:user:save')" type="primary" @click="addOrUpdateHandle()">
        <icon-svg name="add"/>
        新增用户
      </el-button>
      <el-button slot="batchDelete" v-if="isAuth('sys:user:delete')" type="danger" @click="deleteHandle()"
                 :disabled="dataListSelections.length <= 0">
        <icon-svg name="delete"/>
        批量删除
      </el-button>
    </operation>
    <el-table
      ref="userTable"
      :data="dataList"
      @row-click="clickRow"
      highlight-current-row
      border
      v-loading="dataListLoading"
      @selection-change="selectionChangeHandle"
      style="width: 100%;">
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
              修改
            </el-button>
            <el-button v-if="isAuth('sys:user:delete')" type="danger" size="small"
                       @click.stop="deleteHandle(scope.row.userId)">
              <icon-svg name="delete"/>
              删除
            </el-button>
          </el-button-group>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="sizeChangeHandle"
      @current-change="currentChangeHandle"
      :current-page="pageIndex"
      :page-sizes="[10, 20, 50, 100]"
      :page-size="pageSize"
      :total="totalPage"
      background
      layout="total, sizes, prev, pager, next, jumper">
    </el-pagination>
    <!-- 弹窗, 新增 / 修改 -->
    <user-dialog v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></user-dialog>
  </div>
</template>

<script>
  import UserDialog from "./UserDialog";
  import { dateFormat } from '../../filters';
  import Operation from "../../components/Operation/Operation";
  import SearchReset from "../../components/Operation/SearchReset";

  export default {
    name: 'user',
    data () {
      return {
        queryCriteria: {
          username: '',
          mobile: '',
          status: '',
          createTime: ['', '']
        },
        statusList: [{value: 0, label: '禁用'}, {value: 1, label: '正常'}],  // TODO 以后从字典中获取
        dataList: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        dataListLoading: false,
        dataListSelections: [],
        addOrUpdateVisible: false
      }
    },
    components: {
      UserDialog,
      Operation,
      SearchReset
    },
    activated () {
      this.getDataList()
    },
    methods: {
      /**
       * 重置查询条件
       */
      reset () {
        this.$refs.queryCriteria.resetFields();
      },
      // 获取数据列表
      getDataList () {
        this.dataListLoading = true
        const { username, mobile, status, createTime } = this.queryCriteria
        let start = dateFormat(createTime[0])
        let end = dateFormat(createTime[1])
        this.$http({
          url: this.$http.adornUrl('/sys/user/list'),
          method: 'get',
          params: this.$http.adornParams({
            'page': this.pageIndex,
            'limit': this.pageSize,
            'username': username,
            'mobile': mobile,
            'status': status,
            'createTimeStart': start,
            'createTimeEnd': end
          })
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.dataList = data.page.list
            this.totalPage = data.page.totalCount
          } else {
            this.dataList = []
            this.totalPage = 0
          }
          this.dataListLoading = false
        })
      },
      // 每页数
      sizeChangeHandle(val) {
        this.pageSize = val
        this.pageIndex = 1
        this.getDataList()
      },
      // 当前页
      currentChangeHandle(val) {
        this.pageIndex = val
        this.getDataList()
      },
      // 多选
      selectionChangeHandle(val) {
        this.dataListSelections = val
      },
      // 新增 / 修改
      addOrUpdateHandle(id) {
        this.addOrUpdateVisible = true
        this.$nextTick(() => {
          this.$refs.addOrUpdate.init(id)
        })
      },
      // 删除
      deleteHandle(id) {
        var userIds = id ? [id] : this.dataListSelections.map(item => {
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
                  this.getDataList()
                }
              })
            } else {
              this.$message.error(data.msg)
            }
          })
        }).catch(() => {
        })
      },

      /**
       * 点击行选择
       * @param row
       */
      clickRow(row) {
        this.$refs.userTable.toggleRowSelection(row)
      }
    }
  }
</script>
