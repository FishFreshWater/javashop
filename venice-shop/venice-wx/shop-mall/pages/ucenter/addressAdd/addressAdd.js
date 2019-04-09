var util = require('../../../utils/util.js');

var API = require('../../../api_new/api.js');
var app = getApp();
Page({
  data: {
    address: {
      id: 0,
      provinceName: '',
      cityName: '',
      districtName: '',
      address: '',
      fullRegion: '',
      postalCode: '',
      userName: '',
      telNumber: '',
      isDefault: 0
    },
    region: ['福建省', '厦门市', '思明区'],
    addressId: 0
  },
  bindinputMobile(event) {
    let address = this.data.address;
    address.telNumber = event.detail.value;
    this.setData({
      address: address
    });
  },
  bindinputName(event) {
    let address = this.data.address;
    address.userName = event.detail.value;
    this.setData({
      address: address
    });
  },
  bindinputAddress(event) {
    let address = this.data.address;
    address.detailInfo = event.detail.value;
    this.setData({
      address: address
    });
  },
  bindIsDefault() {
    let address = this.data.address;
    address.isDefault = !address.isDefault;
    this.setData({
      address: address
    });
  },
  bindRegionChange: function(e) {
    console.log('picker发送选择改变，携带值为', e.detail.value)
    this.setData({
      region: e.detail.value
    })
  },
  bindinputPostalCode(event) {
    let address = this.data.address;
    address.postalCode = event.detail.value;
    this.setData({
      address: address
    });
  },
  getAddressDetail() {
    let that = this;
    API.methodGet('address', 'AddressDetail', {
      id: that.data.addressId
    }).then(function(res) {
      if (res.code === 0) {
        if (res.data) {

          that.setData({
            address: res.data,
            region: [res.data.provinceName, res.data.cityName, res.data.countyName]
          });
          console.log(that.data.region)
        }
      }
    });
  },
  onLoad: function(options) {
    // 页面初始化 options为页面跳转所带来的参数
    if (options.id) {
      this.setData({
        addressId: options.id
      });
      this.getAddressDetail();
    }

  },
  onReady: function() {

  },
  cancelAddress() {
    wx.navigateBack({
      url: '/pages/ucenter/address/address',
    })
  },
  saveAddress() {
    console.log(this.data.address)
    let address = this.data.address;
    let region = this.data.region;
    if (address.userName == '') {
      util.showErrorToast('请输入姓名');

      return false;
    }

    if (address.telNumber == '') {
      util.showErrorToast('请输入手机号码');
      return false;
    }

    if (region == 0) {
      util.showErrorToast('请输入省市区');
      return false;
    }

    if (address.detailInfo == '') {
      util.showErrorToast('请输入详细地址');
      return false;
    }

    let that = this;
    API.methodPost('address', 'AddressSave', {
      id: address.id,
      userName: address.userName,
      telNumber: address.telNumber,
      postalCode: address.postalCode,
      isDefault: address.isDefault,
      provinceName: region[0],
      cityName: region[1],
      countyName: region[2],
      detailInfo: address.detailInfo,
    }, 'POST').then(function(res) {
      if (res.code === 0) {
        wx.navigateBack({
          url: '/pages/ucenter/address/address',
        })
      }
    });

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