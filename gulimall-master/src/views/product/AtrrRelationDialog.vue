<template>
  <gulimall-dialog
    ref="dialog"
    @submit="submit"
    title="关联分类"
    icon="relation">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="submit()" label-width="120px">
      <el-form-item label="组名" prop="attrGroupName">
        <el-input v-model="dataForm.attrGroupName" placeholder="组名"></el-input>
      </el-form-item>
      <el-form-item label="排序" prop="sort">
        <el-input v-model="dataForm.sort" placeholder="排序"></el-input>
      </el-form-item>
      <el-form-item label="描述" prop="descript">
        <el-input v-model="dataForm.descript" placeholder="描述"></el-input>
      </el-form-item>
      <el-form-item label="组图标" prop="icon">
        <el-input v-model="dataForm.icon" placeholder="组图标"></el-input>
      </el-form-item>
      <el-form-item label="所属分类" prop="catelogId">
        <!-- <el-input v-model="dataForm.catelogId" placeholder="所属分类id"></el-input> @change="handleChange" -->
        <!-- <el-cascader filterable placeholder="试试搜索：手机" v-model="catelogPath" :options="categorys"  :props="props"></el-cascader> -->
        <!-- :catelogPath="catelogPath"自定义绑定的属性，可以给子组件传值 -->
        <category-cascader :catelogPath.sync="catelogPath"></category-cascader>
      </el-form-item>
    </el-form>
  </gulimall-dialog>
</template>

<script>
  import GulimallDialog from "../../components/GulimallDialog/GulimallDialog";
  import CategoryCascader from "../../components/GulimallCategory/CategoryCascader";
  export default {
    name: 'AttrRelationDialog',
    components: {
      GulimallDialog,
      CategoryCascader
    },
    data() {
      return {
        props:{
          value:"catId",
          label:"name",
          children:"children"
        },
        visible: false,
        categorys: [],
        catelogPath: [],
        dataForm: {
          attrGroupId: 0,
          attrGroupName: "",
          sort: "",
          descript: "",
          icon: "",
          catelogId: 0
        },
        dataRule: {
          attrGroupName: [
            { required: true, message: "组名不能为空", trigger: "blur" }
          ],
          sort: [{ required: true, message: "排序不能为空", trigger: "blur" }],
          descript: [
            { required: true, message: "描述不能为空", trigger: "blur" }
          ],
          icon: [{ required: true, message: "组图标不能为空", trigger: "blur" }],
          catelogId: [
            { required: true, message: "所属分类id不能为空", trigger: "blur" }
          ]
        }
      };
    },
    methods: {
      init(id) {
        this.dataForm.attrGroupId = id || 0;
        this.$refs.dialog.openDialog()
        this.$nextTick(() => {
          this.$refs["dataForm"].resetFields();
          if (this.dataForm.attrGroupId) {
            this.$http({
              url: this.$http.adornUrl(
                `/product/attrgroup/info/${this.dataForm.attrGroupId}`
              ),
              method: "get",
              params: this.$http.adornParams()
            }).then(({ data }) => {
              if (data && data.code === 0) {
                this.dataForm.attrGroupName = data.attrGroup.attrGroupName;
                this.dataForm.sort = data.attrGroup.sort;
                this.dataForm.descript = data.attrGroup.descript;
                this.dataForm.icon = data.attrGroup.icon;
                this.dataForm.catelogId = data.attrGroup.catelogId;
                //查出catelogId的完整路径
                this.catelogPath =  data.attrGroup.catelogPath;
              }
            });
          }
        });
      },
      // 表单提交
      submit () {
        this.$refs["dataForm"].validate(valid => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(
                `/product/attrgroup/${
                  !this.dataForm.attrGroupId ? "save" : "update"
                }`
              ),
              method: "post",
              data: this.$http.adornData({
                attrGroupId: this.dataForm.attrGroupId || undefined,
                attrGroupName: this.dataForm.attrGroupName,
                sort: this.dataForm.sort,
                descript: this.dataForm.descript,
                icon: this.dataForm.icon,
                catelogId: this.catelogPath[this.catelogPath.length-1]
              })
            }).then(({ data }) => {
              if (data && data.code === 0) {
                this.$message({
                  message: "操作成功",
                  type: "success",
                  duration: 1500,
                  onClose: () => {
                    this.visible = false;
                    this.$emit("refreshDataList");
                  }
                });
              } else {
                this.$message.error(data.msg);
              }
            });
          }
        })
      }
    }
  }
</script>


<style scoped>

</style>

