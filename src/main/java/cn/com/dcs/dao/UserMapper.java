package cn.com.dcs.dao;

import org.apache.ibatis.annotations.Param;

import cn.com.dcs.model.User;

/**
 * 用户Dao
 * 
 * @author shishb
 * @version 1.0
 */
public interface UserMapper {

	/**
	 * 根据用户和密码查询用户
	 * 
	 * @param name
	 * @param pass
	 * @return
	 */
	public User findByNameAndPassword(@Param("name") String name, @Param("pass") String pass);
}
