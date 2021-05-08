package com.lilith.util;

import org.apache.poi.ss.usermodel.*;

import java.io.File;

/**
 * @Author:JiaJingnan
 * @Date: 上午1:09 2021/5/8
 * 使用poi解析Excel的工具类
 */
public class ExcelUtil {

    public static Object[][] datas(String excelPath){

        // 存储数据的二维数组
        Object[][] datas = null;

        try {
            // 获取workbook对象
            Workbook workbook = WorkbookFactory.create(new File(excelPath));
            // 获取sheet对象
            Sheet sheet = workbook.getSheet("用例");

            datas = new Object[4][2];

            // 获取行
            for (int i = 1; i <= 4; i++){
                Row row = sheet.getRow(i);
                // 获取列
                // 第六列第七列为数据
                for (int j = 5; j <= 6; j++){
                    Cell cell = row.getCell(j);
                    // 将列设置为字符串类型
                    cell.setCellType(CellType.STRING);
                    String stringCellValue = cell.getStringCellValue();
                    datas[i-1][j-5] = stringCellValue;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return datas;
    }

    // 验证数据是否取出
    public static void main(String[] args) {
        String excelPath = "src/main/resources/cases/cases.xls";
        Object[][] datas = datas(excelPath);
        for (Object[] data : datas) {
            for (Object datum : data) {
                System.out.print("[" + datum + "]");
            }
            System.out.println();
        }
    }
}
