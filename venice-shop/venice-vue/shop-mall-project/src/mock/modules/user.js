import Mock from "mockjs";

// 获取用户信息
export function getUserInfo() {
  return {
    url: "/sys/user/info",
    type: "get",
    data: Mock.mock({
      msg: "success",
      code: 0,
      user: {
        userId: "@increment",
        userName: "@name(true)"
      }
    })
  };
}

// 获取最新消息
// 获取用户信息
export function getUserMessage() {
  return {
    url: "/sys/user/message",
    type: "get",
    data: Mock.mock({
      msg: "success",
      code: 0,
      list: [
        {
          state: "公告",
          time: "2018-08-11 00:00",
          title: "2018年7月17日苏州集群升级影响通告",
          rank: 1
        },
        {
          state: "通知",
          time: "2018-08-10 00:00",
          title: "2018年7月17日苏州集群升级影响通告",
          rank: 2
        }
      ]
    })
  };
}
