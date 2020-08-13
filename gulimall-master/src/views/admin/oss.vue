<template>
  <div class="mod-oss">
    <div v-if="ossList">
      <el-form ref="queryCriteria" :inline="true" :model="queryCriteria" @keyup.enter.native="query">
        <el-form-item label="文件名称" prop="originalFilename">
          <el-input v-model="queryCriteria.originalFilename" placeholder="文件名称" clearable></el-input>
        </el-form-item>
        <el-form-item label="IP地址" prop="ip">
          <el-input v-model="queryCriteria.ip" placeholder="IP地址" clearable></el-input>
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
        <gulimall-search :search="query" :reset="reset"></gulimall-search>
      </el-form>
      <gulimall-operation>
        <el-button type="primary" @click="refresh">
          <icon-svg name="oss"/>&nbsp;刷新文件
        </el-button>
        <el-button type="danger" @click="deleteHandle()" :disabled="tableSelectData.length <= 0">
          <icon-svg name="delete"/>&nbsp;批量删除
        </el-button>
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
          prop="originalFilename"
          header-align="center"
          align="center"
          label="文件名称">
        </el-table-column>
        <el-table-column
          prop="uploadUser"
          header-align="center"
          align="center"
          label="上传者">
        </el-table-column>
        <el-table-column
          prop="url"
          header-align="center"
          align="center"
          label="URL地址">
          <template slot-scope="scope">
            <!-- <el-image
                style="width: 100px; height: 80px"
                :src="scope.row.logo"
            fit="fill"></el-image>-->
            <img :src="scope.row.url" style="width: 100px; height: 80px" />
          </template>
        </el-table-column>
        <el-table-column
          prop="status"
          header-align="center"
          align="center"
          label="状态">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.status === 0" size="small" type="warning">禁用</el-tag>
            <el-tag v-else-if="scope.row.status === 2" size="small" type="danger">失效</el-tag>
            <el-tag v-else size="small">正常</el-tag>
          </template>
        </el-table-column>
        <el-table-column
          prop="createDate"
          header-align="center"
          align="center"
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
              <el-button type="danger" size="small" @click.stop="deleteHandle(scope.row.id)">
                <icon-svg name="delete"/>&nbsp;删除
              </el-button>
            </el-button-group>
          </template>
        </el-table-column>
      </gulimall-table>
    </div>

    <gulimall-edit name="文件上传"
                   icon="oss"
                   ref="oss"
                   @close="close"
                   @submit="submit">
      <div>
        hello
      </div>
    </gulimall-edit>
  </div>
</template>

<script>
  import GulimallOperation from '../../components/GulimcallOperation/GulimallOperation'
  import GulimallSearch from '../../components/GulimallSearch/GulimallSearch'
  import GulimallTable from '../../components/GulimallTable/GulimallTable'
  import GulimallEdit from "../../components/GulimallEdit/GulimallEdit";
  import {mapGetters} from 'vuex'
  import {dateFormat} from '../../filters'

  export default {
    name: 'oss',
    components: {
      GulimallOperation,
      GulimallSearch,
      GulimallTable,
      GulimallEdit
    },
    data() {
      return {
        ossList: true,
        icon: 'oss',
        name: '上传文件',
        queryCriteria: {
          originalFilename: '',
          status: '',
          ip: '',
          createDate: ['', '']
        },
        statusList: [{value: 0, label: '禁用'}, {value: 1, label: '正常'}],  // TODO 以后从字典中获取
      }
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
        this.ossList = true
        const {username, operation, ip, createDate} = this.queryCriteria
        let start = dateFormat(createDate[0])
        let end = dateFormat(createDate[1])
        this.$store.dispatch('query', {
          url: '/sys/oss/list',
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
      upload () {
        this.ossList = false
        this.$refs.oss.visible = true
      },
      close () {
        this.query('init')
      },
      submit () {
        console.log('hello')
      },

      /**
       * 刷新文件
       */
      refresh () {
        this.$http({
          url: this.$http.adornUrl('/sys/oss/refresh'),
          method: 'get',
          params: this.$http.adornParams()
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.$message({
              message: '刷新文件成功',
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
      },
      // 删除
      deleteHandle(id) {
        let ids = id ? [id] : this.tableSelectData.map(item => {
          return item.id
        })
        this.$GulimallConfirm({
          content: `确定对[id=${ids.join(',')}]进行[<span style="color: red;display:inline;">${id ? '删除' : '批量删除'}</span>]操作?`
      }).then(() => {
          this.$http({
            url: this.$http.adornUrl('/sys/oss/delete'),
            method: 'post',
            data: this.$http.adornData(ids, false)
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.$message({
                message: '删除文件成功',
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
