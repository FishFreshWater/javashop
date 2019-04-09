var util = require('../../../utils/util.js');
var API = require('../../../api_new/api.js');

var app = getApp();

Page({
  data: {
    couponList: [],
    couponListUse: [],
    couponListNoUse: [],
    buyType: ''
  },
  onLoad: function(options) {
    console.log('selConpon-options', options)
    this.data.buyType = options.buyType
    this.loadListData()
  },

  loadListData: function() {
    let that = this;
    API.methodGet('shopping', 'ListByGoods', {
      type: that.data.buyType
    }).then(function(res) {
      console.log('ListByGoods')
      console.log(res);
      if (res.code === 0) {
        that.setData({
          couponListUse: res.data.useCoupons,
          couponListNoUse: res.data.notUseCoupons,
          couponList: res.data.useCoupons.concat(res.data.notUseCoupons)
        })
        wx.hideLoading();
      }
    });
  },

  /**
   * 点击不使用优惠券
   * 返回上一页
   */
  noUseCoupon: function() {
    app.globalData.userCoupon = 'NO_USE_COUPON'
    wx.navigateBack({})
  },
  //选择优惠卷
  tapCoupon: function(event) {
    console.log(event)
    let item = event.currentTarget.dataset.item
    console.log(item)
    if (item.enabled == 0) {
      return
    }
    app.globalData.userCoupon = 'USE_COUPON'
    app.globalData.courseCouponCode = item
    wx.navigateBack({})
  }
})