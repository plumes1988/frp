package com.figure.core.service.others.impl;

import com.figure.core.db.JavaMemoryDb;
import com.figure.core.helper.DateHelper;
import com.figure.core.model.device.DeviceHistoryIndicator;
import com.figure.core.model.device.DeviceIndicatorParamRel;
import com.figure.core.model.log.LogDeviceStateIndicatorChange;
import com.figure.core.rocketmq.message.DeviceItemInfo;
import com.figure.core.rocketmq.message.DeviceStatus;
import com.figure.core.service.device.IDeviceIndicatorParamRelService;
import com.figure.core.service.device.IDeviceIndicatorParamService;
import com.figure.core.service.device.IDeviceInfoService;
import com.figure.core.service.others.IDenseDataService;
import com.figure.core.service.sys.ISysParaService;
import com.figure.core.sse.SseEmitterManager;
import com.figure.core.util.BusinessUtils;
import com.figure.core.util.DateUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.figure.core.constant.Constants.DATATABLE_ISDELETE_NOTDELETED;
import static com.figure.core.constant.ConstantsForSysPara.UNCHANGED_INDICATOR_HISTORICAL_COLLECTION_INTERVAL;
import static com.figure.core.db.JavaMemoryDb.DEVICE_HISTORY_INDICATOR_CHANGE_RECORD_MODE_LAST_COLLECT_TIME;
import static com.figure.core.model.device.DeviceIndicatorParam.NUMBER_VARIABLE;
import static com.figure.core.model.device.DeviceIndicatorParam.STATUS_VARIABLE;
import static com.figure.core.sse.Constants.LOG_DEVICE_STATE_INDICATOR_CHANGE;

@Service
public class DenseDataServiceImpl implements IDenseDataService {


    @Autowired
    @Qualifier("dataSource02")
    private DataSource dataSource;

    @Autowired
    @Qualifier("dataSource03")
    private DataSource dataSource03;

    @Resource
    private IDeviceIndicatorParamRelService deviceIndicatorParamRelService;

    @Resource
    private IDeviceIndicatorParamService deviceIndicatorParamService;

    @Resource
    private IDeviceInfoService deviceInfoService;

    @Resource
    ISysParaService sysParaService;

    @Override
    public void saveHistoryIndicator(DeviceStatus deviceStatus, DeviceItemInfo indexInfo, String messageTime, Integer alarmStatus) {

        String cur_indicatorValue = indexInfo.getIndexData();

        Integer recordMode =  deviceIndicatorParamRelService.getRecordMode(deviceStatus.getDeviceId(),indexInfo.getIndexCode());

        boolean need_save = false;

        DeviceIndicatorParamRel rel_ = new DeviceIndicatorParamRel();
        rel_.setDeviceCode(deviceStatus.getDeviceCode());
        rel_.setIndicatorCode(indexInfo.getIndexCode());

        String key = BusinessUtils.getLiveDeviceIndicatorKey(rel_);

        Date messageTime_ = DateHelper.strToDate(messageTime);

        //历史数据记录 0：不记录 1：实时记录 2：记录变更
        if(recordMode!=null&&recordMode!=0){
            if(recordMode==1){
                need_save = true;
            }
            if(recordMode==2){
                String prev_indicatorValue = JavaMemoryDb.get_from_DEVICE_CUR_INDICATOR_VALUE_FOR_HISTORY(key);
                boolean hasValue = JavaMemoryDb.DEVICE_CUR_INDICATOR_VALUE_FOR_HISTORY.containsKey(key);
                Integer changeThreshold =  deviceIndicatorParamRelService.getChangeThreshold(deviceStatus.getDeviceId(),indexInfo.getIndexCode());
                if(!hasValue || !BusinessUtils.isSameValue(prev_indicatorValue,cur_indicatorValue)){
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
                if(!need_save){
                    need_save =  checkUnchangedIndicatorHistoricalCollectionInterval(key,messageTime_);
                }
                if(need_save){
                    DEVICE_HISTORY_INDICATOR_CHANGE_RECORD_MODE_LAST_COLLECT_TIME.put(key,messageTime_.getTime());
                }
            }
        }

        if(need_save){
            try (Connection connection = dataSource03.getConnection()) {
                try (Statement stmt = connection.createStatement()) {
                    StringBuilder sb = new StringBuilder("INSERT INTO ");
                    sb.append(key).append(" USING device_indicator TAGS(")
                            .append("\'").append(deviceStatus.getDeviceCode()).append("\'").append(", ") // tag: deviceCode
                            .append("\'").append(indexInfo.getIndexCode()).append("\'") // tag: indicatorCode
                            .append(") VALUES(")
                            .append('\'').append(messageTime).append('\'').append(",") // ts
                            .append('\'').append(cur_indicatorValue).append('\'').append(",") //indicatorValue
                            .append(alarmStatus).append(")"); //alarmStatus
                    String sql = sb.toString();
                    int rowCount = stmt.executeUpdate(sql);
                    JavaMemoryDb.put_into_DEVICE_CUR_INDICATOR_VALUE_FOR_HISTORY(key,cur_indicatorValue);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean checkUnchangedIndicatorHistoricalCollectionInterval(String key, Date messageTime_) {
        String unchangedIndicatorHistoricalCollectionInterval_str = sysParaService.getParamByName(UNCHANGED_INDICATOR_HISTORICAL_COLLECTION_INTERVAL);
        if(unchangedIndicatorHistoricalCollectionInterval_str!=null){
            Integer unchangedIndicatorHistoricalCollectionInterval = Integer.parseInt(unchangedIndicatorHistoricalCollectionInterval_str);
            Long  last_unchange_collect_time = DEVICE_HISTORY_INDICATOR_CHANGE_RECORD_MODE_LAST_COLLECT_TIME.get(key);
            if(last_unchange_collect_time==null){
                return true;
            }
            if(messageTime_.getTime()-last_unchange_collect_time>=unchangedIndicatorHistoricalCollectionInterval*1000){
                return true;
            }
        }
        return false;
    }

    @Override
    public void saveDeviceStateIndicatorChange(DeviceIndicatorParamRel rel) {
        // 使用 QueryWrapper

        Map<String,Integer> indicatorCodeDataTypeMap = deviceIndicatorParamService.getIndicatorCodeDataTypeMapFromCache();

        Map<String,Integer> deviceCodeFrontIdMap = deviceInfoService.getDeviceCodeFrontIdMapFromCache();

        String indicatorCode =  rel.getIndicatorCode();

        if(STATUS_VARIABLE == indicatorCodeDataTypeMap.get(indicatorCode)){
            String key = rel.getDeviceCode()+"_"+rel.getIndicatorCode();
            String old_value = JavaMemoryDb.get_from_DEVICE_LAST_CHANGE_INDICATOR_VALUE(key);
            String curValue = rel.getIndicatorValue();
            if(JavaMemoryDb.DEVICE_LAST_CHANGE_INDICATOR_VALUE.containsKey(key) && !BusinessUtils.isSameValue(old_value,curValue)){
                LogDeviceStateIndicatorChange logDeviceStateIndicatorChange = new LogDeviceStateIndicatorChange();
                logDeviceStateIndicatorChange.setFrontId(deviceCodeFrontIdMap.get(rel.getDeviceCode()));
                logDeviceStateIndicatorChange.setDeviceCode(rel.getDeviceCode());
                logDeviceStateIndicatorChange.setIndicatorCode(rel.getIndicatorCode());
                logDeviceStateIndicatorChange.setOldIndicatorValue(old_value);
                logDeviceStateIndicatorChange.setNewIndicatorValue(curValue);
                logDeviceStateIndicatorChange.setChangeTime(DateUtils.parseDate(rel.getCollectTime()));
                logDeviceStateIndicatorChange.setIsDelete(DATATABLE_ISDELETE_NOTDELETED);
                logDeviceStateIndicatorChange.setDeviceId(rel.getDeviceId());
                logDeviceStateIndicatorChange.setTopic(LOG_DEVICE_STATE_INDICATOR_CHANGE);
                try (Connection connection = dataSource.getConnection()) {
                    // 创建 QueryRunner 对象
                    QueryRunner queryRunner = new QueryRunner();
                    // 执行更新操作
                    int rowsUpdated = queryRunner.update(connection,
                            "INSERT INTO log_device_state_indicator_change (`frontId`, `deviceCode`, `indicatorCode`, `oldIndicatorValue`, `newIndicatorValue`, `changeTime`) VALUES (?, ?, ?, ?, ?, ?)",
                            logDeviceStateIndicatorChange.getFrontId(),logDeviceStateIndicatorChange.getDeviceCode(),logDeviceStateIndicatorChange.getIndicatorCode(),
                            logDeviceStateIndicatorChange.getOldIndicatorValue(),logDeviceStateIndicatorChange.getNewIndicatorValue(),rel.getCollectTime());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                SseEmitterManager.sendMessageToAllMatchSseEmitter(logDeviceStateIndicatorChange);
                // todo 暂时注释
                // noticeMessageService.processNoticeMessage(DEVICE_STATUS_CHANGE, logDeviceStateIndicatorChange);
            }
            JavaMemoryDb.put_into_DEVICE_LAST_CHANGE_INDICATOR_VALUE(key,curValue);
        }
    }

    public static void main(String[] args) {
         Map<String,String> DEVICE_CODE_VALUE_MAP =  new HashMap<>();
         DEVICE_CODE_VALUE_MAP.put("test",null);
         System.out.println(DEVICE_CODE_VALUE_MAP.get("test"));
    }

}
