package com.flf.service.impl;

import com.flf.entity.User;
import com.flf.mapper.UserMapper;
import com.flf.service.UserService;
import com.flf.util.MD5;
import com.flf.util.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserMapper userMapper;

	@Override
	public User getUserById(Integer userId) {
		return userMapper.getUserById(userId);
	}

	@Override
	public boolean insertUser(User user) {
		int count = userMapper.getCountByName(user.getLoginname());
		if (count > 0) {
			return false;
		} else {
			user.setPassword(MD5.MD5Encode(user.getPassword()));
			userMapper.insertUser(user);
			return true;
		}

	}

	@Override
	public List<User> listPageUser(User user) {
		return userMapper.listPageUser(user);
	}

	@Override
	public void updateUser(User user) {
		userMapper.updateUser(user);
	}

	@Override
	public void updateUserBaseInfo(User user) {
		if (Tools.isNotEmpty(user.getPassword())) {
			user.setPassword(MD5.MD5Encode(user.getPassword().trim()));
		}
		userMapper.updateUserBaseInfo(user);
	}

	@Override
	public void updateUserRights(User user) {
		userMapper.updateUserRights(user);
	}

	@Override
	public User getUserByNameAndPwd(String loginname, String password) {
		User user = new User();
		user.setLoginname(loginname);
		user.setPassword(password);
		return userMapper.getUserInfo(user);
	}

	@Override
	public void deleteUser(int userId) {
		userMapper.deleteUser(userId);
	}

	@Override
	public User getUserAndRoleById(Integer userId) {
		return userMapper.getUserAndRoleById(userId);
	}

	@Override
	public void updateLastLogin(User user) {
		userMapper.updateLastLogin(user);
	}

	@Override
	public List<User> listAllUser() {
		return userMapper.listAllUser();
	}

}
