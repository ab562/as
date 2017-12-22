package com.as.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.as.mapper.SUserMapper;
import com.as.mapper.SUserRoleMapper;
import com.as.model.SUser;
import com.as.model.SUserRole;
import com.as.service.SUserService;
import com.as.util.HmacSHA256Utils;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.algorithms.Algorithm;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

@Service("userService")
public class SUserServiceImpl extends BaseService<SUser> implements SUserService{
    @Resource
    private SUserRoleMapper sUserRoleMapper;
    @Resource
    private SUserMapper sUserMapper;

    @Override
    public PageInfo<SUser> selectByPage(SUser user, int start, int length) {
        int page = start/length+1;
        Example example = new Example(SUser.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtil.isNotEmpty(user.getUsername())) {
            criteria.andLike("username", "%" + user.getUsername() + "%");
        }
        if (user.getId() != null) {
            criteria.andEqualTo("id", user.getId());
        }
        if (user.getStatus() != null) {
            criteria.andEqualTo("status", user.getStatus());
        }
        criteria.andEqualTo("userType",0);
        //分页查询
        PageHelper.startPage(page, length);
        List<SUser> userList = selectByExample(example);
        return new PageInfo<>(userList);
    }

    @Override
    public SUser selectByUsername(String username) {
        Example example = new Example(SUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username",username);
        List<SUser> userList = selectByExample(example);
        if(userList.size()>0){
            return userList.get(0);
        }
            return null;
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED,readOnly=false,rollbackFor={Exception.class})
    public void delUser(Integer userid) {
        //删除用户表
        mapper.deleteByPrimaryKey(userid);
        //删除用户角色表
        sUserRoleMapper.deleteByUserId(userid);
    }

	@Override
	public Map<String, Object> login(SUser sUser) {
		sUser.setStatus(1);
		sUser.setUserType(1);
		SUser user=sUserMapper.login(sUser);
		Map<String, Object> map=null;
		if(user!=null){
			Builder cr = JWT.create();
			cr.withClaim("id",user.getId());
			cr.withClaim("lat",System.currentTimeMillis());
			Algorithm algorithm=null;
			try {
				algorithm = Algorithm.HMAC256(HmacSHA256Utils.KEY);
			} catch (IllegalArgumentException | UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			String token = cr.sign(algorithm);
			map=new HashMap<>();
			map.put("token", token);
			map.put("sUserId", user.getId());
		}
		return  map;
	}

	@Override
	public void regist(SUser sUser) {
		// TODO Auto-generated method stub
		
	}
	
}
