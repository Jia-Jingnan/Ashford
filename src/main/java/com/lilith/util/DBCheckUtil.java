package com.lilith.util;

import com.alibaba.fastjson.JSONObject;
import com.lilith.entity.DBChecker;
import com.lilith.entity.DBQueryResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author:JiaJingnan
 * @Date: 下午11:59 2021/6/1
 */
public class DBCheckUtil {


    /**
     * 根据脚本执行查询 并返回查询结果
     * @param validateSql 需要执行的查询语句
     * @return
     */
    public static String doQuery(String validateSql) {
        // 将脚本字符串封装成对象
        List<DBChecker> dbCheckers = JSONObject.parseArray(validateSql, DBChecker.class);
        List<DBQueryResult> dbQueryResults = new ArrayList<>();
        for (DBChecker dbChecker : dbCheckers){
            // 拿到sql的编号
            String no = dbChecker.getNo();
            String sql = dbChecker.getSql();
            // 执行查询，获取结果
            Map<String, Object> columnLabelAndValues = JDBCUtil.query(sql);
            DBQueryResult dbQueryResult = new DBQueryResult();
            // 将结果封装到dbQueryResult中
            dbQueryResult.setNo(no);
            dbQueryResult.setColumenLabelAndValue(columnLabelAndValues);
            dbQueryResults.add(dbQueryResult);
        }
        return JSONObject.toJSONString(dbQueryResults);
    }

    // 验证将json格式数据，解析为对象列表
    public static void main(String[] args) {
        String validateSql = "[{\"no\":\"1\",\"sql\":\"select leavemount from member\"}," +
                "{\"no\":\"2\",\"sql\":\"select leavemount from user\"}]";
        List<DBChecker> dbCheckers = JSONObject.parseArray(validateSql,DBChecker.class);
        for (DBChecker dbChecker : dbCheckers){
            System.out.println(dbChecker);
        }
    }
}
