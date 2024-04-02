package com.figure.core.service.notice.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.model.notice.NoticeAgent;
import com.figure.core.repository.notice.NoticeAgentMapper;
import com.figure.core.service.notice.INoticeAgentService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 通知媒介管理 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2022-08-31
 */
@Service
public class NoticeAgentServiceImpl extends ServiceImpl<NoticeAgentMapper, NoticeAgent> implements INoticeAgentService {

}
