<template>
  <div class="mod-attr-relation" >
    <el-row style="width: 50%; float: right">
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
      <gulimall-operation icon="relation" name="已关联属性">
        <el-button-group>
          <el-button class="mauve-background"  @click="refresh">
            <icon-svg name="refresh"/>&nbsp;刷新
          </el-button>
          <el-button type="danger"  @click="">
            <icon-svg name="delete"/>&nbsp;批量移除关联
          </el-button>
        </el-button-group>
      </gulimall-operation>
      <el-table
        :data="dataList"
        border
        v-loading="dataListLoading"
        @selection-change="selectionChangeHandle"
        style="width: 100%;">
        <el-table-column type="selection" header-align="center" align="center" width="50"></el-table-column>
        <el-table-column prop="attrId"  header-align="center" align="center" label="属性Id"></el-table-column>
        <el-table-column prop="attrName"  header-align="center" align="center" label="属性名"></el-table-column>
        <el-table-column prop="valueSelect"  header-align="center" align="center" label="可选值">
          <template slot-scope="scope">
            <el-tooltip placement="top">
              <div slot="content">
                    <span v-for="(i,index) in scope.row.valueSelect.split(';')" :key="index">
                      {{i}}
                      <br />
                    </span>
              </div>
              <el-tag>{{scope.row.valueSelect.split(";")[0]+" ..."}}</el-tag>
            </el-tooltip>
          </template>
        </el-table-column>
        <el-table-column fixed="right" header-align="center" align="center" label="操作">
          <template slot-scope="scope">
            <el-button type="danger" size="small" @click="relationRemove(scope.row.attrId)"><icon-svg name="delete"/>&nbsp;移除</el-button>
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
    </el-row>
  </div>
</template>

<script>
  import GulimallOperation from '../../components/GulimcallOperation/GulimallOperation'
  import GulimallSearch from "../../components/GulimallSearch/GulimallSearch";
  export default {
    name: 'AttrRelated',
    components: {
      GulimallOperation,
      GulimallSearch
    },
    data () {
      return {
        attrGroupId: 0,
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
      this.PubSub.subscribe('refreshAttrRelation', (msg, data) => {
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
      query() {
        this.queryAttrRelation(this.attrGroupId)
      },
      // 获取数据列表
      queryAttrRelation (attrGroupId) {
        this.dataListLoading = true
        this.attrGroupId = attrGroupId
        this.$http({
          url: this.$http.adornUrl('/product/attrgroup/relationPage'),
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

      refresh() {
        this.queryAttrRelation(this.attrGroupId)
      },
      // 每页数
      sizeChangeHandle (val) {
        this.pageSize = val
        this.pageIndex = 1
        this.getDataList()
      },
      // 当前页
      currentChangeHandle (val) {
        this.pageIndex = val
        this.getDataList()
      },
      // 多选
      selectionChangeHandle (val) {
        this.dataListSelections = val
      },
      // 新增 / 修改
      addOrUpdateHandle (id) {
        this.addOrUpdateVisible = true
        this.$nextTick(() => {
          this.$refs.addOrUpdate.init(id)
        })
      },
      //移除关联
      relationRemove(attrId) {
        let data = [];
        data.push({ attrId, attrGroupId: this.attrGroupId });

        this.$GulimallConfirm({
          content: `确定要进行[<span style="color: red;display:inline;">${data.length > 1 ? '删除' : '批量删除'}</span>]操作?`
        }).then(res => {
          this.$http({
            url: this.$http.adornUrl("/product/attrgroup/delete/relation"),
            method: "post",
            data: this.$http.adornData(data, false)
          }).then(({ data }) => {
            if (data.code == 0) {
              this.$message({ type: "success", message: "移除关联成功" })
              this.queryAttrRelation(this.attrGroupId)
              this.PubSub.publish('refreshNoAttrRelation')
            } else {
              this.$message({ type: "error", message: data.msg })
            }
          })
        })
        /*;*/
      },

      // 删除
      deleteHandle (attrId) {

      }
    }
  }
</script>


<style scoped>

</style>

