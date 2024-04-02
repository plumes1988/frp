package com.figure.core.repository.table;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.figure.core.model.record.CreateRecordFile;
import com.figure.core.model.record.RecordFile;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 新建录制文件表
 * </p>
 *
 * @author feather
 * @date 2023-03-29 10:36:18
 */

public interface OperateTableMapper extends BaseMapper<CreateRecordFile> {

    void createRecordFileTable(CreateRecordFile createRecordFile);

    int countRecordFileTable(@Param("dataBaseName") String dataBaseName, @Param("tableName") String tableName);

    void saveByTableName(@Param("tableName") String tableName, @Param("recordFile") RecordFile recordFile);

    List<RecordFile> selectByTableName(@Param("tableName") String tableName, @Param(Constants.WRAPPER) Wrapper<RecordFile> recordFileWrapper);

    void insertRecordFileNameWithServiceCode(@Param("tableName") String tableName, @Param("serviceCode") String serviceCode);

    int countRecordFileNameWithServiceCode(@Param("tableName")String tableName, @Param("serviceCode")String serviceCode);
}
