package com.atguigu.atcrowdfunding.manager.service;

import java.util.List;
import java.util.Map;

import com.atguigu.atcrowdfunding.bean.Role;
import com.atguigu.atcrowdfunding.util.Data;
import com.atguigu.atcrowdfunding.util.Page;
public interface RoleService {



	public Page<Role> pageQuery(Map<String, Object> paramMap);

	public int queryCount(Map<String, Object> paramMap);

	public void saveRole(Role user);

	public Role getRole(Integer id);

	public int updateRole(Role user);

	public int deleteRole(Integer uid);

	public int batchDeleteRole(Integer[] uid);

	public int batchDeleteRole(Data datas);

	public List<Role> queryAllRole();

	public List<Integer> queryRoleidByUserid(Integer id);

	/*public void doAssignRoleByUserid(Integer userid, Integer[] ids);

	public void doUnAssignRoleByUserid(Integer userid, Integer[] ids);*/


	public void doAssignRoleByUserid(Integer userid, List<Integer> ids);

	public void doUnAssignRoleByUserid(Integer userid, List<Integer> ids);

	public int saveRolePermissionRelationship(Integer roleid, Data datas);
}
