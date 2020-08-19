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
          <el-form-item label="属性名" prop="attrName">
            <el-input v-model="queryCriteria.attrName" placeholder="属性名" clearable></el-input>
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
            &nbsp;{{attrType == 1 ? '新增规格参数' : '新增销售属性'}}
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
          <el-table-column prop="attrId" header-align="center" align="center" label="id"></el-table-column>
          <el-table-column prop="attrName" header-align="center" align="center" label="属性名"></el-table-column>
          <el-table-column
            v-if="attrType == 1"
            prop="searchType"
            header-align="center"
            align="center"
            label="可检索"
          >
            <template slot-scope="scope">
              <i class="el-icon-success" v-if="scope.row.searchType==1"></i>
              <i class="el-icon-error" v-else></i>
            </template>
          </el-table-column>
          <el-table-column prop="valueType" header-align="center" align="center" label="值类型">
            <template slot-scope="scope">
              <el-tag type="success" v-if=" scope.row.valueType === 0">单选</el-tag>
              <el-tag v-else>多选</el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="icon" header-align="center" align="center" label="图标"></el-table-column>
          <el-table-column prop="valueSelect" header-align="center" align="center" label="可选值">
            <template slot-scope="scope">
              <el-tooltip placement="top">
                <div slot="content">
                  <span v-for="(i,index) in scope.row.valueSelect.split(';')" :key="index">{{i}}<br/></span>
                </div>
                <el-tag>{{scope.row.valueSelect.split(";")[0]+" ..."}}</el-tag>
              </el-tooltip>
            </template>
          </el-table-column>
          <el-table-column prop="enable" header-align="center" align="center" label="启用">
            <template slot-scope="scope">
              <i class="el-icon-success" v-if="scope.row.enable==1"></i>
              <i class="el-icon-error" v-else></i>
            </template>
          </el-table-column>
          <el-table-column prop="catelogName" header-align="center" align="center" label="所属分类"></el-table-column>
          <el-table-column
            v-if="attrType == 1"
            prop="groupName"
            header-align="center"
            align="center"
            label="所属规格参数"
          ></el-table-column>
          <el-table-column v-if="attrType == 1" prop="showDesc" header-align="center" align="center" label="快速展示">
            <template slot-scope="scope">
              <i class="el-icon-success" v-if="scope.row.showDesc==1"></i>
              <i class="el-icon-error" v-else></i>
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
                <el-button type="warning" size="small" @click.stop="addOrUpdateHandle(scope.row.attrId)">
                  <icon-svg name="edit"/>
                  &nbsp;修改
                </el-button>
                <el-button type="danger" size="small" @click.stop="deleteHandle(scope.row.attrId)">
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
    <attr-dialog v-if="openDialog" ref="attrDialog"
                 :type="attrType"
                 @refreshDataList="query"></attr-dialog>

    <!-- 弹窗, 关联 -->
    <attr-relation-dialog v-if="openRelationDialog" ref="attrRelationDialog" @refreshDataList="query"></attr-relation-dialog>
  </div>
</template>

<script>
  import AttrDialog from "./AttrDialog";
  import GulimallOperation from '../../components/GulimcallOperation/GulimallOperation'
  import GulimallSearch from '../../components/GulimallSearch/GulimallSearch'
  import GulimallTable from '../../components/GulimallTable/GulimallTable'
  import GulimallCategory from "../../components/GulimallCategory/GulimallCategory"
  import AtrrRelationDialog from "./AttrRelation";
  import {mapGetters} from 'vuex'
  import AttrRelationDialog from "./AttrRelation";

  export default {
    name: 'attr',
    props: {
      attrType: {
        type: Number,
        default: 1
      }
    },
    data() {
      return {
        catId: 0,
        type: 1,
        queryCriteria: {
          catelogId: 0,
          attrName: ''
        },
        attrTitle: this.attrType === 1 ? '规格参数列表' : '销售属性列表',
        attr: this.attrType === 1 ? '规格参数列表' : '销售属性列表',
        openDialog: false,
        openRelationDialog: false
      }
    },
    components: {
      AttrRelationDialog,
      AttrDialog,
      GulimallOperation,
      GulimallSearch,
      GulimallTable,
      GulimallCategory,
      AtrrRelationDialog
    },
    activated() {
      this.$nextTick(() => {
        this.attr = this.attrTitle + '(' + '全部' + ')'
        console.log(this.attr)
        this.query('init')
      })
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
          url: '/product/attr/list',
          type: type,
          formData: {
            'catelogId': this.queryCriteria.catelogId,
            'attrType': this.attrType,
            'attrName': this.queryCriteria.attrName
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
