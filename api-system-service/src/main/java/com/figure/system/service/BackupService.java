package com.figure.system.service;


import com.figure.core.model.base.BaseDataBackupTask;
import com.figure.core.model.base.BaseTableBackup;
import com.figure.core.repository.others.CommonMapper;
import com.figure.core.service.base.IBaseDataBackupService;
import com.figure.core.service.base.IBaseDataBackupTaskService;
import com.figure.core.service.base.IBaseTableBackupService;
import com.figure.core.service.sys.ISysParaService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.figure.core.helper.DateHelper;
import com.figure.core.model.base.BaseDataBackup;
import com.figure.core.model.sys.SysPara;
import com.figure.core.util.compression.CompactAlgorithm;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
@Data
public class BackupService {

    @Autowired
    IBaseDataBackupTaskService logBakTaskService;

    @Autowired
    IBaseDataBackupService baseDataBackupService;

    @Autowired
    IBaseTableBackupService baseTableBackupService;

    @Autowired
    ISysParaService sysParaService;

    @Autowired
    CommonMapper commonMapper;

    public void backup(Integer taskId) {
        BaseDataBackupTask baseDataBackupTask = logBakTaskService.getById(taskId);
        System.out.println("开始执行归档任务");
        String taskObject = baseDataBackupTask.getTaskObject();
        Integer taskMode = baseDataBackupTask.getTaskMode();
        Integer backRetentionTime = baseDataBackupTask.getBackRetentionTime();
        Integer dataRetentionTime = baseDataBackupTask.getDataRetentionTime();
        int userId = baseDataBackupTask.getCreateUserId();
        Date excuteTime = baseDataBackupTask.getExcuteTime();
        int backupMode = 1;
        String[] tables = taskObject.split(",");
        for (String table:tables){
            saveFile(new Date(),backupMode,table,userId,backRetentionTime,dataRetentionTime,excuteTime);
        }
    }

    public void saveFile(Date endTime, int backupMode,String table,int userId,int backRetentionTime,int dataRetentionTime,Date excuteTime) {
        System.out.println("保存归档文件");
        BaseDataBackup baseDataBackup = new BaseDataBackup();
        QueryWrapper<BaseTableBackup> baseTableBackupQueryWrapper = new QueryWrapper<>();
        baseTableBackupQueryWrapper.eq("tablename",table);
        List<BaseTableBackup> baseTableBackups =  baseTableBackupService.list(baseTableBackupQueryWrapper);
        if(baseTableBackups==null||baseTableBackups.size()==0){
            return;
        }
        BaseTableBackup baseTableBackup = baseTableBackups.get(0);
        String tableName = baseTableBackup.getTablename();
        String dataName = baseTableBackup.getDataName();
        String conditions = baseTableBackup.getConditions();
        String name = baseTableBackup.getName();
        baseDataBackup.setExcuteTime(excuteTime);
        baseDataBackup.setBackupMode(backupMode);
        baseDataBackup.setBackupTime(new Date());
        baseDataBackup.setExcuteStatus(0);
        baseDataBackup.setBackRetentionTime(backRetentionTime);
        baseDataBackup.setDataRetentionTime(backRetentionTime);
        baseDataBackup.setExcuteUserId(userId);
        baseDataBackupService.save(baseDataBackup);

        MyThread thread = new MyThread(endTime,tableName,dataName,conditions,name,baseDataBackup.getId());
        thread.start();

    }

    class MyThread extends Thread{
        private final Date endTime;
        private final String tableName;
        private final String dataName;
        private final String condition;
        private final String name;
        private final Integer backupId;
        public MyThread(Date endTime, String tableName, String dataName,String condition,String name,Integer backupId) {
            super();
            this.endTime = endTime;
            this.tableName = tableName;
            this.dataName = dataName;
            this.condition = condition;
            this.name = name;
            this.backupId = backupId;
        }

        @Override
        public void run() {
            createExcel(endTime,tableName,dataName,condition,name,backupId);
        }
    }





    private void createExcel(Date endTime, String tableName, String dataName,String condition,String name,Integer backupId) {
        BaseDataBackup baseDataBackup =    baseDataBackupService.getById(backupId);
        Map<String,String> params = new HashMap<String, String>();
        params.put("tableName", tableName);
        List<Map<String, Object>> columns = commonMapper.table(params);
        List<String> column_names = new ArrayList<String>();
        for(Map<String, Object> column:columns) {
            column_names.add(column.get("column_name").toString());
        }


        QueryWrapper<SysPara> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("paraName","bakFilePath");
        List<SysPara> list =  sysParaService.list(queryWrapper);

        String filePath = list.get(0).getParaValue()+""+ DateHelper.dateToStr1(endTime);

        File filex = new File(filePath);
        if(!filex.exists()) {
            filex.mkdir();
        }

        String endTimeStr = DateHelper.dateToStr(endTime);
        String conditionc = "'"+endTimeStr+"'>="+dataName+" AND "+condition;
        params.clear();
        params.put("condition", conditionc);
        params.put("tableName", tableName);

        List<Map<String, Object>> counts = commonMapper.tableDataCount(params);

        int count = Integer.parseInt(counts.get(0).get("count").toString());

        if(count==0){
            baseDataBackupService.removeById(backupId);
            return;
        }

        int num = 1000;

        int page = count/num;


        String startTimeStr = null;

        for(int p =0;p<page;p++) {
            //System.out.println(page+"-"+p);
            String conditionx = "'"+endTimeStr+"'>="+dataName+" AND "+condition+" limit "+p*num+","+num;
            //System.out.println(conditionx);
            params.put("condition", conditionx);
            List<Map<String, Object>> datas = commonMapper.tableData(params);
            //System.out.println(datas.size());
            String fileName = filePath +"/"+(p)+".xls";
            WritableWorkbook wwb =null;
            try {
                wwb = Workbook.createWorkbook(new File(fileName));
                WritableSheet sheet = wwb.createSheet(name,0);//或者rwb.getSheet(0)获取第一个区域
                for(int i =0;i<column_names.size();i++) {
                    sheet.addCell(new Label(i,0,column_names.get(i)));
                }
                for(int j = 0;j<datas.size();j++) {
                    Map<String, Object> data = datas.get(j);
                    if(startTimeStr==null){
                        startTimeStr = data.get(dataName).toString();
                    }
                    for(int k =0;k<column_names.size();k++) {
                        Object columnData = data.get(column_names.get(k));
                        if(columnData==null) {
                            sheet.addCell(new Label(k,j+1,""));
                        }else {
                            sheet.addCell(new Label(k,j+1,data.get(column_names.get(k)).toString()));
                        }

                    }
                }
                wwb.write();
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                try {
                    wwb.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if(count%num>0) {
            String conditionx = "'"+endTimeStr+"'>="+dataName+" AND "+condition+" limit "+page*num+","+count%num;
            params.put("condition", conditionx);
            List<Map<String, Object>> datas = commonMapper.tableData(params);
            String fileName = filePath +"/"+(page+1)+".xls";
            WritableWorkbook wwb =null;
            try {
                wwb = Workbook.createWorkbook(new File(fileName));
                WritableSheet sheet = wwb.createSheet(name,0);//或者rwb.getSheet(0)获取第一个区域
                for(int i =0;i<column_names.size();i++) {
                    sheet.addCell(new Label(i,0,column_names.get(i)));
                }
                for(int j = 0;j<datas.size();j++) {
                    Map<String, Object> data = datas.get(j);
                    if(startTimeStr==null){
                        startTimeStr = data.get(dataName).toString();
                    }
                    for(int k =0;k<column_names.size();k++) {
                        Object columnData = data.get(column_names.get(k));
                        if(columnData==null) {
                            sheet.addCell(new Label(k,j+1,""));
                        }else {
                            sheet.addCell(new Label(k,j+1,data.get(column_names.get(k)).toString()));
                        }

                    }
                }
                wwb.write();
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                try {
                    baseDataBackup.setExcuteStatus(2);
                    baseDataBackupService.updateById(baseDataBackup);
                    wwb.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        new CompactAlgorithm(new File(filePath+".zip")).zipFiles(filex);
        filex.delete();
        String fileName =  filePath+".zip";
        baseDataBackup.setEndTime(DateHelper.dateToStr(endTime));
        baseDataBackup.setStartTime(startTimeStr);
        baseDataBackup.setBackupFileName(fileName);
        baseDataBackup.setExcuteStatus(1);
        baseDataBackup.setFinishTime(new Date());
        baseDataBackupService.updateById(baseDataBackup);

    }
}
