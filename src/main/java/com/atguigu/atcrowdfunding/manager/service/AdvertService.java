package com.atguigu.atcrowdfunding.manager.service;

import java.util.Map;

import com.atguigu.atcrowdfunding.bean.Advert;
import com.atguigu.atcrowdfunding.util.Data;
import com.atguigu.atcrowdfunding.util.Page;

public interface AdvertService {
	public Advert queryAdvert(Map<String, Object> advertMap);

	public Page<Advert> pageQuery(Map<String, Object> advertMap);

	public int queryCount(Map<String, Object> advertMap);

	public int insertAdvert(Advert advert);

	public Advert queryById(Integer id);

	public int updateAdvert(Advert advert);

	public int deleteAdvert(Integer id);

	public int deleteAdverts(Data ds);
}
