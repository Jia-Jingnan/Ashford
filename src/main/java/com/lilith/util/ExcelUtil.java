package com.lilith.util;

import org.apache.poi.ss.usermodel.*;

import java.io.File;

/**
 * @Author:JiaJingnan
 * @Date: 上午1:09 2021/5/8
 * 使用poi解析Excel的工具类
 */
public class ExcelUtil {

    // 读取连续行和列的数据
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

    // 读取非连续的行和列的数据
    public static Object[][] datas(String excelPath, int[] rows, int[] cells){

        // 存储数据的二维数组
        Object[][] datas = null;

        try {
            // 获取workbook对象
            Workbook workbook = WorkbookFactory.create(new File(excelPath));
            // 获取sheet对象
            Sheet sheet = workbook.getSheet("用例");

            // 定义保存提取出的数据的数组
            datas = new Object[rows.length][cells.length];

            // 获取行
            // 通过循环获取每一行
            for (int i = 0; i < rows.length; i++){
                Row row = sheet.getRow(rows[i]);
                // 获取列
                // 第六列第七列为数据
                for (int j = 0; j < cells.length; j++){
                    Cell cell = row.getCell(cells[j]);
                    // 将列设置为字符串类型
                    cell.setCellType(CellType.STRING);
                    String stringCellValue = cell.getStringCellValue();
                    datas[i][j] = stringCellValue;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return datas;
    }

    // 验证传入行号列号集合是否取出数据
    public static void main(String[] args) {
        String excelPath = "src/main/resources/cases/cases.xls";
        int[] rows = {1,2,3,4};
        int[] cells = {5,6};
        Object[][] datas = datas(excelPath,rows,cells);
        for (Object[] data : datas) {
            for (Object datum : data) {
                System.out.print("[" + datum + "]");
            }
            System.out.println();
        }
    }
}
