import Mock from "mockjs";

var demo1List = [];
for (var i = 0; i < 10; i++) {
  demo1List.push(
    Mock.mock({
      id: "@integer(60, 1000)",
      status: '@pick(["运行中", "已关机", "已到期"])',
      way: '@pick(["全部", "预付费", "后付费"])',
      time: "@datetime",
      publicIp: "@ip",
      networkIp: "@ip",
      settingType: '@pick(["类型1", "类型2", "类型3"])',
      tag: '@pick(["标签1", "标签2", "标签3"])'
    })
  );
}

// 获取示例1数据
export function getDemo1Data() {
  return {
    url: "/sys/demo1/data",
    type: "post",
    data: Mock.mock({
      msg: "success",
      code: 0,
      result: {
        total: 100,
        list: demo1List
      }
    })
  };
}

// 获取树形菜单数据
export function getTreeData() {
  return {
    url: "/sys/demo/tree",
    type: "post",
    data: {
      msg: "success",
      code: 0,
      result: [
        {
          id: 1,
          label: "一级 1",
          children: [
            {
              id: 4,
              label: "二级 1-1",
              children: [
                {
                  id: 9,
                  chkVal: "slc1",
                  label: "三级 1-1-1"
                },
                {
                  id: 10,
                  chkVal: "slc2",
                  label: "三级 1-1-2"
                }
              ]
            }
          ]
        },
        {
          id: 2,
          label: "一级 2",
          children: [
            {
              id: 5,
              chkVal: "slc3",
              label: "二级 2-1"
            },
            {
              id: 6,
              chkVal: "slc4",
              label: "二级 2-2"
            }
          ]
        },
        {
          id: 3,
          label: "一级 3",
          children: [
            {
              id: 7,
              chkVal: "slc5",
              label: "二级 3-1"
            },
            {
              id: 8,
              chkVal: "slc6",
              label: "二级 3-2"
            }
          ]
        }
      ]
    }
  };
}
