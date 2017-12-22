package com.as.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.as.model.SUserRole;
import com.as.service.SUserRoleService;

import tk.mybatis.mapper.entity.Example;

@Service("serRoleService")
public class SUserRoleServiceImpl extends BaseService<SUserRole> implements SUserRoleService {


    @Override
    @Transactional(propagation= Propagation.REQUIRED,readOnly=false,rollbackFor={Exception.class})
    public void addUserRole(SUserRole userRole) {
        //删除
        Example example = new Example(SUserRole.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userid",userRole.getUserid());
        mapper.deleteByExample(example);
        //添加
        String[] roleids = userRole.getRoleid().split(",");
        for (String roleId : roleids) {
            SUserRole u = new SUserRole();
            u.setUserid(userRole.getUserid());
            u.setRoleid(roleId);
            mapper.insert(u);
        }

    }
}
