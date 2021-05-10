package com.lilith.util;

import com.lilith.entity.Case;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author:JiaJingnan
 * @Date: 下午11:30 2021/5/10
 * Case用例工具类,获取case信息
 */
public class CaseUtil {

    /**
     * 保存所有的用例对象
     */
    public static List<Case> cases = new ArrayList<>();

    static {
        // 将所有数据解析封装到cases
        ExcelUtil.load("src/main/resources/cases/cases_v4.xlsx","用例");
    }

    /**
     * 根据接口编号获取接口用例，从所有的用例对象中获取
     * @param apiId 要获取的接口编号
     * @param cellNames 要获取的参数对应的列名
     * @return
     */
    public static Object[][] getCaseDatasByApiId(String apiId, String[] cellNames){

        // 遍历Case集合列表，获取满足条件的case
        for (Case c : cases) {
            
        }
        return null;
    }

}
