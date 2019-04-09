const app = getApp()
const appid = app.globalData.appid
const requestUrl = app.globalData.requestUrl

function request(apiUrl, config, resolve, reject) {
  let {
    method = 'GET',
    data = {},
    ...other
  } = { ...config }
  wx.showLoading({
    title: '加载中',
    mask: true
  })
  wx.request({
    url: requestUrl + apiUrl,
    data: { ...data },
    method: method,
    header: {
      'appid': appid,
      'Content-Type': 'application/json',
      'token': wx.getStorageSync('token')
    },
    success(res) {
      wx.hideLoading();
      if (res.statusCode == 200) {
        if (res.data.code == 401) {
          wx.showModal({
            title: '',
            content: '请先授权登录',
            success: function (res) {
              if (res.confirm) {
                wx.removeStorageSync("userInfo");
                wx.removeStorageSync("token");
                wx.navigateTo({
                  url: "/pages/auth/login/login"
                })
              }
            }
          });
        } else {
          resolve(res.data);
        }
      } else {
        reject(res.errMsg);
      }
    },
    fail(...err) {
      wx.hideLoading()
      reject(err)
    }
  })
}

module.exports = {
  request: request
}