package com.as.mapper;

import com.as.model.SRoleResources;
import com.as.util.MyMapper;

public interface SRoleResourcesMapper extends MyMapper<SRoleResources> {

	void deletebyRoleId(Integer roleid);

	void add(SRoleResources r);
}