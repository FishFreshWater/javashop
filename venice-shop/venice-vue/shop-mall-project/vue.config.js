module.exports = {
  lintOnSave: false,
  configureWebpack: {
    module: {
      rules: [
        {
          test: /\.(svg)(\?.*)?$/,
          use: [
            {
              loader: "svg-sprite-loader"
            }
          ]
        }
      ]
    }
  },
  chainWebpack: config => {
    config.module
      .rule("svg")
      .test(() => false)
      .use("file-loader");
  },
  devServer: {
    host: "localhost",
    port: 8080, // 端口号
    https: false, // https:{type:Boolean}
    open: true, //配置自动启动浏览器
    // proxy: 'http://localhost:4000' // 配置跨域处理,只有一个代理
    // 配置多个代理
    proxy: {
      "/proxyApi": {
        target: "http://linyuchi.mynatapp.cc",
        // target: "http://118.89.30.240:8084",
        changeOrigin: true,
        ws:true,
        pathRewrite:{
          '^/proxyApi':''
      }
      }
    }
  }
};
