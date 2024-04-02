package com.figure.core.service.notice;

import com.baomidou.mybatisplus.extension.service.IService;
import com.figure.core.model.notice.NoticeAlarmTriggerRule;

/**
 * <p>
 * 报警触发通知规则 服务类
 * </p>
 *
 * @author jobob
 * @since 2022-09-05
 */
public interface INoticeAlarmTriggerRuleService extends IService<NoticeAlarmTriggerRule> {

    void updateRuleCache();

}
