package com.figure.core.service.base.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.repository.base.BaseImportantSessionMapper;
import com.figure.core.model.base.BaseImportantSession;
import com.figure.core.service.base.IBaseImportantSessionService;

/**
 * <p>
 * 重点时段Service业务层处理
 * </p>
 * 
 * @author feather
 * @date 2021-04-20 14:03:33
 */
@Service
public class BaseImportantSessionServiceImpl extends ServiceImpl<BaseImportantSessionMapper, BaseImportantSession> implements IBaseImportantSessionService {

}