import Vue from 'vue'
import router from '@/router'
// 放抖动函数
export function debounce(func, wait, immediate) {
  let timeout, args, context, timestamp, result;

  const later = function () {
    // 据上一次触发时间间隔
    const last = +new Date() - timestamp;

    // 上次被包装函数被调用时间间隔last小于设定时间间隔wait
    if (last < wait && last > 0) {
      timeout = setTimeout(later, wait - last);
    } else {
      timeout = null;
      // 如果设定为immediate===true，因为开始边界已经调用过了此处无需调用
      if (!immediate) {
        result = func.apply(context, args);
        if (!timeout) context = args = null;
      }
    }
  };

  return function (...args) {
    context = this;
    timestamp = +new Date();
    const callNow = immediate && !timeout;
    // 如果延时不存在，重新设定延时
    if (!timeout) timeout = setTimeout(later, wait);
    if (callNow) {
      result = func.apply(context, args);
      context = args = null;
    }

    return result;
  };
}

/**
 * 树形数据转换
 * @param {*} data
 * @param {*} id
 * @param {*} pid
 */
export function treeDataTranslate(data, id = 'id', pid = 'parentId') {
  var res = []
  var temp = {}
  for (var i = 0; i < data.length; i++) {
    temp[data[i][id]] = data[i]
  }
  for (var k = 0; k < data.length; k++) {
    if (temp[data[k][pid]] && data[k][id] !== data[k][pid]) {
      if (!temp[data[k][pid]]['children']) {
        temp[data[k][pid]]['children'] = []
      }
      if (!temp[data[k][pid]]['_level']) {
        temp[data[k][pid]]['_level'] = 1
      }
      data[k]['_level'] = temp[data[k][pid]]._level + 1
      temp[data[k][pid]]['children'].push(data[k])
    } else {
      res.push(data[k])
    }
  }
  return res
}

// 换肤加class函数
export function toggleClass(element, className) {
  if (!element || !className) {
    return;
  }
  element.className = className;

  // loadStyle(className)
}
/**
 * 封装插入css   //有问题 无法引入 待优化
 */
function loadStyle(className) {
  let _classNameArr = className.split("-");
  let _className = _classNameArr[1];
  var styleList = [
    {
      id: 'J_auiSKin',
      url: '../assets/css/theme/' + _className + '/index.css?t=' + new Date().getTime()
    }
  ];
  for (var i = 0; i < styleList.length; i++) {
    var el = document.querySelector('#' + styleList[i].id);
    if (el) {
      el.href = styleList[i].url;
      continue;
    }
    el = document.createElement('link');
    el.id = styleList[i].id;
    el.href = styleList[i].url;
    el.rel = 'stylesheet';
    document.querySelector('head').appendChild(el);
  }
}
/**
 * 获取uuid
 */
export function getUUID() {
  return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, c => {
    return (c === 'x' ? (Math.random() * 16 | 0) : ('r&0x3' | '0x8')).toString(16)
  })
}
/**
 * 清除登录信息
 */
export function clearLoginInfo() {
  Vue.cookie.delete('token')
  // store.commit('resetStore')
  router.options.isAddDynamicMenuRoutes = false
}

/**
 * 是否有权限
 * @param {*} key
 */
export function isAuth(key) {
  return JSON.parse(sessionStorage.getItem('permissions') || '[]').indexOf(key) !== -1 || false
}

/**
 * josn 排序
 */
export function compare(property) {
  return function (a, b) {
    var value1 = a[property];
    var value2 = b[property];
    return value1 - value2;
  }
}

/**
 * css theme
 */

export function theme() {
  return sessionStorage.getItem('themecolor') || 'orange'
}