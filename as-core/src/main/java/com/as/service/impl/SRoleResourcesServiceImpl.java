package com.as.service.impl;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.as.mapper.SRoleResourcesMapper;
import com.as.model.SRoleResources;
import com.as.service.SRoleResourcesService;

import tk.mybatis.mapper.entity.Example;


@Service("roleResourcesService")
public class SRoleResourcesServiceImpl extends BaseService<SRoleResources> implements SRoleResourcesService {
	@Resource
	private SRoleResourcesMapper sRoleResourcesMapper;

	@Override
	// 更新权限
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
	@CacheEvict(cacheNames = "resources", allEntries = true)
	public void addRoleResources(SRoleResources sRoleResources) {
		// 删除
		sRoleResourcesMapper.deletebyRoleId(sRoleResources.getRoleid());
		// 添加
		if (!StringUtils.isEmpty(sRoleResources.getResourcesid())) {
			String[] resourcesArr = sRoleResources.getResourcesid().split(",");
			for (String resourcesId : resourcesArr) {
				SRoleResources r = new SRoleResources();
				r.setRoleid(sRoleResources.getRoleid());
				r.setResourcesid(resourcesId);
				sRoleResourcesMapper.add(r);
			}
		}

		// List<Integer> userIds=
		// userRoleMapper.findUserIdByRoleId(roleResources.getRoleid());
		// 更新当前登录的用户的权限缓存
		// shiroService.clearUserAuthByUserId(userIds);

	}
}
