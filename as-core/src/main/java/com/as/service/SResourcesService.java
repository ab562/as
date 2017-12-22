package com.as.service;

import com.as.model.SResources;
import com.as.model.vo.SResourcesVo;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by yangqj on 2017/4/25.
 */
public interface SResourcesService extends IService<SResources> {
    PageInfo<SResources> selectByPage(SResources resources, int start, int length);

    public List<SResources> queryAll();

    public List<SResources> loadUserResources(Map<String,Object> map);

    public List<SResources> queryResourcesListWithSelected(Integer rid);

	List<SResourcesVo> loadUserResources2(SResourcesVo re);
}
