package com.figure.core.repository.others;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2021-04-21
 */
@Component
@Mapper

public interface CommonMapper{

    List<Map<String,Object>> table(@Param("conditions") Map<String,String> conditions);

    List<Map<String,Object>> tableData(@Param("conditions") Map<String,String> conditions);

    List<Map<String,Object>> tableDataCount(@Param("conditions") Map<String,String> conditions);

    List<Map<String,Object>> getTableMate(@Param("conditions") Map<String,String> conditions);

    void DeleteTableData(@Param("conditions") Map<String,String> conditions);

    @Select("select ${columns} from ${tableName} where ${conditions}")
    List<Map<String, Object>> queryTable(String columns, String tableName, String conditions);

    @Select("${sql}")
    List<Map<String, Object>> querySql(String sql);
}
