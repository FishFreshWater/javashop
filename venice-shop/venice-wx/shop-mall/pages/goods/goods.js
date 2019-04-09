var app = getApp();
var WxParse = require('../../lib/wxParse/wxParse.js');
const API = require('../../api_new/api.js');

Page({
  data: {
    winHeight: "",
    id: 0,
    goods: {},
    goodsPrimaryPicUrl: '',
    gallery: [],
    attribute: [],
    issueList: [],
    comment: [],
    brand: {},
    specificationList: [],
    productList: [],
    relatedGoods: [],
    cartGoodsCount: 0,
    userHasCollect: 0,
    number: 1,
    checkedSpecText: '请选择规格数量',
    checkedSpecId: '',
    openAttr: false,
    noCollectImage: "/static/images/icon_collect.png",
    hasCollectImage: "/static/images/icon_collect_checked.png",
    collectBackImage: "/static/images/icon_collect.png"
  },
  getGoodsInfo: function() {
    let that = this;
    API.methodGet('cart', 'cartGoodsCount').then(res => {
      that.setData({
        cartGoodsCount: res.data.goodsCount
      });
    });
    API.methodGet('product', 'GoodsDetail', {
      id: this.data.id
    }).then(res => {
      if (res.code === 0) {
        that.setData({
          goods: res.data.info,
          goodsPrimaryPicUrl: res.data.info.primaryPicUrl,
          gallery: res.data.gallery,
          //attribute: res.data.attribute,
          // issueList: res.issue,
          comment: res.data.comment,
          //brand: res.data.brand,
          specificationList: res.data.specificationList,
          productList: res.data.productList,
          userHasCollect: res.data.userHasCollect
        });

        //设置默认值
        this.setDefSpecInfo(this.data.specificationList);
        if (this.data.userHasCollect == 1) {
          this.setData({
            'collectBackImage': this.data.hasCollectImage
          });
        } else {
          this.setData({
            'collectBackImage': this.data.noCollectImage
          });
        }

        WxParse.wxParse('goodsDetail', 'html', this.data.goods.goodsDesc, this, 5);

        this.getGoodsRelated();
      }
    })
  },
  onShareAppMessage: function() {
    let that = this;
    return {
      title: that.data.goods.goodsBrief,
      desc: that.data.goods.name,
      path: '/pages/goods/goods?id=' + that.data.goods.id
    }
  },
  getGoodsRelated: function() {
    let that = this;
    API.methodGet('product', 'GoodsRelated', {
      id: this.data.id
    }).then(res => {
      if (res.code === 0) {
        this.setData({
          relatedGoods: res.goodsList,
        });
      }
    });
  },
  //点击规格
  clickSkuValue: function(event) {
    let that = this;
    let specNameId = event.currentTarget.dataset.nameId;
    let specValueId = event.currentTarget.dataset.valueId;

    //判断是否可以点击

    //TODO 性能优化，可在wx:for中添加index，可以直接获取点击的属性名和属性值，不用循环
    let _specificationList = this.data.specificationList;
    for (let i = 0; i < _specificationList.length; i++) {
      if (_specificationList[i].specificationId == specNameId) {
        for (let j = 0; j < _specificationList[i].valueList.length; j++) {
          if (_specificationList[i].valueList[j].id == specValueId) {
            //如果已经选中，则反选
            if (_specificationList[i].valueList[j].checked) {
              _specificationList[i].valueList[j].checked = false;
            } else {
              _specificationList[i].valueList[j].checked = true;
            }
          } else {
            _specificationList[i].valueList[j].checked = false;
          }
        }
      }
    }
    this.setData({
      'specificationList': _specificationList
    });
    //重新计算spec改变后的信息
    this.changeSpecInfo();

    //重新计算哪些值不可以点击
  },

  //获取选中的规格信息
  getCheckedSpecValue: function() {
    let checkedValues = [];
    let _specificationList = this.data.specificationList;
    for (let i = 0; i < _specificationList.length; i++) {
      let _checkedObj = {
        nameId: _specificationList[i].specificationId,
        valueId: 0,
        valueText: ''
      };
      for (let j = 0; j < _specificationList[i].valueList.length; j++) {
        if (_specificationList[i].valueList[j].checked) {
          _checkedObj.valueId = _specificationList[i].valueList[j].id;
          _checkedObj.valueText = _specificationList[i].valueList[j].value;
        }
      }
      checkedValues.push(_checkedObj);
    }
    return checkedValues;

  },
  //根据已选的值，计算其它值的状态
  setSpecValueStatus: function() {
    let imgData = this.data.checkedSpecId
    let arr = this.data.productList.filter(function(item) {
      return item.specKey == imgData
    })
    if (arr.length > 0) {
      console.log('arr', arr)
      this.setData({
        'goodsPrimaryPicUrl': arr[0].thumbnailUrl
      })
    }
  },
  //判断规格是否选择完整
  isCheckedAllSpec: function() {
    return !this.getCheckedSpecValue().some(function(v) {
      if (v.valueId == 0) {
        return true;
      }
    });
  },
  getCheckedSpecKey: function() {
    let checkedValue = this.getCheckedSpecValue().map(function(v) {
      return v.valueId;
    });

    // return checkedValue().join('_');
    return checkedValue.join('_');
  },
  //重新计算spec改变后的信息
  changeSpecInfo: function() {
    let checkedNameValue = this.getCheckedSpecValue();
    console.log('checkedNameValue', checkedNameValue)
    //设置选择的信息
    let checkedValue = checkedNameValue.filter(function(v) {
      if (v.valueId != 0) {
        return true;
      } else {
        return false;
      }
    }).map(function(v) {
      return v.valueText;
    });
    let checkedId = checkedNameValue.filter(function(v) {
      if (v.valueId != 0) {
        return true;
      } else {
        return false;
      }
    }).map(function(v) {
      return v.valueId;
    });
    if (checkedValue.length > 0) {
      this.setData({
        'checkedSpecText': checkedValue.join('　'),
      });
    } else {
      this.setData({
        'checkedSpecText': '请选择规格数量'
      });
    }

    if (checkedId.length > 0) {
      this.setData({
        'checkedSpecId': checkedId.reverse().join('_')
      })
      this.setSpecValueStatus()
    }

  },
  getCheckedProductItem: function(key) {
    return this.data.productList.filter(function(v) {
      if (v.specKey == key) {
        return true;
      } else {
        return false;
      }
    });
  },
  onLoad: function(options) {
    // 页面初始化 options为页面跳转所带来的参数
    this.setData({
      id: parseInt(options.id)
    });
    var that = this;
    this.getGoodsInfo();


    var that = this
    //  高度自适应
    wx.getSystemInfo({
      success: function(res) {
        var clientHeight = res.windowHeight,
          clientWidth = res.windowWidth,
          rpxR = 750 / clientWidth;
        var calc = clientHeight * rpxR - 100;
        that.setData({
          winHeight: calc
        });
      }
    });
  },
  onReady: function() {
    // 页面渲染完成

  },
  onShow: function() {
    // 页面显示
    this.getGoodsInfo();
  },
  onHide: function() {
    // 页面隐藏

  },
  onUnload: function() {
    // 页面关闭

  },
  switchAttrPop: function() {
    if (this.data.openAttr == false) {
      this.setData({
        openAttr: !this.data.openAttr,
        // collectBackImage: "/static/images/detail_back.png"
      });
    }
  },
  closeAttrOrCollect: function() {
    let that = this;
    //添加或是取消收藏
    API.methodPost('product', 'CollectAddOrDelete', {
        goodsId: this.data.id
      })
      .then(function(res) {
        let _res = res;
        if (_res.code == 0) {
          if (_res.data == 'add') {
            that.setData({
              'collectBackImage': that.data.hasCollectImage
            });
            wx.showToast({
              title: '收藏成功'
            });
          } else {
            that.setData({
              'collectBackImage': that.data.noCollectImage
            });
            wx.showToast({
              title: '您已取消收藏'
            });
          }

        } else {
          wx.showToast({
            image: '/static/images/icon_error.png',
            title: _res.msg,
            mask: true
          });
        }

      });

  },
  openCartPage: function() {
    wx.switchTab({
      url: '/pages/cart/cart',
    });
  },

  /**
   * 直接购买
   */
  buyGoods: function() {
    var that = this;
    if (this.data.openAttr == false) {
      //打开规格选择窗口
      this.setData({
        openAttr: !this.data.openAttr,
        // collectBackImage: "/static/images/detail_back.png"
      });
    } else {

      //提示选择完整规格
      if (!this.isCheckedAllSpec()) {
        wx.showToast({
          title: '请选择完整规格'
        });
        return false;
      }
      //根据选中的规格，判断是否有对应的sku信息
      //判断是否开启规格
      let checkedProduct;
      if (that.data.goods.isOnSpec != 1) {
        checkedProduct = that.data.productList;
      } else {
        checkedProduct = this.getCheckedProductItem(this.getCheckedSpecKey());
      }
      //根据选中的规格，判断是否有对应的sku信息
      if (!checkedProduct || checkedProduct.length <= 0) {
        //找不到对应的product信息，提示没有库存
        wx.showToast({
          title: '对不起库存不足'
        });
        return false;
      }
      //验证库存
      if (checkedProduct.stockNumber < this.data.number) {
        //找不到对应的product信息，提示没有库存
        wx.showToast({
          title: '库存不足'
        });
        return false;
      }

      // 直接购买商品
      API.methodPost('product', 'BuyAdd', {
          goodsId: this.data.goods.id,
          number: this.data.number,
          productId: checkedProduct[0].id
        })
        .then(function(res) {
          console.log('BuyAdd:', res)
          let _res = res;
          if (_res.code == 0) {
            that.setData({
              openAttr: !that.data.openAttr,
            });
            wx.navigateTo({
              url: '/pages/shopping/checkout/checkout?isBuy=true',
            })
          } else {
            wx.showToast({
              image: '/static/images/icon_error.png',
              title: _res.errmsg,
              mask: true
            });
          }

        });

    }
  },

  /**
   * 添加到购物车
   */
  addToCart: function() {
    var that = this;
    if (this.data.openAttr == false) {
      //打开规格选择窗口
      this.setData({
        openAttr: !this.data.openAttr,
        // collectBackImage: "/static/images/detail_back.png"
      });
    } else {

      //提示选择完整规格
      if (!this.isCheckedAllSpec()) {
        wx.showToast({
          title: '请选择完整规格'
        });
        return false;
      }

      //根据选中的规格，判断是否有对应的sku信息
      //判断是否开启规格
      let checkedProduct;
      if (that.data.goods.isOnSpec != 1) {
        checkedProduct = that.data.productList[0];
      } else {
        checkedProduct = this.getCheckedProductItem(this.getCheckedSpecKey());
      }
      if (!checkedProduct || checkedProduct.length <= 0) {
        //找不到对应的product信息，提示没有库存
        wx.showToast({
          image: '/static/images/icon_error.png',
          title: '请选择产品'
        });
        return false;
      }

      //验证库存
      console.log(checkedProduct);
      console.log(checkedProduct);
      console.log(this.data.number);

      if (checkedProduct.stockNumber < this.data.number) {
        //找不到对应的product信息，提示没有库存
        wx.showToast({
          image: '/static/images/icon_error.png',
          title: '库存不足'
        });
        return false;
      }

      //添加到购物车
      API.methodPost('cart', 'CartAdd', {
          goodsId: this.data.goods.id,
          number: this.data.number,
          productId: checkedProduct.id
        })
        .then(function(res) {
          let _res = res;
          console.log(_res);
          if (_res.code == 0) {
            wx.showToast({
              title: '添加成功'
            });

            that.setData({
              openAttr: !that.data.openAttr,
              cartGoodsCount: _res.data.cartTotal.goodsCount
            });
            if (that.data.userHasCollect == 1) {
              that.setData({
                'collectBackImage': that.data.hasCollectImage
              });
            } else {
              that.setData({
                'collectBackImage': that.data.noCollectImage
              });
            }
          } else {
            wx.showToast({
              image: '/static/images/icon_error.png',
              title: _res.msg,
              mask: true
            });
          }

        });
    }

  },
  cutNumber: function() {
    this.setData({
      number: (this.data.number - 1 > 1) ? this.data.number - 1 : 1
    });
  },
  addNumber: function() {
    this.setData({
      number: this.data.number + 1
    });
  },
  setDefSpecInfo: function(specificationList) {
    //未考虑规格联动情况
    let that = this;
    if (!specificationList) return;
    for (let i = 0; i < specificationList.length; i++) {
      let specification = specificationList[i];
      let specNameId = specification.specificationId;
      //规格只有一个时自动选择规格
      if (specification.valueList && specification.valueList.length == 1) {
        let specValueId = specification.valueList[0].id;
        that.clickSkuValue({
          currentTarget: {
            dataset: {
              "nameId": specNameId,
              "valueId": specValueId
            }
          }
        });
      }
    }
    specificationList.map(function(item) {

    });

  },
  // 点击遮罩 或者关闭x
  spec_model_click: function() {
    this.setData({
      openAttr: !this.data.openAttr
    });
  }
})