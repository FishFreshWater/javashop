import Vue from "vue";
import axios from "axios";
import router from "@/router";
import store from "@/store";
import qs from 'qs'
import merge from 'lodash/merge'
import { clearLoginInfo } from '@/utils'
// 创建axios实例
const service = axios.create({
  timeout: 1000 * 30,
  withCredentials: true,
  headers: {
    "Content-Type": "application/json; charset=utf-8"
  }
});

// request拦截器
service.interceptors.request.use(
  config => {
    store.commit(
      "common/updateMainLoadNum",
      store.state.common.mainLoadNum + 1
    );
    config.headers.common['token']=Vue.cookie.get("token");
    config.headers.common['appid']=Vue.cookie.get("appid");
    return config;
  },
  error => {
    store.commit(
      "common/updateMainLoadNum",
      store.state.common.mainLoadNum - 1
    );
    return Promise.reject(error);
  }
);

// response拦截器
service.interceptors.response.use(
  response => {
    store.commit(
      "common/updateMainLoadNum",
      store.state.common.mainLoadNum - 1
    );
    if (response.data && response.data.code === 401) {
      // 401, token失效
      Vue.cookie.delete("token");
      router.push({ name: "login" });
    }
    return response;
  },
  error => {
    store.commit(
      "common/updateMainLoadNum",
      store.state.common.mainLoadNum - 1
    );
    return Promise.reject(error);
  }
);
/**
 * 请求地址处理
 * @param {*} actionName action方法名称
 */
service.adornUrl = (actionName) => {
  // 非生产环境 && 开启代理, 接口前缀统一使用[/proxyApi/]前缀做代理拦截!
  return (process.env.NODE_ENV !== 'production' ? '/proxyApi' : '' ) + actionName
}

/**
 * get请求参数处理
 * @param {*} params 参数对象
 * @param {*} openDefultParams 是否开启默认参数?
 */
service.adornParams = (params = {}, openDefultParams = true) => {
  var defaults = {
    't': new Date().getTime()
  }
  return openDefultParams ? merge(defaults, params) : params
}

/**
 * post请求数据处理
 * @param {*} data 数据对象
 * @param {*} openDefultdata 是否开启默认数据?
 * @param {*} contentType 数据格式
 *  json: 'application/json; charset=utf-8'
 *  form: 'application/x-www-form-urlencoded; charset=utf-8'
 */
service.adornData = (data = {}, openDefultdata = true, contentType = 'json') => {
  var defaults = {
    't': new Date().getTime()
  }
  data = openDefultdata ? merge(defaults, data) : data
  return contentType === 'json' ? JSON.stringify(data) : qs.stringify(data)
}

export default service;
