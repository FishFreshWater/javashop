/**
 * 请求地址统一处理／组装
 * @param {*} actionName action方法名称
 */
export default function(actionName) {
  // 非生产环境 && 开启代理, 接口前缀统一使用[/proxyApi/]前缀做代理拦截!
  // console.log(process.env.OPEN_PROXY)
  // console.log(process.env.NODE_ENV)
  return (
    // (process.env.NODE_ENV !== "production" && process.env.OPEN_PROXY
    (process.env.NODE_ENV !== "production"
      ? "/proxyApi"
      : "") + actionName
  );
  // return actionName
}