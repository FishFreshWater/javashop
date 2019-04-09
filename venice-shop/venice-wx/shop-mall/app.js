
var user = require('./services/user.js');

App({
  onLaunch: function () {
    // 获取第三方授权appid
    if (!this.globalData.appid){
      let extConfig = wx.getExtConfigSync ? wx.getExtConfigSync() : {}
      console.log('extConfig:', extConfig)
      this.globalData.appid = extConfig.appid || null;
      console.log(extConfig)
    }
    
    //获取用户的登录信息
    user.checkLogin().then(res => {
      this.globalData.userInfo = wx.getStorageSync('userInfo');
      this.globalData.token = wx.getStorageSync('token');
    }).catch(() => {
       wx.removeStorageSync('userInfo');
       wx.removeStorageSync('token');
    });
  },
  
  globalData: {
    appid:"798fcb91187549a5b3b9338b0e8d9485",
    // requestUrl: "https://api.enfunly.com",
    requestUrl: "http://localhost:8081",
    userInfo: {
      nickName: 'Hi,游客',
      userName: '点击去登录',
      avatarUrl: 'https://platform-wxmall.oss-cn-beijing.aliyuncs.com/upload/20180727/150547696d798c.png'
    },
    token: '',
    userCoupon: 'NO_USE_COUPON',//默认不适用优惠券
    courseCouponCode: {},//购买课程的时候优惠券信息
  }
})