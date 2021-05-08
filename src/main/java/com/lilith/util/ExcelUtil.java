package com.lilith.util;

import org.apache.poi.ss.usermodel.*;

import java.io.File;

/**
 * @Author:JiaJingnan
 * @Date: 上午1:09 2021/5/8
 * 使用poi解析Excel的工具类
 */
public class ExcelUtil {

    public static Object[][] datas(String excelPath, int startRow, int endRow, int startCell, int endCell){

        // 存储数据的二维数组
        Object[][] datas = null;

        try {
            // 获取workbook对象
            Workbook workbook = WorkbookFactory.create(new File(excelPath));
            // 获取sheet对象
            Sheet sheet = workbook.getSheet("用例");

            datas = new Object[endRow - startRow + 1][endCell - startCell + 1];

            // 获取行
            for (int i = startRow; i <= endRow; i++){
                Row row = sheet.getRow(i);
                // 获取列
                // 第六列第七列为数据
                for (int j = startCell; j <= endCell; j++){
                    Cell cell = row.getCell(j);
                    // 将列设置为字符串类型
                    cell.setCellType(CellType.STRING);
                    String stringCellValue = cell.getStringCellValue();
                    datas[i-startRow][j-startCell] = stringCellValue;
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
        Object[][] datas = datas(excelPath,1,4,5,6);
        for (Object[] data : datas) {
            for (Object datum : data) {
                System.out.print("[" + datum + "]");
            }
            System.out.println();
        }
    }
}
