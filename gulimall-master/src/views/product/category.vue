<template>
  <div class="mod-category">
    <div>
      <operation>
        <el-button-group>
          <el-button type="primary" v-if="draggable" @click="batchSave">
            <icon-svg name="save"/>&nbsp;批量保存
          </el-button>
          <el-button type="danger" @click="batchDelete">
            <icon-svg name="delete"/>&nbsp;批量删除
          </el-button>
          <el-button type="info" v-if="draggable" @click="draggable=!draggable">
            <el-switch v-model="draggable" style="height: 14px; margin-top: 1px;" @click.stop.native></el-switch>&nbsp;关闭拖拽
          </el-button>
          <el-button type="warning" v-else @click="draggable=!draggable">
            <el-switch v-model="draggable" style="height: 14px; margin-top: 1px;" @click.stop.native></el-switch>&nbsp;开启拖拽
          </el-button>
        </el-button-group>
      </operation>
      <!-- 树 -->
      <div class="custom-tree-container" style="margin-top: 10px;width: 50%; float: left">
        <div class="block">
          <el-tree
            :data="menus"
            :props="defaultProps"
            show-checkbox
            node-key="catId"
            :expand-on-click-node="false">
            <span class="custom-tree-node" slot-scope="{ node, data }">
              <span>{{ node.label }}</span>
              <span>
                <el-button-group>
                  <el-button
                    v-if="node.level <=2"
                    type="primary"
                    size="mini"
                    @click="() => append(data)">
                     <icon-svg name="add"/>&nbsp;新增
                  </el-button>
                  <el-button type="warning" size="mini" @click="edit(data)"><icon-svg name="edit"/>&nbsp;修改</el-button>
                  <el-button
                    type="danger"
                    size="mini"
                    @click="() => remove(node, data)">
                    <icon-svg name="delete"/>&nbsp;删除
                  </el-button>
                </el-button-group>
              </span>
            </span>
          </el-tree>
        </div>
      </div>
    </div>
    <gulimall-edit ref="category" icon="add" name="新增分类" @submit="submitData"
                   style="margin-top: 10px; float: left; width: 50%;">
      <el-form :model="category" label-width="80px" style="width: 98%; text-align: center; margin-top: 10px">
        <el-form-item label="分类名称" prop="name">
          <el-input v-model="category.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="图标" prop="icon">
          <el-input v-model="category.icon" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="计量单位" prop="productUnit">
          <el-input v-model="category.productUnit" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
    </gulimall-edit>
  </div>
</template>

<script>
  import Operation from "../../components/Operation/Operation";
  import GulimallEdit from "../../components/GulimallEdit/GulimallEdit";

  export default {
    name: "category",
    components: {Operation, GulimallEdit},
    data() {
      return {
        menus: [],
        draggable: false,
        defaultProps: {
          children: "children",
          label: "name"
        },
        category: {
          name: "",
          parentCid: 0,
          catLevel: 0,
          showStatus: 1,
          sort: 0,
          productUnit: "",
          icon: "",
          catId: null
        },
        name: '新增分类',
        icon: 'add'
      }
    },
    created() {
      this.getMenus()
      this.$nextTick(() => {
        this.$refs.category.visible = false
      })
    },
    methods: {
      getMenus() {
        this.$http({
          url: this.$http.adornUrl("/product/category/list/tree"),
          method: "get"
        }).then(({data}) => {
          console.log("成功获取到菜单数据...", data.data);
          this.menus = data.data;
        });
      },
      batchSave() {

      },
      batchDelete() {

      },
      append(data) {
        this.$refs.category.visible = true
        // const newChild = {id: id++, label: 'testtest', children: []};
        // if (!data.children) {
        //   this.$set(data, 'children', []);
        // }
        // data.children.push(newChild);
      },
      edit(data) {

      },
      remove(node, data) {
        const parent = node.parent;
        const children = parent.data.children || parent.data;
        const index = children.findIndex(d => d.id === data.id);
        children.splice(index, 1);
      },
      submitData() {

      }
    }
  }
</script>

<style scoped>
  .custom-tree-node {
    flex: 1;
    display: flex;
    align-items: center;
    justify-content: space-between;
    font-size: 14px;
    padding-right: 8px;
  }

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
