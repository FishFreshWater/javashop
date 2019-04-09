var API = require('../../../api_new/api.js');

Page({
  data: {
    orderList: [],
    page: 1,
    size: 10,
    loadmoreText: '正在加载更多数据',
    nomoreText: '全部加载完成',
    nomore: false,
    totalPages: 1
  },
  onLoad: function(options) {
    // 页面初始化 options为页面跳转所带来的参数
    // 页面显示

    wx.showLoading({
      title: '加载中...',
      success: function() {

      }
    });
    this.getOrderList();
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function() {
    console.log("下一页")
    this.getOrderList()
  },

  getOrderList() {
    let that = this;
    console.log(that);
    if (that.data.totalPages <= that.data.page - 1) {
      that.setData({
        nomore: true
      })
      return;
    }
    API.methodGet('afterSales', 'list', {
      page: that.data.page,
      size: that.data.size
    }).then(function(res) {
      console.log(res);
      if (res.code === 0) {
        that.setData({
          orderList: res.data.list,
          page: res.data.currPage + 1,
          totalPages: res.data.totalPage
        });
        wx.hideLoading();
      }
    });
  },
  onReady: function() {
    // 页面渲染完成
  },
  onShow: function() {
    console.log("onShow")
    this.setData({
      page: 1
    })
    this.getOrderList();
  },
  onHide: function() {
    // 页面隐藏
  },
  onUnload: function() {
    // 页面关闭
  }
})