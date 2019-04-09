
const API = require('../../../api_new/api.js');

Page({
  data:{
    orderList: [],
    page: 1,
    size: 10,
    loadmoreText: '正在加载更多数据',
    nomoreText: '全部加载完成',
    nomore: false,
    totalPages: 1,
    tabList:['全部', '待付款', '待发货', '待收货', '已完成'],
    currentTab: 0,
  },
  onLoad:function(options){
    // 页面初始化 options为页面跳转所带来的参数
    // 页面显示
    //console.log(options)
    let that = this;
    wx.showLoading({
      title: '加载中...',
      success: function () {

      }
    });
    that.setData({
      page: 1,//初始化
      totalPages: 1,//初始化，
      orderList: []
    })
    //从其他页面跳转过来 看带的参数，相应对于的列表
    if(typeof(options) != 'undefined') {
      if (options.status == 1) {
        that.setData({
          currentTab: 1,
        })
        this.getOrderList('1');
      } else if (options.status == 2) {
        that.setData({
          currentTab: 2,
        })
        this.getOrderList('2');
      }else if (options.status == 6) {
        that.setData({
          currentTab: 3,
        })
        this.getOrderList('6');
      } else {
        that.setData({
          currentTab: 0,
        })
        this.getOrderList('0');
      }
    }else {
      this.getOrderList('0');
    }
  },

  /**
       * 页面上拉触底事件的处理函数
       */
  onReachBottom: function () {
    console.log("下一页")
    let that = this;
    let currentTab = that.data.currentTab
    if (currentTab == 3) {
      that.getOrderList('6')
    } else if (currentTab == 4) {
      that.getOrderList('8')
    } else {
      that.getOrderList(currentTab)
    }
  },

  getOrderList:function(status){
    console.log('status:', status)
    let that = this;
    if (that.data.totalPages <= that.data.page - 1) {
      that.setData({
        nomore: true
      })
      return;
    }
    API.methodGet('order', 'OrderList', {page: that.data.page, size: that.data.size, status: status }).then(function (res) {
      console.log('orderlist')
      console.log(res);
      if (res.code === 0) {
        that.setData({
          orderList: that.data.orderList.concat(res.data.list),
          page: res.data.currPage + 1,
          totalPages: res.data.totalPage
        });
        wx.hideLoading();
      }
    });
  },
  payOrder(){
    wx.redirectTo({
      url: '/pages/pay/pay',
    })
  },
  swichNav: function (e) {
    console.log(e)
    let that = this;
    let current = e.target.dataset.current
    that.setData({
      currentTab: current,
      page: 1,//初始化
      totalPages: 1,//初始化
      orderList: []
    })
    if (current == 3) {
      that.getOrderList('6')
    }else if (current == 4) {
      that.getOrderList('8')
    } else {
      that.getOrderList(current)
    }
  },
  onReady:function(){
    // 页面渲染完成
  },
  onShow:function(){
  },
  onHide:function(){
    // 页面隐藏
  },
  onUnload:function(){
    // 页面关闭
  }
})