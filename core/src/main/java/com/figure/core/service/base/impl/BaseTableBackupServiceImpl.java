package com.figure.core.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.model.base.BaseTableBackup;
import com.figure.core.repository.base.BaseTableBackupMapper;
import com.figure.core.service.base.IBaseTableBackupService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2021-04-21
 */
@Service
public class BaseTableBackupServiceImpl extends ServiceImpl<BaseTableBackupMapper, BaseTableBackup> implements IBaseTableBackupService {

}
