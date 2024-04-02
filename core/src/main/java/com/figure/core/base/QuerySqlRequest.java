package com.figure.core.base;
import lombok.Data;

@Data
public class QuerySqlRequest {
    private String sql;
    private String[] sqls;
}
