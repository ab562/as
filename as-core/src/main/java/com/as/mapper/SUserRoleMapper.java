package com.as.mapper;


import java.util.List;

import com.as.model.SUserRole;
import com.as.util.MyMapper;

public interface SUserRoleMapper extends MyMapper<SUserRole> {
    public List<Integer> findUserIdByRoleId(Integer roleId);

	public void deleteByUserId(Integer userid);

	
}