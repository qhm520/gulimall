<template>
  <el-row style="background-color: #2D64B3">
    <el-button class="menuName"><icon-svg :name="$route.meta.icon"/>&nbsp;&nbsp;{{$route.name}}</el-button>
    <el-button-group style="float: right; margin-top: 2px; margin-right: -182px;height: 40px">
      <slot></slot>
    </el-button-group>
  </el-row>
</template>

<script>
  export default {
    name: 'Operation',
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
