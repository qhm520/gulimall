<template>
  <div>
    <el-input placeholder="输入关键字进行过滤" v-model="filterText" clearable></el-input>
    <gulimall-operation name="分类" icon="menu" style="margin-top: 22px">
      <el-button  type="primary"  @click="refreshCagegory()">
        <icon-svg name="refresh"/>
        &nbsp;刷新分类
      </el-button>
    </gulimall-operation>

    <el-tree
      :data="menus"
      :props="defaultProps"
      node-key="catId"
      ref="menuTree"
      @node-click="nodeClick"
      :filter-node-method="filterNode"
      :highlight-current="true"
      style="margin-top: 10px"
    ></el-tree>
  </div>
</template>

<script>
  import GulimallOperation from "../GulimcallOperation/GulimallOperation";
  export default {
    name: "GulimallCategory",
    components: {
      GulimallOperation
    },
    data() {
      //这里存放数据
      return {
        filterText: "",
        menus: [],
        expandedKey: [],
        defaultProps: {
          children: "children",
          label: "name"
        }
      };
    },
    //监控data中的数据变化
    watch: {
      filterText(val) {
        this.$refs.menuTree.filter(val);
      }
    },
    //方法集合
    methods: {
      //树节点过滤
      filterNode(value, data) {
        if (!value) return true;
        return data.name.indexOf(value) !== -1;
      },
      getMenus() {
        this.$http({
          url: this.$http.adornUrl("/product/category/list/tree"),
          method: "get"
        }).then(({ data }) => {
          this.menus = data.data;
          this.$message({
            message: '加载分类成功',
            type: 'success',
            duration: 1500,
          })
        });
      },
      nodeClick(data, node, component) {
        console.log("子组件category的节点被点击", data, node, component);
        //向父组件发送事件；
        this.$emit("tree-node-click", data, node, component);
      },
      // 刷新分类
      refreshCagegory() {
        this.getMenus();
      },
    },
    //生命周期 - 创建完成（可以访问当前this实例）
    created() {
      this.getMenus();
    },

  }
</script>

<style scoped>

</style>
