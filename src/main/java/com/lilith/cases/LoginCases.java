package com.lilith.cases;

import com.alibaba.fastjson.JSONObject;
import com.lilith.util.ApiUtil;
import com.lilith.util.CaseUtil;
import com.lilith.util.ExcelUtil;
import com.lilith.util.HttpUtil;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Map;

/**
 * @Author:JiaJingnan
 * @Date: 上午1:46 2021/5/9
 * 目前存在的两个大问题：一个Case里面读取两次excel数据源，大量接口测试时会造成大量的磁盘消耗
 * 二：读取的excel行号列号都写在代码中，耦合性非常高
 */
public class LoginCases extends BaseCase{

    // 使用poi解析Excel中的数据
    @DataProvider
    public Object[][] datas(){
        String[] cellNames = {"CaseId", "ApiId", "Params", "ExpectedResponseData"};
        Object[][] datas = CaseUtil.getCaseDatasByApiId("2",cellNames);
        return datas;
    }

}
