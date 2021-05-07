package com.lilith.cases;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author:JiaJingnan
 * @Date: 下午1:20 2021/5/7
 */
public class Register {

    public static void main(String[] args) throws Exception {

        //1.填写接口请求地址
        String url = "http://127.0.0.1:8848/api/member/register";
        //2.指定接口请求方式
        HttpPost post = new HttpPost(url);
        //3.准备请求数据
        String phone = "18268046852";
        String password = "123456";
        // 封装数据到请求体
        List<BasicNameValuePair> parameters = new ArrayList<>();
        parameters.add(new BasicNameValuePair("phone",phone));
        parameters.add(new BasicNameValuePair("password",password));
        post.setEntity(new UrlEncodedFormEntity(parameters,"utf-8"));
        //4.准备请求头数据
        //5.发起请求，获取接口响应信息
        HttpClient client = HttpClients.createDefault();
        HttpResponse response = client.execute(post);
        System.out.println(response.toString());
        //{HTTP/1.1 200 OK [Content-Length: 60, Content-Type: application/json; charset=utf-8] ResponseEntityProxy{[Content-Type: application/json; charset=utf-8,Content-Length: 60,Chunked: false]}}
        // 状态码
        int code = response.getStatusLine().getStatusCode();
        // 响应报文
        String result = EntityUtils.toString(response.getEntity());
        System.out.println(result);
    }
}
