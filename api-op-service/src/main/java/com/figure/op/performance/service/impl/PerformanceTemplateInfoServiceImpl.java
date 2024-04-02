package com.figure.op.performance.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.figure.op.common.domain.PageQuery;
import com.figure.op.common.enums.PerformanceTemplateLevelEnum;
import com.figure.op.common.exception.ServiceException;
import com.figure.op.common.page.TableDataInfo;
import com.figure.op.common.utils.BeanCopyUtils;
import com.figure.op.performance.domain.PerformanceTemplateDo;
import com.figure.op.performance.domain.PerformanceTemplateInfoDo;
import com.figure.op.performance.domain.bo.*;
import com.figure.op.performance.domain.vo.tempInfo.PerformanceTemplateInfoPageVo;
import com.figure.op.performance.domain.vo.tempInfo.PerformanceTemplateInfoSecondVo;
import com.figure.op.performance.domain.vo.tempInfo.PerformanceTemplateInfoVo;
import com.figure.op.performance.mapper.PerformanceTemplateInfoMapper;
import com.figure.op.performance.service.PerformanceTemplateInfoService;
import com.figure.op.performance.service.PerformanceTemplateService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 绩效模板明细Service业务层处理
 *
 * @date 2023-08-25
 */
@Service
public class PerformanceTemplateInfoServiceImpl implements PerformanceTemplateInfoService {

    @Resource
    private PerformanceTemplateInfoMapper performanceTemplateInfoMapper;
    @Resource
    private PerformanceTemplateService performanceTemplateService;

    private final int maxScoreRate = 100;


    @Override
    public List<PerformanceTemplateInfoDo> selectInfoByTemplateId(Long id) {
        return performanceTemplateInfoMapper.selectInfoByTemplateId(id);
    }

    /**
     * 查询绩效模板明细
     *
     * @param id 绩效模板明细主键
     * @return 绩效模板明细
     */
    @Override
    public PerformanceTemplateInfoDo selectPerformanceTemplateInfoById(Long id) {
        return performanceTemplateInfoMapper.selectById(id);
    }

    /**
     * 查询绩效模板明细分页
     *
     * @param queryBo 查询参数
     * @param pageQuery 分页参数
     * @return 绩效模板明细
     */
    @Override
    public TableDataInfo<PerformanceTemplateInfoPageVo> selectPerformanceTemplateInfoPage(PerformanceTemplateInfoQueryBo queryBo, PageQuery pageQuery) {

        List<Long> childIdList = new ArrayList<>();
        List<Long> parentIdList = new ArrayList<>();
        // 若查询了子级标题 则需筛选出对应父级
        if (queryBo.getTitle() != null && !"".equals(queryBo.getTitle())){
            LambdaQueryWrapper<PerformanceTemplateInfoDo> lqw0 = new LambdaQueryWrapper<>();
            lqw0.like(queryBo.getTitle() != null && !"".equals(queryBo.getTitle()), PerformanceTemplateInfoDo::getTitle, queryBo.getTitle());
            List<PerformanceTemplateInfoDo> childList = performanceTemplateInfoMapper.selectList(lqw0);
            childIdList = childList.stream().map(PerformanceTemplateInfoDo::getId).collect(Collectors.toList());
            parentIdList = childList.stream().map(PerformanceTemplateInfoDo::getParentId).collect(Collectors.toList());
        }

        // 若通过子级标题未查到 则直接返回空
        if (queryBo.getTitle() != null && !"".equals(queryBo.getTitle()) && parentIdList.size() == 0){
            return TableDataInfo.build(new Page<>());
        }

        // 查询明细的父级分页列表
        LambdaQueryWrapper<PerformanceTemplateInfoDo> lqw1 = new LambdaQueryWrapper<>();
        lqw1.eq(PerformanceTemplateInfoDo::getTemplateId, queryBo.getTemplateId());
        lqw1.isNull(PerformanceTemplateInfoDo::getParentId);
        lqw1.like(queryBo.getParentTemplateTitle() != null && !"".equals(queryBo.getParentTemplateTitle()), PerformanceTemplateInfoDo::getTitle, queryBo.getParentTemplateTitle());
        // 若子级查询存在 则对父级进行筛选
        lqw1.in(parentIdList.size() > 0, PerformanceTemplateInfoDo::getId, parentIdList);
        lqw1.orderByDesc(PerformanceTemplateInfoDo::getId);
        Page<PerformanceTemplateInfoDo> result = performanceTemplateInfoMapper.selectPage(pageQuery.build(), lqw1);
        List<PerformanceTemplateInfoPageVo> resultList = BeanCopyUtils.copyList(result.getRecords(), PerformanceTemplateInfoPageVo.class);
        if (resultList != null){
            for (PerformanceTemplateInfoPageVo performanceTemplateInfoPageVo : resultList) {
                // 计算每项父级满分
                BigDecimal totalScore = BigDecimal.valueOf(100);
                BigDecimal firstRate = BigDecimal.valueOf(performanceTemplateInfoPageVo.getScoreRate()).divide(BigDecimal.valueOf(100), 2, RoundingMode.DOWN);
                BigDecimal firstFullScore = totalScore.multiply(firstRate).stripTrailingZeros();
                String firstFullScoreString = firstFullScore.toPlainString();
                performanceTemplateInfoPageVo.setFullScore(new BigDecimal(firstFullScoreString));
                // 查询下属子级列表
                LambdaQueryWrapper<PerformanceTemplateInfoDo> lqw2 = new LambdaQueryWrapper<>();
                lqw2.eq(PerformanceTemplateInfoDo::getTemplateId, queryBo.getTemplateId());
                lqw2.eq(PerformanceTemplateInfoDo::getParentId, performanceTemplateInfoPageVo.getId());
                lqw2.in(childIdList.size() > 0, PerformanceTemplateInfoDo::getId, childIdList);
                lqw2.orderByDesc(PerformanceTemplateInfoDo::getId);
                List<PerformanceTemplateInfoDo> secondList = performanceTemplateInfoMapper.selectList(lqw2);
                performanceTemplateInfoPageVo.setSecondList(BeanCopyUtils.copyList(secondList, PerformanceTemplateInfoSecondVo.class));
                for (PerformanceTemplateInfoSecondVo performanceTemplateInfoSecondVo : performanceTemplateInfoPageVo.getSecondList()) {
                    BigDecimal secondRate = BigDecimal.valueOf(performanceTemplateInfoSecondVo.getScoreRate()).divide(BigDecimal.valueOf(100), 2, RoundingMode.DOWN);
                    BigDecimal secondFullScore = firstFullScore.multiply(secondRate.stripTrailingZeros());
                    String secondFullScoreString = secondFullScore.toPlainString();
                    performanceTemplateInfoSecondVo.setFullScore(new BigDecimal(secondFullScoreString));
                }
            }
        }
        Page<PerformanceTemplateInfoPageVo> resultPage = new Page<>(result.getCurrent(), result.getSize(), result.getTotal());
        resultPage.setRecords(resultList);
        return TableDataInfo.build(resultPage);
    }

    /**
     * 所有简化绩效模板明细
     *
     * @return
     */
    @Override
    public List<PerformanceTemplateInfoVo> selectSimpleList() {
        List<PerformanceTemplateInfoDo> performanceTemplateInfoDos = performanceTemplateInfoMapper.selectList(new QueryWrapper<>());
        return BeanCopyUtils.copyList(performanceTemplateInfoDos, PerformanceTemplateInfoVo.class);
    }


    /**
     * 所有父级简化绩效模板明细
     */
    @Override
    public List<PerformanceTemplateInfoVo> selectSimpleFirstList(Long templateId) {
        LambdaQueryWrapper<PerformanceTemplateInfoDo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PerformanceTemplateInfoDo::getLevel, PerformanceTemplateLevelEnum.FIRST.getLevel());
        queryWrapper.eq(templateId != null, PerformanceTemplateInfoDo::getTemplateId, templateId);
        List<PerformanceTemplateInfoDo> performanceTemplateInfoDos = performanceTemplateInfoMapper.selectList(queryWrapper);
        return BeanCopyUtils.copyList(performanceTemplateInfoDos, PerformanceTemplateInfoVo.class);
    }

    /**
     * 新增绩效模板明细
     *
     * @param reqVo 绩效模板明细
     * @return 结果
     */
    @Override
    public int create(PerformanceTemplateInfoVo reqVo) {
        PerformanceTemplateInfoDo insertDo = new PerformanceTemplateInfoDo();

        BeanUtil.copyProperties(reqVo, insertDo);

        if (insertDo.getParentId() == null) {
            insertDo.setLevel(PerformanceTemplateLevelEnum.FIRST.getLevel());
        } else {
            insertDo.setLevel(PerformanceTemplateLevelEnum.SECOND.getLevel());
        }

        return performanceTemplateInfoMapper.insert(insertDo);
    }

    /**
     * 批量新增绩效模板明细
     *
     * @param bo 绩效模板明细
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean batchCreate(PerformanceTemplateInfoCreateBo bo) {

        Long performanceTemplateId = bo.getPerformanceTemplateId();
        if (performanceTemplateId == null) {
            throw new ServiceException("未提供模板编号");
        }

        PerformanceTemplateDo performanceTemplateDo = performanceTemplateService.selectById(performanceTemplateId);
        if (performanceTemplateDo == null) {
            throw new ServiceException("不存在编号为: " + performanceTemplateId + " 的模板数据");
        }

        Long parentId = bo.getFirstTemplateInfoId();
        // 添加子级
        if (parentId != null) {
            // 上级id不是null,检查是否存在，直接作为二级明细的上级id即可
            PerformanceTemplateInfoDo parentDo = performanceTemplateInfoMapper.selectById(parentId);
            if (parentDo == null) {
                throw new ServiceException("上级明细不存在");
            }
            // 查询其余父级下的子级 累计所有子级占比之和 不能超过100
            LambdaQueryWrapper<PerformanceTemplateInfoDo> lqw = new LambdaQueryWrapper<>();
            lqw.eq(PerformanceTemplateInfoDo::getTemplateId, bo.getPerformanceTemplateId());
            lqw.eq(PerformanceTemplateInfoDo::getParentId, parentId);
            List<PerformanceTemplateInfoDo> otherChildList = performanceTemplateInfoMapper.selectList(lqw);
            if (otherChildList.size() > 10){
                throw new ServiceException("同一父级下最多添加10个子级");
            }
            int sumScoreRate = otherChildList.stream()
                    .mapToInt(PerformanceTemplateInfoDo::getScoreRate)
                    .sum() + bo.getSecondTemplateInfoScoreRate();
            if (sumScoreRate > maxScoreRate){
                throw new ServiceException("同一父级下所有子级占比之和不能超过100");
            }
        // 添加父级
        } else {
            // 上级id为空时，检查父级明细名称，占比是否填写，未填写报错，填写后插入数据库
            if (StrUtil.isBlank(bo.getFirstTemplateInfoTitle())) {
                throw new ServiceException("父级明细名称不能为空");
            }
            if (bo.getFirstTemplateInfoScoreRate() == null) {
                throw new ServiceException("父级评分占比不能为空");
            }
            // 累计所有父级之和占比 不能超过100
            LambdaQueryWrapper<PerformanceTemplateInfoDo> lqw = new LambdaQueryWrapper<>();
            lqw.eq(PerformanceTemplateInfoDo::getTemplateId, bo.getPerformanceTemplateId());
            lqw.isNull(PerformanceTemplateInfoDo::getParentId);
            List<PerformanceTemplateInfoDo> otherParentList = performanceTemplateInfoMapper.selectList(lqw);
            if (otherParentList.size() > 10){
                throw new ServiceException("同一模板下最多添加10个父级");
            }
            int sumScoreRate = otherParentList.stream()
                    .mapToInt(PerformanceTemplateInfoDo::getScoreRate)
                    .sum() + bo.getFirstTemplateInfoScoreRate();
            if (sumScoreRate > maxScoreRate){
                throw new ServiceException("同一模板下所有父级占比之和不能超过100");
            }

            // 记录数据
            PerformanceTemplateInfoDo firstTempInfoDo = new PerformanceTemplateInfoDo();
            firstTempInfoDo.setTemplateId(bo.getPerformanceTemplateId());
            firstTempInfoDo.setTitle(bo.getFirstTemplateInfoTitle());
            firstTempInfoDo.setScoreRate(bo.getFirstTemplateInfoScoreRate());
            firstTempInfoDo.setLevel(PerformanceTemplateLevelEnum.FIRST.getLevel());
            performanceTemplateInfoMapper.insert(firstTempInfoDo);
            parentId = firstTempInfoDo.getId();
        }
        PerformanceTemplateInfoDo secondTempInfoDo = new PerformanceTemplateInfoDo();
        secondTempInfoDo.setTemplateId(bo.getPerformanceTemplateId());
        secondTempInfoDo.setParentId(parentId);
        secondTempInfoDo.setTitle(bo.getSecondTemplateInfoTitle());
        secondTempInfoDo.setScoreRate(bo.getSecondTemplateInfoScoreRate());
        secondTempInfoDo.setInfo(bo.getSecondTemplateInfo());
        secondTempInfoDo.setLevel(PerformanceTemplateLevelEnum.SECOND.getLevel());
        performanceTemplateInfoMapper.insert(secondTempInfoDo);
        return true;
    }

    /**
     * 批量编辑绩效模板明细
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean batchUpdate(PerformanceTemplateInfoUpdateBo bo) {
        // 检测二级明细是否存在
        Long secondTemplateInfoId = bo.getSecondTemplateInfoId();
        if (secondTemplateInfoId == null) {
            throw new ServiceException("未提供当前模板明细编号");
        }
        PerformanceTemplateInfoDo secondTemplateInfoDo = performanceTemplateInfoMapper.selectById(secondTemplateInfoId);
        if (secondTemplateInfoDo == null) {
            throw new ServiceException("未找到当前编辑数据");
        }
        if (secondTemplateInfoDo.getParentId() == null){
            throw new ServiceException("无法直接对父级进行编辑，请选择子级编辑");
        }

        Long performanceTemplateId = bo.getPerformanceTemplateId();
        if (performanceTemplateId == null) {
            throw new ServiceException("未提供模板编号");
        }

        PerformanceTemplateDo performanceTemplateDo = performanceTemplateService.selectById(performanceTemplateId);
        if (performanceTemplateDo == null) {
            throw new ServiceException("不存在编号为: " + performanceTemplateId + " 的模板数据");
        }

        Long parentId = bo.getFirstTemplateInfoId();
        // 编辑子级
        if (parentId != null) {
            // 上级id不是null,检查是否存在，直接作为二级明细的上级id即可
            PerformanceTemplateInfoDo parentDo = performanceTemplateInfoMapper.selectById(parentId);
            if (parentDo == null) {
                throw new ServiceException("上级明细不存在");
            }
            // 查询其余父级下的子级 累计所有子级占比之和 不能超过100
            LambdaQueryWrapper<PerformanceTemplateInfoDo> lqw = new LambdaQueryWrapper<>();
            lqw.eq(PerformanceTemplateInfoDo::getTemplateId, bo.getPerformanceTemplateId());
            lqw.eq(PerformanceTemplateInfoDo::getParentId, parentId);
            lqw.ne(PerformanceTemplateInfoDo::getId, secondTemplateInfoId);
            List<PerformanceTemplateInfoDo> otherChildList = performanceTemplateInfoMapper.selectList(lqw);
            int sumScoreRate = otherChildList.stream()
                    .mapToInt(PerformanceTemplateInfoDo::getScoreRate)
                    .sum() + bo.getSecondTemplateInfoScoreRate();
            if (sumScoreRate > maxScoreRate){
                throw new ServiceException("同一父级下所有子级占比之和不能超过100");
            }
            // 对父级明星进行更新
            // 如果给了同时提供了父级的id，标题，分数占比，那么进入更新流程
            if (StrUtil.isNotBlank(bo.getFirstTemplateInfoTitle()) && !(bo.getFirstTemplateInfoTitle()
                    .equals(parentDo.getTitle()))) {
                System.out.println("! = " );
                parentDo.setTitle(bo.getFirstTemplateInfoTitle());
            }
            if (bo.getFirstTemplateInfoScoreRate() != null && !bo.getFirstTemplateInfoScoreRate().equals(parentDo.getScoreRate())) {
                System.out.println("? = " );
                parentDo.setScoreRate(bo.getFirstTemplateInfoScoreRate());
            }
            // 更新
            performanceTemplateInfoMapper.updateById(parentDo);
        // 编辑父级
        } else {
            // 累计所有父级之和占比 不能超过100
            LambdaQueryWrapper<PerformanceTemplateInfoDo> lqw = new LambdaQueryWrapper<>();
            lqw.eq(PerformanceTemplateInfoDo::getTemplateId, bo.getPerformanceTemplateId());
            lqw.ne(PerformanceTemplateInfoDo::getId, secondTemplateInfoId);
            List<PerformanceTemplateInfoDo> otherParentList = performanceTemplateInfoMapper.selectList(lqw);
            int sumScoreRate = otherParentList.stream()
                    .mapToInt(PerformanceTemplateInfoDo::getScoreRate)
                    .sum() + bo.getFirstTemplateInfoScoreRate();
            if (sumScoreRate > maxScoreRate){
                throw new ServiceException("同一模板下所有父级占比之和不能超过100");
            }

            // 上级id为空时，检查父级明细名称，占比是否填写，未填写报错，填写后插入数据库
            if (StrUtil.isBlank(bo.getFirstTemplateInfoTitle())) {
                throw new ServiceException("父级明细名称不能为空");
            }
            if (bo.getFirstTemplateInfoScoreRate() == null) {
                throw new ServiceException("父级评分占比不能为空");
            }
            // 记录数据
            PerformanceTemplateInfoDo firstTempInfoDo = new PerformanceTemplateInfoDo();
            firstTempInfoDo.setTemplateId(bo.getPerformanceTemplateId());
            firstTempInfoDo.setTitle(bo.getFirstTemplateInfoTitle());
            firstTempInfoDo.setScoreRate(bo.getFirstTemplateInfoScoreRate());
            firstTempInfoDo.setLevel(PerformanceTemplateLevelEnum.FIRST.getLevel());
            performanceTemplateInfoMapper.insert(firstTempInfoDo);
            parentId = firstTempInfoDo.getId();
        }

        // 赋值更新
        secondTemplateInfoDo.setTitle(bo.getSecondTemplateInfoTitle());
        secondTemplateInfoDo.setScoreRate(bo.getSecondTemplateInfoScoreRate());
        secondTemplateInfoDo.setInfo(bo.getSecondTemplateInfo());
        secondTemplateInfoDo.setParentId(parentId);

        return performanceTemplateInfoMapper.updateById(secondTemplateInfoDo) > 0;
    }

    /**
     * 修改绩效模板明细
     *
     * @param reqVo 绩效模板明细
     * @return 结果
     */
    @Override
    public boolean update(PerformanceTemplateInfoVo reqVo) {
        PerformanceTemplateInfoDo updateDo = new PerformanceTemplateInfoDo();
        BeanUtil.copyProperties(reqVo, updateDo);
        return performanceTemplateInfoMapper.updateById(updateDo) > 0;
    }

    /**
     * 批量删除绩效模板明细
     *
     * @param ids 需要删除的绩效模板明细主键
     * @return 结果
     */
    @Override
    public int deletePerformanceTemplateInfoByIds(Long[] ids) {
        return performanceTemplateInfoMapper.deletePerformanceTemplateInfoByIds(ids);
    }

    /**
     * 删除绩效模板明细信息
     *
     * @param id 绩效模板明细主键
     * @return 结果
     */
    @Override
    public int deletePerformanceTemplateInfoById(Long id) {
        PerformanceTemplateInfoDo templateInfoDo = performanceTemplateInfoMapper.selectById(id);
        if (templateInfoDo == null) {
            throw new ServiceException("当前数据不存在");
        }
        // 若是父级 则查询是否存在子级 若存在则不允许删除
        if (templateInfoDo.getParentId() == null){
            LambdaQueryWrapper<PerformanceTemplateInfoDo> lqw = new LambdaQueryWrapper<>();
            lqw.eq(PerformanceTemplateInfoDo::getParentId, id);
            List<PerformanceTemplateInfoDo> performanceTemplateInfoDoList = performanceTemplateInfoMapper.selectList(lqw);
            if (performanceTemplateInfoDoList == null || performanceTemplateInfoDoList.size() > 0){
                throw new ServiceException("当前父级下存在子级，请先删除子级");
            }
        }
        return performanceTemplateInfoMapper.deleteById(id);
    }

    /**
     * 设置得分
     *
     * @param id    分项明细id
     * @param score 得分
     * @return
     */
    @Override
    public void setScore(Long id, BigDecimal score) {
        PerformanceTemplateInfoDo performanceTemplateInfoDo = new PerformanceTemplateInfoDo();
        performanceTemplateInfoDo.setId(id);
//        performanceTemplateInfoDo.setScore(score);
        performanceTemplateInfoMapper.updateById(performanceTemplateInfoDo);
    }

    /**
     * 校验模板明细是否完整
     * @param templateId 模板ID
     * @return 结果
     */
    @Override
    public Boolean validateTemplateInfoComplete(Long templateId){

        PerformanceTemplateInfoQueryBo queryBo = new PerformanceTemplateInfoQueryBo();
        queryBo.setTemplateId(templateId);
        PageQuery pageQuery = new PageQuery();
        pageQuery.setPageNum(1);
        pageQuery.setPageSize(100);

        TableDataInfo<PerformanceTemplateInfoPageVo> templateInfoPage = selectPerformanceTemplateInfoPage(queryBo, pageQuery);
        List<PerformanceTemplateInfoPageVo> templateInfoList = templateInfoPage.getRows();
        if (templateInfoList == null || templateInfoList.size() == 0){
            return false;
        }else {
            // 先累加全部父级占比 是否等于100
            int sumFirstScoreRate = templateInfoList.stream()
                    .mapToInt(PerformanceTemplateInfoPageVo::getScoreRate)
                    .sum();
            if (sumFirstScoreRate != maxScoreRate){
                return false;
            }
            // 遍历每一个父级 分别累加每个父级下的子级占比 是否等于100
            for (PerformanceTemplateInfoPageVo performanceTemplateInfoPageVo : templateInfoList) {
                if (performanceTemplateInfoPageVo.getSecondList() == null || performanceTemplateInfoPageVo.getSecondList().size() == 0){
                    return false;
                }
                int sumSecondScoreRate = performanceTemplateInfoPageVo.getSecondList().stream()
                        .mapToInt(PerformanceTemplateInfoSecondVo::getScoreRate)
                        .sum();
                if (sumSecondScoreRate != maxScoreRate){
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public Boolean createParent(PerformanceTemplateInfoFirstBo bo) {
        PerformanceTemplateDo performanceTemplateDo = performanceTemplateService.selectById(bo.getTemplateId());
        if (performanceTemplateDo == null) {
            throw new ServiceException("模板不存在或已删除");
        }
        // 累计当前模板下所有父级之和占比 不能超过100
        LambdaQueryWrapper<PerformanceTemplateInfoDo> lqw = new LambdaQueryWrapper<>();
        lqw.eq(PerformanceTemplateInfoDo::getLevel, PerformanceTemplateLevelEnum.FIRST.getLevel());
        lqw.eq(PerformanceTemplateInfoDo::getTemplateId, bo.getTemplateId());
        List<PerformanceTemplateInfoDo> otherParentList = performanceTemplateInfoMapper.selectList(lqw);
        if (otherParentList.size() >= 10){
            throw new ServiceException("同一模板下最多添加10个父级项");
        }
        int sumScoreRate = otherParentList.stream()
                .mapToInt(PerformanceTemplateInfoDo::getScoreRate)
                .sum() + bo.getScoreRate();
        if (sumScoreRate > maxScoreRate){
            throw new ServiceException("同一模板下所有父级占比之和不能超过100");
        }

        PerformanceTemplateInfoDo add = BeanUtil.toBean(bo, PerformanceTemplateInfoDo.class);
        add.setLevel(PerformanceTemplateLevelEnum.FIRST.getLevel());
        return performanceTemplateInfoMapper.insert(add) > 0;
    }

    @Override
    public Boolean updateParent(PerformanceTemplateInfoFirstBo bo) {
        // 查询当前模板明细
        PerformanceTemplateInfoDo performanceTemplateInfoDo = performanceTemplateInfoMapper.selectById(bo.getId());
        if (performanceTemplateInfoDo == null){
            throw new ServiceException("当前数据不存在");
        }
        if (performanceTemplateInfoDo.getParentId() != null || !performanceTemplateInfoDo.getLevel().equals(PerformanceTemplateLevelEnum.FIRST.getLevel())){
            throw new ServiceException("当前模板明细不为父级");
        }

        // 累计当前模板下所有父级之和占比 不能超过100
        LambdaQueryWrapper<PerformanceTemplateInfoDo> lqw = new LambdaQueryWrapper<>();
        lqw.eq(PerformanceTemplateInfoDo::getLevel, PerformanceTemplateLevelEnum.FIRST.getLevel());
        lqw.eq(PerformanceTemplateInfoDo::getTemplateId, performanceTemplateInfoDo.getTemplateId());
        lqw.ne(PerformanceTemplateInfoDo::getId, bo.getId());
        List<PerformanceTemplateInfoDo> otherParentList = performanceTemplateInfoMapper.selectList(lqw);
        int sumScoreRate = otherParentList.stream()
                .mapToInt(PerformanceTemplateInfoDo::getScoreRate)
                .sum() + bo.getScoreRate();
        if (sumScoreRate > maxScoreRate){
            throw new ServiceException("同一模板下所有父级占比之和不能超过100");
        }
        PerformanceTemplateInfoDo update = BeanUtil.toBean(bo, PerformanceTemplateInfoDo.class);
        return performanceTemplateInfoMapper.updateById(update) > 0;
    }

    @Override
    public Boolean createChild(PerformanceTemplateInfoSecondBo bo) {
        PerformanceTemplateInfoDo parentDo = performanceTemplateInfoMapper.selectById(bo.getParentId());
        if (parentDo == null) {
            throw new ServiceException("父级模板明细不存在");
        }
        // 查询其余父级下的子级 累计所有子级占比之和 不能超过100
        LambdaQueryWrapper<PerformanceTemplateInfoDo> lqw = new LambdaQueryWrapper<>();
        lqw.eq(PerformanceTemplateInfoDo::getLevel, PerformanceTemplateLevelEnum.SECOND.getLevel());
        lqw.eq(PerformanceTemplateInfoDo::getTemplateId, parentDo.getTemplateId());
        lqw.eq(PerformanceTemplateInfoDo::getParentId, parentDo.getId());
        List<PerformanceTemplateInfoDo> otherChildList = performanceTemplateInfoMapper.selectList(lqw);
        if (otherChildList.size() >= 10){
            throw new ServiceException("同一父级项下最多添加10个子级项");
        }
        int sumScoreRate = otherChildList.stream()
                .mapToInt(PerformanceTemplateInfoDo::getScoreRate)
                .sum() + bo.getScoreRate();
        if (sumScoreRate > maxScoreRate){
            throw new ServiceException("同一父级下所有子级占比之和不能超过100");
        }

        PerformanceTemplateInfoDo add = BeanUtil.toBean(bo, PerformanceTemplateInfoDo.class);
        add.setTemplateId(parentDo.getTemplateId());
        add.setLevel(PerformanceTemplateLevelEnum.SECOND.getLevel());
        return performanceTemplateInfoMapper.insert(add) > 0;
    }

    @Override
    public Boolean updateChild(PerformanceTemplateInfoSecondBo bo) {
        // 查询当前模板明细
        PerformanceTemplateInfoDo performanceTemplateInfoDo = performanceTemplateInfoMapper.selectById(bo.getId());
        if (performanceTemplateInfoDo == null){
            throw new ServiceException("当前数据不存在");
        }
        if (performanceTemplateInfoDo.getParentId() == null || !performanceTemplateInfoDo.getLevel().equals(PerformanceTemplateLevelEnum.SECOND.getLevel())){
            throw new ServiceException("当前模板明细不为子级");
        }

        PerformanceTemplateInfoDo parentDo = performanceTemplateInfoMapper.selectById(bo.getParentId());
        if (parentDo == null) {
            throw new ServiceException("父级模板明细不存在");
        }
        // 查询其余父级下的子级 累计所有子级占比之和 不能超过100
        LambdaQueryWrapper<PerformanceTemplateInfoDo> lqw = new LambdaQueryWrapper<>();
        lqw.eq(PerformanceTemplateInfoDo::getLevel, PerformanceTemplateLevelEnum.SECOND.getLevel());
        lqw.eq(PerformanceTemplateInfoDo::getParentId, parentDo.getId());
        lqw.ne(PerformanceTemplateInfoDo::getId, bo.getId());
        List<PerformanceTemplateInfoDo> otherChildList = performanceTemplateInfoMapper.selectList(lqw);
        if (otherChildList.size() >= 10){
            throw new ServiceException("同一父级下最多添加10个子级");
        }
        int sumScoreRate = otherChildList.stream()
                .mapToInt(PerformanceTemplateInfoDo::getScoreRate)
                .sum() + bo.getScoreRate();
        if (sumScoreRate > maxScoreRate){
            throw new ServiceException("同一父级下所有子级占比之和不能超过100");
        }

        PerformanceTemplateInfoDo update = BeanUtil.toBean(bo, PerformanceTemplateInfoDo.class);
        // 子级明细的模板id为父级明细的模板id
        update.setTemplateId(parentDo.getTemplateId());
        return performanceTemplateInfoMapper.updateById(update) > 0;
    }

}
