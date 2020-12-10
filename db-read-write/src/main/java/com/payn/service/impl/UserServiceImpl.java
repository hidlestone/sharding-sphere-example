package com.payn.service.impl;

import com.payn.entity.User;
import com.payn.mapper.UserMapper;
import com.payn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author: payn
 * @date: 2020/12/9 14:45
 */
@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserMapper userMapper;

	@Override
	public List<User> list() {
		List<User> users = userMapper.selectAll();
		return users;
	}

	@Override
	public String saveOne(User user) {
		user.setCreateTime(new Date());
		user.setUpdateTime(new Date());
		user.setStatus(1);
		userMapper.insert(user);
		return "保存成功";
	}
}
