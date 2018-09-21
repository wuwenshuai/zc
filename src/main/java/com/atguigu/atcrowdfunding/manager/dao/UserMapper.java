package com.atguigu.atcrowdfunding.manager.dao;

import com.atguigu.atcrowdfunding.bean.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    User selectByPrimaryKey(Integer id);

    List<User> selectAll();

    int updateByPrimaryKey(User record);

	User queryUserlogin(Map<String, Object> paramMap);

    //List<User> queryList(@Param("startIndex") Integer startIndex, @Param("pagesize")  Integer pagesize);
    List<User> queryList(Map<String,Object> paramMap);
	//查询总记录数
    int selectUserCount(Map<String, Object> paramMap);
}