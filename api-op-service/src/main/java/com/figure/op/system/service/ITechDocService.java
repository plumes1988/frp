package com.figure.op.system.service;

import com.figure.op.common.domain.PageQuery;
import com.figure.op.common.page.TableDataInfo;
import com.figure.op.system.domain.TechDoc;
import com.figure.op.system.domain.bo.TechDocBo;
import com.figure.op.system.domain.bo.TechDocQueryBo;
import com.figure.op.system.domain.vo.TechDocListVo;
import com.figure.op.system.domain.vo.TechDocPageVo;
import com.figure.op.system.domain.vo.TechDocVo;

import java.util.List;

/**
 * 品牌服务接口
 * @author fsn
 */
public interface ITechDocService {
    
    /**
     * 全部列表
     * @return 全部列表
     */
    List<TechDocListVo> queryList(TechDoc TechDoc);

    /**
     * 详情
     * @param id id
     * @return 详情
     */
    TechDocVo queryById(Integer id);

    /**
     * 新增
     * @param bo 增改对象
     * @return 结果
     */
    Boolean insertByBo(TechDocBo bo);


    /**
     * 更新
     * @param bo 增改对象
     * @return 结果
     */
    Boolean updateByBo(TechDocBo bo);

    /**
     * 删除
     * @param id id
     * @return 结果
     */
    Boolean deleteById(Integer id);

    TableDataInfo<TechDocPageVo> page(TechDocQueryBo queryBo, PageQuery pageQuery);

}
