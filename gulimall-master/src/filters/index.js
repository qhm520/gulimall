import Vue from 'vue'
// import moment from 'moment'
import format from 'date-fns/format'
// 自定义过滤器
Vue.filter('date-format', function (value, formatStr = 'yyyy-MM-dd HH:mm:ss') {
  // return moment(value).format(formatStr)
  return format(value, formatStr)
})

/**
 * 日期转换成String
 * @param value
 * @param formatStr
 * @returns {any}
 */
export function dateFormat(value, formatStr = 'yyyy-MM-dd HH:mm:ss') {
  return value ? format(value, formatStr) : ''
}
