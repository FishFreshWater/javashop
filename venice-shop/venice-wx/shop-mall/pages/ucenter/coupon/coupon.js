var API = require('../../../api_new/api.js');

var app = getApp();

Page({
  data: {
    CouponUserList: [],
    page: 1,
    size: 10,
    totalPages: 1,
    expir: 0,
    useable: 0,
    used: 0,
    loadmoreTxt: '正在加载更多数据',
    nomoreText: '全部加载完成',
    nomore: false,
    status: 1
  },
  onLoad: function(options) {

  },

  loadListData: function(status) {
    console.log('status', status)
    let that = this;
    if (that.data.totalPages <= that.data.page - 1) {
      that.setData({
        nomore: true
      })
      return;
    }
    //用户领取的优惠卷列表
    API.methodGet('coupon', 'CouponUserList', {
      page: that.data.page,
      size: that.data.size,
      status: status
    }).then(function(res) {
      if (res.code === 0) {
        that.setData({
          CouponUserList: that.data.CouponUserList.concat(res.data.page.list),
          page: res.data.page.currPage + 1,
          totalPages: res.data.page.totalPage,
          used: res.data.used,
          useable: res.data.useable,
          expir: res.data.expir
        });
        wx.hideLoading();
      }
    });
  },
  couponListKy: function() {
    this.setData({
      page: 1,
      size: 10,
      totalPages: 1,
      status: 1,
      CouponUserList: []
    })
    this.loadListData(this.data.status)
  },
  couponListYy: function() {
    this.setData({
      page: 1,
      size: 10,
      totalPages: 1,
      status: 2,
      CouponUserList: []
    })
    this.loadListData(this.data.status)
  },
  couponlistGq: function() {
    this.setData({
      page: 1,
      size: 10,
      totalPages: 1,
      status: 3,
      CouponUserList: []
    })
    this.loadListData(this.data.status)
  },
  onReachBottom: function() {
    console.log("下一页")
    let that = this;
    this.loadListData(that.data.status)
  },
  onReady: function() {

  },
  onShow: function() {
    this.loadListData('1')
  },
  onHide: function() {
    // 页面隐藏

  },
  onUnload: function() {
    // 页面关闭
  }
})