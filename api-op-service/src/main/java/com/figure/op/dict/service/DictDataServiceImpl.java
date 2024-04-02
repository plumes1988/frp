package com.figure.op.dict.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.figure.op.common.domain.PageQuery;
import com.figure.op.common.enums.CommonStatusEnum;
import com.figure.op.common.exception.ServiceException;
import com.figure.op.common.page.TableDataInfo;
import com.figure.op.common.utils.CollectionUtils;
import com.figure.op.dict.domain.DictDataDO;
import com.figure.op.dict.domain.DictTypeDO;
import com.figure.op.dict.domain.vo.data.DictDataBaseVO;
import com.figure.op.dict.domain.vo.data.DictDataCreateReqVO;
import com.figure.op.dict.domain.vo.data.DictDataPageReqVO;
import com.figure.op.dict.domain.vo.data.DictDataUpdateReqVO;
import com.figure.op.dict.mapper.DictDataMapper;
import com.google.common.annotations.VisibleForTesting;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * 字典数据 Service 实现类
 *
 * @author ruoyi
 */
@Service
@Slf4j
public class DictDataServiceImpl implements DictDataService {

    /**
     * 排序 dictType > sort
     */
    private static final Comparator<DictDataDO> COMPARATOR_TYPE_AND_SORT = Comparator
            .comparing(DictDataDO::getDictType)
            .thenComparingInt(DictDataDO::getSort);

    @Resource
    private DictTypeService dictTypeService;

    @Resource
    private DictDataMapper dictDataMapper;

    @Override
    public List<DictDataDO> getDictDataList(String type) {
        LambdaQueryWrapper<DictDataDO> dictDataDOQq = new LambdaQueryWrapper<>();
        dictDataDOQq.eq(DictDataDO::getStatus, CommonStatusEnum.ENABLE.getStatus());
        dictDataDOQq.eq(StrUtil.isNotBlank(type), DictDataDO::getDictType, type);
        List<DictDataDO> list = dictDataMapper.selectList(dictDataDOQq);
        list.sort(COMPARATOR_TYPE_AND_SORT);
        return list;
    }

    @Override
    public TableDataInfo<DictDataDO> getDictDataPage(DictDataPageReqVO reqVO, PageQuery pageQuery) {
        return dictDataMapper.selectPage(reqVO, pageQuery);
    }

    @Override
    public List<DictDataDO> getDictDataList(DictDataBaseVO reqVO) {
        List<DictDataDO> list = dictDataMapper.selectList(reqVO);
        list.sort(COMPARATOR_TYPE_AND_SORT);
        return list;
    }

    @Override
    public DictDataDO getDictData(Long id) {
        return dictDataMapper.selectById(id);
    }

    @Override
    public Long createDictData(DictDataCreateReqVO reqVO) {
        // 校验正确性
        validateDictDataForCreateOrUpdate(null, reqVO.getValue(), reqVO.getDictType());

        // 插入字典类型
        DictDataDO dictData = new DictDataDO();
        BeanUtil.copyProperties(reqVO, dictData);
        dictDataMapper.insert(dictData);
        return dictData.getId();
    }

    @Override
    public void updateDictData(DictDataUpdateReqVO reqVO) {
        // 校验正确性
        validateDictDataForCreateOrUpdate(reqVO.getId(), reqVO.getValue(), reqVO.getDictType());

        // 更新字典类型
        DictDataDO updateObj = new DictDataDO();
        BeanUtil.copyProperties(reqVO, updateObj);
        dictDataMapper.updateById(updateObj);
    }

    @Override
    public void deleteDictData(Long id) {
        // 校验是否存在
        validateDictDataExists(id);

        // 删除字典数据
        dictDataMapper.deleteById(id);
    }

    @Override
    public long countByDictType(String dictType) {
        return dictDataMapper.selectCountByDictType(dictType);
    }

    private void validateDictDataForCreateOrUpdate(Long id, String value, String dictType) {
        // 校验自己存在
        validateDictDataExists(id);
        // 校验字典类型有效
        validateDictTypeExists(dictType);
        // 校验字典数据的值的唯一性
        validateDictDataValueUnique(id, dictType, value);
    }

    @VisibleForTesting
    public void validateDictDataValueUnique(Long id, String dictType, String value) {
        DictDataDO dictData = dictDataMapper.selectByDictTypeAndValue(dictType, value);
        if (dictData == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的字典数据
        if (id == null) {
            throw new ServiceException("已经存在该值的字典数据");
        }
        if (!dictData.getId().equals(id)) {
            throw new ServiceException("已经存在该值的字典数据");
        }
    }

    @VisibleForTesting
    public void validateDictDataExists(Long id) {
        if (id == null) {
            return;
        }
        DictDataDO dictData = dictDataMapper.selectById(id);
        if (dictData == null) {
            throw new ServiceException("当前字典数据不存在");
        }
    }

    @VisibleForTesting
    public void validateDictTypeExists(String type) {
        DictTypeDO dictType = dictTypeService.getDictType(type);
        if (dictType == null) {
            throw new ServiceException("当前字典数据不存在");
        }
        if (!CommonStatusEnum.ENABLE.getStatus().equals(dictType.getStatus())) {
            throw new ServiceException("字典类型不处于开启状态，不允许选择");
        }
    }

    @Override
    public void validateDictDataList(String dictType, Collection<String> values) {
        if (CollUtil.isEmpty(values)) {
            return;
        }
        Map<String, DictDataDO> dictDataMap = CollectionUtils.convertMap(
                dictDataMapper.selectByDictTypeAndValues(dictType, values), DictDataDO::getValue);
        // 校验
        values.forEach(value -> {
            DictDataDO dictData = dictDataMap.get(value);
            if (dictData == null) {
                throw new ServiceException("当前字典数据不存在");
            }
            if (!CommonStatusEnum.ENABLE.getStatus().equals(dictData.getStatus())) {
                throw new ServiceException("字典数据不处于开启状态，不允许选择");
            }
        });
    }

    @Override
    public DictDataDO getDictData(String dictType, String value) {
        return dictDataMapper.selectByDictTypeAndValue(dictType, value);
    }

    @Override
    public DictDataDO parseDictData(String dictType, String label) {
        return dictDataMapper.selectByDictTypeAndLabel(dictType, label);
    }

}
