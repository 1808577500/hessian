# hessian-springboot-starter

#### 介绍
基于hessian，做了一个springboot的starter，供需要的使用。
在application.properties中提供如下配置：
hessian.enabled=true(默认)
hessian.timeout=10000(链接超时时间，默认10000ms)
hessian.readTimeout=10000(读取超时时间，默认10000ms)
hessian.context-path=/(hessian接口的提供地址)
hessian.base-package=(填写需要扫描的包名)