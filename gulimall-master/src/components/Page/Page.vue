<template>
  <div>
    <el-col :span="24" class="toolbar" style="padding-bottom:10px;">
      <el-pagination @size-change="handleSizeChange"
                     @current-change="handleCurrentChange"
                     :current-page="currentPage"
                     :page-sizes="[10,20,50,100]"
                     :page-size="pageSize"
                     layout="total, sizes, prev, pager, next, jumper"
                     background
                     :total="total"
                     style="float:right">
      </el-pagination>
    </el-col>
  </div>
</template>

<script>
  import {mapGetters} from 'vuex'

  export default {
    name: 'Page',
    computed: {
      ...mapGetters({
        currentPage: 'currentPage',
        pageSize: 'pageSize',
        total: 'total'
      })
    },
    methods: {
      handleSizeChange(val) {
        this.$store.dispatch('setPageSize', val);
      },
      handleCurrentChange(val) {
        this.$store.dispatch('setCurrentPage', val);
      }
    }
  }
</script>
