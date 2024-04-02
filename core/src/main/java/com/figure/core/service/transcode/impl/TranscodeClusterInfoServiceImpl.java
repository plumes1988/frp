package com.figure.core.service.transcode.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.constant.Constants;
import com.figure.core.model.transcode.TranscodeClusterInfo;
import com.figure.core.model.transcode.TranscodeServiceInfo;
import com.figure.core.repository.transcode.TranscodeClusterInfoMapper;
import com.figure.core.service.transcode.ITranscodeClusterInfoService;
import com.figure.core.service.transcode.ITranscodeServiceInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 转码集群管理Service业务层处理
 * </p>
 *
 * @author feather
 * @date 2021-09-23 15:43:09
 */
@Service
public class TranscodeClusterInfoServiceImpl extends ServiceImpl<TranscodeClusterInfoMapper, TranscodeClusterInfo> implements ITranscodeClusterInfoService {
    @Resource
    ITranscodeServiceInfoService transcodeServiceInfoService;

    @Override
    public List<Object> selectClusterWithServiceInfoList() {
        LambdaQueryWrapper<TranscodeClusterInfo> transcodeClusterInfoLambdaQueryWrapper = Wrappers.lambdaQuery();
        transcodeClusterInfoLambdaQueryWrapper.eq(TranscodeClusterInfo::getIsDelete, Constants.DATATABLE_ISDELETE_NOTDELETED);
        List<TranscodeClusterInfo> transcodeClusterInfoList = this.list(transcodeClusterInfoLambdaQueryWrapper);
        List<Object> result = new ArrayList<>();
        for (TranscodeClusterInfo transcodeClusterInfo : transcodeClusterInfoList) {
            transcodeClusterInfo.setParentId(0);
            result.add(transcodeClusterInfo);
            List<TranscodeServiceInfo> transcodeServiceInfoList =
                    this.transcodeServiceInfoService.selectServiceInfoByClusterId(transcodeClusterInfo.getTranscodeClusterId());
            result.addAll(transcodeServiceInfoList);
        }
        return result;
    }
}