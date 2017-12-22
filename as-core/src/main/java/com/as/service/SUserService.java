package com.as.service;

import java.util.Map;

import com.as.model.SUser;
import com.github.pagehelper.PageInfo;

/**
 * Created by yangqj on 2017/4/21.
 */
public interface SUserService extends IService<SUser>{
    PageInfo<SUser> selectByPage(SUser user, int start, int length);

    SUser selectByUsername(String username);

    void delUser(Integer userid);

	Map<String, Object> login(SUser sUser);

	void regist(SUser sUser);

}
