# api接口项目（开发中...）

导入指定的SDK包后，使用AK、SK验证成功后才能调用api接口

Gateway参考：https://blog.csdn.net/zlt2000/article/details/106992879/

![mark](https://cdn.jsdelivr.net/gh/vincent-nicky/image_store/blog/20200628094613487.png)


app 是处理与前端的交互，发出的请求不需要经过网关，不具备查库能力，是服务的消费者

gateway 网关是处理请求验证，因为不具备查库的能力，所以是服务消费者

api-provider-mysql 提供查库能力，是服务提供者

# Todo List

## [x] 重点在网关：流量染色

![img](https://cdn.jsdelivr.net/gh/vincent-nicky/image_store/blog/gateway_dynamic_staining.png)

## 理解前端业务

前端将参数封装成JSON（包含参数、请求头等数据）

网关起到请求转发的作用，所以在sdk里将json处理后放到.body里直接传，还要完成添加自定义的请求头。