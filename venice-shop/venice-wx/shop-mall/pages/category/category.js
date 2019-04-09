
const API = require('../../api_new/api.js');
// 产品列表页面

Page({
  data: {
    navList: [],
    goodsList: [],
    id: 0,
    currentCategory: {},
    scrollLeft: 0,
    scrollTop: 0,
    scrollHeight: 0,
    page: 1,
    size: 10,
    loadmoreText: '正在加载更多数据',
    nomoreText: '全部加载完成',
    nomore: false,
    totalPages: 1
  },
  onLoad: function(options) {

    var that = this;
    if (options.id) {
      that.setData({
        id: parseInt(options.id)
      });
    }
    //TODO 报异常
    wx.getSystemInfo({
      success: function(res) {
        that.setData({
          scrollHeight: res.windowHeight
        });
      }
    });
    this.getCategoryInfo();

  },
  getCategoryInfo: function() {
    API.methodGet('product', 'GoodsCategory', {
      id: this.data.id
    }).then(res => {
      if (res.code == 0) {
        this.setData({
          navList: res.data.brotherCategory,
          currentCategory: res.data.currentCategory
        });

        //nav位置
        let currentIndex = 0;
        let navListCount = this.data.navList.length;
        for (let i = 0; i < navListCount; i++) {
          currentIndex += 1;
          if (this.data.navList[i].id == this.data.id) {
            break;
          }
        }
        if (currentIndex > navListCount / 2 && navListCount > 5) {
          this.setData({
            scrollLeft: currentIndex * 60
          });
        }
        this.getGoodsList();

      } else {
        //显示错误信息
      }
    });
  },
  onReady: function() {
    // 页面渲染完成
  },
  onShow: function() {
    // 页面显示
    console.log(1);
  },
  onHide: function() {
    // 页面隐藏
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function() {
    console.log("下一页")
    this.getGoodsList()
  },

  getGoodsList: function() {
    let that = this;
    if (this.data.totalPages <= this.data.page - 1) {
      this.setData({
        nomore: true
      })
      return;
    }
    API.methodGet('product', 'GoodsList', {
      categoryId: this.data.id,
      page: this.data.page,
      size: this.data.size
    }).then(res => {
      this.setData({
        goodsList: that.data.goodsList.concat(res.page.list),
        page: res.page.currPage + 1,
        totalPages: res.page.totalPage
      });
    });
  },
  onUnload: function() {
    // 页面关闭
  },
  switchCate: function(event) {
    if (this.data.id == event.currentTarget.dataset.id) {
      return false;
    }
    var clientX = event.detail.x;
    var currentTarget = event.currentTarget;
    if (clientX < 60) {
      this.setData({
        scrollLeft: currentTarget.offsetLeft - 60
      });
    } else if (clientX > 330) {
      this.setData({
        scrollLeft: currentTarget.offsetLeft
      });
    }
    this.setData({
      id: event.currentTarget.dataset.id,
      page: 1,
      totalPages: 1,
      goodsList: [],
      nomore: false
    });

    this.getCategoryInfo();
  }
})