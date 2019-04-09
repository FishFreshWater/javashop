const request = require('./request.js').request;
let api = {};
api.url = {
  'index': {
    'newGoods': '/api/sh_index/newGoods', //首页最新商品
    'hotGoods': '/api/sh_index/hotGoods', //首页最新商品
    'recomTopic': '/api/sh_index/topic', //首页推荐主题
    'recomBrand': '/api/sh_index/brand', //首页推荐品牌
    'recomCategory': '/api/sh_index/category', //首页推荐分类
    'banner': '/api/sh_index/banner', //首页滚动图？
    'channel': '/api/sh_index/channel', //channel
  },
  'topic': {
    'TopicList': '/api/sh_topic/list', //话题列表
    'TopicDetail': '/api/sh_topic/detail', //话题详情
    'TopicRelated': '/api/sh_topic/related', //话题关联商品
  },
  'product':{
    'CatalogList': '/api/sh_category/index', //一级产品目录
    'GoodsHot': '/api/sh_goods/hot', //热门商品
    'GoodsNew': '/api/sh_goods/new', //新品推荐
    'CatalogCurrent': '/api/sh_category/current', //一级产品目录详细
    'GoodsCount': '/api/sh_goods/count', //所有产品总数
    'GoodsCategory': '/api/sh_goods/category', //获得分类数据
    'GoodsList': '/api/sh_goods/list', //获取分类产品列表
    'GoodsDetail': '/api/sh_goods/detail', //商品详情部分
    'GoodsRelated': '/api/sh_goods/related', //关联商品
    'CollectAddOrDelete': '/api/sh_collect/addordelete', //收藏或者取消收藏
    'BuyAdd': '/api/sh_buy/add', //直接购买
  },
  'brand': {
    'brandList':'/api/sh_brand/list',
    'brandDetail':'/api/sh_brand/detail',
    'brandGoodsList':'',
  },
  'address': {
    'AddressList': '/api/sh_address/list',
    'AddressDetail': '/api/sh_address/detail',
    'RegionList': '/api/region/list',
    'AddressSave': '/api/sh_address/save',
    'AddressDelete': '/api/sh_address/delete',
  },
  'afterSales': {
    'list':'/api/sh_return/list',
    'detail':'/api/sh_return/detail',
  },
  'cart': {
    'cartGoodsCount': '/api/sh_cart/goodscount',
    'CartAdd': '/api/sh_cart/add',
    'CartList': '/api/sh_cart/index',
    'CartChecked': '/api/sh_cart/checked',
    'CartUpdate': '/api/sh_cart/update',
    'CartDelete': '/api/sh_cart/delete',
  },
  'collect': {
    'CollectList': '/api/sh_collect/list',
    'CollectAddOrDelete': '/api/sh_collect/addordelete',
  },
  'coupon': {
    'CouponUserList': '/api/sh_coupon/user_list', //用户领取的优惠券列表
    'CouponList': '/api/sh_coupon/list', //还没领取的优惠券列表
    'CouponExchange': '/api/sh_coupon/exchange', //点击领取优惠卷
  },
  'footprint': {
    'subDiyForm': '/api/sh_footprint/list' //提交表单内容
  },
  'order': {
    'OrderQuery': '/api/sh_pay/query', //查询订单post
    'PayPrepayId': '/api/sh_pay/prepay', //获取预支付编码post
    'OrderList': '/api/sh_order/list', //订单列表
    'OrderDetail': '/api/sh_order/detail', //post
    'OrderCancel': '/api/sh_order/cancelOrder', //post
    'OrderConfirm': '/api/sh_order/orderConfirm', //订单确定 post
    'OrderRefund': '/api/sh_order/refundOrder', //订单退款申请 post
    'OrderReturn': '/api/sh_order/returnOrder', //post
    'tracking': '/api/sh_return/tracking', //写退款信息接口 post
  },
  'search': {
    'SearchIndex': '/api/sh_search/index', //搜索首页 post
    'SearchHelper': '/api/sh_search/helper', //搜索帮助 post
    'SearchClearHistory': '/api/sh_search/clearhistory', //清除搜索历史post
  },
  'shopping': {
    'CartCheckout': '/api/sh_cart/checkout', // post
    'OrderSubmit': '/api/sh_order/submit', // post
    'ListByGoods': '/api/sh_coupon/listByGoods', //可用优惠卷列表 
  }
};

// //封装api get方法
function methodGet(page,type, params) {
  var promise = new Promise((resolve, reject) => {
    request(api.url[page][type], { method: "GET", data: params }, resolve, reject)
  })
  return promise.then(res => {
    return res
  }).catch(err => {
    console.log(err)
  })
}
// //封装api post方法
function methodPost(page, type, params) {
  var promise = new Promise((resolve, reject) => {
    request(api.url[page][type], { method: "POST", data: params }, resolve, reject)
  })
  return promise.then(res => {
    return res
  }).catch(err => {
    console.log(err)
  })
}
module.exports = {
  methodGet,
  methodPost
}