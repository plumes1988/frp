package com.figure.op.videoplay.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.figure.op.videoplay.domain.WarningRecord;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
public interface WarningRecordMapper extends BaseMapper<WarningRecord> {
}
