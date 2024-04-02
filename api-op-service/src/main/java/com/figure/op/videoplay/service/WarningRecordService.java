package com.figure.op.videoplay.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.figure.op.cameramanager.domain.Pair;
import com.figure.op.common.domain.PageQuery;
import com.figure.op.videoplay.domain.WarningRecord;
import com.figure.op.videoplay.domain.bo.WarningRecordListSelectQuery;

import java.util.List;

/**
 * @author sdt
 * @version 1.0
 * @description:TODO
 * @date 2023/9/19 16:10
 */
public interface WarningRecordService {
    Page<WarningRecord> list(PageQuery pageQuery, WarningRecordListSelectQuery query);

    boolean update(Integer recordId, Integer status);

    List<Pair<String,Float>> partHisThermometry(String  partCode, String startTime, String endTime);
}
