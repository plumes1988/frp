package com.figure.op.dict.mapper;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.figure.op.common.domain.PageQuery;
import com.figure.op.common.page.TableDataInfo;
import com.figure.op.dict.domain.DictDataDO;
import com.figure.op.dict.domain.vo.data.DictDataBaseVO;
import com.figure.op.dict.domain.vo.data.DictDataPageReqVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;
import java.util.List;

@Mapper
public interface DictDataMapper extends BaseMapper<DictDataDO> {

    default DictDataDO selectByDictTypeAndValue(String dictType, String value) {
        LambdaQueryWrapper<DictDataDO> qw = new LambdaQueryWrapper<DictDataDO>();
        qw.eq(DictDataDO::getDictType, dictType)
                .eq(DictDataDO::getValue, value);
        return selectOne(qw);
    }

    default DictDataDO selectByDictTypeAndLabel(String dictType, String label) {
        LambdaQueryWrapper<DictDataDO> qw = new LambdaQueryWrapper<DictDataDO>();
        qw.eq(DictDataDO::getDictType, dictType)
                .eq(DictDataDO::getLabel, label);
        return selectOne(qw);
    }

    default List<DictDataDO> selectByDictTypeAndValues(String dictType, Collection<String> values) {
        return selectList(new LambdaQueryWrapper<DictDataDO>().eq(DictDataDO::getDictType, dictType)
                .in(DictDataDO::getValue, values));
    }

    default long selectCountByDictType(String dictType) {
        LambdaQueryWrapper<DictDataDO> qw = new LambdaQueryWrapper<DictDataDO>();
        qw.eq(DictDataDO::getDictType, dictType);
        return selectCount(qw);
    }

    default TableDataInfo<DictDataDO> selectPage(DictDataPageReqVO reqVO, PageQuery pageQuery) {


        LambdaQueryWrapper<DictDataDO> qw = new LambdaQueryWrapper<>();
        qw.like(StrUtil.isNotBlank(reqVO.getLabel()), DictDataDO::getLabel, reqVO.getLabel())
                .eq(StrUtil.isNotBlank(reqVO.getDictType()), DictDataDO::getDictType, reqVO.getDictType())
                .eq(reqVO.getStatus() != null, DictDataDO::getStatus, reqVO.getStatus())
                .orderByDesc(DictDataDO::getDictType);

        Page<DictDataDO> dictDataDOPage = selectPage(pageQuery.build(), qw);


        Page<DictDataDO> dictDataDOPageRes = new Page<>(dictDataDOPage.getCurrent(), dictDataDOPage.getSize(), dictDataDOPage.getTotal());
        dictDataDOPageRes.setRecords(dictDataDOPage.getRecords());
        return TableDataInfo.build(dictDataDOPageRes);
    }

    default List<DictDataDO> selectList(DictDataBaseVO reqVO) {
        return selectList(new LambdaQueryWrapper<DictDataDO>()
                .like(StrUtil.isNotBlank(reqVO.getLabel()), DictDataDO::getLabel, reqVO.getLabel())
                .eq(StrUtil.isNotBlank(reqVO.getDictType()), DictDataDO::getDictType, reqVO.getDictType())
                .eq(reqVO.getStatus() != null, DictDataDO::getStatus, reqVO.getStatus()));
    }

}
