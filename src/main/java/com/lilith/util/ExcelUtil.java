package com.lilith.util;

import com.lilith.entity.Api;
import com.lilith.entity.Case;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.*;
import sun.text.normalizer.UBiDiProps;

import java.io.*;
import java.lang.reflect.Method;
import java.time.temporal.ValueRange;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author:JiaJingnan
 * @Date: 上午1:09 2021/5/8
 * 使用poi解析Excel的工具类
 */
public class ExcelUtil {

    // 为writeBack方法使用，存储caseID和行号的映射
    public static Map<String,Integer> caseIdRownumMapping = new HashMap<>();
    // 列名和列号的映射
    public static Map<String,Integer> cellNameCellnumMapping = new HashMap<>();

    static {
        // caseIdRownumMapping，cellNameCellnumMapping存入数据，供wirteBackData使用
        loadRownumAndCellnumMapping("src/main/resources/cases/cases_v5.xlsx","用例");
    }

    /**
     * 获取caseId以及对应的行索引
     * 获取cellName以及对应的列索引
     * @param excelPath
     * @param sheetName
     */
    private static void loadRownumAndCellnumMapping(String excelPath, String sheetName) {
        InputStream in = null;
        try {
            in = new FileInputStream(new File(excelPath));
            Workbook workbook = WorkbookFactory.create(in);
            Sheet sheet = workbook.getSheet(sheetName);
            // 标题行
            Row titleRow = sheet.getRow(0);
            if (titleRow != null && !isEmptyRow(titleRow)){
                int lastCellNum = titleRow.getLastCellNum();
                for (int i = 0; i < lastCellNum; i++) {
                    Cell cell = titleRow.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    cell.setCellType(CellType.STRING);
                    String title = cell.getStringCellValue();
                    title = title.substring(0,title.indexOf("("));
                    int cellnum = cell.getAddress().getColumn();
                    // 拿到标题和索引映射关系存入map中，保存为映射关系
                    cellNameCellnumMapping.put(title,cellnum);
                }
            }
            // 从第二行开始
            int lastRownum = sheet.getLastRowNum();
            // 循环拿到每个数据行
            for (int i = 0; i <= lastRownum ; i++) {
                Row dataRow = sheet.getRow(i);
                Cell firstCellOfRow = dataRow.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                firstCellOfRow.setCellType(CellType.STRING);
                String caseId = firstCellOfRow.getStringCellValue();
                int rownum  = dataRow.getRowNum();
                // 保存在caseId和行号的map中
                caseIdRownumMapping.put(caseId,rownum);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null){
                    in.close();
                }
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }

    }

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

    /**
     * 加载Excel表中的数据
     * @param excelPath excel相对路径
     * @param sheetName 加载的sheet名称
     * 正向封装：显示的判断列名，根据列名调用对应的set方法，完成数据的封装，代码中有列名，存在耦合问题
     * 反向封装：使用反射
     * 使用set+列名组合成set方法，在使用反射调用发方法
     */
    public static <T> void load(String excelPath, String sheetName, Class<T> tClass) {

        // Class clazz = Case.class;

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
                Object o = tClass.newInstance();
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
                    Method method = tClass.getMethod(methodName, String.class);
                    // 反射
                    method.invoke(o,value);
                }

                if (o instanceof Case){
                    // 将封装的对象放入列表中
                    Case cs = (Case) o;
                    CaseUtil.cases.add(cs);
                } else if (o instanceof Api){
                    Api api = (Api) o;
                    ApiUtil.apis.add(api);
                }
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

    public static void writeBackData(String excelPath, String sheetName, String caseId, String cellName, String res) {

        InputStream in = null;
        OutputStream out = null;
        try {
            in = new FileInputStream(new File(excelPath));
            Workbook workbook = WorkbookFactory.create(in);
            Sheet sheet = workbook.getSheet(sheetName);
            int rownum = caseIdRownumMapping.get(caseId);
            Row row = sheet.getRow(rownum);
            int cellNum = cellNameCellnumMapping.get(cellName);
            Cell cell = row.getCell(cellNum, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            cell.setCellType(CellType.STRING);
            // 设置cell的value
            cell.setCellValue(res);
            // 写入
            out = new FileOutputStream(new File(excelPath));
            workbook.write(out);

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            // 关闭流
            try {
                if (in != null){
                    in.close();
                }
                if (out != null){
                    out.close();
                }
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }

    }
}
