package com.as.mapper;


import java.util.List;

import com.as.model.SRole;
import com.as.util.MyMapper;

public interface SRoleMapper extends MyMapper<SRole> {
    public List<SRole> queryRoleListWithSelected(Integer id);
}