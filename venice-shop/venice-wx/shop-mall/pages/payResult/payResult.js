var util = require('../../utils/util.js');
const API = require('../../api_new/api.js');
const pay = require('../../services/pay.js');

var app = getApp();
Page({
  data: {
    status: false,
    orderNumber: ''
  },
  onLoad: function (options) {
    // 页面初始化 options为页面跳转所带来的参数
    console.log(options)
    this.setData({
      orderNumber: options.orderNumber || 24,
      status: options.status
    })
    this.updateSuccess()
  },
  onReady: function () {
  },
  onShow: function () {
    // 页面显示

  },
  onHide: function () {
    // 页面隐藏

  },
  onUnload: function () {
    // 页面关闭
    
  },
  
  updateSuccess: function () {
    let that = this
    API.methodPost('order', 'OrderQuery', { orderNumber: this.data.orderNumber}).then(function (res) {
      if(res.code === 0){
        that.setData({
          status: true
        });
      }
    })
  },

  payOrder() {
    console.log(this.data)
    pay.payOrder(this.data.orderNumber).then(res => {
      this.setData({
        status: true
      });
    }).catch(res => {
      util.showErrorToast('支付失败');
    });
  }
})