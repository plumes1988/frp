package com.figure.op.duty.service;

import com.figure.op.common.domain.PageQuery;
import com.figure.op.common.page.TableDataInfo;
import com.figure.op.duty.domain.bo.DutyWorkRecordBo;
import com.figure.op.duty.domain.bo.DutyWorkRecordQueryBo;
import com.figure.op.duty.domain.vo.DutyWorkRecordListVo;
import com.figure.op.duty.domain.vo.DutyWorkRecordPageVo;
import com.figure.op.duty.domain.vo.DutyWorkRecordVo;

import java.util.List;

/**
 * 值班记录服务接口
 * @author fsn
 */
public interface IDutyWorkRecordService {

    /**
     * 分页列表
     * @param queryBo 查询对象
     * @param pageQuery 分页对象
     * @return 分页列表
     */
    TableDataInfo<DutyWorkRecordPageVo> page(DutyWorkRecordQueryBo queryBo, PageQuery pageQuery);

    /**
     * 全部列表
     * @return 全部列表
     */
    List<DutyWorkRecordListVo> queryList(DutyWorkRecordQueryBo dutyWorkRecordQueryBo);

    /**
     * 详情
     * @param id id
     * @return 详情
     */
    DutyWorkRecordVo queryById(Integer id);

    /**
     * 新增
     * @param bo 增改对象
     * @return 结果
     */
    Boolean insertByBo(DutyWorkRecordBo bo);


    /**
     * 更新
     * @param bo 增改对象
     * @return 结果
     */
    Boolean updateByBo(DutyWorkRecordBo bo);

    /**
     * 删除
     * @param id id
     * @return 结果
     */
    Boolean deleteById(Integer id);

}
