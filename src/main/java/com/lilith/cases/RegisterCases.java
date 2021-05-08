package com.lilith.cases;

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
    public void testRegister(String phone, String password){
        Map<String,String> params = new HashMap<>();
        params.put("phone",phone);
        params.put("password",password);
        String res = HttpUtil.doPost(url, params);
        System.out.println(res);
    }


    // 使用poi解析Excel中的数据
    @DataProvider
    public Object[][] datas(){
        String excelPath = "src/main/resources/cases/cases.xls";
        Object[][] datas = ExcelUtil.datas(excelPath,1,4,5,6);
        return datas;
    }

}
