package org.nobibi.startrace.account.persistence.dao;

import java.io.Serializable;

import org.nobibi.startrace.account.bean.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends CrudRepository<User, Serializable>{
	
	
	@Query(value="select * from user where (email=?1 or username=?1 or mobile=?1) and status=1")
	public User getUser(String key);
	
	
	@Query("select count(*) from User where email=?1")
	int countByEmail(String email);
	
	@Query("select count(*) from User where mobile=?1")
	int countByMobile(String mobile);
}
