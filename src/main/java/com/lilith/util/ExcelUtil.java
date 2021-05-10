package com.lilith.util;

import com.lilith.entity.Case;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.lang.reflect.Method;
import java.time.temporal.ValueRange;

/**
 * @Author:JiaJingnan
 * @Date: 上午1:09 2021/5/8
 * 使用poi解析Excel的工具类
 */
public class ExcelUtil {

    // 读取连续行和列的数据
    public static Object[][] datas(String excelPath, String sheetName, int startRow, int endRow, int startCell, int endCell){

        // 存储数据的二维数组
        Object[][] datas = null;

        try {
            // 获取workbook对象
            Workbook workbook = WorkbookFactory.create(new File(excelPath));
            // 获取sheet对象
            Sheet sheet = workbook.getSheet(sheetName);

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
    public static Object[][] datas(String excelPath, String sheetName, int[] rows, int[] cells){

        // 存储数据的二维数组
        Object[][] datas = null;

        try {
            // 获取workbook对象
            Workbook workbook = WorkbookFactory.create(new File(excelPath));
            // 获取sheet对象
            Sheet sheet = workbook.getSheet(sheetName);

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
        String excelPath = "src/main/resources/cases/cases_v4.xlsx";
        for (Case cs : CaseUtil.cases){
            System.out.println(cs);
        }
    }

    /**
     * 加载Excel表中的数据
     * @param excelPath excel相对路径
     * @param sheetName 加载的sheet名称
     * 正向封装：显示的判断列名，根据列名调用对应的set方法，完成数据的封装，代码中有列名，存在耦合问题
     * 反向封装：使用反射
     * 使用set+列名组合成set方法，在使用反射调用发方法
     */
    public static void load(String excelPath, String sheetName) {

        Class clazz = Case.class;

        try {
            // 创建workBook对象
            Workbook workbook = WorkbookFactory.create(new File(excelPath));
            // 创建sheet对象
            Sheet sheet = workbook.getSheet(sheetName);
            // 获取第一行的数据，用于拼接反射调用的方法名，set+列名，如setCaseId
            Row titleRow = sheet.getRow(0);
            // 获取最后一列列号
            int lastCellNum = titleRow.getLastCellNum();
            // 有几列对应就有几个字段
            String[] fields = new String[lastCellNum];
            // 循环处理每一列,取出每一列里面的字段名，保存到数组中
            for (int i = 0; i < lastCellNum; i++){

                Cell cell = titleRow.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                cell.setCellType(CellType.STRING);
                // 获取标题 CaseId(用例编号)
                String title = cell.getStringCellValue();

                // 截取字符串
                title = title.substring(0,title.indexOf("("));
                // 存入数组中
                fields[i] = title;
            }
            // 获取行索引
            int lastRowIndex = sheet.getLastRowNum();
            // 循环处理每一个数据行
            for (int i = 1; i <= lastRowIndex; i++){
                Case cs = (Case) clazz.newInstance();
                Row dataRow = sheet.getRow(i);
                if (dataRow == null || isEmptyRow(dataRow)){
                    continue;
                }
                // 取出行中的每一列
                for (int j = 0; j < lastCellNum; j++){
                    Cell cell = dataRow.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    cell.setCellType(CellType.STRING);
                    String value = cell.getStringCellValue();
                    // 获取反射方法名
                    String methodName = "set" + fields[j];
                    // System.out.println(methodName);
                    // 获取反射Method类
                    Method method = clazz.getMethod(methodName, String.class);
                    // 反射
                    method.invoke(cs,value);
                }

                // 将封装的对象放入列表中
                CaseUtil.cases.add(cs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static boolean isEmptyRow(Row dataRow) {
        short lastCellNum = dataRow.getLastCellNum();
        for (int i = 0; i < lastCellNum; i++){
            Cell cell = dataRow.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            cell.setCellType(CellType.STRING);
            String value = cell.getStringCellValue();
            if (value != null && value.trim().length() > 0){

                return false;
            }

        }
        return true;
    }
}
