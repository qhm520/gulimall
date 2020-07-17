<template>
  <div>
    <el-table
      ref="gulimallTable"
      :data="tableData"
      @row-click="clickRow"
      highlight-current-row
      border
      v-loading="loading"
      @selection-change="selectionChangeHandle"
      style="width: 100%;">
      <slot></slot>
    </el-table>
    <page></page>
  </div>
</template>

<script>
  import Page from '../Page/Page'
  import { mapGetters } from 'vuex'
  export default {
    name: 'GulimallTable',
    data() {
      return {

      }
    },
    props: {
    },
    components: { Page },

    computed: {
      ...mapGetters({
        tableData: 'tableData',
        loading: 'loading'
      })
    },
    methods: {
      // 多选
      selectionChangeHandle(val) {
        // this.dataListSelections = val
        this.$store.dispatch('setTableSelectData', val);
      },
      /**
       * 点击行选择
       * @param row
       */
      clickRow(row) {
        this.$refs.gulimallTable.toggleRowSelection(row)
      }
    }
  }
</script>
<style scoped>

</style>
