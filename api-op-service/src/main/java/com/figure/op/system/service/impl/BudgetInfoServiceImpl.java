package com.figure.op.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.figure.op.common.domain.PageQuery;
import com.figure.op.common.page.TableDataInfo;
import com.figure.op.common.utils.BeanCopyUtils;
import com.figure.op.common.utils.StringUtils;
import com.figure.op.system.domain.BudgetInfo;
import com.figure.op.system.domain.SysUserInfo;
import com.figure.op.system.domain.bo.BudgetInfoBo;
import com.figure.op.system.domain.bo.BudgetInfoQueryBo;
import com.figure.op.system.domain.bo.BudgetInfoStatusBo;
import com.figure.op.system.domain.bo.PriceQueryBo;
import com.figure.op.system.domain.vo.*;
import com.figure.op.system.helper.LoginHelper;
import com.figure.op.system.mapper.BudgetInfoMapper;
import com.figure.op.system.mapper.SysUserInfoMapper;
import com.figure.op.system.service.IBudgetInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * 品牌服务实现
 * @author fsn
 */
@Service
public class BudgetInfoServiceImpl implements IBudgetInfoService {

    @Resource
    private BudgetInfoMapper budgetInfoMapper;

    @Resource
    private SysUserInfoMapper sysUserInfoMapper;

    /**
     * 用于存储 budgetId 到锁对象的映射关系
     */
    private final ConcurrentHashMap<Integer, Lock> budgetLocks = new ConcurrentHashMap<>();

    /**
     * 全部列表
     */
    @Override
    public List<BudgetInfoListVo> queryList(BudgetInfo BudgetInfo) {
        LambdaQueryWrapper<BudgetInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        List<BudgetInfo> BudgetInfoList = budgetInfoMapper.selectList(lambdaQueryWrapper);
        return BeanCopyUtils.copyList(BudgetInfoList, BudgetInfoListVo.class);
    }

    /**
     * 分页列表
     */
    @Override
    public TableDataInfo<BudgetInfoPageVo> page(BudgetInfoQueryBo queryBo, PageQuery pageQuery) {
        LambdaQueryWrapper<BudgetInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(queryBo.getIsPlan() != null && !"".equals(queryBo.getIsPlan()), BudgetInfo::getIsPlan, queryBo.getIsPlan());
        lambdaQueryWrapper.eq(queryBo.getEntryTime() != null, BudgetInfo::getEntryTime, queryBo.getEntryTime());
        lambdaQueryWrapper.eq(queryBo.getSource() != null && !"".equals(queryBo.getSource()), BudgetInfo::getSource, queryBo.getSource());
        lambdaQueryWrapper.eq(queryBo.getOrgan() != null && !"".equals(queryBo.getOrgan()), BudgetInfo::getOrgan, queryBo.getOrgan());
        lambdaQueryWrapper.eq(queryBo.getCreateUserId() != null, BudgetInfo::getCreateUserId, queryBo.getCreateUserId());
        lambdaQueryWrapper.eq(queryBo.getReviewId() != null, BudgetInfo::getReviewId, queryBo.getReviewId());
        lambdaQueryWrapper.eq(queryBo.getStatus() != null && !"".equals(queryBo.getStatus()), BudgetInfo::getStatus, queryBo.getStatus());
        lambdaQueryWrapper.orderByDesc(BudgetInfo::getBudgetId);
        Page<BudgetInfo> result = budgetInfoMapper.selectPage(pageQuery.build(), lambdaQueryWrapper);

        List<BudgetInfoPageVo> resultList = BeanCopyUtils.copyList(result.getRecords(), BudgetInfoPageVo.class);
        if (resultList != null){
            for (BudgetInfoPageVo budgetInfoPageVo : resultList) {
                // 登记人（创建人）
                SysUserInfo sysUserInfo1 = sysUserInfoMapper.selectById(budgetInfoPageVo.getCreateUserId());
                budgetInfoPageVo.setCreator(sysUserInfo1.getUserName());
                // 审核人
                if (budgetInfoPageVo.getReviewId() != null){
                    SysUserInfo sysUserInfo2 = sysUserInfoMapper.selectById(budgetInfoPageVo.getReviewId());
                    budgetInfoPageVo.setReviewer(sysUserInfo2.getUserName());
                }
            }
        }

        Page<BudgetInfoPageVo> resultPage = new Page<>(result.getCurrent(), result.getSize(), result.getTotal());
        resultPage.setRecords(resultList);
        return TableDataInfo.build(resultPage);
    }

    /**
     * 详情
     */
    @Override
    public BudgetInfoVo queryById(Integer id) {
        BudgetInfo budgetInfo = budgetInfoMapper.selectById(id);
        return BeanCopyUtils.copy(budgetInfo, BudgetInfoVo.class);
    }

    /**
     * 新增
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean insertByBo(BudgetInfoBo bo) {
        BudgetInfo add = BeanUtil.toBean(bo, BudgetInfo.class);
        add.setCreateUserId(LoginHelper.getUserId());
        boolean flag = budgetInfoMapper.insert(add) > 0;
        if (flag) {
            bo.setBudgetId(add.getBudgetId());
        }
        updateOperateTimeline(add.getBudgetId(), LoginHelper.getUserId(), "新增", new Date());
        return flag;
    }

    /**
     * 更新
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateByBo(BudgetInfoBo bo) {
        BudgetInfo update = BeanUtil.toBean(bo, BudgetInfo.class);
        updateOperateTimeline(bo.getBudgetId(), LoginHelper.getUserId(), "编辑", new Date());
        return budgetInfoMapper.updateById(update) > 0;
    }

    /**
     * 更新状态
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateStatus(BudgetInfoStatusBo bo) {

        Date time = new Date();
        BudgetInfo update = new BudgetInfo();
        update.setBudgetId(bo.getBudgetId());
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
        updateOperateTimeline(bo.getBudgetId(), LoginHelper.getUserId(), operate, time);
        return budgetInfoMapper.updateById(update) > 0;
    }

    /**
     * 删除
     */
    @Override
    public Boolean deleteById(Integer id) {
        return budgetInfoMapper.deleteById(id) > 0;
    }

    @Override
    public List<PriceVo> calPrice(PriceQueryBo bo) {
        return budgetInfoMapper.calPrice(bo);
    }

    @Override
    public BigDecimal getTotalByPlanWithoutOrgan(String isPlan, Date start, Date end) {
        return budgetInfoMapper.getTotalByPlanWithoutOrgan(isPlan, start, end);
    }

    @Override
    public BigDecimal getTotalByPlan(String isPlan, String organ, Date start, Date end) {
        return budgetInfoMapper.getTotalByPlan(isPlan, organ, start, end);
    }

    @Override
    public List<PercentVo> getTotalGroupBySource(String isPlan, Date start, Date end) {
        return budgetInfoMapper.getTotalGroupBySourceWithoutOrgan(isPlan, start, end);
    }

    @Override
    public List<PercentVo> getTotalGroupBySource(String isPlan, String organ, Date start, Date end) {
        return budgetInfoMapper.getTotalGroupBySource(isPlan,organ, start, end);
    }

    @Override
    public List<OperateTimelineListVo> getOperateTimelineList(Integer budgetId){
        List<OperateTimelineListVo> operateTimelineList = new ArrayList<>();
        BudgetInfo budgetInfo = budgetInfoMapper.selectById(budgetId);
        if (budgetInfo != null && budgetInfo.getOperateTimeline() != null){
            JSONArray jsonArray = JSONUtil.parseArray(budgetInfo.getOperateTimeline());
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


    private void updateOperateTimeline(Integer budgetId, Integer userId, String operate, Date time){
        // 获取对应 budgetId 的锁对象
        Lock budgetLock = budgetLocks.computeIfAbsent(budgetId, k -> new ReentrantLock());
        // 加锁
        budgetLock.lock();
        try {
            BudgetInfo budgetInfo = budgetInfoMapper.selectById(budgetId);
            JSONArray jsonArray = JSONUtil.parseArray(budgetInfo.getOperateTimeline());

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

            BudgetInfo update = new BudgetInfo();
            update.setBudgetId(budgetId);
            update.setOperateTimeline(updatedJson);
            budgetInfoMapper.updateById(update);
        } finally {
            // 释放锁
            budgetLock.unlock();
        }

    }
}
