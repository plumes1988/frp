package com.figure.op.duty.service;

import com.figure.op.common.domain.PageQuery;
import com.figure.op.common.page.TableDataInfo;
import com.figure.op.duty.domain.bo.DutyInfoBo;
import com.figure.op.duty.domain.bo.DutyInfoQueryBo;
import com.figure.op.duty.domain.vo.DutyInfoListVo;
import com.figure.op.duty.domain.vo.DutyInfoPageVo;
import com.figure.op.duty.domain.vo.DutyInfoVo;

import java.util.List;

/**
 * 品牌服务接口
 * @author fsn
 */
public interface IDutyInfoService {
    
    /**
     * 全部列表
     * @return 全部列表
     */
    List<DutyInfoListVo> queryList();

    /**
     * 详情
     * @param id id
     * @return 详情
     */
    DutyInfoVo queryById(Integer id);

    /**
     * 新增
     * @param bo 增改对象
     * @return 结果
     */
    Boolean insertByBo(DutyInfoBo bo);


    /**
     * 更新
     * @param bo 增改对象
     * @return 结果
     */
    Boolean updateByBo(DutyInfoBo bo);

    /**
     * 删除
     * @param id id
     * @return 结果
     */
    Boolean deleteById(Integer id);

    TableDataInfo<DutyInfoPageVo> page(DutyInfoQueryBo queryBo, PageQuery pageQuery);

}
