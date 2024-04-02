package com.figure.core.service.logic.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.repository.logic.LogicServerGroupRelMapper;
import com.figure.core.model.logic.LogicServerGroupRel;
import com.figure.core.service.logic.ILogicServerGroupRelService;

/**
 * <p>
 * 逻辑中心服务器与逻辑分析组关联Service业务层处理
 * </p>
 *
 * @author feather
 * @date 2022-08-09 16:55:05
 */
@Service
public class LogicServerGroupRelServiceImpl extends ServiceImpl<LogicServerGroupRelMapper, LogicServerGroupRel> implements ILogicServerGroupRelService {

}