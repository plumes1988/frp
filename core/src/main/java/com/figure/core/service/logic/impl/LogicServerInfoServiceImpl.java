package com.figure.core.service.logic.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.repository.logic.LogicServerInfoMapper;
import com.figure.core.model.logic.LogicServerInfo;
import com.figure.core.service.logic.ILogicServerInfoService;

/**
 * <p>
 * 逻辑中心服务器Service业务层处理
 * </p>
 *
 * @author feather
 * @date 2022-08-09 16:55:05
 */
@Service
public class LogicServerInfoServiceImpl extends ServiceImpl<LogicServerInfoMapper, LogicServerInfo> implements ILogicServerInfoService {

}