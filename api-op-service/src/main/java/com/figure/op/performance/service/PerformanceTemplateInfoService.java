package com.figure.op.performance.service;

import com.figure.op.common.domain.PageQuery;
import com.figure.op.common.page.TableDataInfo;
import com.figure.op.performance.domain.PerformanceTemplateInfoDo;
import com.figure.op.performance.domain.bo.*;
import com.figure.op.performance.domain.vo.tempInfo.PerformanceTemplateInfoPageVo;
import com.figure.op.performance.domain.vo.tempInfo.PerformanceTemplateInfoVo;

import java.math.BigDecimal;
import java.util.List;

/**
 * 绩效模板明细Service接口
 *
 * @date 2023-08-25
 */
public interface PerformanceTemplateInfoService {

    /**
     * 查询绩效模板明细根据关联模板id
     *
     * @param id 绩效模板主键
     * @return 绩效模板明细
     */
    public List<PerformanceTemplateInfoDo> selectInfoByTemplateId(Long id);

    /**
     * 查询绩效模板明细
     *
     * @param id 绩效模板明细主键
     * @return 绩效模板明细
     */
    public PerformanceTemplateInfoDo selectPerformanceTemplateInfoById(Long id);

    /**
     * 查询绩效模板明细列表
     *
     * @param queryBo     查询对象
     * @param pageQuery 分页参数
     * @return 绩效模板明细集合
     */
    public TableDataInfo<PerformanceTemplateInfoPageVo> selectPerformanceTemplateInfoPage(PerformanceTemplateInfoQueryBo queryBo, PageQuery pageQuery);

    /**
     * 所有简化绩效模板明细
     * @return
     */
    public List<PerformanceTemplateInfoVo> selectSimpleList();

    /**
     * 所有父级简化绩效模板明细
     */
    public List<PerformanceTemplateInfoVo> selectSimpleFirstList(Long templateId);

    /**
     * 新增绩效模板明细
     *
     * @param reqVo 绩效模板明细
     * @return 结果
     */
    public int create(PerformanceTemplateInfoVo reqVo);

    /**
     * 批量新增绩效模板明细
     *
     * @param reqVo 绩效模板明细
     * @return 结果
     */
    public Boolean batchCreate(PerformanceTemplateInfoCreateBo reqVo);

    /**
     * 批量编辑绩效模板明细
     */
    public boolean batchUpdate(PerformanceTemplateInfoUpdateBo reqVo);

    /**
     * 修改绩效模板明细
     *
     * @param reqVo 绩效模板明细
     * @return 结果
     */
    public boolean update(PerformanceTemplateInfoVo reqVo);

    /**
     * 设置得分
     * @param id 分项明细id
     * @param score 得分
     * @return
     */
    public void setScore(Long id , BigDecimal score);

    /**
     * 批量删除绩效模板明细
     *
     * @param ids 需要删除的绩效模板明细主键集合
     * @return 结果
     */
    public int deletePerformanceTemplateInfoByIds(Long[] ids);

    /**
     * 删除绩效模板明细信息
     *
     * @param id 绩效模板明细主键
     * @return 结果
     */
    public int deletePerformanceTemplateInfoById(Long id);


    /**
     * 校验模板明细是否完整
     * @param templateId 模板ID
     * @return 结果
     */
    Boolean validateTemplateInfoComplete(Long templateId);

    /**
     * 创建父级模板明细
     * @param bo 增改对象
     * @return 结果
     */
    Boolean createParent(PerformanceTemplateInfoFirstBo bo);

    /**
     * 修改父级模板明细
     * @param bo 增改对象
     * @return 结果
     */
    Boolean updateParent(PerformanceTemplateInfoFirstBo bo);


    /**
     * 创建子级模板明细
     * @param bo 增改对象
     * @return 结果
     */
    Boolean createChild(PerformanceTemplateInfoSecondBo bo);

    /**
     * 修改子级模板明细
     * @param bo 增改对象
     * @return 结果
     */
    Boolean updateChild(PerformanceTemplateInfoSecondBo bo);

}
