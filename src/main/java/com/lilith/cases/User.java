package com.lilith.cases;

import com.lilith.util.HttpUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author:JiaJingnan
 * @Date: 下午11:51 2021/5/7
 */
public class User {

    public static void main(String[] args) throws Exception {

        // 接口地址
        String url = "http://127.0.0.1:8848/api/member/user";
        url += ("?name=Stark&age=18");
        // 指定请求方式
        HttpGet get = new HttpGet(url);
        // 准备测试数据
        String name = "Stark";
        String age = "18";
        // 发送请求
        HttpClient httpClient = HttpClients.createDefault();
        HttpResponse response = httpClient.execute(get);
        int statusCode = response.getStatusLine().getStatusCode();
        String result = EntityUtils.toString(response.getEntity());
        System.out.println(statusCode);
        System.out.println(result);

        System.out.println("使用doGet方法请求该接口");

        Map<String,String> params = new HashMap<>();
        params.put("name","Stark");
        params.put("age","18");
        String s = HttpUtil.doGet(url, params);
        System.out.println(s);

    }



}
