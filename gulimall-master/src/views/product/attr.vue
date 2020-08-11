<template>
  <div class="mod-config">
    <div>
      <el-form ref="queryCriteria" :inline="true" :model="queryCriteria" @keyup.enter.native="query()">
        <el-form-item label="品牌名" prop="name">
          <el-input v-model="queryCriteria.name" placeholder="品牌名" clearable></el-input>
        </el-form-item>
        <el-form-item label="显示状态" prop="showStatus">
          <el-select v-model="queryCriteria.showStatus" placeholder="请选择">
            <el-option
              v-for="item in statusList"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <!--查询 和 重置 -->
        <gulimall-search :search="query" :reset="reset"></gulimall-search>
      </el-form>
      <gulimall-operation>
        <el-button v-if="isAuth('sys:user:save')" type="primary" @click="addOrUpdateHandle()">
          <icon-svg name="add"/>
          &nbsp;新增规格参数
        </el-button>
        <el-button v-if="isAuth('sys:user:delete')" type="danger" @click="deleteHandle()"
                   :disabled="tableSelectData.length <= 0">
          <icon-svg name="delete"/>
          &nbsp;批量删除
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
          prop="brandId"
          header-align="center"
          align="center"
          label="品牌id">
        </el-table-column>
        <el-table-column
          prop="name"
          header-align="center"
          align="center"
          label="品牌名">
        </el-table-column>
        <el-table-column
          prop="logo"
          header-align="center"
          align="center"
          label="品牌logo地址">
        </el-table-column>
        <el-table-column
          prop="descript"
          header-align="center"
          align="center"
          label="介绍">
        </el-table-column>
        <el-table-column prop="showStatus" header-align="center" align="center" label="显示状态">
          <template slot-scope="scope">
            <el-switch
              v-model="scope.row.showStatus"
              active-color="#13ce66"
              inactive-color="#ff4949"
              :active-value="1"
              :inactive-value="0"
              @click.stop.native
              @change="updateBrandStatus(scope.row)"
            ></el-switch>
          </template>
        </el-table-column>
        <el-table-column
          prop="firstLetter"
          header-align="center"
          align="center"
          label="检索首字母">
        </el-table-column>
        <el-table-column
          prop="sort"
          header-align="center"
          align="center"
          label="排序">
        </el-table-column>
        <el-table-column
          fixed="right"
          header-align="center"
          align="center"
          width="180"
          label="操作">
          <template slot-scope="scope">
            <el-button-group>
              <el-button type="warning" size="small" @click.stop="addOrUpdateHandle(scope.row.brandId)">
                <icon-svg name="edit"/>
                修改
              </el-button>
              <el-button type="danger" size="small" @click.stop="deleteHandle(scope.row.brandId)">
                <icon-svg name="delete"/>
                删除
              </el-button>
            </el-button-group>
          </template>
        </el-table-column>
      </gulimall-table>

    </div>
    <!-- 弹窗, 新增 / 修改 -->
    <attr-dialog v-if="openDialog" ref="attrDialog" @refreshDataList="query"></attr-dialog>
  </div>
</template>

<script>
  import AttrDialog from "./AttrDialog";
  import GulimallOperation from '../../components/GulimcallOperation/GulimallOperation'
  import GulimallSearch from '../../components/GulimallSearch/GulimallSearch'
  import GulimallTable from '../../components/GulimallTable/GulimallTable'
  import {mapGetters} from 'vuex'

  export default {
    name: 'attr',
    data() {
      return {
        queryCriteria: {
          name: '',
          showStatus: ''
        },
        statusList: [{value: 0, label: '禁用'}, {value: 1, label: '正常'}],  // TODO 以后从字典中获取
        openDialog: false
      }
    },
    components: {
      AttrDialog,
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
        this.$store.dispatch('query', {
          url: '/product/brand/list',
          type: type,
          formData: {
            'name': this.queryCriteria.name,
            'show_status': this.queryCriteria.showStatus
          }
        });
      },

      /**
       * 更新状态
       * @param data
       */
      updateBrandStatus (data) {
        let { brandId, showStatus } = data
        //发送请求修改状态
        this.$http({
          url: this.$http.adornUrl("/product/brand/update/status"),
          method: "post",
          data: this.$http.adornData({ brandId, showStatus }, false)
        }).then(({ data }) => {
          this.$message({
            type: "success",
            message: "状态更新成功"
          });
        });
      },

      // 新增 / 修改
      addOrUpdateHandle(id) {
        this.openDialog = true
        this.$nextTick(() => {
          this.$refs.attrDialog.init(id)
        })
      },
      // 删除
      deleteHandle(id) {
        let ids = id ? [id] : this.tableSelectData.map(item => {
          return item.brandId
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
                message: '删除品牌成功',
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
