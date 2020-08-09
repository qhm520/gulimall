<template>
  <el-dialog
    :close-on-click-modal="false"
    :show-close="false"
    :append-to-body="true"
    :visible.sync="visible">

    <div slot="title" style="background-color: #2D64B3; margin-top: -20px;margin-left: -20px; margin-right: -20px">
      <!--<icon-svg name="add" /> 新增参数-->
      <el-button class="gulimall-dialog">
        <icon-svg :name="icon"/>&nbsp;&nbsp;{{title}}
      </el-button>
      <el-button class="gulimall-dialog" style="float: right;" @click="visible = false">
        <icon-svg name="cancel" />
      </el-button>
    </div>

    <slot></slot>

    <div slot="footer" style="text-align: center; ">
      <el-button-group>
        <el-button type="primary" @click="visible = false"><icon-svg name="cancel"/>&nbsp;&nbsp;取消</el-button>
        <el-button class="blue-background" @click="saveDialog()"><icon-svg name="save"/>&nbsp;&nbsp;保存</el-button>
      </el-button-group>
    </div>
  </el-dialog>
</template>

<script>
  export default {
    name: 'ConfigDialog',
    data () {
      return {
        visible: false
      }
    },
    props: {
      icon: String,
      title: String
    },
    methods: {
      saveDialog () {
        this.$parent.submit()
      },
      openDialog() {
        this.visible = true
      },
      closeDialog() {
        this.visible = false
      }

    }
  }
</script>


<style scoped>
  .gulimall-dialog {
    margin-left: 1px;
    margin-top: 1px;
    font-size: 16px;
    background-color: #2D64B3;
    color: white;
    border: 0px solid #2D64B3;
  }
</style>

