/**
 * 用户相关服务
 */
// const app = getApp();
// const appid = app.globalData.appid
// const requestUrl = app.globalData.requestUrl

// const appid = "be2c510b1b5440dc8550c404031e6035";
// const requestUrl = "http://api.enfunly.com";

const util = require('../utils/util.js');
/**
 * 调用微信登录
 */
function loginByWeixin(userInfo) {
  let code = null;
  return new Promise(function (resolve, reject) {
    return util.login().then((res) => {
      code = res.code;
      return userInfo;
    }).then((userInfo) => {
      //登录远程服务器
      wx.request({
        url: requestUrl +'/api/sh_auth/login_by_weixin',
        data: { code: code, userInfo: userInfo },
        method: "POST",
        header: {
          'appid': appid,
          'Content-Type': 'application/json'
        },
        success(res) {
          if (res.statusCode == 200) {
            resolve(res.data);
          } else {
            reject(res.errMsg);
          }
        },
        fail(...err) {
          reject(err)
        }
      });
    }).catch((err) => {
      reject(err);
    })
  });
}

/**
 * 判断用户是否登录
 */
function checkLogin() {
  return new Promise(function (resolve, reject) {
    if (wx.getStorageSync('userInfo') && wx.getStorageSync('token')) {
      util.checkSession().then(() => {
        resolve(true);
      }).catch(() => {
        reject(false);
      });
    } else {
      reject(false);
    }
  });
}

module.exports = {
  loginByWeixin,
  checkLogin,
};











