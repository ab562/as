package com.as.mapper;

import com.as.model.TTask;

public interface TTaskMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TTask record);

    int insertSelective(TTask record);

    TTask selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TTask record);

    int updateByPrimaryKey(TTask record);
}