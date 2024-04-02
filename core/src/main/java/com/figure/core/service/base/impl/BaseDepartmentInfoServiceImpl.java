package com.figure.core.service.base.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.repository.base.BaseDepartmentInfoMapper;
import com.figure.core.model.base.BaseDepartmentInfo;
import com.figure.core.service.base.IBaseDepartmentInfoService;

/**
 * <p>
 * 单位信息Service业务层处理
 * </p>
 * 
 * @author feather
 * @date 2021-04-20 14:03:33
 */
@Service
public class BaseDepartmentInfoServiceImpl extends ServiceImpl<BaseDepartmentInfoMapper, BaseDepartmentInfo> implements IBaseDepartmentInfoService {

}