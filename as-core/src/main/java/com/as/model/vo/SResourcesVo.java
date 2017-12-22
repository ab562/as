package com.as.model.vo;

import java.util.List;

import com.as.model.SResources;


public class SResourcesVo extends SResources {
	private List<SResourcesVo> childs;
	private Integer userid;
	
	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public List<SResourcesVo> getChilds() {
		return childs;
	}

	public void setChilds(List<SResourcesVo> childs) {
		this.childs = childs;
	}
	
}
