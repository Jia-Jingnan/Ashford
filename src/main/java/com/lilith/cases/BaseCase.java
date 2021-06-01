package com.lilith.cases;

import com.alibaba.fastjson.JSONObject;
import com.lilith.entity.Result;
import com.lilith.util.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.impl.conn.Wire;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Test;

import java.util.Map;

/**
 * 基本Cases类，用来封装基本请求过程
 * @Author:JiaJingnan
 * @Date: 下午12:17 2021/5/15
 */
@Slf4j
public class BaseCase {

    public String[] cellNames = {"CaseId", "ApiId", "Params", "ExpectedResponseData","PreValidateSql", "AfterValidateSql"};

    @Test(dataProvider = "datas")
    public void test(String caseId, String apiId, String parameter, String expectedResponseData, String preValidateSql, String afterValidateSql){
        // 判断SQL验证脚本是否为空，不为空则执行SQL语句
        if (preValidateSql != null && preValidateSql.trim().length() > 0){
            // 接口调用前执行SQL校验查询语句
            String preValidateResult = DBCheckUtil.doQuery(preValidateSql);
            ExcelUtil.resultList.add(new Result("用例", caseId, "PreValidateResult", preValidateResult));

        }
        // url
        log.info("根据接口编号" + apiId + "获取接口请求地址");
        String url = ApiUtil.getUrlByApiId(apiId);
        // type
        log.info("根据接口编号" + apiId + "获取接口请求类型");
        String type = ApiUtil.getTypeByApiId(apiId);

        // 解析json格式字符串,将json格式字符串转换为Map，参数
        Map<String,String> params = (Map<String, String>) JSONObject.parse(parameter);

        log.info("开始调用接口");
        String response = HttpUtil.doService(type,url,params);

        // 如果相同，在Excel表中写入通过， 否则写入实际结果
        response = AssertUtil.assertEquals(response, expectedResponseData);
        log.info("输出期望与实际比较结果");
        System.out.println(response);

        // 将响应结果保存在对象中
        log.info("将接口响应保存到对象中");
        Result result = new Result("用例",caseId, "ActualResponseData",response);
        // 对象保存到resultList中
        ExcelUtil.resultList.add(result);

        if (afterValidateSql != null && afterValidateSql.trim().length() > 0){
            // 接口调用后执行SQL校验查询语句
            String afterValidateResult = DBCheckUtil.doQuery(afterValidateSql);
            // 数据封装成对象，统一写入Excel中
            ExcelUtil.resultList.add(new Result("用例", caseId, "AfterValidateResult", afterValidateResult));
        }

        // 在测试套件中显示结果是否通过
        Assert.assertEquals(response,"通过");
    }

    @AfterSuite
    public void batchWriteDatas(){
        log.info("开始批量写入Excel");
        ExcelUtil.batchWriteDatas("src/main/resources/cases/cases_v6.xlsx");
    }

}
