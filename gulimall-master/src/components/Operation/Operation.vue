<template>
  <el-row style="background-color: #2D64B3">
    <el-button-group style="float: right; margin-right: -175px;">
      <slot name="add"></slot>
      <slot name="batchDelete"></slot>
    </el-button-group>
  </el-row>
</template>

<script>
  export default {
    name: "Operation"
  }
</script>

<style scoped>

</style>
