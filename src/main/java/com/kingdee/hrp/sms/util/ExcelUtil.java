package com.kingdee.hrp.sms.util;

import com.kingdee.hrp.sms.common.enums.CtrlType;
import com.kingdee.hrp.sms.common.model.FormField;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;

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
        // 不显示网格线
        sheet.setDisplayGridlines(false);
        // 第三步，创建单元格，字体等样式
        HSSFCellStyle headerStyle = wb.createCellStyle();
        // 创建一个居中格式
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        //垂直居中
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        //设置边框
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);

        // 字体设置
        HSSFFont headerFont = wb.createFont();
        headerFont.setFontName("宋体");
        //字号
        headerFont.setFontHeightInPoints((short) 12);
        //粗体显示
        headerFont.setBold(true);

        // 颜色
        headerFont.setColor(HSSFFont.COLOR_RED);
        // 字体样式
        headerStyle.setFont(headerFont);

        // 第四步，创建标题,在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        HSSFRow row = sheet.createRow(0);
        // 行高
        row.setHeightInPoints(25);

        HSSFCell cell = null;
        for (int i = 0; i < title.size(); i++) {
            cell = row.createCell(i);
            cell.setCellValue(title.get(i).getName());
            // 标题行样式
            cell.setCellStyle(headerStyle);
        }

        // 带表头数据行的单元格样式
        HSSFCellStyle cellStyle = wb.createCellStyle();
        // 水平居中
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        //垂直居中
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        //设置边框
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        //设置自动换行
        cellStyle.setWrapText(true);
        //设置字体
        HSSFFont cellFont = wb.createFont();
        //粗体显示
        headerFont.setBold(false);
        cellStyle.setFont(cellFont);

        // 无表头数据的单元格样式
        HSSFCellStyle entryDataCellStyle = wb.createCellStyle();
        entryDataCellStyle.cloneStyleFrom(cellStyle);

        entryDataCellStyle.setBorderTop(BorderStyle.NONE);
        entryDataCellStyle.setBorderRight(BorderStyle.NONE);
        entryDataCellStyle.setBorderBottom(BorderStyle.NONE);
        entryDataCellStyle.setBorderLeft(BorderStyle.NONE);
        entryDataCellStyle.setShrinkToFit(true);

        //创建内容
        for (int i = 1; i <= values.size(); i++) {

            // 数据行从1看是计数
            row = sheet.createRow(i);
            // 行高
            row.setHeightInPoints(20);
            // 一行导出的数据
            Map<String, Object> lineData = values.get(i);

            for (int k = 0; k < title.size(); k++) {

                FormField formField = title.get(k);
                String key = formField.getKey();

                if (formField.getCtrlType() == CtrlType.F7.value()) {
                    // F7类型导出名称
                    key = key + "_DspName";
                }

                Object data = lineData.get(key);

                HSSFCell newCell = row.createCell(k);

                // 单元格样式
                if (lineData.containsKey("$entryData") && formField.getPage() == 0) {
                    // 纯分录数据，表头字段值为"",表头字段不要边框-美观些
                    newCell.setCellStyle(entryDataCellStyle);
                } else {
                    newCell.setCellStyle(cellStyle);
                }

                if (null == data) {
                    // 分录行数据第一行与表头共用，其他行不显示表头数据
                    newCell.setCellValue("");
                } else {
                    newCell.setCellValue(data.toString());
                }
            }
        }

        //设置自动列宽

        for (int i = 0; i < title.size(); i++) {
            sheet.autoSizeColumn(i);
            // 自适应宽度稍微增加些更美观
            sheet.setColumnWidth(i, sheet.getColumnWidth(i) * 12 / 10);
        }

        return wb;
    }
}
