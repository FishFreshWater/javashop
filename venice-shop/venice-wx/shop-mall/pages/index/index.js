//获取应用实例
const API = require('../../api_new/api.js');

Page({
  data: {
    newGoods: [],
    hotGoods: [],
    topics: [],
    brands: [],
    floorGoods: [],
    banner: [],
    channel: []
  },
  onShareAppMessage: function () {
    return {
      title: '微智汇商城',
      desc: '微智汇商城',
      path: '/pages/index/index'
    }
  },
  onPullDownRefresh(){
	    var self = this;
	    this.getIndexData();
 },
  getIndexData: function () {
    let that = this;
    var data = new Object();
    API.methodGet('index','newGoods').then((res =>{
      if (res.code === 0) {
        data.newGoods = res.newGoodsList
        this.setData(data);
      }
    }))

    API.methodGet('index', 'hotGoods').then((res => {
      if (res.code === 0) {
        data.hotGoods = res.hotGoodsList
        this.setData(data);
      }
    }))

    API.methodGet('index', 'recomTopic').then((res => {
      if (res.code === 0) {
        data.topics = res.topicList
        this.setData(data);
      }
    }))

    API.methodGet('index', 'recomBrand').then((res => {
      if (res.code === 0) {
        data.brands = res.brandList
        this.setData(data);
      }
    }))

    API.methodGet('index', 'recomCategory').then((res => {
      if (res.code === 0) {
        data.floorGoods = res.categoryList
        this.setData(data);
      }
    }));

    API.methodGet('index', 'banner').then((res => {
      console.log("banner",res)
      if (res.code === 0) {
        data.banner = res.banner
        this.setData(data);
      }
    }));

    API.methodGet('index', 'channel').then((res => {
      if (res.code === 0) {
        data.channel = res.channel
        this.setData(data);
      }
    }));

  },
  onLoad: function (options) {
    this.getIndexData();
  },
  onReady: function () {
    // 页面渲染完成
  },
  onShow: function () {
    // 页面显示
  },
  onHide: function () {
    // 页面隐藏
  },
  onUnload: function () {
    // 页面关闭
  }
})
