<template>
  <div slot="title" class="title">
    <icon-svg name="add64" v-if="isAdd"/>
    <icon-svg name="update64" v-else/>
     &nbsp;&nbsp; {{ title }}
    <div class="button-right">
      <span class="title-close"></span>
    </div>
  </div>
</template>

<script>
  export default {
    name: "DialogTitle",
    props: {
      isAdd: Boolean,
      title: String
    }
  }
</script>

<style scoped>
  .title {
    font-size: 20px;
    color: black;
    letter-spacing: 0;
    margin-top: -40px;
    margin-bottom: 20px;
  }
</style>
