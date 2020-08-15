<template>
  <gulimall-dialog
    ref="dialog"
    @submit="submit"
    :title="!dataForm.brandId ? '新增品牌' : '修改品牌'"
    :icon="!dataForm.brandId ? 'add' : 'edit'">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="submit()" label-width="120px">
      <el-form-item label="品牌名" prop="name">
        <el-input v-model="dataForm.name" placeholder="品牌名" clearable></el-input>
      </el-form-item>
      <el-form-item label="品牌logo地址" prop="logo">
        <el-upload
          class="upload-demo"
          action="#"
          :http-request="httpRequest"
          :before-upload="beforeAvatarUpload"
          :multiple="false"
          :file-list="fileList"
          :show-file-list="showFileList"
          list-type="picture">
          <el-button type="primary"><icon-svg name="oss"/>&nbsp;&nbsp;点击上传</el-button>
          <div slot="tip" class="el-upload__tip" style="font-size: 15px; color: red;"><icon-svg name="warning"/>&nbsp;&nbsp;只能上传一张jpg/png文件，且不超过10MB</div>
        </el-upload>
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
  import GulimallDialog from '../../components/GulimallDialog/GulimallDialog'
  export default {
    name: 'BrandDialog',
    components: {
      GulimallDialog
    },
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
    computed: {
      fileList() {
        return [{
          name: this.dataForm.name,
          url: this.dataForm.logo
        }]
      },
      showFileList: {
        get: function () {
          return this.dataForm.logo != null && this.dataForm.logo != '' && this.dataForm.logo != undefined ;
        },
        set: function (newValue) {
        }
      }
    },

    methods: {
      init (id) {
        this.dataForm.brandId = id || 0
        this.$refs.dialog.openDialog()
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.brandId) {
            this.$http({
              url: this.$http.adornUrl(`/product/brand/info/${this.dataForm.brandId}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                const {name, logo, descript, showStatus, firstLetter, sort} = data.data
                this.dataForm.name = name;
                this.dataForm.logo = logo;
                this.dataForm.descript = descript;
                this.dataForm.showStatus = showStatus;
                this.dataForm.firstLetter = firstLetter;
                this.dataForm.sort = sort;
              }
            })
          }
        })
      },
      // 表单提交
      submit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            const {brandId, name, logo, descript, showStatus, firstLetter, sort} = this.dataForm
            this.$http({
              url: this.$http.adornUrl(`/product/brand/${!this.dataForm.brandId ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                brandId: brandId,
                name: name,
                logo: logo,
                descript: descript,
                showStatus: showStatus,
                firstLetter: firstLetter,
                sort: sort
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
      },
      // 上传之前的格式设置
      beforeAvatarUpload (file) {
        const isJPG = file.type === 'image/jpeg' || 'image/png'
        const isLt10M = file.size / 1024 / 1024 < 10
        if (!isJPG) {
          this.$message.error('上传头像图片只能是 JPG 格式!')
        }
        if (!isLt10M) {
          this.$message.error('上传头像图片大小不能超过 10MB!')
        }
        return isJPG && isLt10M
      },
      httpRequest (data) {
        let _this = this
        let rd = new FileReader() // 创建文件读取对象
        let file = data.file
        rd.readAsDataURL(file) // 文件读取装换为base64类型
        rd.onloadend = function (e) {
          _this.dataForm.logo = this.result // this指向当前方法onloadend的作用域
        }
      },
    }
  }
</script>


<style scoped>

</style>

