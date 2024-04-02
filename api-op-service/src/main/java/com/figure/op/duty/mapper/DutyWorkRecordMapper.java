package com.figure.op.duty.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.figure.op.duty.domain.DutyInfo;
import com.figure.op.duty.domain.DutyWorkRecord;
import com.figure.op.duty.domain.bo.DutyInfoQueryBo;
import com.figure.op.duty.domain.bo.DutyWorkRecordQueryBo;
import com.figure.op.duty.domain.vo.DutyInfoPageVo;
import com.figure.op.duty.domain.vo.DutyWorkRecordListVo;
import com.figure.op.duty.domain.vo.DutyWorkRecordPageVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 品牌管理表 Mapper 接口
 *
 * @author fsn
 */
public interface DutyWorkRecordMapper extends BaseMapper<DutyWorkRecord> {

    /**
     * 查询分页列表
     * @param page 分页对象
     * @param queryBo 查询对象
     * @return 分页列表
     */
    Page<DutyWorkRecordPageVo> selectVoPage(@Param("page") Page<DutyWorkRecord> page, @Param("queryBo") DutyWorkRecordQueryBo queryBo);

    List<DutyWorkRecordListVo> selectVoList( @Param("queryBo") DutyWorkRecordQueryBo queryBo);
}
