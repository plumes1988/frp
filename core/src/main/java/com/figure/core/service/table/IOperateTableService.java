package com.figure.core.service.table;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.figure.core.model.record.CreateRecordFile;
import com.figure.core.model.record.RecordFile;

import java.util.List;

public interface IOperateTableService extends IService<CreateRecordFile> {

    void createRecordFileTable(CreateRecordFile createRecordFile);

    boolean checkRecordFileTable(String tableName);

    void saveByTableName(RecordFile recordFile);

    List<RecordFile> selectByTableName(String tableName, Wrapper<RecordFile> recordFileWrapper);

    void insertRecordFileNameWithServiceCode(String tableName, String serviceCode);

    int countRecordFileNameWithServiceCode(String tableName, String serviceCode);
}
