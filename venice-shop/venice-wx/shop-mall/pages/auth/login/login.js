// 统一的授权登录页面
const util = require('../../../utils/util.js');
const app = getApp();

const requestUrl = app.globalData.requestUrl
const appid = app.globalData.appid

Page({
  data: {
  },
  onLoad: function(){
  },
  onShow: function () {
  },
  loginByWeiXin: function (userInfo) {
    var that = this;
    util.login().then((res) => {
      wx.request({
        url: requestUrl + '/api/sh_auth/login_by_weixin',
        data: {
          code: res.code,
          userInfo: userInfo
        },
        method: "POST",
        header: {
          'appid': appid,
          'Content-Type': 'application/json'
        },
        success(res) {
          that.setData({
            userInfo: res.data.data.userInfo
          });
          wx.setStorageSync('userInfo', res.data.data.userInfo);
          wx.setStorageSync('token', res.data.data.token);
          app.globalData.userInfo = res.data.data.userInfo;
          app.globalData.token = res.data.data.token;
          wx.navigateBack();
        },
        fail(...err) {
          console.log(err)
        }
      });
    });
  },
  goBack(){
    wx.navigateBack();
  },
  bindGetUserInfo(e) {
    let userInfo = wx.getStorageSync('userInfo');
    let token = wx.getStorageSync('token');
    if (userInfo && token) {
      wx.navigateBack();
      return;
    }
    if (e.detail.userInfo) {
      //用户按了允许授权按钮
      this.loginByWeiXin(e.detail);
    } else {
      //用户按了拒绝按钮
      wx.showModal({
        title: '警告通知',
        content: '您点击了拒绝授权,将无法正常显示个人信息,点击确定重新获取授权。',
        success: function (res) {
          if (res.confirm) {
            wx.openSetting({
              success: (res) => {
                if (res.authSetting["scope.userInfo"]) { ////如果用户重新同意了授权登录
                  wx.getUserInfo({
                    success: function (res) {
                      that.loginByWeiXin(res);
                    }
                  })
                }
              }
            })
          }
        }
      });
    }
  }
})