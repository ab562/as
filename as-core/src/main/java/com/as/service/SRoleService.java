package com.as.service;

import com.as.model.SRole;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface SRoleService extends IService<SRole> {

    public List<SRole> queryRoleListWithSelected(Integer uid);

    PageInfo<SRole> selectByPage(SRole role, int start, int length);

    /**
     * 删除角色 同时删除角色资源表中的数据
     * @param roleid
     */
    public void delRole(Integer roleid);
}
