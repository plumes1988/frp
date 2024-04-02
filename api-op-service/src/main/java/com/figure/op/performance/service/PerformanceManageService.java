package com.figure.op.performance.service;

import com.figure.op.common.domain.PageQuery;
import com.figure.op.common.page.TableDataInfo;
import com.figure.op.performance.domain.PerformanceManageDO;
import com.figure.op.performance.domain.bo.PerformanceManageScoreReqBo;
import com.figure.op.performance.domain.vo.date.PerformanceDataExcelVo;
import com.figure.op.performance.domain.vo.date.PerformanceDataTreeVo;
import com.figure.op.performance.domain.vo.manage.*;

import java.io.IOException;
import java.util.*;
import javax.servlet.http.HttpServletResponse;
import javax.validation.*;

/**
 * 绩效评分管理 Service 接口
 *
 * @author 管理员
 */
public interface PerformanceManageService {

    /**
     * 打分
     *
     * @param scoreReqVO 打分请求实体类
     * @return
     */
    Boolean score(PerformanceManageScoreReqBo scoreReqVO);

    /**
     * 获取评价打分结果
     *
     * @param id
     * @return
     */
    PerformanceManageScoreRespVO scoreRes(Long id);

    /**
     * 创建绩效评分管理
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createManage(@Valid PerformanceManageCreateReqVO createReqVO);

    /**
     * 更新绩效评分管理
     *
     * @param updateReqVO 更新信息
     */
    void updateManage(@Valid PerformanceManageUpdateReqVO updateReqVO);

    /**
     * 删除绩效评分管理
     *
     * @param id 编号
     */
    void deleteManage(Long id);

    /**
     * 获得绩效评分管理
     *
     * @param id 编号
     * @return 绩效评分管理
     */
    PerformanceManageDO getManage(Long id);

    /**
     * 获得绩效评分管理列表
     *
     * @param id 编号
     * @return 绩效评分管理列表
     */
    List<PerformanceDataTreeVo> getDateListById(Long id);


    List<PerformanceDataTreeVo> getDateTreeById(Long id);

    List<PerformanceDataExcelVo> getDateList(Long id);


    /**
     * 导出
     *
     * @param id 绩效评分id
     * @throws IOException
     */
    void export(HttpServletResponse response, Long id) throws IOException;
    void exportV2(HttpServletResponse response, Long id) throws IOException;



    /**
     * 获得绩效评分管理列表
     *
     * @param ids 编号
     * @return 绩效评分管理列表
     */
    List<PerformanceManageDO> getManageList(Collection<Long> ids);

    /**
     * 获得绩效评分管理分页
     *
     * @param pageReqVO 分页查询
     * @return 绩效评分管理分页
     */
    TableDataInfo<PerformanceManagePageReqVO> getManagePage(PerformanceManagePageReqVO pageReqVO, PageQuery pageQuery);

    /**
     * 根据关联模板id获取绩效列表
     *
     * @param templateId 关联模板id
     * @return 绩效评分管理分页
     */
    List<PerformanceManageDO> selectInfoByTemplateId(Long templateId);


}
