package com.figure.core.repository.device;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.figure.core.model.device.DeviceHistoryIndicator;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2022-06-30
 */

public interface DeviceHistoryIndicatorMapper extends BaseMapper<DeviceHistoryIndicator> {

    @Insert("INSERT INTO `device_history_indicator_${deviceCode}` (`deviceId`, `deviceCode`, `indicatorCode`, `indicatorValue`, `updateTime`, `collectTime`, `createTime`, `receiveTime`) VALUES (#{entity.deviceId}, #{entity.deviceCode}, #{entity.indicatorCode}, #{entity.indicatorValue}, #{entity.updateTime}, #{entity.collectTime}, #{entity.createTime}, #{entity.receiveTime})")
    int saveHistoryIndicator(@Param("deviceCode") String deviceCode,
                               @Param("entity") DeviceHistoryIndicator entity);

    @Delete("DELETE FROM `device_history_indicator_${deviceCode}` WHERE createTime < #{createTime} AND indicatorCode = #{indicatorCode}")
    void deleteByCondition(@Param("deviceCode") String deviceCode,@Param("createTime") Date createTime,@Param("indicatorCode") String indicatorCode);

    List<DeviceHistoryIndicator> list(Page<?> page, @Param("conditions") Map<String,String> conditions);

    Integer count(@Param("conditions") Map<String,String> conditions);

    @Insert("INSERT INTO device_history_indicator (`deviceId`, `deviceCode`, `indicatorCode`, `indicatorValue`, `updateTime`, `collectTime`, `createTime`, `receiveTime`) SELECT `deviceId`, `deviceCode`, `indicatorCode`, `indicatorValue`, `updateTime`, `collectTime`, `createTime`, `receiveTime` FROM  `device_history_indicator_${deviceCode}` WHERE  indicatorCode = #{indicatorCode} AND createTime > #{start} and createTime < #{end}")
    void saveHistoryIndicatorForAlarm(@Param("deviceCode") String deviceCode,@Param("indicatorCode") String indicatorCode,@Param("start") Date start,@Param("end") Date end);

}
