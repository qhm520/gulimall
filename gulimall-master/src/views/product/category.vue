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
            :default-expanded-keys="expandedKey"
            :draggable="draggable"
            :allow-drop="allowDrop"
            @node-drop="handleDrop"
            ref="menuTree"
            :highlight-current="true"
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
                    v-if="node.childNodes.length === 0"
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
    <gulimall-edit ref="category"  :icon="icon" :name="title" @submit="submit"
                   style="margin-top: 10px; margin-left: 44%; width: 40%; z-index: 1040; position: fixed;">
      <el-form ref="categoryForm" :model="category" :rules="dataRule" label-width="80px" style="width: 98%; text-align: center; margin-top: 10px">
        <el-form-item label="分类名称" prop="name">
          <el-input v-model="category.name" autocomplete="off" clearable></el-input>
        </el-form-item>
        <el-form-item label="图标" prop="icon">
          <el-input v-model="category.icon" autocomplete="off" clearable></el-input>
        </el-form-item>
        <el-form-item label="计量单位" prop="productUnit">
          <el-input v-model="category.productUnit" autocomplete="off" clearable></el-input>
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
        expandedKey: [],
        title: '',
        categoryType: '',  //add, edit 新增或修改
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
        icon: 'add',
        dataRule: {
          name: [{ required: true, message: "分类名称不能为空", trigger: "blur" }]
        }
      }
    },
    created() {
      this.getMenus()
      // this.$nextTick(() => {
      //   this.$refs.category.visible = false
      // })
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
      // 删除
      batchDelete() {
        let catIds = [];
        let names = []
        let checkedNodes = this.$refs.menuTree.getCheckedNodes();
        if (checkedNodes && checkedNodes.length > 0) {
          console.log("被选中的元素", checkedNodes);
          checkedNodes.forEach(item => {
            catIds.push(item.catId)
            names.push(item.name)
          })
          this.$GulimallConfirm(
            {content: `确定对<br/>[${names.join(',')}]<br/>进行[<span style="color: red;display:inline;">批量删除</span>]操作?`
            }).then((res) => {
            this.$http({
              url: this.$http.adornUrl("/product/category/delete"),
              method: "post",
              data: this.$http.adornData(catIds, false)
            }).then(({ data }) => {
              this.$message({
                message: "菜单批量删除成功",
                type: "success"
              });
              this.getMenus();
            })
          })
        } else {
          this.$message({
            message: "请选择要删除的菜单",
            type: "info"
          });
        }

      },
      handleDrop(draggingNode, dropNode, dropType, ev) {

      },
      allowDrop(draggingNode, dropNode, type) {

      },
      // 新增
      append(data) {
        this.clearValidate()
        this.categoryType = 'add'

        this.name = '新增分类'
        this.icon = 'add'
        this.$refs.category.visible = true
        console.log("append", data)
        this.title = this.name + '-' + data.name
        this.category.parentCid = data.catId
        this.category.catLevel = data.catLevel * 1 + 1
        this.category.catId = null
        this.category.name = ""
        this.category.icon = ""
        this.category.productUnit = ""
        this.category.sort = 0
        this.category.showStatus = 1
        // document.documentElement.scrollTop = 0  // 回到顶部
      },
      // 修改
      edit(data) {
        this.clearValidate()
        this.categoryType = 'edit'
        this.name = '修改分类'
        this.icon = 'edit'
        // document.documentElement.scrollTop = 0  // 回到顶部

        // this.category.name = data.name;
        // this.category.catId = data.catId;
        // this.category.icon = data.icon;
        // this.category.productUnit = data.productUnit;
        // this.category.parentCid = data.parentCid;
        // this.category.catLevel = data.catLevel;
        // this.category.sort = data.sort;
        // this.category.showStatus = data.showStatus;
        //发送请求获取当前节点最新的数据
        this.$http({
          url: this.$http.adornUrl(`/product/category/info/${data.catId}`),
          method: "get"
        }).then(({ data }) => {
          //请求成功

          console.log("要回显的数据", data)
          this.category.name = data.data.name
          this.category.catId = data.data.catId
          this.category.icon = data.data.icon
          this.category.productUnit = data.data.productUnit
          this.category.parentCid = data.data.parentCid
          this.category.catLevel = data.data.catLevel
          this.category.sort = data.data.sort
          this.category.showStatus = data.data.showStatus

          this.title = this.name + '-' + data.data.name
          this.$refs.category.visible = true
          /**
           *         parentCid: 0,
           catLevel: 0,
           showStatus: 1,
           sort: 0,
           */
        });
      },
      // 删除
      remove(node, data) {
        const ids = [data.catId];
        this.$GulimallConfirm({
          content: `确定对[${data.name}]进行[<span style="color: red;display:inline;">删除</span>]操作?`
        }).then(res=>{
          this.$http({
            url: this.$http.adornUrl("/product/category/delete"),
            method: "post",
            data: this.$http.adornData(ids, false)
          }).then(({ response }) => {
            this.$message({
              message: `菜单[${data.name}]删除成功`,
              type: "success"
            });
            // 直接在页面中去掉已删除的记录
            // const parent = node.parent;
            // const children = parent.data.children || parent.data;
            // const index = children.findIndex(d => d.catId === data.catId);
            // children.splice(index, 1);
            // 从数据库中重新获取菜单，刷新出新的菜单
            this.getMenus();
            //设置需要默认展开的菜单
            this.expandedKey = [node.parent.data.catId];
          });
        })
      },
      submit() {
        this.$refs['categoryForm'].validate((valid) => {
          if (valid) {
            if (this.categoryType === 'add') {
              this.addCategory()
            } else if (this.categoryType === 'edit') {
              this.editCategory()
            }
          } else {
            // GulimallEdit中的submit方法设置为 false，所以验证不通过时在这里需要显示
            this.$refs.category.visible = true
          }
        })
      },
      //修改三级分类数据
      editCategory() {
        var { catId, name, icon, productUnit } = this.category;
        this.$http({
          url: this.$http.adornUrl("/product/category/update"),
          method: "post",
          data: this.$http.adornData({ catId, name, icon, productUnit }, false)
        }).then(({ data }) => {
          this.$message({
            message: `菜单${name}修改成功`,
            duration: 1500,
            type: "success",
            onClose: () => {
            }
          });
          //关闭对话框
          this.$refs.category.visible = false
          //刷新出新的菜单
          this.getMenus();
          //设置需要默认展开的菜单
          this.expandedKey = [this.category.parentCid];

        });
      },

      //添加三级分类
      addCategory() {
        console.log("提交的三级分类数据", this.category);
        this.$http({
          url: this.$http.adornUrl("/product/category/save"),
          method: "post",
          data: this.$http.adornData(this.category, false)
        }).then(({ data }) => {
          this.$message({
            message: "菜单保存成功",
            type: "success"
          });
          //关闭对话框
          this.$refs.category.visible = false
          //刷新出新的菜单
          this.getMenus();
          //设置需要默认展开的菜单
          this.expandedKey = [this.category.parentCid];
        });
      },
      clearValidate() {
        this.$nextTick(()=>{
          try {
            this.$refs.categoryForm.clearValidate();
          } catch (e) {
          }
        })
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
</style>
