package com.figure.core.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.model.base.BaseDataBackup;
import com.figure.core.repository.base.BaseDataBackupMapper;
import com.figure.core.service.base.IBaseDataBackupService;
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
public class BaseDataBackupServiceImpl extends ServiceImpl<BaseDataBackupMapper, BaseDataBackup> implements IBaseDataBackupService {

}
