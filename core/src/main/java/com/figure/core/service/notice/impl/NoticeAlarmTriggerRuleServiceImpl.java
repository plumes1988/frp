package com.figure.core.service.notice.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.figure.core.model.device.DeviceInfo;
import com.figure.core.model.notice.*;
import com.figure.core.model.signal.SignalChannelInfo;
import com.figure.core.model.signal.SignalTypeInfo;
import com.figure.core.model.sys.SysUserInfo;
import com.figure.core.redis.IRedisTemplateService;
import com.figure.core.repository.notice.NoticeAlarmTriggerRuleMapper;
import com.figure.core.service.device.IDeviceInfoService;
import com.figure.core.service.notice.INoticeAgentService;
import com.figure.core.service.notice.INoticeAlarmTriggerRuleService;
import com.figure.core.service.notice.INoticeStrategyService;
import com.figure.core.service.notice.INoticeUserGroupService;
import com.figure.core.service.signal.ISignalChannelInfoService;
import com.figure.core.service.signal.ISignalTypeInfoService;
import com.figure.core.service.sys.ISysUserInfoService;
import com.figure.core.util.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.figure.core.redis.RedisConstants.NOTICE_STRUCT;

/**
 * <p>
 * 报警触发通知规则 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2022-09-05
 */
@Service
public class NoticeAlarmTriggerRuleServiceImpl extends ServiceImpl<NoticeAlarmTriggerRuleMapper, NoticeAlarmTriggerRule> implements INoticeAlarmTriggerRuleService {


    @Resource
    private INoticeStrategyService noticeStrategyService;
    @Resource
    private INoticeAgentService noticeAgentService;
    @Resource
    private INoticeUserGroupService noticeUserGroupService;
    @Resource
    private ISysUserInfoService sysUserInfoService;
    @Resource
    private IDeviceInfoService deviceInfoService;
    @Resource
    private ISignalTypeInfoService signalTypeInfoService;
    @Resource
    private ISignalChannelInfoService signalChannelInfoService;

    @Resource
    private IRedisTemplateService redisTemplateService;

    @Override
    @PostConstruct
    public void updateRuleCache() {
        List<NoticeAlarmTriggerRule> noticeAlarmTriggerRuleList = this.list();

        List<NoticeStruct> noticeStructList = new ArrayList<>();
        for (NoticeAlarmTriggerRule noticeAlarmTriggerRule : noticeAlarmTriggerRuleList) {
            String setting = noticeAlarmTriggerRule.getSettings();

            List<NoticeAlarmTriggerRule.Setting> list = JSON.parseArray(setting, NoticeAlarmTriggerRule.Setting.class);

            noticeAlarmTriggerRule.setSettings_(list);

            //报警类型
            NoticeStruct noticeStruct = new NoticeStruct();
            noticeStruct.setSettings(list);
            noticeStruct.setAlarmObjectType(noticeAlarmTriggerRule.getAlarmObjectType());
            //报警对象列表
            switch (Integer.valueOf(noticeAlarmTriggerRule.getAlarmObjectType())) {
                case 1:
                    LambdaQueryWrapper<DeviceInfo> deviceInfoLambdaQueryWrapper = Wrappers.lambdaQuery();
                    List<Integer> deviceIds  = new ArrayList<>();
                    List<NoticeAlarmTriggerRule.Setting> settings = noticeAlarmTriggerRule.getSettings_();
                    Map<String, DeviceInfo> deviceInfoMap = new HashMap<>();
                    if(settings!=null){
                        for (NoticeAlarmTriggerRule.Setting setting_: settings){
                            NoticeAlarmTriggerRule.Param[] params = setting_.getParams();
                            for ( NoticeAlarmTriggerRule.Param param:params) {
                                deviceIds.add(param.getDeviceId());
                            }
                        }
                        deviceInfoLambdaQueryWrapper.in(DeviceInfo::getDeviceId, deviceIds);
                        List<DeviceInfo> deviceInfoList = this.deviceInfoService.list(deviceInfoLambdaQueryWrapper);

                        List<String> deviceCodes =  new ArrayList<>();
                        for (DeviceInfo deviceInfo : deviceInfoList) {
                            deviceInfoMap.put(deviceInfo.getDeviceCode(), deviceInfo);
                            deviceCodes.add(deviceInfo.getDeviceCode());
                        }
                        noticeAlarmTriggerRule.setAlarmObjectIds(String.join(",",deviceCodes));
                    }
                    noticeStruct.setDeviceInfoMap(deviceInfoMap);
                    break;
                case 2:
                    LambdaQueryWrapper<SignalTypeInfo> signalTypeInfoLambdaQueryWrapper = Wrappers.lambdaQuery();
                    signalTypeInfoLambdaQueryWrapper.in(SignalTypeInfo::getSignalCode, StringUtils.StringToStringList(noticeAlarmTriggerRule.getAlarmObjectIds()));
                    List<SignalTypeInfo> signalTypeInfoList = signalTypeInfoService.list(signalTypeInfoLambdaQueryWrapper);
                    Map<Integer, SignalTypeInfo> signalTypeInfoMap = new HashMap<>();
                    for (SignalTypeInfo signalTypeInfo : signalTypeInfoList) {
                        signalTypeInfoMap.put(signalTypeInfo.getSignalCode(), signalTypeInfo);
                    }
                    noticeStruct.setSignalTypeInfoMap(signalTypeInfoMap);
                    break;
                case 3:
                    List<SignalChannelInfo> signalChannelInfoList = signalChannelInfoService.getBaseMapper()
                            .selectBatchIds(StringUtils.StringToStringList(noticeAlarmTriggerRule.getAlarmObjectIds()));
                    Map<String, SignalChannelInfo> signalChannelInfoMap = new HashMap<>();
                    for (SignalChannelInfo signalChannelInfo : signalChannelInfoList) {
                        signalChannelInfoMap.put(signalChannelInfo.getChannelId(), signalChannelInfo);
                    }
                    noticeStruct.setSignalChannelInfoMap(signalChannelInfoMap);
                    break;
                default:
                    break;
            }
            noticeStruct.setDeviceCodeList(StringUtils.StringToStringList(noticeAlarmTriggerRule.getAlarmObjectIds()));
            noticeStruct.setIndicatorCodeList(StringUtils.StringToStringList(noticeAlarmTriggerRule.getAlarmTypes()));

            //策略
            LambdaQueryWrapper<NoticeStrategy> noticeStrategyLambdaQueryWrapper = Wrappers.lambdaQuery();
            noticeStrategyLambdaQueryWrapper.eq(NoticeStrategy::getStrategyId, noticeAlarmTriggerRule.getStrategyId());
            List<NoticeStrategy> noticeStrategyList = noticeStrategyService.list(noticeStrategyLambdaQueryWrapper);

            for (NoticeStrategy noticeStrategy : noticeStrategyList) {
                //发送工具
                LambdaQueryWrapper<NoticeAgent> noticeAgentLambdaQueryWrapper = Wrappers.lambdaQuery();
                String[] agentIds = noticeStrategy.getAgentIds().split(",");
                noticeAgentLambdaQueryWrapper.in(NoticeAgent::getAgentId, agentIds);
                List<NoticeAgent> noticeAgentList = noticeAgentService.list(noticeAgentLambdaQueryWrapper);

                noticeStruct.setNoticeAgentList(noticeAgentList);
                //通知用户组
                LambdaQueryWrapper<NoticeUserGroup> noticeUserGroupLambdaQueryWrapper = Wrappers.lambdaQuery();
                noticeUserGroupLambdaQueryWrapper.in(NoticeUserGroup::getGroupId, noticeStrategy.getGroupIds());
                List<NoticeUserGroup> noticeUserGroupList = noticeUserGroupService.list(noticeUserGroupLambdaQueryWrapper);

                List<SysUserInfo> sysUserInfoList = new ArrayList<>();
                //获取所有用户
                for (NoticeUserGroup noticeUserGroup : noticeUserGroupList) {
                    sysUserInfoList.addAll(sysUserInfoService.getBaseMapper()
                            .selectBatchIds(StringUtils.StringToStringList(noticeUserGroup.getUserIds())));
                }
                noticeStruct.setSysUserInfoList(sysUserInfoList);
            }
            noticeStructList.add(noticeStruct);
        }
        redisTemplateService.setRedisCache(NOTICE_STRUCT, noticeStructList);
    }
}
