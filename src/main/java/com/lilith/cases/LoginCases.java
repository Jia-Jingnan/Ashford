package com.lilith.cases;

import com.alibaba.fastjson.JSONObject;
import com.lilith.util.ExcelUtil;
import com.lilith.util.HttpUtil;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Map;

/**
 * @Author:JiaJingnan
 * @Date: 上午1:46 2021/5/9
 */
public class LoginCases {

    @Test(dataProvider = "datas")
    public void testRegister(String apiIdFromCase, String parameter){
        // 获取接口信息sheet页内容,根据获取的接口编号获取接口地址
        String url = null;
        // 请求方式
        String type = null;
        int[] rows = {1,2,3,4};
        int[] cells = {0,2,3};
        String excelPath = "src/main/resources/cases/cases_v3.xlsx";
        Object[][] datas = ExcelUtil.datas(excelPath,"接口信息",rows,cells);
        for (Object[] data : datas) {
            String apiIdFromApiInfo = data[0].toString();
            // 接口编号一致时，取出url地址
            if (apiIdFromCase.equals(apiIdFromApiInfo)){
                type = data[1].toString();
                url = data[2].toString();
                break;
            }
        }


        // 解析json格式字符串,将json格式字符串转换为Map，参数
        Map<String,String> params = (Map<String, String>) JSONObject.parse(parameter);

        String res = HttpUtil.doService(type,url,params);

        System.out.println(res);
    }


    // 使用poi解析Excel中的数据
    @DataProvider
    public Object[][] datas(){
        String excelPath = "src/main/resources/cases/cases_v3.xlsx";

        int[] rows = {5,6};
        // 获取用例sheet页接口编号第3列，参数第4列
        int[] cells = {2,3};
        Object[][] datas = ExcelUtil.datas(excelPath,"用例", rows,cells);
        return datas;
    }

}
