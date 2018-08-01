package com.kingdee.hrp.sms.util;

import com.kingdee.hrp.sms.common.enums.CtrlType;
import com.kingdee.hrp.sms.common.model.FormField;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

import java.util.List;
import java.util.Map;

/**
 * Excel 导入导出工具类
 *
 * @author yadda
 */
public class ExcelUtil {

    /**
     * 导出Excel
     *
     * @param sheetName sheet名称
     * @param title     标题
     * @param values    内容
     * @param wb        HSSFWorkbook对象
     * @return HSSFWorkbook  对象
     */
    public static HSSFWorkbook getHSSFWorkbook(String sheetName, String[] title, String[][] values, HSSFWorkbook wb) {

        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        if (wb == null) {
            wb = new HSSFWorkbook();
        }

        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(sheetName);

        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        HSSFRow row = sheet.createRow(0);

        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        // 创建一个居中格式
        style.setAlignment(HorizontalAlignment.CENTER);

        //声明列对象
        HSSFCell cell = null;

        //创建标题
        for (int i = 0; i < title.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(style);
        }

        //创建内容
        for (int i = 0; i < values.length; i++) {
            row = sheet.createRow(i + 1);
            for (int j = 0; j < values[i].length; j++) {
                //将内容按顺序赋给对应的列对象
                row.createCell(j).setCellValue(values[i][j]);
            }
        }
        return wb;
    }

    /**
     * 导出Excel
     *
     * @param sheetName sheet名称
     * @param title     标题
     * @param values    内容
     * @param wb        HSSFWorkbook对象
     * @return HSSFWorkbook  对象
     */
    public static HSSFWorkbook getHSSFWorkbook(String sheetName, List<FormField> title,
            Map<Integer, Map<String, Object>> values, HSSFWorkbook wb) {

        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        if (wb == null) {
            wb = new HSSFWorkbook();
        }

        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(sheetName);

        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        HSSFRow row = sheet.createRow(0);

        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        // 创建一个居中格式
        style.setAlignment(HorizontalAlignment.CENTER);

        //声明列对象
        HSSFCell cell = null;

        //创建标题
        for (int i = 0; i < title.size(); i++) {
            cell = row.createCell(i);
            cell.setCellValue(title.get(i).getName());
            cell.setCellStyle(style);
        }

        //创建内容
        for (int i = 1; i <= values.size(); i++) {

            // 数据行从1看是计数
            row = sheet.createRow(i);
            Map<String, Object> lineData = values.get(i);

            for (int k = 0; k < title.size(); k++) {

                FormField formField = title.get(k);
                String key = formField.getKey();

                if (formField.getCtrlType() == CtrlType.F7.value()) {
                    // F7类型导出名称
                    key = key + "_DspName";
                }

                Object data = lineData.get(key);

                if (null == data) {
                    // 分录行数据第一行与表头共用，其他行不显示表头数据
                    row.createCell(k).setCellValue("");
                } else {
                    row.createCell(k).setCellValue(data.toString());
                }
            }
        }

        return wb;
    }
}
