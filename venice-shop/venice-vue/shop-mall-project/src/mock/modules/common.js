import Mock from "mockjs";

// 登录
export function login() {
  return {
    url: "/sys/login",
    type: "post",
    data: {
      msg: "success",
      code: 0,
      token: Mock.Random.string("abcdefghijklmnopqrstuvwxyz0123456789", 32)
    }
  };
}

// 退出
export function logout() {
  return {
    url: "/sys/logout",
    type: "post",
    data: {
      msg: "success",
      code: 0
    }
  };
}

// 获取验证码
export function createVcode() {
  return {
    url: "/sys/create_vcode",
    type: "get",
    data: {
      msg: "success",
      code: 0
    }
  };
}

// 获取侧边菜单
export function getSideMenu() {
  return {
    url: "/sys/sidemenu",
    type: "get",
    data: {
      msg: "success",
      code: 0,
      list: [
        {
          menuId: "1",
          name: "首页",
          icon: "home",
          routeName: "home"
        },
        {
          menuId: "5-3",
          name: "icons",
          icon: "appstore",
          routeName: "icons"
        },
        {
          menuId: "5",
          name: "示例",
          icon: "apps",
          routeName: undefined,
          list: [
            {
              menuId: "5-1",
              name: "单页面增删改查",
              icon: "add-circle-outline",
              routeName: "demo1"
            },
            {
              menuId: "5-2",
              name: "左侧树形条件",
              icon: "git-branch",
              routeName: "demo2"
            },
            {
              menuId: "5-3",
              name: "配置页面",
              icon: "filing",
              routeName: "demo3"
            }
          ]
        },
        {
          menuId: "6",
          name: "系统管理",
          icon: "setting",
          routeName: undefined,
          list: [
            {
              menuId: "6-1",
              name: "管理员列表",
              icon: "admin",
              routeName: "user"
            },
            {
              menuId: "6-2",
              name: "角色列表",
              icon: "role",
              routeName: "role"
            },
            {
              menuId: "6-3",
              name: "菜单列表",
              icon: "menu",
              routeName: "menu"
            },
            {
              menuId: "6-4",
              name: "SQL监控",
              icon: "sql",
              routeName: "sql"
            },
            {
              menuId: "6-5",
              name: "定时任务",
              icon: "timer",
              routeName: "schedule"
            },
            {
              menuId: "6-6",
              name: "参数管理",
              icon: "config",
              routeName: "config"
            },
            {
              menuId: "6-7",
              name: "文件上传",
              icon: "oss",
              routeName: "oss"
            },
            {
              menuId: "6-8",
              name: "系统日志",
              icon: "log",
              routeName: "log"
            }
          ]
        },
        {
          menuId: "5-5",
          name: "帮助页面",
          icon: "help-circle-outline",
          routeName: "help"
        },
        {
          menuId: "5-6",
          name: "忘记密码",
          icon: "lock",
          routeName: "forgetpwd"
        }
      ]
    }
  };
}

// 获取侧边菜单
export function getTreeMenu() {
  return {
    url: "/sys/treemenu",
    type: "get",
    data: {
      msg: "success",
      code: 0,
      title: "云安全",
      list: [
        {
          menuId: "tree1",
          name: "树形demo1",
          routeName: undefined,
          list: [
            {
              menuId: "tree1-1",
              name: "树形demo1-1",
              routeName: "tree1-1"
            }
          ]
        },
        {
          menuId: "tree2",
          name: "树形demo2",
          routeName: undefined,
          list: [
            {
              menuId: "tree2-1",
              name: "树形demo2-1",
              routeName: "tree2-1"
            }
          ]
        }
      ]
    }
  };
}
