package com.lilith.util;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author:JiaJingnan
 * @Date: 上午12:15 2021/5/8
 * 封装http请求方法
 */
public class HttpUtil {

    public String doPost(String url, Map<String,String> params){

        String result = null;

        //2.指定接口请求方式
        HttpPost post = new HttpPost(url);

        // 构建请求体中的参数
        List<BasicNameValuePair> parameters = new ArrayList<>();

        // 取出map中的参数和value，循环map,然后加入请求体paramesters中
        Set<String> keys = params.keySet();
        // 循环map
        for (String key : keys) {
            String value = params.get(key);
            parameters.add(new BasicNameValuePair(key,value));
        }

        try {
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
            result = EntityUtils.toString(response.getEntity());
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
