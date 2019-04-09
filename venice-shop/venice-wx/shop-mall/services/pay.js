/**
 * 支付相关服务
 */
const API = require('../api_new/api.js');

/**
 * 判断用户是否登录
 */
function payOrder(orderNumber) {
  return new Promise(function (resolve, reject) {
    API.methodPost('order','PayPrepayId',{
      orderNumber: orderNumber
    }).then((res) => {
      console.log(res)
      if (res.code === 0) {
        const payParam = res.data;
        wx.requestPayment({
          'timeStamp': payParam.timeStamp,
          'nonceStr': payParam.nonceStr,
          'package': payParam.package,
          'signType': payParam.signType,
          'paySign': payParam.paySign,
          'success': function (res) {
            resolve(res);
          },
          'fail': function (res) {
            reject(res);
          },
          'complete': function (res) {
            reject(res);
          }
        });
      } else {
        reject(res);
      }
    });
  });
}


module.exports = {
  payOrder,
};











