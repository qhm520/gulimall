<template>
  <gulimall-dialog
    ref="dialog"
    :title="!dataForm.id ? '新增品牌' : '修改品牌'"
    :icon="!dataForm.id ? 'add' : 'edit'">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="submit()" label-width="120px">
      <el-form-item label="品牌名" prop="name">
        <el-input v-model="dataForm.name" placeholder="品牌名" clearable></el-input>
      </el-form-item>
      <el-form-item label="品牌logo地址" prop="logo">
        <el-input v-model="dataForm.logo" placeholder="品牌logo地址" clearable></el-input>
      </el-form-item>
      <el-form-item label="介绍" prop="descript">
        <el-input v-model="dataForm.descript" placeholder="介绍" clearable></el-input>
      </el-form-item>
      <el-form-item label="显示状态" prop="showStatus">
        <el-switch
          v-model="dataForm.showStatus"
          active-color="#13ce66"
          inactive-color="#ff4949"
          :active-value="1"
          :inactive-value="0"
        ></el-switch>
      </el-form-item>
      <el-form-item label="检索首字母" prop="firstLetter">
        <el-input v-model="dataForm.firstLetter" placeholder="检索首字母" clearable></el-input>
      </el-form-item>
      <el-form-item label="排序" prop="sort">
        <el-input v-model.number="dataForm.sort" placeholder="排序" clearable></el-input>
      </el-form-item>
    </el-form>
  </gulimall-dialog>
</template>

<script>
  import GulimallDialog from "../../components/GulimallDialog/GulimallDialog";
  export default {
    name: 'ConfigDialog',
    components: {GulimallDialog},
    data () {
      return {
        visible: false,
        dataForm: {
          brandId: 0,
          name: '',
          logo: '',
          descript: '',
          showStatus: 1,
          firstLetter: '',
          sort: 0
        },
        dataRule: {
          name: [{ required: true, message: "品牌名不能为空", trigger: "blur" }],
          logo: [
            { required: true, message: "品牌logo地址不能为空", trigger: "blur" }
          ],
          descript: [
            { required: true, message: "介绍不能为空", trigger: "blur" }
          ],
          showStatus: [
            {
              required: true,
              message: "显示状态[0-不显示；1-显示]不能为空",
              trigger: "blur"
            }
          ],
          firstLetter: [
            {
              validator: (rule, value, callback) => {
                if (value === '') {
                  callback(new Error("首字母必须填写"));
                } else if (!/^[a-zA-Z]$/.test(value)) {
                  callback(new Error("首字母必须a-z或者A-Z之间"))
                } else {
                  callback();
                }
              },
              trigger: "blur"
            }
          ],
          sort: [
            {
              validator: (rule, value, callback) => {
                if (value === '') {
                  callback(new Error("排序字段必须填写"));
                } else if (!Number.isInteger(value) || value<0) {
                  callback(new Error("排序必须是一个大于等于0的整数"));
                } else {
                  callback();
                }
              },
              trigger: "blur"
            }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.id = id || 0
        this.$refs.dialog.openDialog()
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/product/brand/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                // this.dataForm.paramKey = data.sysConfig.paramKey
                // this.dataForm.paramValue = data.sysConfig.paramValue
                // this.dataForm.remark = data.sysConfig.remark
              }
            })
          }
        })
      },
      // 表单提交
      submit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/product/brand/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                // 'id': this.dataForm.id || undefined,
                // 'paramKey': this.dataForm.paramKey,
                // 'paramValue': this.dataForm.paramValue,
                // 'remark': this.dataForm.remark
              })
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.$message({
                  message: '操作成功',
                  type: 'success',
                  duration: 1500,
                  onClose: () => {
                    this.$refs.dialog.closeDialog()
                    this.$emit('refreshDataList')
                  }
                })
              } else {
                this.$message.error(data.msg)
              }
            })
          }
        })
      }
    }
  }
</script>


<style scoped>

</style>

