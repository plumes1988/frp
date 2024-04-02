package com.figure.op.system.service;

import com.figure.op.system.domain.SysUserInfo;
import com.figure.op.system.domain.bo.SysUserInfoBo;
import com.figure.op.system.domain.vo.SysUserInfoListVo;
import com.figure.op.system.domain.vo.SysUserInfoVo;

import java.util.List;

/**
 * 品牌服务接口
 * @author fsn
 */
public interface ISysUserInfoService {
    
    /**
     * 全部列表
     * @return 全部列表
     */
    List<SysUserInfoListVo> queryList(SysUserInfo SysUserInfo);

    /**
     * 详情
     * @param id id
     * @return 详情
     */
    SysUserInfoVo queryById(Integer id);

    /**
     * 新增
     * @param bo 增改对象
     * @return 结果
     */
    Boolean insertByBo(SysUserInfoBo bo);


    /**
     * 更新
     * @param bo 增改对象
     * @return 结果
     */
    Boolean updateByBo(SysUserInfoBo bo);

    /**
     * 删除
     * @param id id
     * @return 结果
     */
    Boolean deleteById(Integer id);

}
