package com.lilith.util;


import com.lilith.entity.Result;
import org.apache.poi.ss.usermodel.*;

import java.io.*;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    // 保存数据回写Excel对象的集合
    public static List<Result> resultList = new ArrayList<>();

    static {
        // caseIdRownumMapping，cellNameCellnumMapping存入数据，供wirteBackData使用
        loadRownumAndCellnumMapping(PropertiesUtil.getProperty("excel.path"),PropertiesUtil.getProperty("case.sheet.name"));
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


    /**
     * 加载Excel表中的数据
     * @param excelPath excel相对路径
     * @param sheetName 加载的sheet名称
     * 正向封装：显示的判断列名，根据列名调用对应的set方法，完成数据的封装，代码中有列名，存在耦合问题
     * 反向封装：使用反射
     * 使用set+列名组合成set方法，在使用反射调用发方法
     */
    public static <T> List<T> load(String excelPath, String sheetName, Class<T> tClass) {

        List<T> list = new ArrayList<>();

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
                T o = tClass.newInstance();
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
                list.add(o);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;

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

    /**
     * 批量回写数据的方法
     * @param excelPath
     */
    public static void batchWriteDatas(String excelPath) {
        InputStream in = null;
        OutputStream out = null;
        try {
            in = new FileInputStream(new File(excelPath));
            Workbook workbook = WorkbookFactory.create(in);
            for (Result result : resultList){
                String sheetName = result.getSheetName();
                Sheet sheet = workbook.getSheet(sheetName);

                String caseId = result.getCaseId();
                int rownum = caseIdRownumMapping.get(caseId);
                Row row = sheet.getRow(rownum);
                String cellName = result.getCellName();
                int cellnum = cellNameCellnumMapping.get(cellName);
                Cell cell = row.getCell(cellnum, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                cell.setCellType(CellType.STRING);
                String res = result.getRes();
                cell.setCellValue(res);
            }
            out = new FileOutputStream(new File(excelPath));
            workbook.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
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
