package com.figure.core.repository.sys;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.figure.core.model.sys.SysUserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Component
@Mapper

public interface TUserInfoMapper  extends BaseMapper<SysUserInfo> {
    
    @Select("SELECT * FROM t_user_info WHERE userId = #{userId}")
    SysUserInfo findByUserId(Integer userId);

}
