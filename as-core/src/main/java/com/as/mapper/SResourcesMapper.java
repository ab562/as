package com.as.mapper;


import java.util.List;
import java.util.Map;

import com.as.model.SResources;
import com.as.model.vo.SResourcesVo;
import com.as.util.MyMapper;

public interface SResourcesMapper extends MyMapper<SResources> {

    public List<SResources> queryAll();

    public List<SResources> loadUserResources(Map<String,Object> map);

    public List<SResources> queryResourcesListWithSelected(Integer rid);

	public List<SResourcesVo> loadUserResources2(SResourcesVo re);
}