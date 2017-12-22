package com.as.mapper;

import com.as.model.DDatadict;

public interface DDatadictMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DDatadict record);

    int insertSelective(DDatadict record);

    DDatadict selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DDatadict record);

    int updateByPrimaryKey(DDatadict record);
}