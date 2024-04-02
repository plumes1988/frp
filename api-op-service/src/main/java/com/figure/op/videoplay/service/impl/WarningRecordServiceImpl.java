package com.figure.op.videoplay.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.figure.op.cameramanager.domain.Pair;
import com.figure.op.common.domain.PageQuery;
import com.figure.op.videoplay.domain.RealMonitor;
import com.figure.op.videoplay.domain.WarningRecord;
import com.figure.op.videoplay.domain.bo.WarningRecordListSelectQuery;
import com.figure.op.videoplay.mapper.RealMonitorMapper;
import com.figure.op.videoplay.mapper.WarningRecordMapper;
import com.figure.op.videoplay.service.WarningRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author sdt
 * @version 1.0
 * @description:TODO
 * @date 2023/9/19 16:10
 */
@Service
public class WarningRecordServiceImpl implements WarningRecordService {

    @Resource
    private WarningRecordMapper warningRecordMapper;

    @Resource
    private RealMonitorMapper realMonitorMapper;
    @Override
    public  Page<WarningRecord> list(PageQuery pageQuery,WarningRecordListSelectQuery query) {
        QueryWrapper<WarningRecord> warningRecordQueryWrapper = new QueryWrapper<>();

        if (query.getDeviceName()!=null){
            if("_".equals(query.getDeviceName())||"%".equals(query.getDeviceName()))
            {
                warningRecordQueryWrapper.like("devicename","\\"+query.getDeviceName());
            }else {
                warningRecordQueryWrapper.like("devicename",query.getDeviceName());
            }
        }
        if (query.getPartName()!=null){
            if("_".equals(query.getPartName())||"%".equals(query.getPartName()))
            {
                warningRecordQueryWrapper.like("partName","\\"+query.getPartName());
            }else {
                warningRecordQueryWrapper.like("partName",query.getPartName());
            }
        }
        if (query.getCameraName()!=null){
            if("_".equals(query.getCameraName())||"%".equals(query.getCameraName()))
            {
                warningRecordQueryWrapper.like("cameraName","\\"+query.getCameraName());
            }else {
                warningRecordQueryWrapper.like("cameraName",query.getCameraName());
            }
        }
        if(query.getStartTime()!=null){
            warningRecordQueryWrapper.ge("startTime",query.getStartTime());
        }
        if(query.getEndTime()!=null){
            warningRecordQueryWrapper.le("endTime",query.getEndTime());
        }
        warningRecordQueryWrapper.orderByDesc("startTime");
        Page<WarningRecord> list = warningRecordMapper.selectPage(pageQuery.build(), warningRecordQueryWrapper);
        return list;
    }

    @Override
    public boolean update(Integer recordId, Integer status) {
        WarningRecord warningRecord = new WarningRecord();
        warningRecord.setRecordId(recordId);
        warningRecord.setStatus(status);
        warningRecordMapper.updateById(warningRecord);
        return true;
    }

    @Override
    public List<Pair<String, Float>> partHisThermometry(String partCode, String startTime, String endTime) {

        QueryWrapper<RealMonitor> realMonitorQueryWrapper = new QueryWrapper<>();
        realMonitorQueryWrapper.eq("partCode",partCode).between("insertTime",startTime,endTime).orderByAsc();
        List<RealMonitor> realMonitors = realMonitorMapper.selectList(realMonitorQueryWrapper);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ArrayList<Pair<String, Float>> resultList = new ArrayList<>();
        for (RealMonitor realMonitor : realMonitors) {
            Pair<String, Float> pair = new Pair<>(sdf.format(realMonitor.getInsertTime()),realMonitor.getTemperature());
            resultList.add(pair);
        }

        return resultList;
    }
}
