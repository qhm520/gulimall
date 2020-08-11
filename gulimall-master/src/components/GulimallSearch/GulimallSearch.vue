<template>
  <el-form-item style="float: right">
    <el-button-group>
      <el-button class="blue-background" @click="search">
        <icon-svg name="search"/>
        &nbsp;查询
      </el-button>
      <el-button class="blue-background" @click="reset">
        <icon-svg name="clear"/>
        &nbsp;重置
      </el-button>
    </el-button-group>
  </el-form-item>
</template>

<script>
  export default {
    name: 'GulimallSearch',
    props: {
      search: Function,
      reset: Function
    },
    methods: {}
  }
</script>

<style scoped>

</style>
