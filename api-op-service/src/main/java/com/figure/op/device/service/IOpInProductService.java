package com.figure.op.device.service;

import com.figure.op.common.domain.PageQuery;
import com.figure.op.common.page.TableDataInfo;
import com.figure.op.device.domain.OpInProduct;
import com.figure.op.device.domain.bo.OpInProductBo;
import com.figure.op.device.domain.bo.OpInProductQueryBo;
import com.figure.op.device.domain.vo.OpInProductListVo;
import com.figure.op.device.domain.vo.OpInProductPageVo;
import com.figure.op.device.domain.vo.OpInProductVo;

import java.util.List;

/**
 * 品牌服务接口
 * @author fsn
 */
public interface IOpInProductService {

    /**
     * 分页列表1
     * @param queryBo 查询对象
     * @param pageQuery 分页对象
     * @return 分页列表
     */
    TableDataInfo<OpInProductPageVo> page(OpInProductQueryBo queryBo, PageQuery pageQuery);

    /**
     * 分页列表2（多表）
     * @param queryBo 查询对象
     * @param pageQuery 分页对象
     * @return 分页列表
     */
    TableDataInfo<OpInProductPageVo> queryPageList2(OpInProductQueryBo queryBo, PageQuery pageQuery);

    /**
     * 全部列表
     * @return 全部列表
     */
    List<OpInProductListVo> queryList(OpInProduct OpInProduct);

    /**
     * 详情
     * @param id id
     * @return 详情
     */
    OpInProductVo queryById(Integer id);

    /**
     * 新增
     * @param bo 增改对象
     * @return 结果
     */
    Boolean insertByBo(OpInProductBo bo);


    /**
     * 更新
     * @param bo 增改对象
     * @return 结果
     */
    Boolean updateByBo(OpInProductBo bo);

    /**
     * 删除
     * @param id id
     * @return 结果
     */
    Boolean deleteById(Integer id);

}
