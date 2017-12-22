package com.as.mapper;

import com.as.model.SUser;
import com.as.util.MyMapper;

public interface SUserMapper extends MyMapper<SUser> {

	SUser login(SUser sUser);
}