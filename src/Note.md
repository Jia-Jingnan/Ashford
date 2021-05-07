接口测试流程：  
1.根据接口文档编写测试用例  
2.准备工具（测试工具或者接口测试代码）  
3.填写接口地址  
4.指定接口请求方式  
5.准备测试数据  
6.准备请求头数据（如果有必要，比如cookie，content-type等）
7.发起请求，获取接口响应信息  

客户端接口调用框架：  
HttpClient

Mock启动方式：  
mock runner jar目录下
```java -jar moco-runner-0.11.0-standalone.jar start -p 8848 -c api.json```