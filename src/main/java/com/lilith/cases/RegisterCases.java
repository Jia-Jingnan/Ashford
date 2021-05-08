package com.lilith.cases;

import com.alibaba.fastjson.JSONObject;
import com.lilith.entity.RegisterParam;
import com.lilith.util.ExcelUtil;
import com.lilith.util.HttpUtil;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author:JiaJingnan
 * @Date: 上午12:42 2021/5/8
 * 完成register接口的多个测试用例的测试，使用testng
 */
public class RegisterCases {

    /**
     * 测试数据：
     *
     * 注册成功 "phone":"18268046852",
     *         "password":"123456"
     * 重复注册 "phone":"18268046853",
     *         "password":"123456"
     * 不合格手机号 "phone":"1826804",
     *            "password":"123456"
     * 密码少于6位  "phone":"18268046852",
     *         "password":"12345"
     */

    String url = "http://127.0.0.1:8848/api/member/register";

    @Test(dataProvider = "datas")
    public void testRegister(String parameter){ //{"phone":"1826804","password":"123456"}
        // 解析json格式字符串,将json格式字符串转换为Map
        Map<String,String> params = (Map<String, String>) JSONObject.parse(parameter);
        // 第二种解析方式,需要用到RegisterParam
        // RegisterParam registerParam = JSONObject.parseObject(parameter, RegisterParam.class);
        String res = HttpUtil.doPost(url, params);
        System.out.println(res);
    }


    // 使用poi解析Excel中的数据
    @DataProvider
    public Object[][] datas(){
        String excelPath = "src/main/resources/cases/cases_v2.xls";
        // 使用行号列号集合读取数据
        int[] rows = {1,2,3,4};
        int[] cells = {5};
        Object[][] datas = ExcelUtil.datas(excelPath,rows,cells);
        return datas;
    }

}
