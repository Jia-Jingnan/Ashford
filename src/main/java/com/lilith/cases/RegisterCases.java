package com.lilith.cases;

import com.alibaba.fastjson.JSONObject;
import com.lilith.entity.RegisterParam;
import com.lilith.util.ApiUtil;
import com.lilith.util.CaseUtil;
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

    // 调用顺序
    // Case(从数组中取出数据执行用例) -> CaseUtil(将对象中数据取出放在数组中) -> ExcelUtil(将数据封装成对象)

    @Test(dataProvider = "datas")
    public void testRegister(String apiId, String parameter){
        // url
        String url = ApiUtil.getUrlByApiId(apiId);
        // type
        String type = ApiUtil.getTypeByApiId(apiId);

        // 解析json格式字符串,将json格式字符串转换为Map，参数
        Map<String,String> params = (Map<String, String>) JSONObject.parse(parameter);

        String res = HttpUtil.doService(type,url,params);

        System.out.println(res);
    }


    // 使用poi解析Excel中的数据
    @DataProvider
    public Object[][] datas(){
        String excelPath = "src/main/resources/cases/cases_v4.xlsx";
        String[] cellNames = {"ApiId","Params"};
        Object[][] datas = CaseUtil.getCaseDatasByApiId("1",cellNames);
        return datas;
    }



}
