package com.figure.core.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.model.base.BaseDataBackupTask;
import com.figure.core.repository.base.BaseDataBackupTaskMapper;
import com.figure.core.service.base.IBaseDataBackupTaskService;
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
public class BaseDataBackupTaskServiceImpl extends ServiceImpl<BaseDataBackupTaskMapper, BaseDataBackupTask> implements IBaseDataBackupTaskService {

}
