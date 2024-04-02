package com.figure.op.device.service;

import com.figure.op.common.domain.PageQuery;
import com.figure.op.common.page.TableDataInfo;
import com.figure.op.device.domain.OpProductInfo;
import com.figure.op.device.domain.bo.OpProductInfoBo;
import com.figure.op.device.domain.bo.OpProductInfoQueryBo;
import com.figure.op.device.domain.vo.OpProductInfoListVo;
import com.figure.op.device.domain.vo.OpProductInfoPageVo;
import com.figure.op.device.domain.vo.OpProductInfoVo;

import java.util.List;

/**
 * 品牌服务接口
 * @author fsn
 */
public interface IOpProductInfoService {

    /**
     * 分页列表1
     * @param queryBo 查询对象
     * @param pageQuery 分页对象
     * @return 分页列表
     */
    TableDataInfo<OpProductInfoPageVo> page(OpProductInfoQueryBo queryBo, PageQuery pageQuery);

    /**
     * 分页列表2（多表）
     * @param queryBo 查询对象
     * @param pageQuery 分页对象
     * @return 分页列表
     */
    TableDataInfo<OpProductInfoPageVo> queryPageList2(OpProductInfoQueryBo queryBo, PageQuery pageQuery);

    /**
     * 全部列表
     * @return 全部列表
     */
    List<OpProductInfoListVo> queryList(OpProductInfo OpProductInfo);

    /**
     * 详情
     * @param id id
     * @return 详情
     */
    OpProductInfoVo queryById(Integer id);

    /**
     * 新增
     * @param bo 增改对象
     * @return 结果
     */
    Boolean insertByBo(OpProductInfoBo bo);


    /**
     * 更新
     * @param bo 增改对象
     * @return 结果
     */
    Boolean updateByBo(OpProductInfoBo bo);

    /**
     * 删除
     * @param id id
     * @return 结果
     */
    Boolean deleteById(Integer id);



}
