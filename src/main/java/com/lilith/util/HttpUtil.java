package com.lilith.util;

import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.*;

/**
 * @Author:JiaJingnan
 * @Date: 上午12:15 2021/5/8
 * 封装http请求方法
 */
@Slf4j
public class HttpUtil {

    public static Map<String, String> cookies = new HashMap<>();
    // cookie中表示已登陆的字段
    public static String cookieFlag = "JSESSIONID";
    public static Map<String, String> token = new HashMap<>();
    // 接口响应中表示token字段名
    public static String tokenFlag = "token";

    public static String doPostByJson(String url, Map<String,String> params){
        String result = null;

        //2.指定接口请求方式
        HttpPost post = new HttpPost(url);

        try {
            String stringParams = JSONObject.toJSONString(params);

            StringEntity stringEntity = new StringEntity(stringParams);

            post.setEntity(stringEntity);
            //4.准备请求头数据
            post.setHeader("Content-Type","application/json");
            //5.发起请求，获取接口响应信息
            HttpClient client = HttpClients.createDefault();
            HttpResponse response = client.execute(post);
            // System.out.println(response.toString());
            //{HTTP/1.1 200 OK [Content-Length: 60, Content-Type: application/json; charset=utf-8] ResponseEntityProxy{[Content-Type: application/json; charset=utf-8,Content-Length: 60,Chunked: false]}}
            //判断接口响应的header中是否有Set-Cookie
            getAndStoreCookiesFromResHeader(response,cookieFlag);

            // 状态码
            int statusCode = response.getStatusLine().getStatusCode();
            // 响应报文
            result = EntityUtils.toString(response.getEntity());
            // 从body中取出表示表示已登陆的字段
            getAndStoreTokenFromSingleResBody(result,tokenFlag);
            log.info("响应状态码：" + "[" + statusCode + "]" + ", 响应结果：[" + result + "]");

            // System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;

    }


    public static String doPostByForm(String url, Map<String,String> params){

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
            // System.out.println(response.toString());
            //{HTTP/1.1 200 OK [Content-Length: 60, Content-Type: application/json; charset=utf-8] ResponseEntityProxy{[Content-Type: application/json; charset=utf-8,Content-Length: 60,Chunked: false]}}
            // 状态码
            int statusCode = response.getStatusLine().getStatusCode();
            // 响应报文
            result = EntityUtils.toString(response.getEntity());
            log.info("响应状态码：" + "[" + statusCode + "]" + ", 响应结果：[" + result + "]");
            // System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String doGet(String url, Map<String,String> params){

        String result = null;

        // 取出map中的参数和value，循环map,然后加入请求体paramesters中
        Set<String> keys = params.keySet();
        // 循环map
        // 定义标识位,通过mark判断是否是第一个参数，第一个参数前面需要加？
        int mark = 1;
        for (String key : keys) {
            String value = params.get(key);
            if (mark == 1){
                url += ("?"+ key + "=" + value);
            } else {
                url += ("&" + key + "=" + value);
            }
            mark ++;
        }

        // 指定请求方式
        HttpGet get = new HttpGet(url);

        try {
            // 发送请求
            HttpClient httpClient = HttpClients.createDefault();
            HttpResponse response = httpClient.execute(get);
            int statusCode = response.getStatusLine().getStatusCode();
            result = EntityUtils.toString(response.getEntity());
            log.info("响应状态码：" + "[" + statusCode + "]" + ", 响应结果：[" + result + "]");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static String doService(String type, String url, Map<String,String> params){
        String result = null;
        if ("post".equalsIgnoreCase(type)){
            result = HttpUtil.doPostByJson(url,params);
        } else if ("get".equalsIgnoreCase(type)){
            result = HttpUtil.doGet(url,params);
        }

        return result;
    }

    private static void getAndStoreCookiesFromResHeader(HttpResponse response,String flag) {
        // 从header中取出"Set-Cookie"响应头
        Header setCookieHeader = response.getFirstHeader("Set-Cookie");
        if (setCookieHeader!=null){
            //  取出响应头的值
            String cookiePairsString = setCookieHeader.getValue();
            if (cookiePairsString != null && cookiePairsString.length() > 0){
                String[] cookiePairs = cookiePairsString.split(";");
                for (String s : cookiePairs) {
                    // 如果包含name如"JSESSIONID"或其他可以表示已登陆的字段，保存到Map中
                    if (s.contains(flag)){
                        // 保存到map中
                        cookies.put(flag,s);
                    }
                }
            }
        }
    }

    // 从单一json响应中取出token
    private static void getAndStoreTokenFromSingleResBody(String response,String flag) {
        try {
            // 将json转换为map
            Map<String,String> responseMap = (Map<String, String>) JSONObject.parse(response);
            if (responseMap.containsKey(flag)){
                String value = responseMap.get(flag);
                // System.out.println(flag + responseMap.get(flag));
                token.put("msg",value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
