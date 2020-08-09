import GulimallConfirm from "../components/GulimallConfirm/GulimallConfirm";
let Confirm = {};
Confirm.install = function (Vue, options) {
  const ConfirmViewConstructor = Vue.extend(GulimallConfirm)
  // 生成一个该子类的实例
  const instance = new ConfirmViewConstructor();
  Vue.prototype.$GulimallConfirm = (config) => {
    // 这里 return 一个 Promise
    // 在项目中使用的时候，就可以进行链式调用了~
    return new Promise((resolve, reject) => {
      instance.config = config;
      instance.visible = true;
      // 确定按键
      instance.confirm = () => {
        instance.visible = false;
        resolve(true)
      }
      // 取消按键
      // instance.cancle = () => {
      //   instance.visible = false;
      //   resolve(false)
      // }
    })
  }

  // 将这个实例挂载在我创建的div上
  // 并将此div加入全局挂载点内部
  instance.$mount(document.createElement('div'))
  document.body.appendChild(instance.$el)
}

export default Confirm;
