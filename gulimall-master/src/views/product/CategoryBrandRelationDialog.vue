<template>
  <gulimall-dialog
    ref="dialog"
    @submit="submit"
    title="关联分类"
    btnSave="确定"
    icon="relation">
    <el-row>
      <category-cascader :catelogPath.sync="catelogPath" style="float: left;width: 60%"></category-cascader>
      <el-button style="float: right" type="primary" @click="addRelationHandler"> <icon-svg name="add"/>&nbsp;&nbsp;新增关联</el-button>
    </el-row>
    <el-table :data="cateRelationTableData"
              highlight-current-row
              border
              v-loading="loading"
              style="width: 100%; margin-top: 10px">
      <el-table-column prop="id" width="80px" header-align="center" align="center" label="ID"></el-table-column>
      <el-table-column prop="brandName" header-align="center" align="center" label="品牌名"></el-table-column>
      <el-table-column prop="catelogName" header-align="center" align="center" label="分类名"></el-table-column>
      <el-table-column fixed="right" width="120px" header-align="center" align="center" label="操作">
        <template slot-scope="scope">
          <el-button
            type="danger"
            size="small"
            @click="deleteCateRelationHandle(scope.row.id, scope.row.brandId)"
          > <icon-svg name="delete"/>&nbsp;移除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </gulimall-dialog>
</template>

<script>
  import GulimallDialog from "../../components/GulimallDialog/GulimallDialog";
  import CategoryCascader from "../../components/GulimallCategory/CategoryCascader"
  import GulimallOperation from "../../components/GulimcallOperation/GulimallOperation";
  export default {
    name: 'CategoryBrandRelationDialog',
    components: {
      GulimallDialog,
      CategoryCascader,
      GulimallOperation
    },
    data() {
      return {
        visible: false,
        loading:true,
        brandId: 0,
        catelogPath: [],
        cateRelationTableData: [],
      };
    },
    activated() {
      this.getCateRelation()
    },
    methods: {
      operationCategoryBrandRelation(brandId) {
        this.catelogPath = []
        this.$refs.dialog.openDialog()
        this.brandId = brandId;
        this.getCateRelation()
      },
      // 表单提交
      submit () {
        this.$refs.dialog.closeDialog()
      },
      /**
       * 添加关联分类
       */
      addRelationHandler() {
        const catelogId = this.catelogPath[this.catelogPath.length - 1];
        if (catelogId !== '' && catelogId !== null && catelogId !== undefined) {
          this.$http({
            url: this.$http.adornUrl("/product/categorybrandrelation/save"),
            method: "post",
            data: this.$http.adornData({brandId: this.brandId,catelogId: catelogId}, false)
          }).then(({ data }) => {

            if (data.data === 1) {
              this.catelogPath = []
              this.$message({
                message: '关联分类成功',
                type: 'success',
                duration: 1500,
                onClose: () => {
                  this.getCateRelation()
                }
              })
            } else {
              this.$message({
                message: '分类已关联',
                type: 'error',
                duration: 1500
              })
            }

          })
        } else {
          this.$message({
            type: "warning",
            message: "请选择关联分类"
          });
        }

      },
      deleteCateRelationHandle(id, brandId) {
        this.$GulimallConfirm({
          content: `确定对[分类id=${id}]进行[<span style="color: red;display:inline;">移除</span>]操作?`
        }).then(res=>{
          this.$http({
            url: this.$http.adornUrl("/product/categorybrandrelation/delete"),
            method: "post",
            data: this.$http.adornData([id], false)
          }).then(({ data }) => {
            this.$message({
              message: '移除关联分类成功',
              type: 'success',
              duration: 1500,
              onClose: () => {
                this.getCateRelation()
              }
            })
          })
        })



      },
      getCateRelation() {
        this.loading = true
        this.$http({
          url: this.$http.adornUrl("/product/categorybrandrelation/catelog/list"),
          method: "get",
          params: this.$http.adornParams({
            brandId: this.brandId
          })
        }).then(({ data }) => {
          this.loading = false
          this.cateRelationTableData = data.data
        }).catch(error => {
          this.loading = false
        })
      },
    }
  }
</script>


<style scoped>

</style>

