##项目背景
基于目前十分火热的小程序市场，我们开源一套在线商城系统。
###项目特点
代码干净简洁，可读性高。打造高颜值，高质量的开源项目。
###技术选用(主要)
#### 服务端
- spring-boot 2.0.3.RELEASE
- mybatis-plus 3.0.6
- shiro 1.3.2
- swagger2 2.7.0
- weixin-java-miniapp 3.3.1.B
- weixin-java-pay 3.3.1.B
- kaptcha 0.0.9
- aliyun 2.5.0
#### 前端
- vue 2.5.16
- vue-router 3.0.1
- vuex 3.0.1
- axios 0.18.0
### 项目结构
```
venice-shop
|-- venice-app 商城模块代码
|-- venice-common 公共模块
|-- venice-doc 文档以及初始化数据库脚本
|-- venice-framework 打包模块 （打包发布代码）
|-- venice-shiro 权限模块
|-- venice-vue 前端管理台
|-- venice-miniapp 小程序
```
### 本地部署
1. 下载源码
[venice-shop](https://gitee.com/xunli/venice-shop.git)
2. 开发环境搭建
- 数据库
1. 安装 [MYSQL](https://dev.mysql.com/downloads/mysql/5.6.html)
2. 创建数据库(使用utf-8mb4 基字符类型)
3. venice-doc中将init.sql导入数据库
4. 安装 [redis](https://redis.io/download)
5. 点击运行 redis-server.exe
- spring-boot2 开发环境
1. 安装 [JDK8](https://www.oracle.com/technetwork/java/javase/downloads/java-archive-javase8-2177648.html)
2. 开发工具 [IDEA](https://www.jetbrains.com/idea/)
3. 打开下载源码
4. 修改(开发环境) 
venice-shiro/src/resources/application-dev.yml 数据库连接登陆信息。
```
spring:
    datasource:
        type : com.alibaba.druid.pool.DruidDataSource
        driver-class-name : com.mysql.jdbc.Driver
        url: jdbc:mysql://localhost:3306/venice_shop?allowMultiQueries=true&useUnicode=true&characterEncoding=UTF-8
        username : root
        password: root
        druid:
          initial-size : 5
          min-idle : 5
          max-active : 500
          max-wait : 10
          time-between-eviction-runs-millis : 60000
          test-while-idle : true
          test-on-return : true
          test-on-borrow : true
          validation-query : SELECT 1
          filters : stat
          connection-properties : druid.stat.slowSqlMillis=5000
          remove-abandoned: true
          remove-abandoned-timeout: 21600
          log-abandoned: true
```
- 小程序开发环境
1. 安装 [微信小程序开发工具](https://developers.weixin.qq.com/miniprogram/dev/devtools/download.html)
2. 导入本项目的venice-miniapp模块
3. 配置app.js 中 requestUrl
- vue 开发环境
1. 安装 [nodejs](https://nodejs.org/en/)
2. 开发工具 [vscode](https://code.visualstudio.com/)
3. 安装依赖库  
```
npm install
```
4. 编译并运行
```
npm run serve
```

5. 打开浏览器输入 ```http://localhost:8080``` 。出现登陆台则运行成功。
- 项目配置
1. 管理台前端
  
2. 小程序前端

3. 管理台后端
   venice-shiro/src/resources目录中 application.yml 
```
spring:
    # 环境 dev|test|prod
    profiles:
        active: dev
```
对应配置文档为同级目录下 application-dev.yml/application-test.yml/application-prod.yml 分别为 开发环境/测试环境/生产环境 配置
```
wx:
  miniapp:
    configs:
        - appid: #小程序appid
          secret: #小程序密钥
          token: #微信小程序消息服务器配置的token
          aesKey: #微信小程序消息服务器配置的EncodingAESKey
          msgDataFormat: JSON
    pay:
      appId: #小程序appid
      mchId: #商户号
      mchKey: #商户密钥
      subAppId: #服务商模式下的子商户公众账号ID
      subMchId: #服务商模式下的子商户号
      keyPath: # p12证书的位置，可以指定绝对路径，也可以指定类路径（以classpath:开头）
      notify_url: #微信支付回调函数
```
微信账户以及商户申请方式如果与问题。请在群里联系群主。


