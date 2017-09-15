package org.nobibi.startrace.account.persistence.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.nobibi.startrace.account.bean.User;

@Mapper
public interface UserMapper {
	
	@Select(value="select * from user where (email=#{key} or username=#{key} or mobile=#{key}) and status=1")
	public User getUser(String key);
}
