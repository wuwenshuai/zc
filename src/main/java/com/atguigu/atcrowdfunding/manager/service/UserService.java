package com.atguigu.atcrowdfunding.manager.service;

import java.util.List;
import java.util.Map;

import com.atguigu.atcrowdfunding.bean.Permission;
import com.atguigu.atcrowdfunding.bean.Role;
import com.atguigu.atcrowdfunding.bean.User;
import com.atguigu.atcrowdfunding.util.Data;
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
	//删除用户
	int deleteUser(Integer id);
	//批量删除
	int deletes(Integer[] ids);


    List<Integer> queryRoleIdByUserId(Integer id);

	List<Role> queryAllRole();

	//添加角色关系表
	void saveUserRoleRelationship(Data data,Integer userid);

	//删除用户角色关系
	void  deleteUserRoleRelationship(Data data,Integer userid);

	List<Permission> queryPermissionByUserid(Integer id);
}
