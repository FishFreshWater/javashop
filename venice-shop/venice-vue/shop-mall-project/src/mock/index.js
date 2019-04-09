import Mock from "mockjs";
import * as common from "./modules/common";
import * as user from "./modules/user";
import * as demo from "./modules/demo";
import * as system from "./modules/system";
import * as sysUser from "./modules/sys-user";
import * as sysRole from "./modules/sys-role";
import * as sysMenu from "./modules/sys-menu";
import * as sysLog from "./modules/sys-log";
import * as sysConfig from "./modules/sys-config";
import * as sysOss from "./modules/sys-oss";
import * as sysSchedule from "./modules/sys-schedule";

// tips
// 1. 开启/关闭[业务模块]拦截, 通过调用fnCreate方法[isOpen参数]设置.
// 2. 开启/关闭[业务模块中某个请求]拦截, 通过函数返回对象中的[isOpen属性]设置.
fnCreate(common, false);
fnCreate(user, false);
fnCreate(demo, false);
fnCreate(system, false);
fnCreate(sysUser, false);
fnCreate(sysRole, false);
fnCreate(sysMenu, false);
fnCreate(sysLog, false);
fnCreate(sysConfig, false);
fnCreate(sysOss, false);
fnCreate(sysSchedule, false);
Mock.setup({
  timeout: "500-800"
});
/**
 * 创建mock模拟数据
 * @param {*} mod 模块
 * @param {*} isOpen 是否开启?
 */
function fnCreate(mod, isOpen = false) {
  if (isOpen) {
    for (var key in mod) {
      (res => {
        if (res.isOpen !== false) {
          Mock.mock(new RegExp(res.url), res.type, opts => {
            opts["data"] = opts.body ? JSON.parse(opts.body) : null;
            delete opts.body;
            // console.log("\n");
            // console.log("%cmock拦截, 请求: ", "color:blue", opts);
            // console.log("%cmock拦截, 响应: ", "color:blue", res.data);
            return res.data;
          });
        }
      })(mod[key]() || {});
    }
  }
}
