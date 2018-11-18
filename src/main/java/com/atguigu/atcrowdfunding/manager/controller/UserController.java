package com.atguigu.atcrowdfunding.manager.controller;

import com.atguigu.atcrowdfunding.bean.Role;
import com.atguigu.atcrowdfunding.bean.User;
import com.atguigu.atcrowdfunding.manager.service.UserService;
import com.atguigu.atcrowdfunding.util.AjaxResult;
import com.atguigu.atcrowdfunding.util.Data;
import com.atguigu.atcrowdfunding.util.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: wuwenshuai
 * @Date: 2018/9/18 16:30
 * @Description:
 */

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/index")
    public String index() {
        return "user/index";
    }

    //异步请求
	@ResponseBody
	@RequestMapping("/doIndex")
	public Object index(@RequestParam(value="pageno",required=false,defaultValue="1") Integer pageno,
				@RequestParam(value="pagesize",required=false,defaultValue="10") Integer pagesize,String queryText){
		AjaxResult result = new AjaxResult();
		//封装请求参数的map
		Map<String,Object> map = new HashMap<>();
		map.put("pageno",pageno);
		map.put("pagesize",pagesize);
		if (StringUtils.isNotEmpty(queryText)){
			//清除包含%的问题
			if (queryText.contains("%")){
				queryText = queryText.replaceAll("%","\\\\%");
			}
			map.put("queryText",queryText);
		}
		try {
			Page page = userService.queryPage(map);
			result.setSuccess(true);
			result.setPage(page);
		} catch (Exception e) {
			result.setSuccess(false);
			e.printStackTrace();
			result.setMessage("查询数据失败!");
		}

		return result; //将对象序列化为JSON字符串,以流的形式返回.
	}

	//新增用户
	@RequestMapping("/toAdd")
	public String toAdd(){
    	return "user/add";
	}

	@RequestMapping("/add")
	@ResponseBody
	public AjaxResult add(User user){
    	AjaxResult result = new AjaxResult();
    	try {
			int i = userService.insertUser(user);
			if (i>0){
				result.setSuccess(true);
				result.setMessage("success");
			}else {
				result.setSuccess(false);
				result.setMessage("error");
			}
		}catch (Exception e){
			result.setSuccess(false);
			result.setMessage("error");
		}
		return result;

	}

	//去修改页面

	@RequestMapping("/toEdit")
	public String toEdit(Integer id ,Map map){
    	//回显数据
		User user = userService.selectUserByid(id);
		map.put("user",user);
		return "user/edit";
	}

	//修改
	@RequestMapping("/edit")
	@ResponseBody
	public AjaxResult edit(User user){
		AjaxResult result = new AjaxResult();
		try {
			int i = userService.editUser(user);
			if (i>0){
				result.setSuccess(true);
				result.setMessage("success");
			}else {
				result.setSuccess(false);
				result.setMessage("修改失败");
			}
		}catch (Exception e){
			result.setSuccess(false);
			result.setMessage("error");
		}
		return result;

	}

	//删除
	@RequestMapping("/delete")
	@ResponseBody
	public AjaxResult delete(Integer id){
		AjaxResult result = new AjaxResult();
		try {
			int i = userService.deleteUser(id);
			if (i>0){
				result.setSuccess(true);
				result.setMessage("success");
			}else {
				result.setSuccess(false);
				result.setMessage("删除失败");
			}
		}catch (Exception e){
			result.setSuccess(false);
			result.setMessage("error");
		}
		return result;

	}
	//批量删除
	@RequestMapping("/deletes")
	@ResponseBody
	public AjaxResult deletes(Integer[] id){
		AjaxResult result = new AjaxResult();
		try {
			int i = userService.deletes(id);
			if (i>0){
				result.setSuccess(true);
				result.setMessage("success");
			}else {
				result.setSuccess(false);
				result.setMessage("批量删除失败");
			}
		}catch (Exception e){
			result.setSuccess(false);
			result.setMessage("error");
		}
		return result;

	}

	//去角色分配页面
	@RequestMapping("toAssignRole")
	public String toAssignRole(Integer id,Model model){

    	//通过id查找的角色id
		List<Integer> roleIds =  userService.queryRoleIdByUserId(id);
		//查找所有的角色
		List<Role> roles = userService.queryAllRole();
		//定义一个未分配的角色列表
		List<Role> leftRoleList = new ArrayList<Role>(); //未分配角色
		//定义一个已经分配的角色列表
		List<Role> rightRoleList = new ArrayList<Role>(); //已分配角色
		for (Role role : roles){
			if (roleIds.contains(role.getId())){
				rightRoleList.add(role);
			}else {
				leftRoleList.add(role);
			}
		}
		model.addAttribute("userid",id);
		model.addAttribute("rightRoleList",rightRoleList);
		model.addAttribute("leftRoleList",leftRoleList);

    	return "user/assign_role";
	}

	//添加角色
	@ResponseBody
	@RequestMapping("/doAssignRole")
	public AjaxResult addrole(Data data, Integer userid){
    	AjaxResult ajaxResult = new AjaxResult();
    	try {
			userService.saveUserRoleRelationship(data,userid);
			ajaxResult.setSuccess(true);
			ajaxResult.setMessage("success");
		}catch (Exception e){
			ajaxResult.setSuccess(false);
			ajaxResult.setMessage("修改角色失败");
    		e.printStackTrace();
		}

    	return ajaxResult;
	}

	@ResponseBody
	@RequestMapping("/deleteAssignRole")
	public AjaxResult deleteRole(Data data,Integer userid){
		AjaxResult ajaxResult = new AjaxResult();
		try {
			userService.deleteUserRoleRelationship(data,userid);
			ajaxResult.setSuccess(true);
			ajaxResult.setMessage("success");
		}catch (Exception e){
			ajaxResult.setSuccess(false);
			ajaxResult.setMessage("删除角色失败");
			e.printStackTrace();
		}

		return ajaxResult;
	}

}
