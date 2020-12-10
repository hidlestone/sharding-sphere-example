package com.payn.service;

import com.payn.entity.User;

import java.util.List;

public interface UserService {

	/**
	 * 获取所有用户信息
	 */
	List<User> list();

	/**
	 * 单个 保存用户信息
	 *
	 * @param user
	 */
	String saveOne(User user);
}
