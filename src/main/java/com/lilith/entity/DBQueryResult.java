package com.lilith.entity;

import java.util.Map;

/**
 * 数据库查询结果实体类
 * @Author:JiaJingnan
 * @Date: 15:00 2020/4/12
 */
public class DBQueryResult {

    // 脚本编号
    private String no;
    // 脚本执行查到的数据，一次性保存到Map中，key为字段名，value保存的是对应字段查到的数据
    private Map<String, Object> columnLabelAndValue;

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public Map<String, Object> getColumenLabelAndValue() {
        return columnLabelAndValue;
    }

    public void setColumenLabelAndValue(Map<String, Object> columenLabelAndValue) {
        this.columnLabelAndValue = columenLabelAndValue;
    }

    @Override
    public String toString() {
        return "DBQueryResult{" +
                "no='" + no + '\'' +
                ", columnLabelAndValue=" + columnLabelAndValue +
                '}';
    }
}
