package com.lilith.cases;

import com.alibaba.fastjson.JSONObject;
import com.lilith.entity.Result;
import com.lilith.util.ApiUtil;
import com.lilith.util.ExcelUtil;
import com.lilith.util.HttpUtil;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Test;

import java.util.Map;

/**
 * 基本Cases类，用来封装基本请求过程
 * @Author:JiaJingnan
 * @Date: 下午12:17 2021/5/15
 */
public class BaseCase {

    @Test(dataProvider = "datas")
    public void testRegister(String caseId, String apiId, String parameter){
        // url
        String url = ApiUtil.getUrlByApiId(apiId);
        // type
        String type = ApiUtil.getTypeByApiId(apiId);

        // 解析json格式字符串,将json格式字符串转换为Map，参数
        Map<String,String> params = (Map<String, String>) JSONObject.parse(parameter);

        String response = HttpUtil.doService(type,url,params);

        // 将响应结果保存在对象中
        // Result result = new Result("用例",caseId, "ActualResponseData",response);
        // 对象保存到resultList中
        // ExcelUtil.resultList.add(result);

        System.out.println(response);
    }

    @AfterSuite
    public void batchWriteDatas(){
        ExcelUtil.batchWriteDatas("src/main/resources/cases/cases_v5.xlsx");
    }

}
