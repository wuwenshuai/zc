package com.atguigu.atcrowdfunding.manager.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.atguigu.atcrowdfunding.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.atcrowdfunding.bean.User;
import com.atguigu.atcrowdfunding.exception.LoginFailException;
import com.atguigu.atcrowdfunding.manager.dao.UserMapper;
import com.atguigu.atcrowdfunding.manager.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper ;

    @Override
    public int saveUser(User user) {
       return   userMapper.insert(user);
    }

    @Override
	public User queryUserlogin(Map<String, Object> paramMap) {
		
		User user = userMapper.queryUserlogin(paramMap);
		
		if(user==null){
			throw new LoginFailException("用户账号或密码不正确!");
		}
		
		return user;
	}

	@Override
	public Page queryPage(Map<String,Object> paramMap) {

		Page page = new Page((Integer)paramMap.get("pageno"),(Integer)paramMap.get("pagesize"));
		Integer startIndex = page.getStartIndex();
		paramMap.put("startIndex",startIndex);

		List<User> datas = userMapper.queryList(paramMap);

		page.setDatas(datas);

		Integer totalsize = userMapper.selectUserCount(paramMap);

		page.setTotalsize(totalsize);

		return page;
	}

    @Override
    public int insertUser(User user) {
        //封装对象
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(new Date());
        user.setUserpswd("123");
        user.setCreatetime(date);
        //插入数据
        int i = userMapper.insert(user);
        return i;
    }

    @Override
    public User selectUserByid(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public int editUser(User user) {
        int i = userMapper.updateByPrimaryKey(user);
        return i;
    }


}
