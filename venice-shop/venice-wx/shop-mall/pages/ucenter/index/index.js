var util = require('../../../utils/util.js');
var user = require('../../../services/user.js');
const app = getApp();

const requestUrl = app.globalData.requestUrl
const appid = app.globalData.appid

Page({
  data: {
    userInfo: {},
    hasMobile: '',
    payed: '',//待发货
    pending:'',//待支付
    send: '',//待收货,
    refund:''//售后
  },
  onLoad: function(options) {
    // 页面初始化 options为页面跳转所带来的参数
  },
  onReady: function() {

  },
  onShow: function() {

    let userInfo = wx.getStorageSync('userInfo');
    let token = wx.getStorageSync('token');
    // 页面显示
    if (userInfo && token) {
      app.globalData.userInfo = userInfo;
      app.globalData.token = token;

      this.getOrderIndex()
    }

    this.setData({
      userInfo: app.globalData.userInfo,
    });

  },
  onHide: function() {
    // 页面隐藏

  },
  onUnload: function() {
    // 页面关闭
  },

  loginByWeiXin: function (userInfo){
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
          console.log(res)

          that.setData({
            userInfo: res.data.data.userInfo
          });
          wx.setStorageSync('userInfo', res.data.data.userInfo);
          wx.setStorageSync('token', res.data.data.token);
          app.globalData.userInfo = res.data.data.userInfo;
          app.globalData.token = res.data.data.token;
        },
        fail(...err) {
          console.log(err)
        }
      });
    });
  },

  bindGetUserInfo(e) {
    let userInfo = wx.getStorageSync('userInfo');
    let token = wx.getStorageSync('token');
    let that=this;
    if (userInfo && token) {
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
        success: function(res) {
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
  },
  getOrderIndex: function () {
    var that = this;
    wx.request({
      url: requestUrl + '/api/sh_order/index',
      data: {
      },
      method: "POST",
      header: {
        'appid': appid,
        'Content-Type': 'application/json',
        'token': wx.getStorageSync('token')
      },
      success(res) {
        console.log('order/index')
        console.log(res)
        let data = res.data.data
        that.setData({
          payed: data.payed,
          send: data.send,
          pending: data.pending,
          refund: data.refund
        })
      },
      fail(...err) {
        console.log(err)
      }
    });
  },
  exitLogin: function() {
    wx.showModal({
      title: '',
      confirmColor: '#b4282d',
      content: '退出登录？',
      success: function(res) {
        if (res.confirm) {
          wx.removeStorageSync('token');
          wx.removeStorageSync('userInfo');
          wx.switchTab({
            url: '/pages/index/index'
          });
        }
      }
    })

  }
})