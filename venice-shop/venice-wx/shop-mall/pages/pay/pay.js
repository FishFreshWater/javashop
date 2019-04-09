var app = getApp();
var API = require('../../api_new/api.js');

Page({
  data: {
    orderNumber: '',
    actualPrice: 0.00
  },
  onLoad: function(options) {
    // 页面初始化 options为页面跳转所带来的参数
    console.log('pay')
    this.setData({
      orderNumber: options.orderNumber,
      actualPrice: options.actualPrice
    })
  },
  onReady: function() {

  },
  onShow: function() {
    // 页面显示

  },
  onHide: function() {
    // 页面隐藏

  },
  onUnload: function() {
    // 页面关闭

  },
  //向服务请求支付参数
  requestPayParam() {
    let that = this;
    API.methodPost('order', 'PayPrepayId', {
      orderNumber: that.data.orderNumber,
      payType: 1
    }).then(function(res) {
      if (res.errno === 0) {
        let payParam = res.data;
        wx.requestPayment({
          'timeStamp': payParam.timeStamp,
          'nonceStr': payParam.timeStamp,
          'package': payParam.nonceStr,
          'signType': payParam.signType,
          'paySign': payParam.paySign,
          'success': function(res) {
            wx.redirectTo({
              url: '/pages/payResult/payResult?status=true&orderNumber=' + that.data.orderNumber
            })
          },
          'fail': function(res) {
            wx.redirectTo({
              url: '/pages/payResult/payResult?status=false&orderNumber=' + that.data.orderNumber
            })
          }
        })
      }
    });
  },
  startPay() {
    this.requestPayParam();
  }
})