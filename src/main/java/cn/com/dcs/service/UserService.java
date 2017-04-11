package cn.com.dcs.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.dcs.dao.UserMapper;
import cn.com.dcs.model.User;

@Service
public class UserService {
	@Resource
	private UserMapper userMapper;

	/**
	 * 根据用户和密码查询用户
	 * 
	 * @param name
	 * @param pass
	 * @return
	 */
	public User findByNameAndPass(String name, String pass) {
		return userMapper.findByNameAndPassword(name, pass);
	}
}
