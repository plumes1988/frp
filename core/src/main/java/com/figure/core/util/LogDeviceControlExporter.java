package com.figure.core.util;

import com.figure.core.model.log.LogDeviceControl;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.figure.core.model.log.LogDeviceControl.requestStatus_map;
import static com.figure.core.util.DateUtils.dateToStrLong;

public class LogDeviceControlExporter {

    public static Map export(List<LogDeviceControl> logs, String uploadPath){
        Map  result = new HashMap();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("设备报警");

        // 创建标题行
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("请求 ID");
        headerRow.createCell(1).setCellValue("设备名称");
        headerRow.createCell(2).setCellValue("操作时间");
        headerRow.createCell(4).setCellValue("指标名称");
        headerRow.createCell(5).setCellValue("指标值");
        headerRow.createCell(6).setCellValue("操作人员");
        headerRow.createCell(7).setCellValue("响应消息");
        headerRow.createCell(8).setCellValue("响应时间");
        headerRow.createCell(9).setCellValue("操作状态");

        // 填充数据行
        int rowNum = 1;
        for (LogDeviceControl log : logs) {
            Row dataRow = sheet.createRow(rowNum++);
            dataRow.createCell(0).setCellValue(log.getRequestId());
            dataRow.createCell(1).setCellValue(log.getDeviceName());
            dataRow.createCell(2).setCellValue(log.getRequestTime()!=null?dateToStrLong(log.getRequestTime()):"");
            dataRow.createCell(4).setCellValue(log.getIndicatorName());
            dataRow.createCell(5).setCellValue(log.getIndicatorValue());
            dataRow.createCell(6).setCellValue(log.getOperatorUserName());
            dataRow.createCell(7).setCellValue(log.getResponseContent());
            dataRow.createCell(8).setCellValue(log.getResponseTime()!=null?dateToStrLong(log.getResponseTime()):"");
            dataRow.createCell(9).setCellValue(requestStatus_map.get(log.getRequestStatus()));

        }

        // 调整列宽以适应内容
        sheet.autoSizeColumn(1);

        String fileName = "设备操作日志_"+(new Date().getTime())+".xlsx";

        String file = uploadPath+"/"+fileName;

        // 保存工作簿到文件
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            workbook.write(outputStream);
            System.out.println("Excel文件导出成功！");
            result.put("success",true);
            result.put("file",fileName);
        } catch (IOException e) {
            System.out.println("导出Excel文件时发生错误：" + e.getMessage());
            result.put("success",false);
            result.put("msg",e.getMessage());
        }
        return result;
    }
}
