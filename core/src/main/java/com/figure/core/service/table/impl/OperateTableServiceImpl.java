package com.figure.core.service.table.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.model.record.CreateRecordFile;
import com.figure.core.model.record.RecordFile;
import com.figure.core.repository.table.OperateTableMapper;
import com.figure.core.service.table.IOperateTableService;
import lombok.Data;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Service
@Data
public class OperateTableServiceImpl extends ServiceImpl<OperateTableMapper, CreateRecordFile> implements IOperateTableService {

    private String dataBaseName;

    @Resource
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    private void initDataBaseName() throws SQLException {
        Connection conn = jdbcTemplate.getDataSource().getConnection();
        dataBaseName = conn.getCatalog();
        conn.close();
    }

    @Override
    public void createRecordFileTable(CreateRecordFile createRecordFile) {
        this.baseMapper.createRecordFileTable(createRecordFile);
    }

    @Override
    public boolean checkRecordFileTable(String tableName) {
        int count = this.baseMapper.countRecordFileTable(dataBaseName, tableName);
        if (count == 1) {
            return true;
        } else {
            return false;
        }
    }


    @Override
    public void saveByTableName(RecordFile recordFile) {
        this.baseMapper.saveByTableName(recordFile.getTableName(), recordFile);
    }

    @Override
    public List<RecordFile> selectByTableName(String tableName, Wrapper<RecordFile> recordFileWrapper) {
        return this.baseMapper.selectByTableName(tableName, recordFileWrapper);
    }

    @Override
    public void insertRecordFileNameWithServiceCode(String tableName, String serviceCode) {
        if(this.countRecordFileNameWithServiceCode(tableName,serviceCode)==0){
            this.baseMapper.insertRecordFileNameWithServiceCode(tableName, serviceCode);
        }
    }

    @Override
    public int countRecordFileNameWithServiceCode(String tableName, String serviceCode) {
        return this.baseMapper.countRecordFileNameWithServiceCode(tableName,serviceCode);
    }
}
