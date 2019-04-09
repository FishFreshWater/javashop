var API = require('../../../api_new/api.js');
var app = getApp();

Page({
  data: {
    couponList: [],
    page: 1,
    size: 10,
    loadmoreText: '正在加载更多数据',
    nomoreText: '全部加载完成',
    nomore: false,
    totalPages: 1,
  },
  onLoad: function(options) {
    this.loadListData()
  },

  loadListData: function() {
    let that = this;
    if (that.data.totalPages <= that.data.page - 1) {
      that.setData({
        nomore: true
      })
      return;
    }
    //可以用带优惠卷列表
    API.methodGet('coupon', 'CouponList', {
      page: that.data.page,
      size: that.data.size
    }).then(function(res) {
      console.log('CouponList')
      console.log(res);
      if (res.code === 0) {
        that.setData({
          couponList: that.data.couponList.concat(res.data.content),
          page: res.data.currPage + 1,
          totalPages: res.data.totalPage
        });
        wx.hideLoading();
      }
    });
  },
  //点击领取优惠券
  CouponExchange: function(event) {
    console.log(event)
    let that = this
    let couponId = event.currentTarget.dataset.setid;
    API.methodPost('coupon', 'CouponExchange', {
      id: couponId
    }).then(function(res) {
      if (res.code === 0) {
        wx.showToast({
          title: '领取成功',
          icon: 'success',
          duration: 2000
        })
        that.setData({
          page: 1,
          size: 10,
          totalPages: 1,
          couponList: []
        })
        that.loadListData()
      }
    })
  },
  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function() {
    console.log("下一页")
    let that = this;
    that.loadListData()
  },
  onReady: function() {

  },
  onShow: function() {

  },
  onHide: function() {
    // 页面隐藏

  },
  onUnload: function() {
    // 页面关闭
  }
})