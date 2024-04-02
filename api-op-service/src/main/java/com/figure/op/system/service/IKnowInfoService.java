package com.figure.op.system.service;

import com.figure.op.common.domain.PageQuery;
import com.figure.op.common.page.TableDataInfo;
import com.figure.op.system.domain.KnowInfo;
import com.figure.op.system.domain.bo.KnowInfoBo;
import com.figure.op.system.domain.bo.KnowInfoQueryBo;
import com.figure.op.system.domain.vo.KnowInfoListVo;
import com.figure.op.system.domain.vo.KnowInfoPageVo;
import com.figure.op.system.domain.vo.KnowInfoVo;

import java.util.List;

/**
 * 品牌服务接口
 * @author fsn
 */
public interface IKnowInfoService {
    
    /**
     * 全部列表
     * @return 全部列表
     */
    List<KnowInfoListVo> queryList(KnowInfo KnowInfo);

    /**
     * 详情
     * @param id id
     * @return 详情
     */
    KnowInfoVo queryById(Integer id);

    /**
     * 新增
     * @param bo 增改对象
     * @return 结果
     */
    Boolean insertByBo(KnowInfoBo bo);


    /**
     * 更新
     * @param bo 增改对象
     * @return 结果
     */
    Boolean updateByBo(KnowInfoBo bo);

    /**
     * 删除
     * @param id id
     * @return 结果
     */
    Boolean deleteById(Integer id);

    TableDataInfo<KnowInfoPageVo> page(KnowInfoQueryBo queryBo, PageQuery pageQuery);

}
