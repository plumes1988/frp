package com.figure.core.service.log.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.model.log.LogUserAction;
import com.figure.core.repository.log.LogUserActionMapper;
import com.figure.core.service.log.ILogUserActionService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统用户日志表 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2021-03-24
 */
@Service
public class LogUserActionServiceImpl extends ServiceImpl<LogUserActionMapper, LogUserAction> implements ILogUserActionService {
}


