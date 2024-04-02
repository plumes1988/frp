package com.figure.core.service.sys.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.model.sys.SysApiKey;
import com.figure.core.repository.sys.SysApiKeyMapper;
import com.figure.core.service.sys.ISysApiKeyService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 第三方调用key管理 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2023-07-17
 */
@Service
public class SysApiKeyServiceImpl extends ServiceImpl<SysApiKeyMapper, SysApiKey> implements ISysApiKeyService {

}
