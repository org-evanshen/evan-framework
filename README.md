# evan-framework
 
### 模块说明

+ domain          核心传输对象
+ cache           cache存储组件
+ sysconfig       系统配置读取组件
+ datadict        数据字典读取组件
+ httpclient      http请求组件
+ mail            邮件发送组件
+ mq              消息队列组件
+ store           存储组件
+ persistence     持久化组件
+ syslog          系统日志组件
+ utils           工具集
+ web             web工程依赖包

### 操作说明

##### 更新版本
```
mvn-updata-version.sh(.bat) $version
//参数version--要更新的目标版本
//例如更新到1.2-SNAPSHOT,执行：mvn-updata-version.sh(.bat) 1.2-SNAPSHOT
```
##### 发布到maven私服快照仓库
```
mvn-deploy-snapshot.sh(.bat)   
```
##### 发布到maven私服release仓库
```
mvn-deploy-release.sh(.bat) $version
//参数version--要发布的目标版本
```



### 版本说明

#### v2.1.1 20180129
1. [fix] Redis日志问题和级别
1. [fix] 图片验证码的cache不再保存到cookie
1. [feature] 运行环境不再读取属性spring.profiles.active
1. [feature] 优化排除读取当前登录用户的Url的缓存


#### v2.1 20171204
1. AESUtil加解密失败时原来抛出runtimeException改为AESException

#### v1.1.4 20170105
1. cache模块增加判断缓存是否存在的方法

####  v1.1.3 20161227
1. redis增加参数maxWaitMillis