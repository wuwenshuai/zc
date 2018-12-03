package com.atguigu.atcrowdfunding.manager.controller;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.atguigu.atcrowdfunding.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.atguigu.atcrowdfunding.bean.Advert;
import com.atguigu.atcrowdfunding.bean.User;
import com.atguigu.atcrowdfunding.manager.service.AdvertService;

@Controller
@RequestMapping("/advert")
public class AdvertController {

	@Autowired
	private AdvertService advertService;
	

	@RequestMapping("/index")
	public String index() {
		return "advert/index";
	}
	
	@RequestMapping("/add")
	public String add() {
		return "advert/add";
	}
	
	@RequestMapping("/edit")
	public String edit( Integer id, Model model ) {
		
		// 根据主键查询资质信息
		Advert advert = advertService.queryById(id);
		model.addAttribute("advert", advert);
		
		return "advert/edit";
	}
	
	@ResponseBody
	@RequestMapping("/batchDelete")
	public Object batchDelete( Data ds ) {
		AjaxResult result = new AjaxResult();
		
		try {
			int count = advertService.deleteAdverts(ds);
			if ( count == ds.getDatas().size() ) {
				result.setSuccess(true);
			} else {
				result.setSuccess(false);
			}
		} catch ( Exception e ) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/delete")
	public Object delete( Integer id ) {
		AjaxResult result = new AjaxResult();
		
		try {
			int count = advertService.deleteAdvert(id);
			if ( count == 1 ) {
				result.setSuccess(true);
			} else {
				result.setSuccess(false);
			}
		} catch ( Exception e ) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public Object update( Advert advert ) {
		AjaxResult result = new AjaxResult();
		
		try {
			int count = advertService.updateAdvert(advert);
			if ( count == 1 ) {
				result.setSuccess(true);
			} else {
				result.setSuccess(false);
			}
		} catch ( Exception e ) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		
		return result;
	}
	
	/**
	 * 新增资质数据
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/doAdd")
	public Object doAdd(HttpServletRequest request, Advert advert ,HttpSession session) {
		AjaxResult result = new AjaxResult();
		
		
		try {
			MultipartHttpServletRequest mreq = (MultipartHttpServletRequest)request;
			
			MultipartFile mfile = mreq.getFile("advpic");
			
			String name = mfile.getOriginalFilename();//java.jpg
			String extname = name.substring(name.lastIndexOf(".")); // .jpg
			
			String iconpath = UUID.randomUUID().toString()+extname; //232243343.jpg
			
			ServletContext servletContext = session.getServletContext();
			String realpath = servletContext.getRealPath("/pics");
			
			String path =realpath+ "\\adv\\"+iconpath;
			
			mfile.transferTo(new File(path));
			
			User user = (User)session.getAttribute(Const.LOGIN_USER);
			advert.setUserid(user.getId());
			advert.setStatus("1");
			advert.setIconpath(iconpath);
			
			int count = advertService.insertAdvert(advert);
			result.setSuccess(count==1);
		} catch ( Exception e ) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		
		return result;
	}
	
	
	
	//同步请求处理.
	/*@RequestMapping("/doAdd")
	public Object doAdd(HttpServletRequest request, Advert advert ,HttpSession session) {
	
		try {
			MultipartHttpServletRequest mreq = (MultipartHttpServletRequest)request;
			
			MultipartFile mfile = mreq.getFile("advpic");
			
			String name = mfile.getOriginalFilename();//java.jpg
			String extname = name.substring(name.lastIndexOf(".")); // .jpg
			
			String iconpath = UUID.randomUUID().toString()+extname; //  232243343.jpg
			
			ServletContext servletContext = session.getServletContext();
			String realpath = servletContext.getRealPath("/pic");
			
			String path =realpath+ "\\adv\\"+iconpath;
			
			mfile.transferTo(new File(path)); //文件上传.
			
			User user = (User)session.getAttribute(Const.LOGIN_USER);
			advert.setUserid(user.getId());
			advert.setStatus("1");
			advert.setIconpath(iconpath);
			
			int count = advertService.insertAdvert(advert);
			
		} catch ( Exception e ) {
			e.printStackTrace();
			
		}
		
		return "redirect:/advert/index.htm";
	}*/
	
	/**
	 * 分页查询资质数据
	 * @advert pageno
	 * @advert pagesize
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/pageQuery")
	public Object pageQuery(String pagetext, Integer pageno, Integer pagesize) {
		AjaxResult result = new AjaxResult();
		
		try {
			// 查询资质数据
			Map<String, Object> advertMap = new HashMap<String, Object>();
			advertMap.put("pageno", pageno);
			advertMap.put("pagesize", pagesize);
			if ( StringUtil.isNotEmpty(pagetext) ) {
				pagetext = pagetext.replaceAll("%", "\\\\%");
			}
			advertMap.put("pagetext", pagetext);
			
			// 分页查询
			Page<Advert> page = advertService.pageQuery(advertMap);
			result.setPage(page);
			result.setSuccess(true);
		} catch ( Exception e ) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		
		return result;
	}
}
