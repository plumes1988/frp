package com.figure.op.dict.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.figure.op.common.domain.PageQuery;
import com.figure.op.common.exception.ServiceException;
import com.figure.op.common.page.TableDataInfo;
import com.figure.op.common.utils.BeanCopyUtils;
import com.figure.op.dict.domain.DictTypeDO;
import com.figure.op.dict.domain.vo.type.DictTypeBaseVO;
import com.figure.op.dict.domain.vo.type.DictTypeCreateReqVO;
import com.figure.op.dict.domain.vo.type.DictTypePageReqVO;
import com.figure.op.dict.domain.vo.type.DictTypeUpdateReqVO;
import com.figure.op.dict.mapper.DictTypeMapper;
import com.google.common.annotations.VisibleForTesting;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;


/**
 * 字典类型 Service 实现类
 *
 * @author ly
 */
@Service
public class DictTypeServiceImpl implements DictTypeService {


    @Resource
    private DictDataService dictDataService;

    @Resource
    private DictTypeMapper dictTypeMapper;

    @Override
    public TableDataInfo<DictTypeDO> getDictTypePage(DictTypePageReqVO reqVO, PageQuery pageQuery) {
        return dictTypeMapper.selectPage(reqVO, pageQuery);
    }

    @Override
    public List<DictTypeDO> getDictTypeList(DictTypePageReqVO reqVO) {
        return dictTypeMapper.selectList(reqVO);
    }

    @Override
    public DictTypeDO getDictType(Long id) {
        return dictTypeMapper.selectById(id);
    }

    @Override
    public DictTypeDO getDictType(String type) {
        return dictTypeMapper.selectByType(type);
    }

    @Override
    public Long createDictType(DictTypeCreateReqVO reqVO) {
        // 校验正确性
        validateDictTypeForCreateOrUpdate(null, reqVO.getName(), reqVO.getType());

        // 插入字典类型
        DictTypeDO dictType = BeanCopyUtils.copy(reqVO, DictTypeDO.class);
        dictType.setDeletedTime(LocalDateTime.of(1970, 1, 1, 0, 0, 0)); // 唯一索引，避免 null 值
        dictTypeMapper.insert(dictType);
        return dictType.getId();
    }

    @Override
    public void updateDictType(DictTypeUpdateReqVO reqVO) {
        // 校验正确性
        validateDictTypeForCreateOrUpdate(reqVO.getId(), reqVO.getName(), null);

        // 更新字典类型
        DictTypeDO updateObj = BeanCopyUtils.copy(reqVO, DictTypeDO.class);
        dictTypeMapper.updateById(updateObj);
    }

    @Override
    public void deleteDictType(Long id) {
        // 校验是否存在
        DictTypeDO dictType = validateDictTypeExists(id);
        // 校验是否有字典数据
        if (dictDataService.countByDictType(dictType.getType()) > 0) {
            throw new ServiceException("无法删除，该字典类型还有字典数据");
        }
        // 删除字典类型
        dictTypeMapper.updateToDelete(id, LocalDateTime.now());
    }

    @Override
    public List<DictTypeDO> getDictTypeList() {
        return dictTypeMapper.selectList(new QueryWrapper<>());
    }

    private void validateDictTypeForCreateOrUpdate(Long id, String name, String type) {
        // 校验自己存在
        validateDictTypeExists(id);
        // 校验字典类型的名字的唯一性
        validateDictTypeNameUnique(id, name);
        // 校验字典类型的类型的唯一性
        validateDictTypeUnique(id, type);
    }

    @VisibleForTesting
    void validateDictTypeNameUnique(Long id, String name) {
        DictTypeDO dictType = dictTypeMapper.selectByName(name);
        if (dictType == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的字典类型
        if (id == null) {
            throw new ServiceException("已经存在该名字的字典类型");
        }
        if (!dictType.getId().equals(id)) {
            throw new ServiceException("已经存在该名字的字典类型");
        }
    }

    @VisibleForTesting
    void validateDictTypeUnique(Long id, String type) {
        if (StrUtil.isEmpty(type)) {
            return;
        }
        DictTypeDO dictType = dictTypeMapper.selectByType(type);
        if (dictType == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的字典类型
        if (id == null) {
            throw new ServiceException("已经存在该类型的字典类型");
        }
        if (!dictType.getId().equals(id)) {
            throw new ServiceException("已经存在该类型的字典类型");

        }
    }

    @VisibleForTesting
    DictTypeDO validateDictTypeExists(Long id) {
        if (id == null) {
            return null;
        }
        DictTypeDO dictType = dictTypeMapper.selectById(id);
        if (dictType == null) {
            throw new ServiceException("当前字典类型不存在");
        }
        return dictType;
    }

}
