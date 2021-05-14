package com.lilith.cases;

import com.alibaba.fastjson.JSONObject;
import com.lilith.util.ApiUtil;
import com.lilith.util.CaseUtil;
import com.lilith.util.ExcelUtil;
import com.lilith.util.HttpUtil;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Map;

/**
 * @Author:JiaJingnan
 * @Date: 上午1:46 2021/5/9
 * 目前存在的两个大问题：一个Case里面读取两次excel数据源，大量接口测试时会造成大量的磁盘消耗
 * 二：读取的excel行号列号都写在代码中，耦合性非常高
 */
public class LoginCases {

    @Test(dataProvider = "datas")
    public void testLogin(String apiId, String parameter){

        // url
        String url = ApiUtil.getUrlByApiId(apiId);

        // 解析json格式字符串,将json格式字符串转换为Map，参数
        Map<String,String> params = (Map<String, String>) JSONObject.parse(parameter);

        String res = HttpUtil.doService(type,url,params);

        System.out.println(res);
    }


    // 使用poi解析Excel中的数据
    @DataProvider
    public Object[][] datas(){
        String excelPath = "src/main/resources/cases/cases_v4.xlsx";
        String[] cellNames = {"ApiId", "Params"};
        Object[][] datas = CaseUtil.getCaseDatasByApiId("2",cellNames);
        return datas;
    }

}
