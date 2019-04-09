
const API = require('../../api_new/api.js');
var app = getApp();

Page({
  data: {
    id: 0,
    brand: {},
    goodsList: [],
    page: 1,
    size: 1000
  },
  onLoad: function(options) {
    // 页面初始化 options为页面跳转所带来的参数
    var that = this;
    that.setData({
      id: parseInt(options.id)
    });
    this.getBrand();
  },
  getBrand: function() {
    let that = this;
    API.methodGet('brand', 'brandDetail', {
      id: that.data.id
    }).then((res => {
      if (res.code === 0) {
        that.setData({
          brand: res.brand
        });
        that.getGoodsList();
      }
    }));
  },
  getGoodsList() {
    var that = this;
    API.methodGet('product', 'GoodsList', {
      brandId: that.data.id,
      page: this.data.page,
      size: this.data.size
    }).then(res => {
      if (res.code === 0) {
        this.setData({
          goodsList: res.page.list,
          // totalPage: res.items.totalPage
        });
      }
      wx.hideLoading();
    })
    wx.showLoading({
      title: '加载中...',
    });
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