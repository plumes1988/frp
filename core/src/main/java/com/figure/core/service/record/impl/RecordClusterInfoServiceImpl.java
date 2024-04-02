package com.figure.core.service.record.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.constant.Constants;
import com.figure.core.entity.BaseSyncReturn;
import com.figure.core.model.record.RecordClusterInfo;
import com.figure.core.model.record.RecordServiceClusterRel;
import com.figure.core.model.record.RecordServiceInfoList;
import com.figure.core.repository.record.RecordClusterInfoMapper;
import com.figure.core.service.record.IRecordClusterInfoService;
import com.figure.core.service.record.IRecordServiceClusterRelService;
import com.figure.core.service.record.IRecordServiceInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 录制集群管理Service业务层处理
 * </p>
 *
 * @author feather
 * @date 2023-04-10 10:23:52
 */
@Service
public class RecordClusterInfoServiceImpl extends ServiceImpl<RecordClusterInfoMapper, RecordClusterInfo> implements IRecordClusterInfoService {

    @Resource
    IRecordServiceInfoService recordServiceInfoService;

    @Resource
    IRecordServiceClusterRelService recordServiceClusterRelService;

    @Override
    public List<Object> selectClusterWithServiceInfoList() {
        LambdaQueryWrapper<RecordClusterInfo> recordClusterInfoLambdaQueryWrapper = Wrappers.lambdaQuery();
        recordClusterInfoLambdaQueryWrapper.eq(RecordClusterInfo::getIsDelete, Constants.DATATABLE_ISDELETE_NOTDELETED);
        List<RecordClusterInfo> recordClusterInfoList = this.list(recordClusterInfoLambdaQueryWrapper);
        List<Object> result = new ArrayList<>();
        for (RecordClusterInfo recordClusterInfo : recordClusterInfoList) {
            recordClusterInfo.setParentId(0);
            result.add(recordClusterInfo);
            List<RecordServiceInfoList> recordServiceInfoList =
                    this.recordServiceInfoService.selectServiceInfoByClusterId(recordClusterInfo.getRecordClusterId());
            result.addAll(recordServiceInfoList);
        }
//        List<RecordServiceInfo> allRecordServiceInfoList = this.recordServiceInfoService.selectServiceInfoNotWithCluster();
//        RecordClusterInfo recordClusterInfo = new RecordClusterInfo();
//        recordClusterInfo.setParentId(0);
//        recordClusterInfo.setRecordClusterName("未分配的录制服务资源");
        return result;
    }

    @Override
    public Map<String, Object> removeRecordClusterInfoByIds(List<Integer> ids) {
        Map<String, Object> result = new HashMap<>();
        List<BaseSyncReturn> baseSyncReturnList = new ArrayList<>();
        for (Integer id : ids) {
            BaseSyncReturn baseSyncReturn = new BaseSyncReturn();
            RecordClusterInfo recordClusterInfo = this.getById(id);
            LambdaQueryWrapper<RecordServiceClusterRel> recordServiceInfoLambdaQueryWrapper = Wrappers.lambdaQuery();
            recordServiceInfoLambdaQueryWrapper.eq(RecordServiceClusterRel::getRecordClusterId, id).eq(RecordServiceClusterRel::getIsDelete, Constants.DATATABLE_ISDELETE_NOTDELETED);
            List<RecordServiceClusterRel> recordServiceClusterRels = this.recordServiceClusterRelService.getBaseMapper().selectList(recordServiceInfoLambdaQueryWrapper);
            if (recordServiceClusterRels.size() > 0) {
                baseSyncReturn.setResult(0);
                baseSyncReturn.setResultMsg(recordClusterInfo.getRecordClusterName() + "下还有节点，不能删除。");
            } else {
                recordClusterInfo.setIsDelete(Constants.DATATABLE_ISDELETE_DELETED);
                this.updateById(recordClusterInfo);
                baseSyncReturn.setResult(1);
                baseSyncReturn.setResultMsg(recordClusterInfo.getRecordClusterName() + "集群，删除成功。");
            }
            baseSyncReturnList.add(baseSyncReturn);
        }

        result.put("return", baseSyncReturnList);
        return result;
    }
}