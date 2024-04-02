package com.figure.op.dict.mapper;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.figure.op.common.domain.PageQuery;
import com.figure.op.common.page.TableDataInfo;
import com.figure.op.common.utils.ArrayUtils;
import com.figure.op.dict.domain.DictTypeDO;
import com.figure.op.dict.domain.vo.type.DictTypeBaseVO;
import com.figure.op.dict.domain.vo.type.DictTypePageReqVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface DictTypeMapper extends BaseMapper<DictTypeDO> {

    default TableDataInfo<DictTypeDO> selectPage(DictTypePageReqVO reqVO, PageQuery pageQuery) {
        // 开始时间
        Object val1 = ArrayUtils.get(reqVO.getCreateTime(), 0);
        // 结束时间
        Object val2 = ArrayUtils.get(reqVO.getCreateTime(), 1);

        Page<DictTypeDO> dictTypeDOPage = selectPage(pageQuery.build(), new LambdaQueryWrapper<DictTypeDO>()
                .like(StrUtil.isNotBlank(reqVO.getName()), DictTypeDO::getName, reqVO.getName())
                .like(StrUtil.isNotBlank(reqVO.getType()), DictTypeDO::getType, reqVO.getType())
                .eq(reqVO.getStatus() != null, DictTypeDO::getStatus, reqVO.getStatus())
                .between((reqVO.getCreateTime() != null && reqVO.getCreateTime().length > 0), DictTypeDO::getCreateTime, val1, val2)
                .orderByDesc(DictTypeDO::getId));

        Page<DictTypeDO> dictTypeDOPageRes = new Page<>(dictTypeDOPage.getCurrent(), dictTypeDOPage.getSize(), dictTypeDOPage.getTotal());
        dictTypeDOPageRes.setRecords(dictTypeDOPage.getRecords());
        return TableDataInfo.build(dictTypeDOPageRes);
    }

    default List<DictTypeDO> selectList(DictTypePageReqVO reqVO) {
        // 开始时间
        Object val1 = ArrayUtils.get(reqVO.getCreateTime(), 0);
        // 结束时间
        Object val2 = ArrayUtils.get(reqVO.getCreateTime(), 1);
        return selectList(new LambdaQueryWrapper<DictTypeDO>()
                .like(StrUtil.isNotBlank(reqVO.getName()), DictTypeDO::getName, reqVO.getName())
                .like(StrUtil.isNotBlank(reqVO.getType()), DictTypeDO::getType, reqVO.getType())
                .eq(reqVO.getStatus() != null, DictTypeDO::getStatus, reqVO.getStatus())
                .between((reqVO.getCreateTime() != null && reqVO.getCreateTime().length > 0), DictTypeDO::getCreateTime, val1, val2));
    }

    default DictTypeDO selectByType(String type) {
        LambdaQueryWrapper<DictTypeDO> qw = new LambdaQueryWrapper<>();
        qw.eq(DictTypeDO::getType, type);
        return selectOne(qw);
    }

    default DictTypeDO selectByName(String name) {
        LambdaQueryWrapper<DictTypeDO> qw = new LambdaQueryWrapper<>();
        qw.eq(DictTypeDO::getName, name);
        return selectOne(qw);
    }

    int deleteById(@Param("id") Long id, @Param("deletedTime") LocalDateTime deletedTime);

    @Update("UPDATE sys_dict_type SET isDelete = 1, deletedTime = #{deletedTime} WHERE id = #{id}")
    void updateToDelete(@Param("id") Long id, @Param("deletedTime") LocalDateTime deletedTime);
}
