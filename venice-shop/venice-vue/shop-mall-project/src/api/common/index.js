import request from "../request";
import requestUrl from "../requestUrl";
import requestParam from "../requestParam";



// 获取左侧菜单
export function getSideMenu() {
  return request({
    url: requestUrl("/sys/menu/nav"),
    method: "get",
    data: requestParam()
  });
}

// 获取树形菜单，根据路由参数
export function getTreeMenu() {
  return request({
    url: requestUrl("/sys/treemenu"),
    method: "get",
    data: requestParam()
  });
}
// 富文本组件编辑器
export function ueditor () {
  return requestUrl(`/ueditor/config`)
}
// 文件上传写入附件
export function upload () {
  return requestUrl(`/upload`)
}
// 文件上传不写入附件
export function uploadurl () {
  return requestUrl(`/upload_url`)
}
// 文件ftp上传
export function uploadftpurl () {
  return requestUrl(`/upload_ftp`)
}
// 文件ftp上传
export function uploadlocal () {
  return requestUrl(`/upload_local`)
}