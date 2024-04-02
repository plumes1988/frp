package com.figure.core.service.device.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.db.JavaMemoryDb;
import com.figure.core.model.device.DeviceHistoryIndicator;
import com.figure.core.model.device.DeviceIndicatorParamRel;
import com.figure.core.repository.device.DeviceHistoryIndicatorMapper;
import com.figure.core.rocketmq.message.DeviceItemInfo;
import com.figure.core.rocketmq.message.DeviceStatus;
import com.figure.core.service.device.IDeviceHistoryIndicatorService;
import com.figure.core.service.device.IDeviceIndicatorParamRelService;
import com.figure.core.service.device.IDeviceIndicatorParamService;
import com.figure.core.service.others.ICommonService;
import com.figure.core.util.BusinessUtils;
import com.figure.core.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import static com.figure.core.model.device.DeviceIndicatorParam.NUMBER_VARIABLE;
import static com.figure.core.model.device.DeviceIndicatorParam.UNIT_TAG;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2022-06-30
 */
@Service
public class DeviceHistoryIndicatorServiceImpl extends ServiceImpl<DeviceHistoryIndicatorMapper, DeviceHistoryIndicator> implements IDeviceHistoryIndicatorService {


    @Autowired
    ICommonService commonService;

    @Resource
    private IDeviceIndicatorParamRelService deviceIndicatorParamRelService;

    @Resource
    private IDeviceIndicatorParamService deviceIndicatorParamService;

    @Autowired
    @Qualifier("dataSource03")
    private DataSource dataSource03;

    private Map<String,String> column_map = new HashMap(){{
        put("collectTime","ts");
    }};

    @Override
    public void saveHistoryIndicator(DeviceStatus deviceStatus, DeviceItemInfo indexInfo, String messageTime,Date updateTime,Date receiveTime) {

        String cur_indicatorValue = indexInfo.getIndexData();

        Integer recordMode =  deviceIndicatorParamRelService.getRecordMode(deviceStatus.getDeviceId(),indexInfo.getIndexCode());

        boolean need_save = false;

        //历史数据记录 0：不记录 1：实时记录 2：记录变更
        if(recordMode!=null&&recordMode!=0){
            if(recordMode==1){
                need_save = true;
            }
            if(recordMode==2){
                DeviceIndicatorParamRel rel_ = new DeviceIndicatorParamRel();
                rel_.setDeviceCode(deviceStatus.getDeviceCode());
                rel_.setIndicatorCode(indexInfo.getIndexCode());
                String key = BusinessUtils.getLiveDeviceIndicatorKey(rel_);
                String prev_indicatorValue = JavaMemoryDb.get_from_DEVICE_CUR_INDICATOR_VALUE_FOR_HISTORY(key);
                Integer changeThreshold =  deviceIndicatorParamRelService.getChangeThreshold(deviceStatus.getDeviceId(),indexInfo.getIndexCode());
                if(!cur_indicatorValue.equals(prev_indicatorValue)){
                    need_save = true;
                    if(changeThreshold!=null){
                        Map<String, Integer> indicatorCodeDataTypeMap =  deviceIndicatorParamService.getIndicatorCodeDataTypeMapFromCache();
                        Integer dataType =  indicatorCodeDataTypeMap.get(rel_.getIndicatorCode());
                        if(dataType!=null && NUMBER_VARIABLE==dataType){
                            if(Math.abs(Integer.parseInt(prev_indicatorValue)-Integer.parseInt(cur_indicatorValue))<changeThreshold){
                                need_save = false;
                            }
                        }
                    }else{
                        need_save = true;
                    }
                }
            }
        }

        if(need_save){
            DeviceHistoryIndicator deviceHistoryIndicator = new DeviceHistoryIndicator();

            deviceHistoryIndicator.setIndicatorValue(indexInfo.getIndexData());

            deviceHistoryIndicator.setDeviceCode(deviceStatus.getDeviceCode());

            deviceHistoryIndicator.setIndicatorCode(indexInfo.getIndexCode());

            deviceHistoryIndicator.setUpdateTime(updateTime);

            deviceHistoryIndicator.setDeviceId(deviceStatus.getDeviceId());

            deviceHistoryIndicator.setCollectTime(messageTime);

            deviceHistoryIndicator.setReceiveTime(receiveTime);

            this.saveHistoryIndicator(deviceHistoryIndicator.getDeviceCode(),deviceHistoryIndicator);
        }
    }

    @Override
    public int saveHistoryIndicator(String deviceCode, DeviceHistoryIndicator entity) {
       return this.baseMapper.saveHistoryIndicator(deviceCode,entity);
    }

    @Override
    public void deleteByCondition(String deviceCode, Date createTime, String indicatorCode) {
        this.baseMapper.deleteByCondition(deviceCode,createTime,indicatorCode);
    }

    public IPage<DeviceHistoryIndicator> selectPage_old(Page<DeviceHistoryIndicator> page, Map<String,String> conditions) {
        page.setSearchCount(false);
        List<DeviceHistoryIndicator> deviceHistoryIndicators= baseMapper.list(page,conditions);
        page.setRecords(deviceHistoryIndicators);
        page.setTotal(baseMapper.count(conditions));
        return page;
    }


    @Override
    public IPage<DeviceHistoryIndicator> selectPage(Page<DeviceHistoryIndicator> page, Map<String,String> conditions) {

        try (Connection connection = dataSource03.getConnection()) {

            List<DeviceHistoryIndicator> deviceHistoryIndicators = new ArrayList<>();

            String selelctSql = "select * from device_indicator";

            String selectCountSql = "select count(*) from device_indicator";

            String whereSql = " WHERE 1=1";

            for (String key:conditions.keySet()){
                String value  = conditions.get(key);
                if("deviceCode".equals(key) && !StringUtils.isEmpty(value)){
                    whereSql += " AND devicecode='"+value+"'";
                }
                if("indicatorCode".equals(key) && !StringUtils.isEmpty(value)){
                    whereSql += " AND indicatorcode='"+value+"'";
                }
                if("multi_indicatorCode".equals(key) && !StringUtils.isEmpty(value)){
                    whereSql += " AND indicatorcode in ("+value+")";
                }
                if("begin_collectTime".equals(key) && !StringUtils.isEmpty(value)){
                    whereSql += " AND ts >= '"+value+"'";
                }
                if("end_collectTime".equals(key) && !StringUtils.isEmpty(value)){
                    whereSql += " AND ts <= '"+value+"'";
                }
            }

            // 获取 LIMIT 子句的起始位置和大小
            long offset = page.offset();
            long size = page.getSize();

            String limitSql = " limit "+offset+","+size;

            String orderSql = " order by ";

            int orderSize = page.orders().size();

            for (int i = 0; i < orderSize; i++) {
                OrderItem orderItem = page.orders().get(i);
                if(i==orderSize-1){
                    orderSql +=  column_map.get(orderItem.getColumn())+" "+(orderItem.isAsc()?"asc":"desc");
                }else{
                    orderSql +=  column_map.get(orderItem.getColumn())+" "+(orderItem.isAsc()?"asc":"desc"+",");
                }
            }


            // 创建 Statement
            Statement statement = connection.createStatement();

            String sql = selelctSql + whereSql  + orderSql  + limitSql;

            // 执行查询
            ResultSet resultSet = statement.executeQuery(sql);

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

            sql = selectCountSql + whereSql;

            // 执行查询
            resultSet = statement.executeQuery(sql);

            // 获取记录总数
            int count = 0;
            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }

            page.setTotal(count);

            page.setRecords(deviceHistoryIndicators);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return page;
    }

    @Override
    public void  saveHistoryIndicatorForAlarm(String deviceCode, String indicatorCode, Date start, Date end) {
        this.baseMapper.saveHistoryIndicatorForAlarm(deviceCode,indicatorCode,start,end);
    }

    @Override
    public void fillEntityProps(List<DeviceHistoryIndicator> records) {

        Map<String, Map<String,String>>  indicatorCode_value_label_map = commonService.get_indicatorCode_value_label_map();

        Map<String, String> unit_tag_map = indicatorCode_value_label_map.get(UNIT_TAG);

        Map<String, String> deviceCode_deviceName_map  = commonService.get_deviceCode_deviceName_map();

        Map<String, String> indicatorCode_indicatorName_map  = commonService.get_indicatorCode_indicatorName_map();

        for(DeviceHistoryIndicator deviceHistoryIndicator:records) {
            String indicatorCode = deviceHistoryIndicator.getIndicatorCode();
            String indicatorValue = deviceHistoryIndicator.getIndicatorValue();
            if(indicatorCode_value_label_map.get(indicatorCode)!=null && indicatorCode_value_label_map.get(indicatorCode).get(indicatorValue)!=null){
                String new_indicatorValue = indicatorCode_value_label_map.get(indicatorCode).get(indicatorValue);
                String unit = unit_tag_map.get(deviceHistoryIndicator.getIndicatorCode());
                if(!StringUtils.isEmpty(unit)){
                    new_indicatorValue += unit;
                }
                deviceHistoryIndicator.setIndicatorValue(new_indicatorValue);
            }
            deviceHistoryIndicator.setDeviceName(deviceCode_deviceName_map.get(deviceHistoryIndicator.getDeviceCode()));
            deviceHistoryIndicator.setIndicatorName(indicatorCode_indicatorName_map.get(deviceHistoryIndicator.getIndicatorCode()));
        }

    }
}
