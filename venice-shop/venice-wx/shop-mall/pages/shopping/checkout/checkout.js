var util = require('../../../utils/util.js');
const API = require('../../../api_new/api.js');
const pay = require('../../../services/pay.js');

var app = getApp();

Page({
  data: {
    checkedGoodsList: [],
    checkedAddress: {},
    checkedCoupon: [],
    couponList: [],
    goodsTotalPrice: 0.00, //商品总价
    freightPrice: 0.00, //快递费
    couponPrice: 0.00, //优惠券的价格
    orderTotalPrice: 0.00, //订单总价
    actualPrice: 0.00, //实际需要支付的总价
    addressId: 0,
    couponId: 0,
    isBuy: false,
    couponDesc: '',
    couponCode: '',
    buyType: '',
    isCoupon: false // 是否使用优惠卷
  },
  onLoad: function(options) {

    console.log(options.isBuy)
    // 页面初始化 options为页面跳转所带来的参数
    if (options.isBuy != null) {
      this.data.isBuy = options.isBuy
    }
    this.data.buyType = this.data.isBuy ? 'buy' : 'cart'
    //每次重新加载界面，清空数据
    app.globalData.userCoupon = 'NO_USE_COUPON'
    app.globalData.courseCouponCode = {}
  },

  getCheckoutInfo: function() {
    let that = this;
    let buyType = this.data.isBuy ? 'buy' : 'cart'
    API.methodPost('shopping', 'CartCheckout', {
        addressId: that.data.addressId,
        couponId: that.data.couponId,
        type: buyType
      })
      .then(function(res) {
        console.log('CartCheckout:', res)
        if (res.code === 0) {
          that.setData({
            checkedGoodsList: res.data.checkedGoodsList,
            checkedAddress: res.data.checkedAddress,
            actualPrice: res.data.actualPrice,
            //checkedCoupon: res.data.checkedCoupon,
            //couponList: res.data.couponList,
            couponPrice: res.data.couponPrice,
            freightPrice: res.data.freightPrice,
            goodsTotalPrice: res.data.goodsTotalPrice,
            orderTotalPrice: res.data.orderTotalPrice
          });
          //设置默认收获地址
          if (that.data.checkedAddress) {
            let addressId = that.data.checkedAddress.id;
            console.log(wx.getStorageSync('addressId'));
            if (wx.getStorageSync('addressId') != '') {
              addressId = wx.getStorageSync('addressId');
              API.methodGet('address', 'AddressDetail', {
                id: addressId
              }).then(function(res) {
                if (res.code === 0) {
                  if (res.data) {
                    that.setData({
                      checkedAddress: res.data,
                    });
                  }
                }
              });
            }

            if (addressId) {
              that.setData({
                addressId: addressId
              });
            }
          } else {
            wx.showModal({
              title: '',
              content: '请添加默认收货地址!',
              success: function(res) {
                if (res.confirm) {
                  that.selectAddress();
                  console.log('用户点击确定')
                }
              }
            })
          }
        }
        wx.hideLoading();
      });
  },
  selectAddress() {
    wx.navigateTo({
      url: '/pages/shopping/address/address',
    })
  },
  addAddress() {
    wx.navigateTo({
      url: '/pages/shopping/addressAdd/addressAdd',
    })
  },
  onReady: function() {
    // 页面渲染完成

  },
  onShow: function() {
    this.getCouponData()
    // 页面显示
    // wx.showLoading({
    //   title: '加载中...',
    // })
    this.getCheckoutInfo();

    try {
      var addressId = wx.getStorageSync('addressId');
      if (addressId) {
        this.setData({
          'addressId': addressId
        });
      }
    } catch (e) {
      // Do something when catch error
    }
  },

  /**
   * 获取优惠券
   */
  getCouponData: function() {
    if (app.globalData.userCoupon == 'USE_COUPON') {
      console.log('globalData:', app.globalData)
      this.setData({
        isCoupon: true,
        couponDesc: app.globalData.courseCouponCode.couponName,
        couponId: app.globalData.courseCouponCode.couponUserId,
      })
    } else if (app.globalData.userCoupon == 'NO_USE_COUPON') {
      this.setData({
        couponDesc: "不使用优惠券",
        couponId: '',
        isCoupon: false
      })
    }
  },

  onHide: function() {
    // 页面隐藏

  },
  onUnload: function() {
    // 页面关闭

  },

  /**
   * 选择可用优惠券
   */
  tapCoupon: function() {
    let that = this

    wx.navigateTo({
      url: '../selCoupon/selCoupon?buyType=' + that.data.buyType,
    })
  },

  //去付款
  submitOrder: function(e) {
    if (this.data.addressId <= 0) {
      util.showErrorToast('请选择收货地址');
      return false;
    }
    API.methodPost('shopping', 'OrderSubmit', {
      addressId: this.data.addressId,
      couponId: this.data.couponId,
      type: this.data.buyType
    }).then(res => {
      if (res.code === 0) {
        const orderNumber = res.orderNumber;
        pay.payOrder(orderNumber).then(res => {
          wx.redirectTo({
            url: '/pages/payResult/payResult?status=true&orderNumber=' + orderNumber
          });
        }).catch(res => {
          wx.redirectTo({
            url: '/pages/payResult/payResult?status=false&orderNumber=' + orderNumber
          });
        });
      } else {
        util.showErrorToast('下单失败');
      }
    });
  }
})