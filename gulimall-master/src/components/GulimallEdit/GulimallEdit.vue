<template>
  <div v-if="visible">
    <el-row style="background-color: #2D64B3">
      <el-col :span="4">
        <el-button class="category-edit">
          <icon-svg :name="icon"/>&nbsp;&nbsp;{{name}}
        </el-button>
      </el-col>
      <el-col :span="20">
        <div style="float: right;margin-right: 10px;height: 40px;">
          <el-button class="category-edit" @click="closeEdit">
            <icon-svg name="cancel"/>
          </el-button>
        </div>
      </el-col>
    </el-row>
    <div style="margin-top: 10px; border:2px solid #bce8f1; border-radius: 10px; margin-bottom: 10px;">
      <slot></slot>
      <el-row style="margin-top: 20px; text-align: center; background-color: #bce8f1; border-radius: 10px;">
        <el-button-group>
          <el-button class="blue-background" @click="cancelEdit">
            <icon-svg name="cancel"/>&nbsp;取消
          </el-button>
          <el-button class="blue-background" @click="saveEdit" >
            <icon-svg name="save"/>&nbsp;保存
          </el-button>
        </el-button-group>
      </el-row>
    </div>
  </div>
</template>

<script>
  export default {
    name: 'GulimallEit',
    components: {},
    props: {
      icon: String,
      name: String,
      close: Function,
      cancel: Function
    },
    data () {
      return {
        visible: false
      }
    },
    methods: {
      closeEdit () {
        this.visible = false
        this.$emit('close')
      },
      cancelEdit () {
        this.visible = false
      },
      saveEdit () {
        this.visible = false
        this.$emit('submit')
      }
    }
  }
</script>

<style scoped>
  .category-edit {
    margin-left: 1px;
    margin-top: 1px;
    font-size: 16px;
    background-color: #2D64B3;
    color: white;
    border: 0px solid #2D64B3;
  }

  .blue-background {
    background-color: #409EFF;
    color: white;
  }
</style>
