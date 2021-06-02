package com.lilith.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @Author:JiaJingnan
 * @Date: 22:14 2020/4/9
 */
public class JDBCUtil {
    public static Properties properties = new Properties();
    static {
        System.out.println("静态代码块解析properties数据");
        InputStream inputStream;
        try {
            String jdbcPropertiesPath = "src/test/resources/jdbc.properties";
            inputStream = new FileInputStream(new File(jdbcPropertiesPath));
            properties.load(inputStream);
        } catch (Exception e) {
            System.out.println("发生了异常");
            e.printStackTrace();
        }
    }

    /**
     * 根据sql查询表数据，并以map返回，key为字段名，value为字段值
     * @param sql 要执行的查询语句
     * @param values 条件字段的值
     * @return
     */
    public static Map<String, Object> query(String sql, Object ... values){ // value是可变参数
        Map<String, Object> columnLabelAndValues = null;

        try {
            // 1.根据连接信息，获得数据库连接（连上数据库）
            Connection connection = getConnection();
            // 2.获取PreparedStatement对象（此类型的对象有提供数据库操作的方法）
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            // 3.设置条件字段的值（实时绑定）
            for (int i =0; i < values.length; i++) {
                preparedStatement.setObject(i + 1, values[i]);
            }
            // 4.调用查询方法，执行查询，返回ResultSet(结果集)
            ResultSet resultSet = preparedStatement.executeQuery();
            // 获取查询相关信息
            ResultSetMetaData metaData = resultSet.getMetaData();
            // 得到查询字段的数目
            int columnCount = metaData.getColumnCount();
            // 5.从结果集中取查询数据
            columnLabelAndValues = new HashMap<String, Object>();
            while (resultSet.next()){
                // 循环取出每个查询字段的数据
                for (int i = 1; i <= columnCount; i++){
                    String columnLabel = metaData.getColumnLabel(i);
                    String columnValue = resultSet.getObject(columnLabel).toString();
                    columnLabelAndValues.put(columnLabel, columnValue);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return columnLabelAndValues;
    }

    /**
     * 获取数据库连接
     * @return
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        // 1.从properties文件获取url
        String url = properties.getProperty("jdbc.url");
        // 2.从properties文件获取user
        String user = properties.getProperty("jdbc.username");
        // 3.从properties文件获取password
        String password = properties.getProperty("jdbc.password");
        Connection connection = DriverManager.getConnection(url, user, password);
        return connection;
    }
}
