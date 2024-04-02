package com.figure.core.service.record;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.figure.core.model.record.RecordTaskInfo;
import com.figure.core.model.record.RecordTaskInfoList;
import com.figure.core.model.record.RecordTimingRule;
import com.figure.core.rocketmq.struct.consumer.RecordTaskControlS2PConsumer;
import com.figure.core.rocketmq.struct.consumer.RecordTaskSetS2PConsumer;
import com.figure.core.rocketmq.struct.consumer.RecordTaskStatusS2PConsumer;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 录制任务 IService
 * </p>
 *
 * @author feather
 * @date 2021-10-19 10:36:18
 */
public interface IRecordTaskInfoService extends IService<RecordTaskInfo> {

    /**
     * 保存发送录制任务
     *
     * @Description:
     * @Param: [recordTaskInfoList, recordTimingRuleList]
     * @return: java.util.Map<java.lang.String, java.lang.Object>
     * @Author: feather
     * @Date: 11/4/2023
     */
    Map<String, Object> saveAndSendRecordTask(List<RecordTaskInfo> recordTaskInfoList, List<RecordTimingRule> recordTimingRuleList);

    /**
     * 删除发送录制任务
     *
     * @Description:
     * @Param: [recordTaskInfoList, recordTimingRuleList]
     * @return: java.util.Map<java.lang.String, java.lang.Object>
     * @Author: feather
     * @Date: 11/4/2023
     */
    Map<String, Object> delAndSendRecordTask(List<Integer> ids);

    /**
     * 根据录制ID查询录制任务
     *
     * @Description:
     * @Param: [recordServiceId]
     * @return: java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     * @Author: feather
     * @Date: 2021/10/26
     */
    List<Map<String, Object>> queryRecordTaskInfoByServiceId(Integer recordServiceId);

    List<RecordTaskInfoList> listByQuery(QueryWrapper<RecordTaskInfo> queryWrapper);

    void sendTaskControl(Integer instruct, String serviceCode, List<RecordTaskInfo> recordTaskInfoList);

    void processTaskControl(RecordTaskControlS2PConsumer consumer);

    void processTaskSet(RecordTaskSetS2PConsumer consumer);

    void processTaskStatus(RecordTaskStatusS2PConsumer consumer);

    Map<String, Object> operateRecordTask(Integer operation, List<Integer> ids);

    Map<String, Object> moveAndSendRecordTask(List<Integer> ids, Integer recordServiceId);
}