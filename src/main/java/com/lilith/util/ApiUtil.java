package com.lilith.util;

import com.lilith.entity.Api;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author:JiaJingnan
 * @Date: 下午11:47 2021/5/14
 */
public class ApiUtil {

    public static List<Api> apis = new ArrayList<>();

    static {
        ExcelUtil.load("src/main/resources/cases/cases_v6.xlsx","接口信息",Api.class);
    }

    /**
     * 根据接口编号获取接口地址
     * @param apiId
     * @return
     */
    public static String getUrlByApiId(String apiId){
        for (Api api : apis){
            if (api.getApiId().equals(apiId)){
                return api.getUrl();
            }
        }
        return "";
    }

    // 根据apiId获取请求方式
    public static String getTypeByApiId(String apiId){
        for (Api api : apis){
            if (api.getApiId().equals(apiId)){
                return api.getType();
            }
        }
        return "";
    }

    public static void main(String[] args) {
        for (Api api : apis){
            System.out.println(api);
        }
    }
}
