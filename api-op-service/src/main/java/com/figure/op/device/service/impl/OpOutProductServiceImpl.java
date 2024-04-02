package com.figure.op.device.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.figure.op.common.domain.PageQuery;
import com.figure.op.common.exception.ServiceException;
import com.figure.op.common.page.TableDataInfo;
import com.figure.op.common.utils.BeanCopyUtils;
import com.figure.op.device.domain.OpOutProduct;
import com.figure.op.device.domain.OpProductInfo;
import com.figure.op.device.domain.bo.OpOutProductBo;
import com.figure.op.device.domain.bo.OpOutProductQueryBo;
import com.figure.op.device.domain.bo.OpOutProductReturnBo;
import com.figure.op.device.domain.bo.OpProductInfoBo;
import com.figure.op.device.domain.vo.*;
import com.figure.op.device.mapper.OpOutProductMapper;
import com.figure.op.device.mapper.OpProductInfoMapper;
import com.figure.op.device.service.IOpOutProductService;
import com.figure.op.device.service.IOpProductInfoService;
import com.figure.op.system.domain.SysUserInfo;
import com.figure.op.system.helper.LoginHelper;
import com.figure.op.system.mapper.SysUserInfoMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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
public class OpOutProductServiceImpl implements IOpOutProductService {

    @Resource
    private OpOutProductMapper opOutProductMapper;

    @Resource
    private IOpProductInfoService opProductInfoService;

    @Resource
    private SysUserInfoMapper sysUserInfoMapper;

    @Resource
    private OpProductInfoMapper opProductInfoMapper;

    /**
     * 用于存储 outId 到锁对象的映射关系
     */
    private final ConcurrentHashMap<Integer, Lock> outLocks = new ConcurrentHashMap<>();

    /**
     * 分页列表
     */
    @Override
    public TableDataInfo<OpOutProductPageVo> page(OpOutProductQueryBo queryBo, PageQuery pageQuery) {
        Page<OpOutProductPageVo> result = opOutProductMapper.selectVoPage(pageQuery.build(), queryBo);
        return TableDataInfo.build(result);
    }


    /**
     * 分页列表2
     */
    @Override
    public TableDataInfo<OpOutProductPageVo> queryPageList2(OpOutProductQueryBo queryBo, PageQuery pageQuery) {
        Page<OpOutProductPageVo> result = opOutProductMapper.selectVoPage(pageQuery.build(), queryBo);
        return TableDataInfo.build(result);
    }

    /**
     * 全部列表
     */
    @Override
    public List<OpOutProductListVo> queryList(OpOutProduct opOutProduct) {
        LambdaQueryWrapper<OpOutProduct> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        List<OpOutProduct> opOutProductList = opOutProductMapper.selectList(lambdaQueryWrapper);
        return BeanCopyUtils.copyList(opOutProductList, OpOutProductListVo.class);
    }

    /**
     * 详情
     */
    @Override
    public OpOutProductVo queryById(Integer id) {
        OpOutProduct opOutProduct = opOutProductMapper.selectById(id);
        return BeanCopyUtils.copy(opOutProduct, OpOutProductVo.class);
    }

    /**
     * 新增出库
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean insertByBo(OpOutProductBo bo) {

        // 查询库存管理（悲观锁）
        OpProductInfo opProductInfo = opProductInfoMapper.selectByIdLock(bo.getOpProductId());
        Integer stock = opProductInfo.getStock();
        if (stock >= bo.getUseAmount()) {
            Integer newStock = stock - bo.getUseAmount();
            OpProductInfo productInfoUpdate = new OpProductInfo();
            productInfoUpdate.setOpProductId(opProductInfo.getOpProductId());
            productInfoUpdate.setStock(newStock);
            // 库存管理-减少库存
            opProductInfoMapper.updateById(productInfoUpdate);
        } else {
            throw new ServiceException("领用数量必须小于等于剩余库存！");
        }

        OpOutProduct add = BeanUtil.toBean(bo, OpOutProduct.class);
        boolean flag = opOutProductMapper.insert(add) > 0;
        if (flag) {
            bo.setOutId(add.getOutId());
        }
        return flag;
    }

    /**
     * 更新
     */
    @Override
    public Boolean updateByBo(OpOutProductBo bo) {
        OpOutProduct update = BeanUtil.toBean(bo, OpOutProduct.class);
        return opOutProductMapper.updateById(update) > 0;
    }

    /**
     * 删除
     */
    @Override
    public Boolean deleteById(Integer id) {
        return opOutProductMapper.deleteById(id) > 0;
    }

    /**
     * 归还登记
     * @param returnBo 归还对象
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean returnProduct(OpOutProductReturnBo returnBo) {
        Date now = new Date();
        OpOutProductVo opOutProductVo = queryById(returnBo.getOutId());
        if (opOutProductVo == null){
            throw new ServiceException("当前记录不存在");
        }
        if ("1".equals(opOutProductVo.getStatus())){
            throw new ServiceException("当前记录已归还");
        }
        // 查询库存管理（悲观锁）
        OpProductInfo opProductInfo = opProductInfoMapper.selectByIdLock(opOutProductVo.getOpProductId());
        Integer stock = opProductInfo.getStock();

        OpOutProductBo opOutProductBo = new OpOutProductBo();
        opOutProductBo.setOutId(returnBo.getOutId());
        opOutProductBo.setReturnNum(opOutProductVo.getReturnNum() + returnBo.getReturnNum());

        // 剩余需归还数量
        Integer unReturnNum = opOutProductVo.getUseAmount() - opOutProductVo.getReturnNum();
        if (returnBo.getReturnNum() > unReturnNum){
            throw new ServiceException("归还数量不得大于剩余归还数量");
        }
        // 归还完成
        if (returnBo.getReturnNum().equals(unReturnNum)){
            opOutProductBo.setStatus("1");
            opOutProductBo.setReturnTime(now);
        // 未完成
        }else if (returnBo.getReturnNum() < unReturnNum){
            opOutProductBo.setStatus("0");
        }
        updateByBo(opOutProductBo);

        // 库存管理-增加库存
        Integer newStock = stock + returnBo.getReturnNum();
        OpProductInfo productInfoUpdate = new OpProductInfo();
        productInfoUpdate.setOpProductId(opProductInfo.getOpProductId());
        productInfoUpdate.setStock(newStock);
        opProductInfoMapper.updateById(productInfoUpdate);

        // 更新归还历史
        updateReturnTimeline(returnBo.getOutId(), LoginHelper.getUserId(), returnBo.getReturnNum(), now);
        return true;
    }

    public void updateReturnTimeline(Integer outId, Integer userId, Integer num, Date time){
        // 获取对应 outId 的锁对象
        Lock outLock = outLocks.computeIfAbsent(outId, k -> new ReentrantLock());
        // 加锁
        outLock.lock();
        try {
            OpOutProduct opOutProduct = opOutProductMapper.selectById(outId);
            JSONArray jsonArray = JSONUtil.parseArray(opOutProduct.getReturnTimeline());

            // 使用 SimpleDateFormat 格式化日期
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedDate = sdf.format(time);


            // 创建新的 JSON 对象
            JSONObject newObject = new JSONObject();
            newObject.put("userId", userId);
            newObject.put("num", num);
            newObject.put("time", formattedDate);

            // 将新的 JSON 对象添加到 JSON 数组中
            jsonArray.add(newObject);

            // 将更新后的 JSON 数组转换为 JSON 字符串
            String updatedJson = JSONUtil.toJsonStr(jsonArray);

            OpOutProduct update = new OpOutProduct();
            update.setOutId(outId);
            update.setReturnTimeline(updatedJson);
            opOutProductMapper.updateById(update);
        } finally {
            // 释放锁
            outLock.unlock();
        }

    }

    @Override
    public List<OpOutProductReturnListVo> getReturnTimelineList(Integer outId){
        List<OpOutProductReturnListVo> returnListVoList = new ArrayList<>();
        OpOutProduct opOutProduct = opOutProductMapper.selectById(outId);
        if (opOutProduct != null && opOutProduct.getReturnTimeline() != null){
            JSONArray jsonArray = JSONUtil.parseArray(opOutProduct.getReturnTimeline());
            // 遍历 JSON 数组并将每个 JSON 对象转换为 OperateTimelineListVo 对象
            for (Object obj : jsonArray) {
                JSONObject jsonObj = (JSONObject) obj;
                SysUserInfo sysUserInfo = sysUserInfoMapper.selectById(jsonObj.getInt("userId"));
                // 创建 returnTimelineListVo 对象并添加到列表中
                OpOutProductReturnListVo returnListVo = new OpOutProductReturnListVo();
                returnListVo.setUserId(jsonObj.getInt("userId"));
                returnListVo.setUserName(sysUserInfo.getUserName());
                returnListVo.setNum(jsonObj.getInt("num"));
                returnListVo.setTime(DateUtil.parse(jsonObj.getStr("time")));
                returnListVoList.add(returnListVo);
            }

            return returnListVoList.stream()
                    .sorted((o1, o2) -> o2.getTime().compareTo(o1.getTime()))
                    .collect(Collectors.toList());
        }
        return returnListVoList;
    }
}
