var app = getApp();
var WxParse = require('../../lib/wxParse/wxParse.js');

const API = require('../../api_new/api.js');

Page({
  data: {
    id: 0,
    topic: {},
    topicList: [],
    commentCount: 0,
    commentList: []
  },
  onLoad: function(options) {
    // 页面初始化 options为页面跳转所带来的参数
    var that = this;
    that.setData({
      id: parseInt(options.id)
    });
    API.methodGet('topic', 'TopicDetail', {
      id: that.data.id
    }).then(function(res) {
      if (res.code === 0) {
        that.setData({
          topic: res.data,
        });
        WxParse.wxParse('topicDetail', 'html', res.data.list, that);
      }
    })

    API.methodGet('topic', 'TopicRelated', {
      id: that.data.id
    }).then(function(res) {
      if (res.code === 0) {
        that.setData({
          topicList: res.data
        });
      }
    })
  },
  onShareAppMessage: function() {
    let that = this;
    return {
      title: that.data.topic.subtitle,
      desc: that.data.topic.title,
      path: '/pages/topicDetail/topicDetail??id=' + that.data.topic.id
    }
  },
  onReady: function() {

  },
  onShow: function() {
    // 页面显示
    // this.getCommentList();
  },
  onHide: function() {
    // 页面隐藏

  },
  onUnload: function() {
    // 页面关闭

  }
})