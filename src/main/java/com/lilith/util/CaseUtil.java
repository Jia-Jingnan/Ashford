package com.lilith.util;

import com.lilith.cases.RegisterCases;
import com.lilith.entity.Case;

import java.lang.reflect.Method;
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
        // 将所有数据解析封装到cases，load方法中已经调用CaseUtil.cases.add()将对象加入列表中
        // 下一步可以直接取出列表中的数据组成datas
        List<Case> list = ExcelUtil.load(PropertiesUtil.getProperty("excel.path"), PropertiesUtil.getProperty("case.sheet.name"), Case.class);
        cases.addAll(list);

    }

    /**
     * 根据接口编号获取接口用例，从所有的用例对象中获取，从接口中取出数据
     * @param apiId 要获取的接口编号
     * @param cellNames 要获取的参数对应的列名
     * @return
     */
    public static Object[][] getCaseDatasByApiId(String apiId, String[] cellNames){

        // 保存符合条件的对象
        List<Case> caseList = new ArrayList<>();

        Class<Case> clazz = Case.class;

        // 遍历Case集合列表，获取满足条件的case，并将数据添加到caseList中
        for (Case c : cases) {

            if (c.getApiId().equals(apiId)){
                caseList.add(c);
            }
        }
        Object[][] datas = new Object[caseList.size()][cellNames.length];

        // 遍历对象，从caseList对象中取出要的数据存入datas中
        for (int i = 0; i < caseList.size(); i++){
            Case cs = caseList.get(i);

            String value = "";

            // 循环出要取的列号
            for (int j = 0; j < cellNames.length; j++){
                try {
                    // 使用反射
                    String methodName = "get" + cellNames[j];
                    // 获取到反射的方法对象
                    Method method = clazz.getMethod(methodName);
                    // System.out.println(methodName);
                    value = (String) method.invoke(cs);
                    datas[i][j] = value;
                } catch (Exception e){
                    e.printStackTrace();
                }
                datas[i][j] = value;
            }
        }
        return datas;
    }

    public static void main(String[] args) {
        for (Case cs : CaseUtil.cases){
            System.out.println(cs);
        }

        System.out.println("--------------------");

        String[] cellNames = {"Params"};
        Object[][] datas = getCaseDatasByApiId("1",cellNames);
        for (Object[] objects : datas) {
            for (Object object : objects) {
                System.out.println(object);
            }
        }
    }

}
