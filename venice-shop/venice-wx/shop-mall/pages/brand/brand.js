var API = require('../../api_new/api.js');
Page({
  data: {
    brandList: [],
    page: 1,
    size: 10,
    totalPage: 1
  },
  onLoad: function(options) {
    // 页面初始化 options为页面跳转所带来的参数
    this.getBrandList();
  },
  getBrandList: function() {
    wx.showLoading({
      title: '加载中...',
    });
    let that = this;
    API.methodGet('brand', 'brandList', {
      page: this.data.page,
      size: this.data.size
    }).then(res => {
      console.log(res)
      if (res.code === 0) {
        this.setData({
          brandList: this.data.brandList.concat(res.items.content),
          totalPage: res.items.totalPage
        });
      }
      wx.hideLoading();
    })
  },
  onReachBottom() {
    if (this.data.totalPage > this.data.page) {
      this.setData({
        page: this.data.page + 1
      });
    } else {
      return false;
    }

    this.getBrandList();
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

  }
})