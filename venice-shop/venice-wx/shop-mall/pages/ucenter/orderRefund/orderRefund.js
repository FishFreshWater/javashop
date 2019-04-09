var util = require('../../../utils/util.js');
var API = require('../../../api_new/api.js');

Page({
  data: {
    orderNumber: '',
    orderInfo: {},
    orderGoods: [],
    content: '',
    handleOption: {}
  },
  onLoad: function (options) {
    // 页面初始化 options为页面跳转所带来的参数
    console.log(options)
    this.setData({
      orderNumber: options.orderNumber
    });
    this.getOrderDetail();
  },
  onClose:function(){
    wx.navigateBack()
  },
  onPost:function(){
    let that = this;
    if (!that.data.content) {
      util.showErrorToast('请填写退款理由！')
      return false;
    }
    API.methodPost('order', 'OrderRefund', {
      orderNumber: that.data.orderNumber,
      comment: that.data.content
    }).then(function (res) {
      if (res.code === 0) {
        util.redirect('/pages/ucenter/afterSalesDetail/afterSalesDetail?returnNumber='+res.returnNumber);
      }
    });
  },
  bindInpuntValue(event) {
    let value = event.detail.value;
    //判断是否超过140个字符
    if (value && value.length > 140) {
      return false;
    }
    this.setData({
      content: event.detail.value,
    })
  },
  getOrderDetail() {
    let that = this;
    API.methodPost('order', 'OrderDetail',{
      orderNumber: that.data.orderNumber
    }).then(function (res) {
      if (res.code === 0) {
        console.log(res.data);
        that.setData({
          orderInfo: res.data.orderInfo,
          orderGoods: res.data.orderGoods,
          handleOption: res.data.handleOption
        });
      }
    });
  },
  cancelOrder() {

  },
  onReady: function () {
    // 页面渲染完成
  },
  onShow: function () {
    // 页面显示
  },
  onHide: function () {
    // 页面隐藏
  },
  onUnload: function () {
    // 页面关闭
  }
})