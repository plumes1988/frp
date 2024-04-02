package com.figure.op.system.service;

import com.figure.op.system.domain.SysDept;
import com.figure.op.system.domain.bo.SysDeptBo;
import com.figure.op.system.domain.vo.SysDeptListVo;
import com.figure.op.system.domain.vo.SysDeptVo;

import java.util.List;

/**
 * 品牌服务接口
 * @author fsn
 */
public interface ISysDeptService {
    
    /**
     * 全部列表
     * @return 全部列表
     */
    List<SysDeptListVo> queryList(SysDept SysDept);

    /**
     * 详情
     * @param id id
     * @return 详情
     */
    SysDeptVo queryById(Integer id);

    /**
     * 新增
     * @param bo 增改对象
     * @return 结果
     */
    Boolean insertByBo(SysDeptBo bo);


    /**
     * 更新
     * @param bo 增改对象
     * @return 结果
     */
    Boolean updateByBo(SysDeptBo bo);

    /**
     * 删除
     * @param id id
     * @return 结果
     */
    Boolean deleteById(Integer id);

}
