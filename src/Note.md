接口测试流程：  
1.根据接口文档编写测试用例  
2.准备工具（测试工具或者接口测试代码）  
3.填写接口地址  
4.指定接口请求方式  
5.准备测试数据  
6.准备请求头数据（如果有必要，比如cookie，content-type等）
7.发起请求，获取接口响应信息  
8.根据响应报文判断实际与期望数据是否一致，断言

3～6为构建http请求消息
```java
//3.准备请求数据
String phone = "18268046852";
String password = "123456";
// 封装数据到请求体
List<BasicNameValuePair> parameters = new ArrayList<>();
parameters.add(new BasicNameValuePair("phone",phone));
parameters.add(new BasicNameValuePair("password",password));
post.setEntity(new UrlEncodedFormEntity(parameters,"utf-8"));
```
7～8为处理http响应消息
```java
//5.发起请求，获取接口响应信息
HttpClient client = HttpClients.createDefault();
HttpResponse response = client.execute(post);
//{HTTP/1.1 200 OK [Content-Length: 60, Content-Type: application/json; charset=utf-8] ResponseEntityProxy{[Content-Type: application/json; charset=utf-8,Content-Length: 60,Chunked: false]}}
// 状态码
int code = response.getStatusLine().getStatusCode();
// 响应报文
String result = EntityUtils.toString(response.getEntity());
```

客户端接口调用框架：  
HttpClient

Mock启动方式：  
mock runner jar目录下
```java -jar moco-runner-0.11.0-standalone.jar start -p 8848 -c api.json```

在框架中执行接口测试的流程（不含鉴权）：  
1.在excel数据源中按照列名填入接口相关信息及用例信息  
2.编写一个测试类，继承BaseCase，类中提供测试所需的数据  
3.在testng.xml文件中加入测试类  
4.执行测试套件，执行成功后测试结果会批量写入数据源中的实际响应结果列

接口断言


接口鉴权


接口报告
