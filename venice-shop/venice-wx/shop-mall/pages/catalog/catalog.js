const API = require('../../api_new/api.js');
Page({
  data: {
    userInfo: {},
    navList: [],
    currentCategory: {},
    goodsCount: 0,
    scrollLeft: 0,
    scrollTop: 0,
    scrollHeight: 0
  },
  onLoad: function(options) {
    this.getCatalog();
  },
  getCatalog: function() {
    let that = this;
    API.methodGet('product', 'CatalogList').then(res => {
      this.setData({
        navList: res.data.categoryList,
        currentCategory: res.data.currentCategory
      });
    });
    API.methodGet('product', 'GoodsCount').then(res => {
      this.setData({
        goodsCount: res.data.goodsCount
      });
    });
  },
  getCurrentCategory: function(id) {
    let that = this;
    API.methodGet('product', 'CatalogCurrent', {
      id: id
    }).then(res => {
      this.setData({
        currentCategory: res.data.currentCategory
      });
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
  },
  switchCate: function(event) {
    var currentTarget = event.currentTarget;
    if (this.data.currentCategory.id == event.currentTarget.dataset.id) {
      return false;
    }
    this.getCurrentCategory(event.currentTarget.dataset.id);
  }
})