package com.as.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.as.model.SResources;
import com.as.model.vo.SResourcesVo;
import com.as.service.SResourcesService;
import com.as.shiro.ShiroService;
import com.github.pagehelper.PageInfo;

/**
 * Created by yangqj on 2017/4/25.
 */
@RestController
@RequestMapping("/resources")
public class SResourcesController {

    @Resource
    private SResourcesService resourcesService;
    @Resource
    private ShiroService shiroService;

    @RequestMapping
    public Map<String,Object> getAll(SResources resources, String draw,
                                     @RequestParam(required = false, defaultValue = "1") int start,
                                     @RequestParam(required = false, defaultValue = "10") int length){
        Map<String,Object> map = new HashMap<>();
        PageInfo<SResources> pageInfo = resourcesService.selectByPage(resources, start, length);
        map.put("draw",draw);
        map.put("recordsTotal",pageInfo.getTotal());
        map.put("recordsFiltered",pageInfo.getTotal());
        map.put("data", pageInfo.getList());
        return map;
    }

    @RequestMapping("/resourcesWithSelected")
    public List<SResources> resourcesWithSelected(Integer rid){
        return resourcesService.queryResourcesListWithSelected(rid);
    }

    @SuppressWarnings("unused")
	@RequestMapping("/loadMenu")
    public  List<SResourcesVo>  loadMenu(){
        Integer userid = (Integer) SecurityUtils.getSubject().getSession().getAttribute("userSessionId");
        SResourcesVo rev=new SResourcesVo();
        rev.setType(1);
        rev.setParentid(0);
        rev.setUserid(userid);
        List<SResourcesVo> sResourcesVos= resourcesService.loadUserResources2(rev);
        for( SResourcesVo re:sResourcesVos){
        	rev.setParentid(re.getId());
        	 List<SResourcesVo> childs= resourcesService.loadUserResources2(rev);
        	re.setChilds(childs);
        	
        }
        return sResourcesVos;
    }

    @CacheEvict(cacheNames="resources", allEntries=true)
    @RequestMapping(value = "/add")
    public String add(SResources resources){
        try{
            resourcesService.save(resources);
            //更新权限
            shiroService.updatePermission();
            return "success";
        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }
    }
    @CacheEvict(cacheNames="resources", allEntries=true)
    @RequestMapping(value = "/delete")
    public String delete(Integer id){
        try{
            resourcesService.delete(id);
            //更新权限
            shiroService.updatePermission();
            return "success";
        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }
    }
}
