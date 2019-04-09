
var API = require('../../../api_new/api.js');

Page({
  data: {
    returnNumber: '',
    orderInfo: {},
    orderGoods: [],
    content: '',
    isCommit: false, //是否提交了物流单号
    index: 0, //默认物流
    array: ['顺丰速运', '申通快递', '中通快递', '圆通速递', '百世快递', '中国邮政', 'EMS', '天天快递', '宅急送', '其它物流公司'], //物流公司
    //commitTime: '2018-01-01',   //物流提交时间
    //logisticsCount: '123456789', //物流单号
    //expressCompany: '顺丰快递',  //物流公司名称
    returnId: '', //提交物流订单id
  },
  onLoad: function(options) {
    // 页面初始化 options为页面跳转所带来的参数
    console.log(options)
    this.setData({
      returnNumber: options.returnNumber
    });
    this.getOrderDetail();
  },
  onClose: function() {
    wx.navigateBack()
  },
  getOrderDetail() {
    let that = this;
    API.methodGet('afterSales', 'detail', {
      returnNumber: that.data.returnNumber
    }).then(function(res) {
      if (res.code === 0) {
        that.setData({
          orderInfo: res.data.orderInfo,
          orderReturn: res.data.orderReturn,
          orderGoods: res.data.orderGoods,
          returnId: res.data.orderReturn.id,
        });
        if (res.data.orderReturn.tracking != 'null') {
          that.setData({
            isCommit: true
          })
        }
      }
    });
  },
  cancelOrder() {

  },
  //选择物流公司
  bindPickerChange: function(e) {
    console.log('picker发送选择改变，携带值为', e.detail.value)
    this.setData({
      index: e.detail.value
    })
  },
  //物流单号
  formSubmitLogistics: function(e) {
    console.log('form发生了submit事件，携带数据为：', e.detail.value)
    let that = this
    let expressCrop = that.data.array[that.data.index]
    let tracking = e.detail.value.tracking;
    let id = that.data.returnId;
    let expressDesc = e.detail.value.expressDesc;
    API.methodPost('order', 'tracking', {
      id: id,
      tracking: tracking,
      expressCrop: expressCrop,
      expressDesc: expressDesc
    }).then(function(res) {
      if (res.code === 0) {
        wx.showToast({
          title: '提交成功',
          icon: 'success',
          duration: 2000
        })
        that.setData({
          isCommit: true
        })
        that.getOrderDetail()
      }
    });
  },
  //重新提交
  submitAgain: function() {
    this.setData({
      isCommit: false
    })
  },
  onReady: function() {
    // 页面渲染完成
  },
  onShow: function() {
    // 页面显示
  },
  onHide: function() {
    // 页面隐藏
  },
  onUnload: function() {
    // 页面关闭
  }
})