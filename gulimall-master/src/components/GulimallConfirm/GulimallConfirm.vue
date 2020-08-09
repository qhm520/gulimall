<template>
  <el-dialog
    :close-on-click-modal="false"
    :show-close="false"
    :append-to-body="true"
    width="30%"
    :visible.sync="visible">
    <div slot="title" style="background-color: #E6A23C; margin-top: -20px;margin-left: -20px; margin-right: -20px">
      <el-button class="gulimall-confirm">
        <icon-svg name="tips"/>&nbsp;&nbsp;来自系统最深处的提示
      </el-button>
      <el-button class="gulimall-confirm" style="float: right;" @click="visible = false">
        <icon-svg name="cancel" />
      </el-button>
    </div>
   <el-row style="font-size: 18px">
     <icon-svg name="warning"/>&nbsp;&nbsp;<span style="display:inline;" v-html="config.content"></span>
   </el-row>
    <div slot="footer" style="text-align: center;">
       <el-button-group>
         <el-button type="primary" @click="visible = false"><icon-svg name="cancel"/>&nbsp;&nbsp;取消</el-button>
         <el-button type="danger" @click="confirm(true)"><icon-svg name="confirm"/>&nbsp;&nbsp;确定</el-button>
       </el-button-group>
    </div>
  </el-dialog>
</template>
<script>
  export default {
    data() {
      return {
        visible: false
      };
    },
    props: {
      config: {
        type: Object,
        default() {
          return {};
        }
      },
    },
    methods: {}
  };
</script>
<style scoped>
  .gulimall-confirm {
    margin-left: 1px;
    margin-top: 1px;
    font-size: 16px;
    background-color: #E6A23C;
    color: white;
    border: 0px solid #E6A23C;
  }

  .gulimall-button {
    margin-left: 1px;
    margin-top: 1px;
    background-color: #E6A23C;
    color: white;
    border: 0px solid #E6A23C;
  }
</style>
