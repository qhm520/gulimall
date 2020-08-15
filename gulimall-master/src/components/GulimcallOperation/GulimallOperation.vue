<template>
  <el-row style="background-color: #2D64B3">
    <el-col :span="4">
      <el-button class="menuName"><icon-svg :name="icon ? icon : $route.meta.icon"/>&nbsp;&nbsp;{{name ? name : $route.name}}</el-button>
    </el-col>
    <el-col :span="20">
      <el-button-group style="float: right;margin-right: 10px;height: 40px;margin-top: 2px;">
        <slot></slot>
      </el-button-group>
    </el-col>
  </el-row>
</template>

<script>
  export default {
    name: 'Operation',
    props: {
      icon: String,
      name: String
    }
  }
</script>

<style scoped>
  .menuName {
    margin-left: 1px;
    margin-top: 1px;
    font-size: 16px;
    background-color: #2D64B3;
    color: white;
    border: 0px solid #2D64B3;
  }
</style>
