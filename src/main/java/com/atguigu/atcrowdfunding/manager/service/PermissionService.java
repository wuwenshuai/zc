package com.atguigu.atcrowdfunding.manager.service;

import java.util.List;

import com.atguigu.atcrowdfunding.bean.Permission;

public interface PermissionService {

	Permission getRootPermission();

	List<Permission> getChildrenPermissionByPid(Integer id);

	List<Permission> queryAllPermission();

	int savePermission(Permission permission);

	Permission getPermissionById(Integer id);

	int updatePermission(Permission permission);

	int deletePermission(Integer id);

	List<Integer> queryPermissionidsByRoleid(Integer roleid);

}
