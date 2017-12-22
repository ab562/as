package com.as.mapper;

import com.as.model.SUserLog;

public interface SUserLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SUserLog record);

    int insertSelective(SUserLog record);

    SUserLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SUserLog record);

    int updateByPrimaryKey(SUserLog record);
}