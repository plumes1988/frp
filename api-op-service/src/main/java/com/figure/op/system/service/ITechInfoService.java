package com.figure.op.system.service;

import com.figure.op.common.domain.PageQuery;
import com.figure.op.common.page.TableDataInfo;
import com.figure.op.system.domain.TechInfo;
import com.figure.op.system.domain.bo.TechInfoBo;
import com.figure.op.system.domain.bo.TechInfoQueryBo;
import com.figure.op.system.domain.vo.TechInfoListVo;
import com.figure.op.system.domain.vo.TechInfoPageVo;
import com.figure.op.system.domain.vo.TechInfoVo;

import java.util.List;

/**
 * 品牌服务接口
 * @author fsn
 */
public interface ITechInfoService {
    
    /**
     * 全部列表
     * @return 全部列表
     */
    List<TechInfoListVo> queryList(TechInfo TechInfo);

    /**
     * 详情
     * @param id id
     * @return 详情
     */
    TechInfoVo queryById(Integer id);

    /**
     * 新增
     * @param bo 增改对象
     * @return 结果
     */
    Boolean insertByBo(TechInfoBo bo);


    /**
     * 更新
     * @param bo 增改对象
     * @return 结果
     */
    Boolean updateByBo(TechInfoBo bo);

    /**
     * 删除
     * @param id id
     * @return 结果
     */
    Boolean deleteById(Integer id);

    TableDataInfo<TechInfoPageVo> page(TechInfoQueryBo queryBo, PageQuery pageQuery);

}
