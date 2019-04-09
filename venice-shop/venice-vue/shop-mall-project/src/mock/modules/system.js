export function getPublishData() {
  return {
    url: "/sys/publishData",
    type: "post",
    data: {
      msg: "success",
      code: 0,
      result: {
        lastNum: 1,
        totalNum: 10,
        chartData: {
          lineData: [21, 27, 22, 20, 23, 25, 24],
          xData: [
            "20180512",
            "20180513",
            "20180514",
            "20180515",
            "20180516",
            "20180517",
            "20180518"
          ],
          nameData: "最近七日打开次数"
        }
      }
    }
  };
}

export function getSysData() {
  return {
    url: "/sys/sysData",
    type: "post",
    data: {
      msg: "success",
      code: 0,
      result: {
        lastNum: 2,
        totalNum: 20,
        chartData: {
          lineData: [200, 120, 9, 30, 9, 10, 20],
          xData: [
            "20180512",
            "20180513",
            "20180514",
            "20180515",
            "20180516",
            "20180517",
            "20180518"
          ],
          nameData: "最近七日新增用户数"
        }
      }
    }
  };
}

export function getServeData() {
  return {
    url: "/sys/serveData",
    type: "post",
    data: {
      msg: "success",
      code: 0,
      result: {
        lastNum: 3,
        totalNum: 30,
        chartData: {
          lineData: [20, 30, 10, 30, 14, 32, 9],
          xData: [
            "20180512",
            "20180513",
            "20180514",
            "20180515",
            "20180516",
            "20180517",
            "20180518"
          ],
          nameData: "最近七日访问人数"
        }
      }
    }
  };
}

export function getErrorData() {
  return {
    url: "/sys/errorData",
    type: "post",
    data: {
      msg: "success",
      code: 0,
      result: {
        lastNum: 0,
        totalNum: 5,
        chartData: {
          lineData: [2, 4, 0, 10, 20, 6, 1],
          xData: [
            "20180512",
            "20180513",
            "20180514",
            "20180515",
            "20180516",
            "20180517",
            "20180518"
          ],
          nameData: "最近七日人均停留时长"
        }
      }
    }
  };
}
