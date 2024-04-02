package com.figure.op.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.figure.op.common.domain.PageQuery;
import com.figure.op.common.exception.ServiceException;
import com.figure.op.common.page.TableDataInfo;
import com.figure.op.common.utils.BeanCopyUtils;
import com.figure.op.common.utils.StringUtils;
import com.figure.op.system.domain.BudgetInfo;
import com.figure.op.system.domain.OutPriceInfo;
import com.figure.op.system.domain.SysUserInfo;
import com.figure.op.system.domain.bo.*;
import com.figure.op.system.domain.vo.*;
import com.figure.op.system.helper.LoginHelper;
import com.figure.op.system.mapper.OutPriceInfoMapper;
import com.figure.op.system.mapper.SysUserInfoMapper;
import com.figure.op.system.service.IOutPriceInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * 品牌服务实现
 * @author fsn
 */
@Service
public class OutPriceInfoServiceImpl implements IOutPriceInfoService {

    @Resource
    private OutPriceInfoMapper outPriceInfoMapper;

    @Resource
    private SysUserInfoMapper sysUserInfoMapper;

    /**
     * 用于存储 outPriceId 到锁对象的映射关系
     */
    private final ConcurrentHashMap<Integer, Lock> outPriceLocks = new ConcurrentHashMap<>();

    /**
     * 全部列表
     */
    @Override
    public List<OutPriceInfoListVo> queryList(OutPriceInfo outPriceInfo) {
        LambdaQueryWrapper<OutPriceInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        List<OutPriceInfo> outPriceInfoList = outPriceInfoMapper.selectList(lambdaQueryWrapper);
        return BeanCopyUtils.copyList(outPriceInfoList, OutPriceInfoListVo.class);
    }

    /**
     * 分页列表
     */
    @Override
    public TableDataInfo<OutPriceInfoPageVo> page(OutPriceInfoQueryBo queryBo, PageQuery pageQuery) {
        LambdaQueryWrapper<OutPriceInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(queryBo.getOutPriceId() != null, OutPriceInfo::getOutPriceId, queryBo.getOutPriceId());
        lambdaQueryWrapper.eq(queryBo.getUseInfo() != null, OutPriceInfo::getUseInfo, queryBo.getUseInfo());
        lambdaQueryWrapper.eq(queryBo.getSource() != null, OutPriceInfo::getSource, queryBo.getSource());
        lambdaQueryWrapper.eq(queryBo.getApplierId() != null, OutPriceInfo::getCreateUserId, queryBo.getApplierId());
        lambdaQueryWrapper.eq(queryBo.getStatus() != null && !"".equals(queryBo.getStatus()), OutPriceInfo::getStatus, queryBo.getStatus());
        lambdaQueryWrapper.orderByDesc(OutPriceInfo::getOutPriceId);
        Page<OutPriceInfo> result = outPriceInfoMapper.selectPage(pageQuery.build(), lambdaQueryWrapper);

        List<OutPriceInfoPageVo> resultList = BeanCopyUtils.copyList(result.getRecords(), OutPriceInfoPageVo.class);

        if (resultList != null){
            for (OutPriceInfoPageVo outPriceInfoPageVo : resultList) {
                // 申请人（创建人）
                SysUserInfo sysUserInfo1 = sysUserInfoMapper.selectById(outPriceInfoPageVo.getCreateUserId());
                outPriceInfoPageVo.setApplier(sysUserInfo1.getUserName());
                // 审核人
                if (outPriceInfoPageVo.getReviewId() != null){
                    SysUserInfo sysUserInfo2 = sysUserInfoMapper.selectById(outPriceInfoPageVo.getReviewId());
                    outPriceInfoPageVo.setReviewer(sysUserInfo2.getUserName());
                }
            }
        }

        Page<OutPriceInfoPageVo> resultPage = new Page<>(result.getCurrent(), result.getSize(), result.getTotal());
        resultPage.setRecords(resultList);
        return TableDataInfo.build(resultPage);
    }

    /**
     * 详情
     */
    @Override
    public OutPriceInfoVo queryById(Integer id) {
        OutPriceInfo outPriceInfo = outPriceInfoMapper.selectById(id);
        return BeanCopyUtils.copy(outPriceInfo, OutPriceInfoVo.class);
    }

    /**
     * 新增
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean insertByBo(OutPriceInfoBo bo) {
        OutPriceInfo add = BeanUtil.toBean(bo, OutPriceInfo.class);
        add.setCreateUserId(LoginHelper.getUserId());
        boolean flag = outPriceInfoMapper.insert(add) > 0;
        if (flag) {
            bo.setOutPriceId(add.getOutPriceId());
        }
        updateOperateTimeline(add.getOutPriceId(), LoginHelper.getUserId(), "新增", new Date());
        return flag;
    }

    /**
     * 更新
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateByBo(OutPriceInfoBo bo) {
        Date time = new Date();
        OutPriceInfo update = BeanUtil.toBean(bo, OutPriceInfo.class);
        if (update.getPurchase() != null && !"".equals(update.getPurchase())){
            if ("已付款".equals(update.getPurchase())){
                update.setPaymentTime(time);
                updateOperateTimeline(bo.getOutPriceId(), LoginHelper.getUserId(), "付款状态-已付款", time);
            }else if ("未付款".equals(update.getPurchase())){
                // 构建更新构造器 仅对部分字段进行更新
                LambdaUpdateWrapper<OutPriceInfo> updateWrapper = new LambdaUpdateWrapper<>();
                updateWrapper.set(OutPriceInfo::getPaymentTime, null);
                updateWrapper.eq(OutPriceInfo::getOutPriceId, bo.getOutPriceId());
                outPriceInfoMapper.update(null, updateWrapper);
                updateOperateTimeline(bo.getOutPriceId(), LoginHelper.getUserId(), "付款状态-未付款", time);
            }else {
                throw new ServiceException("付款状态异常");
            }
        }else {
            updateOperateTimeline(bo.getOutPriceId(), LoginHelper.getUserId(), "编辑", time);
        }
        return outPriceInfoMapper.updateById(update) > 0;
    }

    /**
     * 更新状态
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateStatus(OutPriceInfoStatusBo bo) {

        Date time = new Date();
        OutPriceInfo update = new OutPriceInfo();
        update.setOutPriceId(bo.getOutPriceId());
        if (!StringUtils.isEmpty(bo.getStatus())) {
            update.setStatus(bo.getStatus());
            if ("1".equals(bo.getStatus())){
                update.setSubmitTime(time);
            }
            if ("2".equals(bo.getStatus()) || "3".equals(bo.getStatus())) {
                update.setReviewId(LoginHelper.getUserId());
                update.setReason(bo.getReason());
                update.setReviewTime(new Date());
            }
        }
        String operate;
        switch (bo.getStatus()){
            case "1":
                operate = "提交";
                break;
            case "2":
                operate = "审核通过";
                break;
            case "3":
                operate = "审核驳回";
                break;
            default:
                operate = "";
        }
        updateOperateTimeline(bo.getOutPriceId(), LoginHelper.getUserId(), operate, time);
        return outPriceInfoMapper.updateById(update) > 0;
    }

    /**
     * 删除
     */
    @Override
    public Boolean deleteById(Integer id) {
        return outPriceInfoMapper.deleteById(id) > 0;
    }

    @Override
    public List<PriceVo> calPrice(PriceQueryBo bo) {
        return outPriceInfoMapper.calPrice(bo);
    }

    @Override
    public BigDecimal getTotalByPlanWithoutOrgan(Date start, Date end) {
        return outPriceInfoMapper.getTotalByPlanWithoutOrgan(start, end);
    }


    @Override
    public BigDecimal getTotalByPlan(String organ, Date start, Date end) {
        return outPriceInfoMapper.getTotalByPlan(organ, start, end);
    }

    @Override
    public List<Map<String, Object>> getSumGroupByUseInfo(Date start, Date end) {
        return outPriceInfoMapper.getSumGroupByUse(start, end);
    }

    @Override
    public List<Map<String, Object>> getSumGroupBySource(Date start, Date end) {
        return outPriceInfoMapper.getSumGroupBySourceWithoutOrgan();
    }

    @Override
    public List<Map<String, Object>> getSumGroupBySource(String organ, Date start, Date end) {
        return outPriceInfoMapper.getSumGroupBySource(organ, start, end);
    }

    @Override
    public List<OperateTimelineListVo> getOperateTimelineList(Integer budgetId){
        List<OperateTimelineListVo> operateTimelineList = new ArrayList<>();
        OutPriceInfo outPriceInfo = outPriceInfoMapper.selectById(budgetId);
        if (outPriceInfo != null && outPriceInfo.getOperateTimeline() != null){
            JSONArray jsonArray = JSONUtil.parseArray(outPriceInfo.getOperateTimeline());
            // 遍历 JSON 数组并将每个 JSON 对象转换为 OperateTimelineListVo 对象
            for (Object obj : jsonArray) {
                JSONObject jsonObj = (JSONObject) obj;
                SysUserInfo sysUserInfo = sysUserInfoMapper.selectById(jsonObj.getInt("userId"));
                // 创建 OperateTimelineListVo 对象并添加到列表中
                OperateTimelineListVo operateTimeline = new OperateTimelineListVo();
                operateTimeline.setUserId(jsonObj.getInt("userId"));
                operateTimeline.setUserName(sysUserInfo.getUserName());
                operateTimeline.setOperate(jsonObj.getStr("operate"));
                operateTimeline.setTime(DateUtil.parse(jsonObj.getStr("time")));
                operateTimelineList.add(operateTimeline);
            }

            return operateTimelineList.stream()
                    .sorted((o1, o2) -> o2.getTime().compareTo(o1.getTime()))
                    .collect(Collectors.toList());
        }
        return operateTimelineList;
    }

    private void updateOperateTimeline(Integer outPriceId, Integer userId, String operate, Date time){
        // 获取对应 budgetId 的锁对象
        Lock budgetLock = outPriceLocks.computeIfAbsent(outPriceId, k -> new ReentrantLock());
        // 加锁
        budgetLock.lock();
        try {
            OutPriceInfo outPriceInfo = outPriceInfoMapper.selectById(outPriceId);
            JSONArray jsonArray = JSONUtil.parseArray(outPriceInfo.getOperateTimeline());

            // 使用 SimpleDateFormat 格式化日期
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedDate = sdf.format(time);


            // 创建新的 JSON 对象
            JSONObject newObject = new JSONObject();
            newObject.put("userId", userId);
            newObject.put("operate", operate);
            newObject.put("time", formattedDate);

            // 将新的 JSON 对象添加到 JSON 数组中
            jsonArray.add(newObject);

            // 将更新后的 JSON 数组转换为 JSON 字符串
            String updatedJson = JSONUtil.toJsonStr(jsonArray);

            OutPriceInfo update = new OutPriceInfo();
            update.setOutPriceId(outPriceId);
            update.setOperateTimeline(updatedJson);
            outPriceInfoMapper.updateById(update);
        } finally {
            // 释放锁
            budgetLock.unlock();
        }

    }

}
