package com.as.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.as.mapper.SRoleMapper;
import com.as.mapper.SRoleResourcesMapper;
import com.as.model.SRole;
import com.as.model.SRoleResources;
import com.as.service.SRoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

@Service("roleService")
public class SRoleServiceImpl extends BaseService<SRole> implements SRoleService{

    @Resource
    private SRoleMapper roleMapper;
    @Resource
    private SRoleResourcesMapper roleResourcesMapper;

    @Override
    public List<SRole> queryRoleListWithSelected(Integer uid) {
        return roleMapper.queryRoleListWithSelected(uid);
    }

    @Override
    public PageInfo<SRole> selectByPage(SRole role, int start, int length) {
        int page = start/length+1;
        Example example = new Example(SRole.class);
        //分页查询
        PageHelper.startPage(page, length);
        List<SRole> rolesList = selectByExample(example);
        return new PageInfo<>(rolesList);
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED,readOnly=false,rollbackFor={Exception.class})
    public void delRole(Integer roleid) {
        //删除角色
        mapper.deleteByPrimaryKey(roleid);
        //删除角色资源
        Example example = new Example(SRoleResources.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("roleid",roleid);
        roleResourcesMapper.deleteByExample(example);

    }
}
