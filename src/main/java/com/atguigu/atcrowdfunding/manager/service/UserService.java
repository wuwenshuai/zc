package com.atguigu.atcrowdfunding.manager.service;

import java.util.Map;

import com.atguigu.atcrowdfunding.bean.User;
import com.atguigu.atcrowdfunding.util.Page;
import com.atguigu.atcrowdfunding.util.PageResult;

public interface UserService {

    int saveUser(User user);

	User queryUserlogin(Map<String, Object> paramMap);

	//提供分页的方法
	//PageResult queryPageList(Integer pageno, Integer pagesize);

	//Page queryPage(Integer pageno, Integer pagesize);
	Page queryPage(Map<String,Object> map);
	int insertUser(User user);

	User selectUserByid(Integer id);

	//修该
	int editUser(User user);



}
