### venice-app
本章节主要说明管理后台的服务端代码
### 项目包说明
```
venice-app
|- annotation 自定义注解
  |- IgnoreAuth  忽略Token验证
  |- LoginUser  登陆用户
|- config
  |- ShApiInterceptorConfig 拦截器
  |- WxMaConfiguration 微信小程序wxjava demo
  |- WxMaProperties 微信小程序配置文件信息
  |- WxPayConfiguration 微信支付 wxjava demo  
  |- WxPayProperties 微信支付配置信息
|- constant 静态变量
|- controller 商城管理台以及小程序api
|- dto 参数封装
|- entity 实体类
|- interceptor 拦截器
|- mapper 
|- resolver 注解方法
|- service 服务层
|- utils 工具包
  |- ShOrderUtils 订单相关工具包 如订单号生成  等
```
###类说明
### 管理台服务端
#### 首页管理
见AdminShIndexController类
#### 广告管理
见AdminShAdvertController类
#### 会员管理
见AdminShUserController类
#### 商品管理
   - 商品分类 见AdminShGoodsCategoryController类
   - 品牌管理 见AdminShBrandController类
   - 专题管理 见AdminShTopicController类
   - 商品管理 见AdminShGoodsController类
   - 优惠券管理  见AdminShCouponController类
#### 订单管理
   - 订单管理  见AdminShOrderController类
   - 退货列表 见AdminShOrderReturnController类
#### 其他相关
   - 图片上传 见UploadController类
   - 富文本集成 见UeditorController类
### 小程序服务端
#### 首页 
见ApiShIndexController类
#### 分类列表
见ApiShCategoryController类
#### 专题列表
见ApiShTopicController类
#### 品牌列表
见ApiShBrandController类
#### 商品列表
见ApiShGoodsController类
#### 搜索功能
见ApiShSearchController类
#### 购物车
见ApiShCartController类
#### 订单管理
   - 订单管理 见ApiShOrderController类
   - 退货管理 见ApiShOrderReturnController类
#### 支付管理
见ApiShPayController类
#### 用户中心
   - 登陆管理  见ApiShAuthController类 
   - 历史足迹  见ApiShFootprintController类 
   - 用户收藏  见ApiShCollectController类 