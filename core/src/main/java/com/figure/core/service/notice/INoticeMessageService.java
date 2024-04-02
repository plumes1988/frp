package com.figure.core.service.notice;

import com.baomidou.mybatisplus.extension.service.IService;
import com.figure.core.model.notice.NoticeMessage;
import com.figure.core.sse.SseMessage;

import java.util.List;

/**
 * <p>
 * 通知消息表 服务类
 * </p>
 *
 * @author jobob
 * @since 2023-12-08
 */
public interface INoticeMessageService extends IService<NoticeMessage> {

    void processNoticeMessage(String alarmObjectType, SseMessage message);

    void fillEntityProps(List<NoticeMessage> records);
}
