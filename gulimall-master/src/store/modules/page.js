/**
 * 数据列表的公共参数
 * @author QIAN
 */
import axios from '../../utils/httpRequest'

const state = {
  total: 0,  // 记录列表总数
  tableData: [], // 列表数据
  tableSelectData: [], // 选中行列表数据
  url: '',   // 查询列表的url
  formData: {},   // 请求参数,
  loading: true  // 是否需要加载提示
}

const actions = {

  /**
   * 列表查询
   * @param state 全局state
   * @param params 请求参数
   */
  query({state}, params) {
    state.url = params.url
    //  如果是首次加载，初始化查询条件
    if (params.type === "init") {
      state.total = 0
      state.tableData = []
      state.formData.pageNumber = 1
      state.formData.pageSize = 10
    }
    let formData = params.formData
    formData.pageNumber = state.formData.pageNumber
    formData.pageSize = state.formData.pageSize

    if (formData.pageNumber === 0) {
      formData.pageNumber = 1
    }
    if (formData.pageSize === 0) {
      formData.pageSize = 10
    }
    state.formData = formData
    query()
  },

  //  设置页数
  setCurrentPage({commit, state}, currentPage) {
    state.formData.pageNumber = currentPage
    query()
  },

  //  设置每页显示多少条
  setPageSize({commit, state}, pageSize) {
    state.formData.pageSize = pageSize
    query()
  },

  //  列表页面首次加载初始化分页信息，并查询
  initPage({state}) {
    state.total = 0
      state.tableData = []
      state.formData.pageNumber = 1
    state.formData.pageSize = 10
    query()
  },

  //  在列表中把删除的数据清掉
  flushData({state, commit}, {rows}) {
    commit('flushData', rows)
  },
  //  更新列表数据状态
  flushStatus({state, commit}, {id, status}) {
    commit('flushStatus', {id: id, status: status})
  },
  //  批量更新列表数据状态
  batchFlushStatus({state, commit}, {rows, status}) {
    commit('batchFlushStatus', {rows: rows, status: status})
  },
  setTableSelectData({commit, state}, data) {
    state.tableSelectData = data
  }

}

const mutations = {
  //  设置当前页数
  setCurrentPage(state, currentPage) {
    state.formData.currentPage = currentPage
  },
  //  设置当前显示条数
  setPageSize(state, pageSize) {
    state.formData.pageSize = pageSize
  },

  //  在列表中把删除的数据清掉
  flushData(state, rows) {
    for (let row of rows) {
      let index = state.tableData.findIndex(x => x.id == row.id)
      state.tableData.splice(index, 1)
    }
  },
  flushStatus(state, {id, status}) {
    for (let row of state.tableData) {
      if (row.id == id) {
        row.stat = status
      }

    }
  },
  batchFlushStatus(state, {rows, status}) {
    for (let row of rows) {
      let data = state.tableData.find(x => x.id == row.id)
      data.stat = status
    }
  },
  setTableSelectData(state, {data}) {
    state.tableSelectData = data
  }

}

const getters = {
  currentPage(state) {
    return state.formData.currentPage
  },
  pageSize(state) {
    return state.formData.pageSize
  },
  total(state) {
    return state.total
  },
  tableData(state) {
    return state.tableData
  },
  loading(state) {
    return state.loading
  },
  tableSelectData (state) {
    return state.tableSelectData
  },
  formData(state) {
    return state.formData
  }
}

/**
 * 公用查询列表数据
 * @returns {Promise.<TResult>|Promise<R2|R1>|Promise<R>}
 */
function query() {
  state.loading = false
  axios({
    url: axios.adornUrl(state.url),
    method: 'get',
    params: axios.adornParams(state.formData)
  }).then(({data}) => {
    if (data && data.code === 0) {
      state.total = data.page.totalCount
      state.tableData = data.page.list
    } else {
      state.total = 0
      state.tableData = []
      state.formData.pageNumber = 1
      state.formData.pageSize = 10
    }
    state.loading = false
  })
}

export default {
  state,
  actions,
  mutations,
  getters
}
