package com.payn.mapper;

import com.payn.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author payn
 * @date 2020/12/9 14:43 
 */
@Mapper
public interface UserMapper {

	/**
	 * 批量插入
	 *
	 * @param list 插入集合
	 * @return 插入数量
	 */
	int insertForeach(List<User> list);

	/**
	 * 获取所有用户
	 */
	List<User> selectAll();
}
