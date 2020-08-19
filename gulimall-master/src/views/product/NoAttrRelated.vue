<template>
  <div class="mod-attr-relation">
    <el-row style="width: 49%; float: left">
      <el-form ref="queryCriteria" :inline="true" :model="queryCriteria" @keyup.enter.native="query()">
        <el-form-item label="属性id" prop="attrId">
          <el-input v-model="queryCriteria.attrId" placeholder="属性id" clearable></el-input>
        </el-form-item>
        <el-form-item label="属性名" prop="attrName">
          <el-input v-model="queryCriteria.attrName" placeholder="属性名" clearable></el-input>
        </el-form-item>
        <!--查询 和 重置 -->
        <gulimall-search :search="query" :reset="reset">
        </gulimall-search>
      </el-form>
      <gulimall-operation icon="norelation" name="未关联属性">
        <el-button-group>
          <el-button class="mauve-background" @click="refresh">
            <icon-svg name="refresh"/>&nbsp;刷新
          </el-button>
          <el-button type="primary" @click="addRelation">
            <icon-svg name="add"/>&nbsp;新增关联属性
          </el-button>
        </el-button-group>
      </gulimall-operation>
      <el-table
        :data="dataList"
        border
        v-loading="dataListLoading"
        @selection-change="selectionHandle"
        style="width: 100%;">
        <el-table-column type="selection" header-align="center" align="center"></el-table-column>
        <el-table-column prop="attrId" header-align="center" align="center" label="属性id"></el-table-column>
        <el-table-column prop="attrName" header-align="center" align="center" label="属性名"></el-table-column>
        <el-table-column prop="icon" header-align="center" align="center" label="属性图标"></el-table-column>
        <el-table-column prop="valueSelect" header-align="center" align="center" label="可选值列表"></el-table-column>
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
    </el-row>
  </div>
</template>

<script>
  import GulimallOperation from '../../components/GulimcallOperation/GulimallOperation'
  import GulimallSearch from "../../components/GulimallSearch/GulimallSearch";

  export default {
    name: 'NoAttrRelated',
    components: {
      GulimallOperation,
      GulimallSearch
    },
    data() {
      return {
        attrGroupId: 0,
        selectDataList: [],
        queryCriteria: {
          attrId: '',
          attrName: ''
        },
        dataList: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        dataListLoading: false,
        dataListSelections: [],
        addOrUpdateVisible: false
      }
    },
    created() {
      this.PubSub.subscribe('refreshNoAttrRelation', (msg, data) => {
        this.query()
      })
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
      query() {
        this.queryNoAttrRelation(this.attrGroupId)
      },
      selectionHandle(val) {
        this.selectDataList = val
      },

      // 获取数据列表
      queryNoAttrRelation(attrGroupId) {
        this.dataListLoading = true
        this.attrGroupId = attrGroupId
        this.$http({
          url: this.$http.adornUrl('/product/attrgroup/noattr/relationPage'),
          method: 'get',
          params: this.$http.adornParams({
            'pageNumber': this.pageIndex,
            'pageSize': this.pageSize,
            'attrId': this.queryCriteria.attrId,
            'attrName': this.queryCriteria.attrName,
            'attrGroupId': attrGroupId
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
      addRelation() {
        console.log(this.selectDataList)
        if (this.selectDataList.length > 0) {
          let postData = []
          this.selectDataList.forEach(item => {
            postData.push({attrId: item.attrId, attrGroupId: this.attrGroupId});
          })
          this.$http({
            url: this.$http.adornUrl("/product/attrgroup/attr/relation"),
            method: "post",
            data: this.$http.adornData(postData, false)
          }).then(({data}) => {
            if (data.code == 0) {
              this.$message({type: "success", message: "新增关联成功"})
            }
            this.queryNoAttrRelation(this.attrGroupId)
            this.PubSub.publish('refreshAttrRelation')
          })
        } else {
          this.$message({type: "warning", message: "请选择关联数据"})
        }
      },
      refresh() {
        this.queryNoAttrRelation(this.attrGroupId)
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
        var ids = id ? [id] : this.dataListSelections.map(item => {
          return item.id
        })
        this.$confirm(`确定对[id=${ids.join(',')}]进行[${id ? '删除' : '批量删除'}]操作?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl('/admin/sysconfig/delete'),
            method: 'post',
            data: this.$http.adornData(ids, false)
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
        })
      }
    }
  }
</script>


<style scoped>

</style>

