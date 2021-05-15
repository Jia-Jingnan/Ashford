package com.lilith.cases;

import com.alibaba.fastjson.JSONObject;
import com.lilith.entity.RegisterParam;
import com.lilith.entity.Result;
import com.lilith.util.ApiUtil;
import com.lilith.util.CaseUtil;
import com.lilith.util.ExcelUtil;
import com.lilith.util.HttpUtil;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author:JiaJingnan
 * @Date: 上午12:42 2021/5/8
 * 完成register接口的多个测试用例的测试，使用testng
 */
public class RegisterCases extends BaseCase{

    // 调用顺序
    // Case(从数组中取出数据执行用例) -> CaseUtil(将对象中数据取出放在数组中) -> ExcelUtil(将数据封装成对象)

    // 使用poi解析Excel中的数据
    @DataProvider
    public Object[][] datas(){
        String[] cellNames = {"CaseId", "ApiId", "Params"};
        Object[][] datas = CaseUtil.getCaseDatasByApiId("1",cellNames);
        return datas;
    }


}
