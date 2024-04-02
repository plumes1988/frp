package com.figure.op.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.figure.op.common.domain.PageQuery;
import com.figure.op.common.page.TableDataInfo;
import com.figure.op.common.utils.BeanCopyUtils;
import com.figure.op.common.utils.StringUtils;
import com.figure.op.system.domain.TechDoc;
import com.figure.op.system.domain.bo.TechDocBo;
import com.figure.op.system.domain.bo.TechDocQueryBo;
import com.figure.op.system.domain.vo.TechDocListVo;
import com.figure.op.system.domain.vo.TechDocPageVo;
import com.figure.op.system.domain.vo.TechDocVo;
import com.figure.op.system.mapper.TechDocMapper;
import com.figure.op.system.service.ITechDocService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 品牌服务实现
 * @author fsn
 */
@Service
public class TechDocServiceImpl implements ITechDocService {

    @Resource
    private TechDocMapper TechDocMapper;

    /**
     * 全部列表
     */
    @Override
    public List<TechDocListVo> queryList(TechDoc TechDoc) {
        LambdaQueryWrapper<TechDoc> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        List<TechDoc> TechDocList = TechDocMapper.selectList(lambdaQueryWrapper);
        return BeanCopyUtils.copyList(TechDocList, TechDocListVo.class);
    }

    /**
     * 详情
     */
    @Override
    public TechDocVo queryById(Integer id) {
        TechDoc TechDoc = TechDocMapper.selectById(id);
        return BeanCopyUtils.copy(TechDoc, TechDocVo.class);
    }

    /**
     * 新增
     */
    @Override
    public Boolean insertByBo(TechDocBo bo) {
        TechDoc add = BeanUtil.toBean(bo, TechDoc.class);
        boolean flag = TechDocMapper.insert(add) > 0;
        if (flag) {
            bo.setDocId(add.getDocId());
        }
        return flag;
    }

    /**
     * 更新
     */
    @Override
    public Boolean updateByBo(TechDocBo bo) {
        TechDoc update = BeanUtil.toBean(bo, TechDoc.class);
        return TechDocMapper.updateById(update) > 0;
    }

    /**
     * 删除
     */
    @Override
    public Boolean deleteById(Integer id) {
        return TechDocMapper.deleteById(id) > 0;
    }

    @Override
    public TableDataInfo<TechDocPageVo> page(TechDocQueryBo queryBo, PageQuery pageQuery) {
        LambdaQueryWrapper<TechDoc> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(queryBo.getTechInfoId() != null, TechDoc::getTechInfoId, queryBo.getTechInfoId());
        lambdaQueryWrapper.like(StringUtils.isNotEmpty(queryBo.getDocName()), TechDoc::getDocName, queryBo.getDocName());
        if (!StringUtils.isEmpty(queryBo.getDocType())) {
            List<String> collect = Arrays.stream(queryBo.getDocType().split(",")).collect(Collectors.toList());
            lambdaQueryWrapper.and(wq -> {
                collect.forEach(item -> {
                    wq.like(StringUtils.isNotEmpty(item), TechDoc::getDocType, item).or();
                });
            });
        }
        lambdaQueryWrapper.orderByDesc(TechDoc::getDocId);
        Page<TechDoc> result = TechDocMapper.selectPage(pageQuery.build(), lambdaQueryWrapper);

        List<TechDocPageVo> resultList = BeanCopyUtils.copyList(result.getRecords(), TechDocPageVo.class);
        Page<TechDocPageVo> resultPage = new Page<>(result.getCurrent(), result.getSize(), result.getTotal());
        resultPage.setRecords(resultList);
        return TableDataInfo.build(resultPage);
    }
}
