package com.figure.op.device.service;

import com.figure.op.common.domain.PageQuery;
import com.figure.op.common.page.TableDataInfo;
import com.figure.op.device.domain.OpOutProduct;
import com.figure.op.device.domain.bo.OpOutProductBo;
import com.figure.op.device.domain.bo.OpOutProductQueryBo;
import com.figure.op.device.domain.bo.OpOutProductReturnBo;
import com.figure.op.device.domain.vo.OpOutProductListVo;
import com.figure.op.device.domain.vo.OpOutProductPageVo;
import com.figure.op.device.domain.vo.OpOutProductReturnListVo;
import com.figure.op.device.domain.vo.OpOutProductVo;

import java.util.List;

/**
 * 品牌服务接口
 * @author fsn
 */
public interface IOpOutProductService {

    /**
     * 分页列表1
     * @param queryBo 查询对象
     * @param pageQuery 分页对象
     * @return 分页列表
     */
    TableDataInfo<OpOutProductPageVo> page(OpOutProductQueryBo queryBo, PageQuery pageQuery);

    /**
     * 分页列表2（多表）
     * @param queryBo 查询对象
     * @param pageQuery 分页对象
     * @return 分页列表
     */
    TableDataInfo<OpOutProductPageVo> queryPageList2(OpOutProductQueryBo queryBo, PageQuery pageQuery);

    /**
     * 全部列表
     * @return 全部列表
     */
    List<OpOutProductListVo> queryList(OpOutProduct OpOutProduct);

    /**
     * 详情
     * @param id id
     * @return 详情
     */
    OpOutProductVo queryById(Integer id);

    /**
     * 新增
     * @param bo 增改对象
     * @return 结果
     */
    Boolean insertByBo(OpOutProductBo bo);


    /**
     * 更新
     * @param bo 增改对象
     * @return 结果
     */
    Boolean updateByBo(OpOutProductBo bo);

    /**
     * 删除
     * @param id id
     * @return 结果
     */
    Boolean deleteById(Integer id);


    /**
     * 归还登记
     * @param returnBo 归还对象
     * @return 结果
     */
    Boolean returnProduct(OpOutProductReturnBo returnBo);


    List<OpOutProductReturnListVo> getReturnTimelineList(Integer outId);

}
