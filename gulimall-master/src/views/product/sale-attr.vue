<template>
  <div class="mod-attr-group">
    <gulimall-operation>
    </gulimall-operation>

    <el-row :gutter="20" style="margin-top: 10px;">

      <el-col :span="6">
        <gulimall-category ref="category" @tree-node-click="treeNodeClick"></gulimall-category>
      </el-col>
      <el-col :span="18">
        <el-form ref="queryCriteria" :inline="true" :model="queryCriteria" @keyup.enter.native="query()">
          <el-form-item label="分组id" prop="attrGroupId">
            <el-input v-model="queryCriteria.attrGroupId" placeholder="分组id" clearable></el-input>
          </el-form-item>
          <el-form-item label="组名" prop="attrGroupName">
            <el-input v-model="queryCriteria.attrGroupName" placeholder="组名" clearable></el-input>
          </el-form-item>
          <!--查询 和 重置 -->
          <gulimall-search :search="query" :reset="reset">
            <el-button type="info" @click="queryAll()">
              <icon-svg name="all"/>
              &nbsp;查询全部
            </el-button>
          </gulimall-search>
        </el-form>
        <gulimall-operation :name="attr" icon="attr">
          <el-button  type="primary" @click="addOrUpdateHandle()">
            <icon-svg name="add"/>
            &nbsp;新增分组
          </el-button>
          <el-button type="danger" @click="deleteHandle()"
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
          <el-table-column prop="attrGroupId" header-align="center" align="center" label="分组id"></el-table-column>
          <el-table-column prop="attrGroupName" header-align="center" align="center" label="组名"></el-table-column>
          <el-table-column prop="sort" header-align="center" align="center" label="排序"></el-table-column>
          <el-table-column prop="descript" header-align="center" align="center" label="描述"></el-table-column>
          <el-table-column prop="icon" header-align="center" align="center" label="组图标"></el-table-column>
          <el-table-column prop="catelogId" header-align="center" align="center" label="所属分类id"></el-table-column>
          <el-table-column
            fixed="right"
            header-align="center"
            align="center"
            width="260"
            label="操作">
            <template slot-scope="scope">
              <el-button-group>
                <el-button type="primary" size="small" @click.stop="relationHandle(scope.row.attrGroupId)">
                  <icon-svg name="relation"/>
                  &nbsp;关联
                </el-button>
                <el-button type="warning" size="small" @click.stop="addOrUpdateHandle(scope.row.attrGroupId)">
                  <icon-svg name="edit"/>
                  &nbsp;修改
                </el-button>
                <el-button type="danger" size="small" @click.stop="deleteHandle(scope.row.attrGroupId)">
                  <icon-svg name="delete"/>
                  &nbsp;删除
                </el-button>
              </el-button-group>
            </template>
          </el-table-column>
        </gulimall-table>
      </el-col>
    </el-row>
    <!-- 弹窗, 新增 / 修改 -->
    <attr-group-dialog v-if="openDialog" ref="attrDialog" @refreshDataList="query"></attr-group-dialog>

    <!-- 弹窗, 关联 -->
    <attr-relation-dialog v-if="openRelationDialog" ref="attrRelationDialog" @refreshDataList="query"></attr-relation-dialog>
  </div>
</template>

<script>
  import AttrGroupDialog from "./AttrGroupDialog";
  import GulimallOperation from '../../components/GulimcallOperation/GulimallOperation'
  import GulimallSearch from '../../components/GulimallSearch/GulimallSearch'
  import GulimallTable from '../../components/GulimallTable/GulimallTable'
  import GulimallCategory from "../../components/GulimallCategory/GulimallCategory"
  import AtrrRelationDialog from "./AtrrRelationDialog";
  import {mapGetters} from 'vuex'
  import AttrRelationDialog from "./AtrrRelationDialog";

  export default {
    name: 'sale-attr',
    data() {
      return {
        queryCriteria: {
          catelogId: 0,
          attrGroupId: '',
          attrGroupName: ''
        },
        attrTitle: '分组列表',
        attr: '分组列表',
        openDialog: false,
        openRelationDialog: false
      }
    },
    components: {
      AttrRelationDialog,
      AttrGroupDialog,
      GulimallOperation,
      GulimallSearch,
      GulimallTable,
      GulimallCategory,
      AtrrRelationDialog
    },
    activated() {
      this.attr = this.attrTitle + '(' + '全部' + ')'
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
          url: '/product/attrgroup/list',
          type: type,
          formData: {
            'catelogId': this.queryCriteria.catelogId,
            'attrGroupId': this.queryCriteria.attrGroupId,
            'attrGroupName': this.queryCriteria.attrGroupName
          }
        });
      },
      queryAll() {
        this.queryCriteria.catelogId = 0
        this.attr = this.attrTitle + '(' + '全部' + ')'
        this.query('init')
      },
      treeNodeClick (data, node, component) {
        if (node.level === 3) {
          const name = node.parent.parent.data.name + '-' + node.parent.data.name + '-' + node.data.name
          this.attr = this.attrTitle + '(' + name + ')'  // 列表名称
          this.queryCriteria.catelogId = data.catId
          this.$refs.queryCriteria.resetFields()
          this.query('init'); //重新查询
        }
      },


      // 关联
      relationHandle (id) {
        this.openRelationDialog = true
        this.$nextTick(() => {
          this.$refs.attrRelationDialog.init(id)
        })
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
          return item.attrGroupId
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
