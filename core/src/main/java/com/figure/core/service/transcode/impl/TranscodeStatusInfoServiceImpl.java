package com.figure.core.service.transcode.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.model.transcode.TranscodeStatusInfo;
import com.figure.core.repository.transcode.TranscodeStatusInfoMapper;
import com.figure.core.service.transcode.ITranscodeStatusInfoService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 转码服务节点实时状态Service业务层处理
 * </p>
 *
 * @author feather
 * @date 2023-04-11 09:45:29
 */
@Service
public class TranscodeStatusInfoServiceImpl extends ServiceImpl<TranscodeStatusInfoMapper, TranscodeStatusInfo> implements ITranscodeStatusInfoService {

}