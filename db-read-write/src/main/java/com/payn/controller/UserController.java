package com.payn.controller;

import com.payn.entity.User;
import com.payn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: payn
 * @date: 2020/12/9 14:45
 */
@RestController
public class UserController {

	@Autowired
	private UserService userService;

	/**
	 * @Description: 保存用户
	 */
	@GetMapping("save-user")
	public Object saveUser() {
		return userService.saveOne(new User("小小", "女", 3));
	}

	/**
	 * @Description: 获取用户列表
	 */
	@GetMapping("list-user")
	public Object listUser() {
		return userService.list();
	}
}
