package com.figure.core.service.notice.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.model.notice.NoticeStrategy;
import com.figure.core.repository.notice.NoticeStrategyMapper;
import com.figure.core.service.notice.INoticeStrategyService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 通知策略管理 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2022-08-31
 */
@Service
public class NoticeStrategyServiceImpl extends ServiceImpl<NoticeStrategyMapper, NoticeStrategy> implements INoticeStrategyService {

}
