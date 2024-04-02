package com.figure.system.test;

import com.figure.core.model.device.DeviceHistoryIndicator;
import com.taosdata.jdbc.TSDBDriver;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class TDengineExample<main> {


    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        Class.forName("com.taosdata.jdbc.TSDBDriver");

        String jdbcUrl = "jdbc:TAOS-RS://192.168.110.204:6041/frp?user=figure&password=Figure@321";
        Properties connProps = new Properties();
        connProps.setProperty(TSDBDriver.PROPERTY_KEY_BATCH_LOAD, "true");
        Connection connection = DriverManager.getConnection(jdbcUrl, connProps);

        Statement stmt = connection.createStatement();

        StringBuilder sb = new StringBuilder("INSERT INTO ");

        sb.append("DC00027_P012").append(" USING device_indicator TAGS(")
         .append("\'DC00027\'").append(", ") // tag: deviceCode
                .append("\'P012\'") // tag: indicatorCode
                .append(") VALUES(")
                .append('\'').append("2024-03-06 10:51:45.239").append('\'').append(",") // ts
                .append('\'').append(123).append('\'').append(",") //indicatorValue
                .append(1).append(")"); //alarmStatus


        String sql = sb.toString();
        System.out.println("sql:"+sql);
        int rowCount = stmt.executeUpdate(sql);
        System.out.println("rowCount=" + rowCount); // rowCount=8


        sql = "select * from device_indicator WHERE 1=1 AND ts >= '2020-03-23 06:10:08' AND ts <= '2024-03-23 18:10:08' order by ts desc limit 0,10";

        // 执行查询
        ResultSet resultSet = stmt.executeQuery(sql);

        List<DeviceHistoryIndicator> deviceHistoryIndicators = new ArrayList<>();


        // 处理结果
        while (resultSet.next()) {
            // 创建实体对象
            DeviceHistoryIndicator entity = new DeviceHistoryIndicator();
            entity.setDeviceCode(resultSet.getString("devicecode"));
            entity.setIndicatorCode(resultSet.getString("indicatorcode"));
            entity.setCollectTime(resultSet.getString("ts"));
            entity.setAlarmStatus(resultSet.getInt("alarmstate"));
            entity.setIndicatorValue(resultSet.getString("indicatorvalue"));
            deviceHistoryIndicators.add(entity);
        }

        sql = "select count(*) from device_indicator WHERE 1=1 AND ts >= '2020-03-23 06:10:08' AND ts <= '2024-03-23 18:10:08'";

        // 执行查询
        resultSet = stmt.executeQuery(sql);

        // 获取记录总数
        int count = 0;
        if (resultSet.next()) {
            count = resultSet.getInt(1);
        }

        // 关闭连接
        connection.close();
    }


}
