package com.lilith.cases;

import com.lilith.util.CaseUtil;
import org.testng.annotations.DataProvider;

/**
 * @Author:JiaJingnan
 * @Date: 上午12:42 2021/5/8
 * 完成register接口的多个测试用例的测试，使用testng
 */
public class RechargeCases extends BaseCase{

    // 调用顺序
    // Case(从数组中取出数据执行用例) -> CaseUtil(将对象中数据取出放在数组中) -> ExcelUtil(将数据封装成对象)

    // 使用poi解析Excel中的数据
    @DataProvider
    public Object[][] datas(){
        String[] cellNames = {"CaseId", "ApiId", "Params"};
        Object[][] datas = CaseUtil.getCaseDatasByApiId("3",cellNames);
        return datas;
    }


}
