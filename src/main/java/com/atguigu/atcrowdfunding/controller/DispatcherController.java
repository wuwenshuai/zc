package com.atguigu.atcrowdfunding.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.atguigu.atcrowdfunding.bean.Permission;
import com.atguigu.atcrowdfunding.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.atguigu.atcrowdfunding.bean.User;
import com.atguigu.atcrowdfunding.manager.service.UserService;
import com.atguigu.atcrowdfunding.util.Const;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DispatcherController {

	@Autowired
	private UserService userService ;
	
	
	@RequestMapping("/index")
	public String index(){		
		return "index";
	}
	
	@RequestMapping("/login")
	public String login(){		
		return "login";
	}

	@RequestMapping("/main")
	public String main(HttpSession session){

		//加载当前登录用户的所拥有的许可权限.

		User user = (User)session.getAttribute(Const.LOGIN_USER);

		List<Permission> myPermissions = userService.queryPermissionByUserid(user.getId());

		Permission permissionRoot = null;

		Map<Integer,Permission> map = new HashMap<Integer,Permission>();

		for (Permission innerpermission : myPermissions) {
			map.put(innerpermission.getId(), innerpermission);
		}


		for (Permission permission : myPermissions) {
			//通过子查找父
			//子菜单
			Permission child = permission ; //假设为子菜单
			if(child.getPid() == null ){
				permissionRoot = permission;
			}else{
				if(child.getPid()!=null){
					//父节点
					Permission parent = map.get(child.getPid());
					parent.getChildren().add(child);
				}

			}
		}
		System.out.println(permissionRoot);
		session.setAttribute("permissionRoot", permissionRoot);

		return "main";
	}
	
	@RequestMapping("/doLogin")
	@ResponseBody
	public AjaxResult doLogin(String loginacct, String userpswd, String type, HttpSession session){
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("loginacct", loginacct);
		paramMap.put("userpswd", userpswd);
		paramMap.put("type", type);
		AjaxResult result = new AjaxResult();

		try {
			User user = userService.queryUserlogin(paramMap);
			session.setAttribute(Const.LOGIN_USER, user);
			result.setSuccess(true);
			result.setMessage("success");
		}catch (Exception e){
			e.printStackTrace();
			result.setSuccess(false);
			result.setMessage("用户名或者密码错误");
		}
		return result;

	}


	//退出系统
	@RequestMapping("/logout")
	public String logout(HttpSession session){

		//清除session
		session.invalidate();
		return "redirect:index.htm";
	}
	
}
