import request from "../request";
import requestUrl from "../requestUrl";
import requestParam from "../requestParam";

// 获取
export function getPublishData() {
  return request({
    url: requestUrl("/sys/publishData"),
    method: "post",
    data: requestParam()
  });
}

export function getSysData() {
  return request({
    url: requestUrl("/sys/sysData"),
    method: "post",
    data: requestParam()
  });
}

export function getServeData() {
  return request({
    url: requestUrl("/sys/serveData"),
    method: "post",
    data: requestParam()
  });
}

export function getErrorData() {
  return request({
    url: requestUrl("/sys/errorData"),
    method: "post",
    data: requestParam()
  });
}
