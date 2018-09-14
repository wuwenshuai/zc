package com.atguigu.atcrowdfunding.manager.service;

import java.util.Map;

import com.atguigu.atcrowdfunding.bean.User;

public interface UserService {

	User queryUserlogin(Map<String, Object> paramMap);

}
