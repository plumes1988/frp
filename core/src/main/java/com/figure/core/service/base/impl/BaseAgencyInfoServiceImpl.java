package com.figure.core.service.base.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.repository.base.BaseAgencyInfoMapper;
import com.figure.core.model.base.BaseAgencyInfo;
import com.figure.core.service.base.IBaseAgencyInfoService;

/**
 * <p>
 * 机构信息Service业务层处理
 * </p>
 * 
 * @author feather
 * @date 2021-04-20 14:03:32
 */
@Service
public class BaseAgencyInfoServiceImpl extends ServiceImpl<BaseAgencyInfoMapper, BaseAgencyInfo> implements IBaseAgencyInfoService {

}