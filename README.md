核心组件
============================================

+ cache           cache存储组件
+ client-core     服务层客户端依赖包
+ sysconfig       系统配置读取组件
+ httpclient      http请求组件
+ mail            邮件发送组件
+ mq              消息队列组件
+ sms             短信发送组件
+ store           存储组件
+ datadict        数据字典组件
+ persistence     持久化组件
+ syslog          系统日志组件
+ sysparameter    系统参数读取组件
+ spring          spring组件
+ web-core        web工程依赖包
+ utils           工具集
+ easy-activemq   activemq组件

v1.1.4 20170105
-------------------------------------------
1. cache模块增加判断缓存是否存在的方法

v1.1.3 20161227
-------------------------------------------
1. redis增加参数maxWaitMillis

v1.1.2 20161115
-------------------------------------------
1. sysconfig支持读取环境变量

v1.1.1 20160930
-------------------------------------------
1. 删除ancun-utils中的CipherUtil,新增AesUtils

v1.1 20160930
-------------------------------------------
1. 将数据字典、持久化和系统参数从ancun-service-core中独立出来
2. 废弃ancun-service-core