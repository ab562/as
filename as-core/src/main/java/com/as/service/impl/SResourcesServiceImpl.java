package com.as.service.impl;

import com.as.mapper.SResourcesMapper;
import com.as.model.SResources;
import com.as.model.vo.SResourcesVo;
import com.as.service.SResourcesService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("resourcesService")
public class SResourcesServiceImpl extends BaseService<SResources> implements SResourcesService {
   @Resource
    private SResourcesMapper resourcesMapper;

    @Override
    public PageInfo<SResources> selectByPage(SResources resources, int start, int length) {
        int page = start/length+1;
        Example example = new Example(SResources.class);
        //分页查询
        PageHelper.startPage(page, length);
        List<SResources> userList = selectByExample(example);
        return new PageInfo<>(userList);
    }

    @Override
    public List<SResources> queryAll(){
        return resourcesMapper.queryAll();
    }

    @Override
    @Cacheable(cacheNames="resources",key="#map['userid'].toString()+#map['type']")
    public List<SResources> loadUserResources(Map<String, Object> map) {
        return resourcesMapper.loadUserResources(map);
    }

    @Override
    public List<SResources> queryResourcesListWithSelected(Integer rid) {
        return resourcesMapper.queryResourcesListWithSelected(rid);
    }

	@Override
	public List<SResourcesVo> loadUserResources2(SResourcesVo re) {
		return resourcesMapper.loadUserResources2(re);
	}
}
