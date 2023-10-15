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

后端使用map获取数据

难点：

{"params":
        "name": "wsj"
        "age":"18"
      "header":
        "aa":"123"
        "bb":"456"
    }

在哪里构造参数和请求头？？？

ApiClient 还是 该接口的项目里？？？

app 构造 JSON 发送到网关，网关解析后构造请求来调用 接口

---

不用这么麻烦了

优化：在app端JSON发过去，在sdk将json放到.body里直接传。