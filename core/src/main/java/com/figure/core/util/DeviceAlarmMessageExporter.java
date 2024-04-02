package com.figure.core.util;

import com.figure.core.model.device.DeviceAlarmMessage;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

import static com.figure.core.model.device.DeviceAlarmMessage.*;
import static com.figure.core.util.DateUtils.dateToStrLong;

public class DeviceAlarmMessageExporter {

    public static Map export(List<DeviceAlarmMessage> alarms,String uploadPath){
        Map  result = new HashMap();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("设备报警");

        // 创建标题行
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("设备编号");
        headerRow.createCell(1).setCellValue("设备名称");
        headerRow.createCell(2).setCellValue("设备编号");
        headerRow.createCell(3).setCellValue("指标编号");
        headerRow.createCell(4).setCellValue("指标名称");
        headerRow.createCell(5).setCellValue("报警类型");
        headerRow.createCell(6).setCellValue("开始时间");
        headerRow.createCell(7).setCellValue("结束时间");
        headerRow.createCell(8).setCellValue("持续时间");
        headerRow.createCell(9).setCellValue("报警状态");
        headerRow.createCell(10).setCellValue("确认状态");
        headerRow.createCell(11).setCellValue("故障类型");
        headerRow.createCell(12).setCellValue("处理结果");

        // 填充数据行
        int rowNum = 1;
        for (DeviceAlarmMessage alarm : alarms) {
            Row dataRow = sheet.createRow(rowNum++);
            dataRow.createCell(0).setCellValue(alarm.getAlarmId().toString());
            dataRow.createCell(1).setCellValue(alarm.getDeviceName());
            dataRow.createCell(2).setCellValue(alarm.getDeviceCode());
            dataRow.createCell(3).setCellValue(alarm.getIndicatorCode());
            dataRow.createCell(4).setCellValue(alarm.getIndicatorName());
            dataRow.createCell(5).setCellValue(alarm.getAlarmTypeName());
            dataRow.createCell(6).setCellValue(alarm.getHappenTime()!=null?dateToStrLong(alarm.getHappenTime()):"");
            dataRow.createCell(7).setCellValue(alarm.getEndTime()!=null?dateToStrLong(alarm.getEndTime()):"");
            Integer continueTime = alarm.getContinueTime();
            String continueTime_str = "";
            if(continueTime!=null){
                double seconds = (double) continueTime / 1000; // 毫秒转换为秒
                seconds = Math.round(seconds * 100.0) / 100.0; // 保留两位小数
                continueTime_str = seconds+"秒";
            }
            dataRow.createCell(8).setCellValue(continueTime_str);
            dataRow.createCell(9).setCellValue(status_map.get(alarm.getStatus()));
            dataRow.createCell(10).setCellValue(confirm_map.get(alarm.getConfirm()));
            dataRow.createCell(11).setCellValue(faultType_map.get(alarm.getFaultType()));
            dataRow.createCell(12).setCellValue(alarm.getResult());

        }

        // 调整列宽以适应内容
        sheet.autoSizeColumn(1);

        String fileName = "设备报警_"+(new Date().getTime())+".xlsx";

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
